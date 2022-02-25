package com.luka.berry.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.luka.berry.R
import com.luka.berry.adapter.UnsplashPhotoAdapter.*
import com.luka.berry.databinding.ItemPhotoBinding
import com.luka.berry.model.UnsplashPhoto

class UnsplashPhotoAdapter : PagingDataAdapter<UnsplashPhoto, PhotoHolder>(PHOTO_COMPARATOR) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoHolder {
        val binding = ItemPhotoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PhotoHolder(binding)
    }

    override fun onBindViewHolder(holder: PhotoHolder, position: Int) {
        var item = getItem(position)

        if(item != null)
        {
            holder.bind(item)
        }

    }


    class PhotoHolder(private val itemPhotoBinding: ItemPhotoBinding) :
        RecyclerView.ViewHolder(itemPhotoBinding.root) {

        fun bind(photo: UnsplashPhoto) {
            itemPhotoBinding.apply {
                Glide.with(itemView).load(photo.urls.regular).centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade()).error(R.drawable.ic_error_foreground)
                    .into(image)

                name.text = photo.user.username
            }


        }


    }

    companion object {
        private val PHOTO_COMPARATOR = object : DiffUtil.ItemCallback<UnsplashPhoto>() {
            override fun areItemsTheSame(oldItem: UnsplashPhoto, newItem: UnsplashPhoto): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: UnsplashPhoto,
                newItem: UnsplashPhoto
            ): Boolean {
                return oldItem == newItem
            }


        }
    }
}
