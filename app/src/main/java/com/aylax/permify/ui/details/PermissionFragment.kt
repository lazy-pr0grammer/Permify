package com.aylax.permify.ui.details

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.aylax.library.api.AppManager
import com.aylax.library.model.Permission
import com.aylax.permify.databinding.FragmentPermissionBinding
import com.aylax.permify.utils.Util
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder


class PermissionFragment : BottomSheetDialogFragment(), PermissionAdapter.OnClickListener {
    private lateinit var binding: FragmentPermissionBinding
    private lateinit var viewModel: PermissionViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentPermissionBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[PermissionViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val app = AppManager(requireContext()).getApplicationInfo(arguments?.getString("pkg", "")!!)

        binding.apply {
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
            icon.setImageDrawable(app.appIcon)
            name.text = app.appName
            pkg.text = app.pkgName
            viewModel.getPermissions(requireArguments(), 1).observe(this@PermissionFragment) {
                recyclerView.adapter =
                    it?.let { it1 -> PermissionAdapter(it1, this@PermissionFragment) }
            }

            open.setOnClickListener {
                MaterialAlertDialogBuilder(requireContext()).apply {
                    setTitle("Action")
                    setMessage("Are you sure you want to open this app?")
                    setPositiveButton("Sure") { _, _ ->
                        Util.launchPackage(app.pkgName, requireContext())
                    }
                    setNegativeButton("Cancel") { _, _ ->
                        dismiss()
                    }
                }.create().show()
            }
        }

    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        return super.onCreateDialog(savedInstanceState).apply {
            window?.setDimAmount(0.4f)
            setOnShowListener {
                val bottomSheet =
                    findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
                bottomSheet.setBackgroundResource(android.R.color.transparent)
            }
        }
    }

    override fun onItemClicked(permission: Permission) {
        MaterialAlertDialogBuilder(requireContext()).apply {
            setTitle("Action")
            setMessage("Are you sure you want to view the app details?")
            setPositiveButton("Sure") { _, _ ->
                Util.openPackageSettings(permission.pkgName, requireContext())
            }
            setNegativeButton("Cancel") { _, _ ->
                dismiss()
            }
        }.create().show()
    }
}