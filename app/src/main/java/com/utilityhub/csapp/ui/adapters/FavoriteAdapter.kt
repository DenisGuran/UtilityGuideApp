package com.utilityhub.csapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.utilityhub.csapp.common.Utils
import com.utilityhub.csapp.databinding.LayoutFavoriteBinding
import com.utilityhub.csapp.domain.model.Favorite

class FavoriteAdapter(
    private val onFavoriteClickListener: OnFavoriteClickListener
) : ListAdapter<Favorite, FavoriteAdapter.FavoriteViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavoriteAdapter.FavoriteViewHolder {
        val binding =
            LayoutFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val utility = currentList[position]
        holder.bind(utility)
    }

    override fun getItemCount() = currentList.size

    companion object DiffCallback : DiffUtil.ItemCallback<Favorite>() {
        override fun areItemsTheSame(oldItem: Favorite, newItem: Favorite) =
            oldItem.landing == newItem.landing &&
                    oldItem.throwing == newItem.throwing

        override fun areContentsTheSame(oldItem: Favorite, newItem: Favorite) =
            oldItem == newItem

    }

    inner class FavoriteViewHolder(private val binding: LayoutFavoriteBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.posCard.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val selectedFavorite = currentList[position]
                    onFavoriteClickListener.onFavoriteClick(selectedFavorite)
                }
            }
        }

        fun bind(favorite: Favorite) {
            binding.apply {
                throwSpot.text = favorite.throwing
                landSpot.text = favorite.landing
                ivBackground.load(Utils.getMapBackgroundBlur(favorite.map!!, itemView.context))
                utility.load(Utils.getUtilityPin(favorite.utilityType!!))
            }
        }
    }

    interface OnFavoriteClickListener {
        fun onFavoriteClick(favorite: Favorite)
    }

}