package com.aylax.permify.ui.main

import android.content.DialogInterface
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.aylax.library.model.Application
import com.aylax.permify.R
import com.aylax.permify.databinding.ActivityMainBinding
import com.aylax.permify.ui.details.PermissionFragment
import com.aylax.permify.utils.Util
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.elevation.SurfaceColors

class MainActivity : AppCompatActivity(), MainAdapter.OnClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        setContentView(binding.root)
        designs()
    }

    private fun designs() {
        window.apply {
            statusBarColor = SurfaceColors.SURFACE_2.getColor(this@MainActivity)
        }
        binding.apply {
            setSupportActionBar(toolbar)
            Util.setToolbarFont(toolbar)
            recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
            viewModel.loadApplications(applicationContext, false).observe(this@MainActivity) {
                recyclerView.adapter = MainAdapter(it, this@MainActivity)
                recyclerView.visibility = View.VISIBLE
                indicator.visibility = View.GONE
            }
            toolbar.setBackgroundColor(SurfaceColors.SURFACE_2.getColor(this@MainActivity))
        }
    }

    private fun refreshWithFilter(): Boolean {
        val options = arrayOf("Installed applications (Default)", "System applications")

        MaterialAlertDialogBuilder(this).apply {
            setTitle("Filter")
            setItems(options) { _: DialogInterface, i: Int ->
                when (i) {
                    0 -> binding.apply {
                        recyclerView.visibility = View.GONE
                        indicator.visibility = View.VISIBLE
                        viewModel.loadApplications(context, false).observe(this@MainActivity) {
                            recyclerView.adapter = MainAdapter(it, this@MainActivity)
                            recyclerView.visibility = View.VISIBLE
                            indicator.visibility = View.GONE
                        }
                    }

                    1 -> binding.apply {
                        recyclerView.visibility = View.GONE
                        indicator.visibility = View.VISIBLE
                        viewModel.loadApplications(context, true).observe(this@MainActivity) {
                            recyclerView.adapter = MainAdapter(it, this@MainActivity)
                            recyclerView.visibility = View.VISIBLE
                            indicator.visibility = View.GONE
                        }
                    }
                }
            }
        }.create().show()
        return true
    }

    private fun open(url: String): Boolean {
        Util.launchURL(url, this)
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_os -> open(Util.OPEN_SOURCE_URL)
            R.id.action_policy -> open(Util.POLICY_URL)
            R.id.action_filter -> refreshWithFilter()

            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onItemClicked(app: Application) {
        PermissionFragment().apply {
            val bundle = Bundle()
            bundle.putString("pkg", app.pkgName)
            bundle.putSerializable("data", ArrayList(app.permissions))
            arguments = bundle
            show(supportFragmentManager, this.javaClass.name)
        }
    }
}