package com.aylax.permify.ui.fragment

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.aylax.library.api.AppManager
import com.aylax.library.util.Mode
import com.aylax.permify.databinding.FragmentPermissionBinding
import com.aylax.permify.ui.adapter.PermissionAdapter
import com.aylax.permify.utils.Util
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class PermissionFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentPermissionBinding
    private lateinit var viewModel: PermissionViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentPermissionBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[PermissionViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val application =
            AppManager(requireContext()).getApplicationInfo(arguments?.getString("pkg", "")!!)

        binding.apply {
            pkg.text = application.pkg_name
            name.text = application.app_name
            icon.setImageDrawable(application.app_icon)
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
            viewModel.getPermissions(requireArguments(), Mode.AUTO)
                .observe(this@PermissionFragment) {
                    recyclerView.adapter = PermissionAdapter(it)
                }
            open.setOnClickListener {
                MaterialAlertDialogBuilder(requireContext()).apply {
                    setTitle("Action")
                    setMessage("Are you sure you want to open this app?")
                    setPositiveButton("Sure") { _, _ ->
                        Util.launchPackage(application.pkg_name, requireActivity())
                    }
                    setNegativeButton("Cancel") { _, _ ->
                        this.create().dismiss()
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
                    findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as FrameLayout
                bottomSheet.setBackgroundResource(android.R.color.transparent)
            }
        }
    }
}