package com.aylax.permify.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.aylax.permify.data.model.Application
import com.aylax.permify.databinding.ItemApplicationBinding

class ApplicationAdapter(private val application: List<Application>) :
    RecyclerView.Adapter<ApplicationAdapter.AppViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppViewHolder {
        val binding = ItemApplicationBinding.inflate(LayoutInflater.from(parent.context))
        return AppViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return application.size
    }

    override fun onBindViewHolder(holder: AppViewHolder, position: Int) {
        holder.bind(application[position])
    }

    class AppViewHolder(private val binding: ItemApplicationBinding) : ViewHolder(binding.root) {
        fun bind(application: Application) {
            binding.root.layoutParams = RecyclerView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            binding.apply {
                title.text = application.app_name
                description.text = application.pkg_name
                imageView.setImageDrawable(application.app_icon)
            }
        }
    }
}