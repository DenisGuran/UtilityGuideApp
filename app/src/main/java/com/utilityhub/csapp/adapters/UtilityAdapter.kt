package com.utilityhub.csapp.adapters


import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.DocumentSnapshot
import com.utilityhub.csapp.databinding.CardLayoutBinding
import com.utilityhub.csapp.models.Data
import com.utilityhub.csapp.utils.GlideLoader

class UtilityAdapter(
    private val onClickListener: OnClickListener, options: FirestoreRecyclerOptions<Data>
) : FirestoreRecyclerAdapter<Data, UtilityAdapter.UtilityHolder>(options) {

    interface OnClickListener {
        fun onItemClick(documentSnapshot: DocumentSnapshot, position: Int)
    }

    inner class UtilityHolder(val binding: CardLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UtilityAdapter.UtilityHolder {
        val binding = CardLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UtilityHolder(binding)
    }

    override fun onBindViewHolder(holder: UtilityHolder, position: Int, model: Data) {
        holder.binding.apply {
            posName.text = model.name
            GlideLoader(holder.itemView.context).loadImageView(Uri.parse(model.img), posImage)
            holder.itemView.setOnClickListener {
                onClickListener.onItemClick(snapshots.getSnapshot(position), position)
            }
        }
    }

}


