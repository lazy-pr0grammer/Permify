package com.aylax.permify.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.aylax.library.model.Application
import com.aylax.permify.databinding.ItemApplicationBinding
import com.google.android.material.elevation.SurfaceColors

class ApplicationAdapter(
    private val application: List<Application>,
    private val listener: OnClickListener
) :
    RecyclerView.Adapter<ApplicationAdapter.AppViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppViewHolder {
        val binding = ItemApplicationBinding.inflate(LayoutInflater.from(parent.context))
        return AppViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return application.size
    }

    override fun onBindViewHolder(holder: AppViewHolder, position: Int) {
        holder.bind(application[position], listener)
    }

    class AppViewHolder(private val binding: ItemApplicationBinding) : ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(application: Application, listener: OnClickListener) {
            binding.root.layoutParams = RecyclerView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            binding.apply {
                title.text = application.app_name
                imageView.setImageDrawable(application.app_icon)
                description.text = "Granted " +

                        application.permissions.count {
                            it.is_granted == true
                        } + ", Denied " +
                        application.permissions.count { it.is_granted == false }

                background.setCardBackgroundColor(SurfaceColors.SURFACE_1.getColor(title.context))
                background.setOnClickListener {
                    listener.onItemClicked(application)
                }
            }
        }
    }

    interface OnClickListener {
        fun onItemClicked(app: Application)
    }
}