package com.utilityhub.csapp.ui.utility.tutorial

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.utilityhub.csapp.R
import com.utilityhub.csapp.common.Constants
import com.utilityhub.csapp.common.Utils
import com.utilityhub.csapp.databinding.FragmentTutorialBinding
import com.utilityhub.csapp.domain.model.Favorite
import com.utilityhub.csapp.domain.model.Response
import com.utilityhub.csapp.domain.model.Tutorial
import com.utilityhub.csapp.domain.model.UtilityThrow
import com.utilityhub.csapp.ui.adapters.TutorialAdapter
import com.utilityhub.csapp.ui.core.BaseFragment
import com.utilityhub.csapp.ui.home.maps.MapsFragmentDirections
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

@AndroidEntryPoint
class TutorialFragment : BaseFragment<FragmentTutorialBinding>(FragmentTutorialBinding::inflate) {

    private val args: TutorialFragmentArgs by navArgs()
    private val viewModel by viewModels<TutorialViewModel>()
    private var adapter = TutorialAdapter()

    @Inject
    lateinit var applicationContext: Context

    @Inject
    lateinit var cachePath: File

    private lateinit var shareResult: ActivityResultLauncher<Intent>
    private lateinit var map: String
    private lateinit var landingSpot: String
    private lateinit var throwingSpot: UtilityThrow
    private lateinit var tutorial: ArrayList<Tutorial>
    private lateinit var utilityType: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initShareResultLauncher()

        getNavArgs()
        setBackground()
        setAdapter()
        setTutorialDetails()

        binding.apply {
            btnMaps.setOnClickListener {
                navigateToMaps()
            }
            btnShare.setOnClickListener {
                lifecycleScope.launch(Dispatchers.Default) {
                    shareTutorial()
                }
            }
            if (!viewModel.isLoggedIn)
                btnFavorites.setOnClickListener {
                    Toast.makeText(
                        requireContext(),
                        "You must be logged in to add to your favorites",
                        Toast.LENGTH_LONG
                    ).show()
                }
            else
                handleFavoritesButton()
        }
    }

    private fun handleFavoritesButton() {
        val currentTutorial = Favorite(
            map = map,
            utilityType = utilityType,
            landing = landingSpot,
            throwing = throwingSpot.name!!
        )
        viewModel.favorites.observe(viewLifecycleOwner) { response ->
            if (response is Response.Success) {
                val favoritesList = response.data!!
                if (favoritesList.contains(currentTutorial))
                    removeFavoriteSettings(currentTutorial)
                else
                    addFavoriteSettings(currentTutorial)
            }
        }
    }

    private fun removeFavoriteSettings(currentTutorial: Favorite) {
        binding.btnFavorites.apply {
            setIconTintResource(R.color.red)
            setText(R.string.remove_from_favorites)
            setOnClickListener {
                removeFromFavorites(currentTutorial)
            }
        }
    }

    private fun addFavoriteSettings(currentTutorial: Favorite) {
        binding.btnFavorites.apply {
            setIconTintResource(R.color.icon_color)
            setText(R.string.add_to_favorites)
            setOnClickListener {
                addToFavorites(currentTutorial)
            }
        }
    }

    private fun getNavArgs() {
        landingSpot = args.landingSpot
        throwingSpot = args.throwingSpot
        tutorial = throwingSpot.tutorial as ArrayList<Tutorial>
        map = args.map
        utilityType = args.utilityType
    }

    private fun setTutorialDetails() {
        binding.tutorialTitle.text = throwingSpot.name.plus(" to ").plus(landingSpot)
        binding.tutorialTickrate.text = throwingSpot.tags?.joinToString("/")
    }

    private fun setAdapter() {
        adapter.submitList(tutorial)
        binding.recyclerView.adapter = adapter
    }

    private fun setBackground() {
        val backgroundBlur = Utils.getMapBackgroundBlurDrawable(map, requireContext())
        binding.tutorialLayout.background = backgroundBlur
    }

    private fun addToFavorites(
        favorite: Favorite
    ) {
        viewModel.addTutorialToFavorites(favorite)
            .observe(viewLifecycleOwner) { response ->
                when (response) {
                    is Response.Success ->
                        Toast.makeText(
                            requireContext(),
                            "Successfully added to Favorites",
                            Toast.LENGTH_SHORT
                        ).show()
                    is Response.Failure -> Log.w("addToFavorites", response.errorMessage)
                }
            }
    }

    private fun removeFromFavorites(
        favorite: Favorite
    ) {
        viewModel.removeTutorialFromFavorites(favorite)
            .observe(viewLifecycleOwner) { response ->
                when (response) {
                    is Response.Success ->
                        Toast.makeText(
                            requireContext(),
                            "Successfully removed from Favorites",
                            Toast.LENGTH_SHORT
                        ).show()
                    is Response.Failure -> Log.w("removeFromFavorites", response.errorMessage)
                }
            }
    }

    private suspend fun getBitmaps(urls: ArrayList<String>): ArrayList<Bitmap> {
        val imageDrawables = arrayListOf<Deferred<Drawable>>()
        val imageBitmaps = arrayListOf<Deferred<Bitmap>>()

        coroutineScope {
            urls.forEach { url ->
                val request = ImageRequest.Builder(requireContext())
                    .data(url)
                    .build()
                val result = async(Dispatchers.IO) {
                    (ImageLoader(requireContext()).execute(request) as SuccessResult).drawable
                }
                imageDrawables.add(result)
            }

            imageDrawables.awaitAll().forEach { drawable ->
                val bitmap = async { (drawable as BitmapDrawable).bitmap }
                imageBitmaps.add(bitmap)
            }
        }

        return imageBitmaps.awaitAll() as ArrayList<Bitmap>
    }

    private suspend fun getSharedImagesUri(
        imageBitmaps: ArrayList<Bitmap>,
        fileNames: ArrayList<String>
    ): ArrayList<Uri> {
        if (!cachePath.isDirectory) {
            cachePath.mkdirs()
        }

        val imageFiles = arrayListOf<Deferred<File>>()
        val imageUris = arrayListOf<Deferred<Uri>>()

        coroutineScope {
            imageBitmaps.zip(fileNames).forEach { pair ->
                val fileName = pair.second
                val imageFile = async(Dispatchers.IO) {
                    File(cachePath, "$fileName.${Constants.IMAGE_TYPE}").also { file ->
                        FileOutputStream(file).use { fileOutputStream ->
                            val imageBitmap = pair.first
                            imageBitmap.compress(
                                Bitmap.CompressFormat.JPEG,
                                100,
                                fileOutputStream
                            )
                            fileOutputStream.close()
                        }
                    }
                }
                imageFiles.add(imageFile)
            }

            imageFiles.awaitAll().forEach { file ->
                val uri = async(Dispatchers.IO) {
                    FileProvider.getUriForFile(
                        requireContext(),
                        applicationContext.packageName + ".provider",
                        file
                    )
                }
                imageUris.add(uri)
            }
        }
        return imageUris.awaitAll() as ArrayList<Uri>
    }

    private suspend fun shareTutorial() {
        val tutorialDetails = arrayListOf<String>()
        val tutorialUrls = arrayListOf<String>()

        tutorial.forEach { step ->
            step.img?.let { tutorialUrls.add(it) }
            tutorialDetails.add(step.details.toString().trim())
        }

        val sharedSubject = "${throwingSpot.name} to $landingSpot"
        val sharedText = sharedSubject + "\n" +
                tutorialDetails.joinToString(", ") + ".\n" +
                Constants.SHARE_TUTORIAL_TEXT

        initShareIntent(
            sharedImageUris = getSharedImagesUri(getBitmaps(tutorialUrls), tutorialDetails),
            sharedText = sharedText,
            sharedSubject = sharedSubject
        )
    }

    @SuppressLint("QueryPermissionsNeeded")
    private fun initShareIntent(
        sharedImageUris: ArrayList<Uri>,
        sharedText: String,
        sharedSubject: String
    ) {
        val shareIntent = Intent.createChooser(Intent().apply {
            action = Intent.ACTION_SEND_MULTIPLE
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            putExtra(Intent.EXTRA_SUBJECT, sharedSubject)
            putExtra(Intent.EXTRA_TEXT, sharedText)
            putParcelableArrayListExtra(Intent.EXTRA_STREAM, sharedImageUris)
            type = "image/${Constants.IMAGE_TYPE}"
        }, null)

        val resInfoList: List<ResolveInfo> =
            applicationContext.packageManager.queryIntentActivities(
                shareIntent,
                PackageManager.MATCH_DEFAULT_ONLY
            )
        resInfoList.forEach { resolveInfo ->
            val packageName: String = resolveInfo.activityInfo.packageName
            sharedImageUris.forEach { sharedImageUri ->
                applicationContext.grantUriPermission(
                    packageName,
                    sharedImageUri,
                    Intent.FLAG_GRANT_WRITE_URI_PERMISSION or Intent.FLAG_GRANT_READ_URI_PERMISSION
                )
            }
        }

        shareResult.launch(shareIntent)
    }

    private fun initShareResultLauncher() {
        shareResult =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == 0)
                    cachePath.deleteRecursively()
            }
    }

    private fun navigateToMaps() {
        findNavController().setGraph(R.navigation.nav_graph)
        val navHome = MapsFragmentDirections.actionGlobalHome()
        findNavController().navigate(navHome)
    }

}