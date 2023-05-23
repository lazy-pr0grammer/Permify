package com.aylax.permify.ui.fragment

import android.app.Dialog
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.aylax.library.api.AppManager
import com.aylax.library.model.Permission
import com.aylax.permify.databinding.FragmentPermissionBinding
import com.aylax.permify.ui.adapter.PermissionAdapter
import com.aylax.permify.utils.Util
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.io.Serializable

class PermissionFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentPermissionBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentPermissionBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return binding.root
    }

    @Suppress("UNCHECKED_CAST", "DEPRECATION")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        val permissions: List<Permission> =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                arguments?.getSerializable("data", Serializable::class.java) as List<Permission>
            } else {
                arguments?.getSerializable("data") as List<Permission>
            }
        val application =
            AppManager(requireContext()).getApplicationInfo(arguments?.getString("pkg", "")!!)
        binding.textView.text = application.pkg_name
        binding.textView2.text = application.app_name
        binding.icon.setImageDrawable(application.app_icon)
        binding.recyclerView.adapter = PermissionAdapter(permissions)

        binding.open.setOnClickListener {
            MaterialAlertDialogBuilder(requireContext()).apply {
                setTitle("Action")
                setMessage("Are you sure you want to open this app?")
                setPositiveButton("Sure") { _, _ ->
                    Util.launchPackage(binding.textView.text.toString(), requireActivity())
                }
                setNegativeButton("Cancel") { _, _ ->
                    dismiss()
                }
            }.create().show()
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