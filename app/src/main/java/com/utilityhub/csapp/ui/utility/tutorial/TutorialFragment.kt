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
import com.utilityhub.csapp.core.Constants
import com.utilityhub.csapp.databinding.FragmentTutorialBinding
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
    lateinit var cachePath : File

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
        setAdapter()
        setTutorialName()

        binding.apply {
            btnMaps.setOnClickListener {
                navigateToMaps()
            }
            btnFavorites.setOnClickListener {
                addToFavorites(
                    map = map,
                    utilityType = utilityType,
                    landingSpot = landingSpot,
                    throwingSpot = throwingSpot.name!!
                )
            }
            btnShare.setOnClickListener {
                lifecycleScope.launch(Dispatchers.Default) {
                    shareTutorial()
                }
            }
        }
    }

    private fun getNavArgs() {
        landingSpot = args.landingSpot
        throwingSpot = args.throwingSpot
        tutorial = throwingSpot.tutorial as ArrayList<Tutorial>
        adapter.submitList(tutorial)
        map = args.map
        utilityType = args.utilityType
    }

    private fun setTutorialName() {
        binding.textview1.text = throwingSpot.name.plus(" to ").plus(landingSpot)
    }

    private fun setAdapter() {
        binding.recyclerView.adapter = adapter
    }

    private fun setLayoutBackground() {
        //TODO : switch case with map name -> set background accordingly
    }

    private fun addToFavorites(
        map: String,
        utilityType: String,
        landingSpot: String,
        throwingSpot: String
    ) {
        viewModel.addTutorialToFavorites(map, utilityType, landingSpot, throwingSpot)
            .observe(viewLifecycleOwner) { response ->
                when (response) {
                    is Response.Success -> Log.i("addToFavorites", "SUCCESS")
                    is Response.Failure -> Log.w("addToFavorites", response.errorMessage)
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

    private fun initShareResultLauncher(){
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