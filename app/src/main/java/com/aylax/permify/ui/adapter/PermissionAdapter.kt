package com.aylax.permify.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aylax.library.model.Permission
import com.aylax.permify.databinding.ItemPermissionBinding
import com.google.android.material.elevation.SurfaceColors

class PermissionAdapter(private val permission: List<Permission>) :
    RecyclerView.Adapter<PermissionAdapter.ViewHolder>() {
    class ViewHolder(private val binding: ItemPermissionBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(permission: Permission) {
            binding.root.layoutParams = RecyclerView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
            )
            binding.apply {
                title.text = permission.name
                description.text = if (permission.is_granted == true) {
                    "Granted"
                } else {
                    "Denied"
                }
                background.setCardBackgroundColor(SurfaceColors.SURFACE_2.getColor(title.context))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemPermissionBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return permission.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(
            permission[position]
        )
    }
}