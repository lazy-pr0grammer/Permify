package com.aylax.permify.ui.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.aylax.library.model.Application
import com.aylax.permify.R
import com.aylax.permify.databinding.ActivityMainBinding
import com.aylax.permify.ui.adapter.ApplicationAdapter
import com.aylax.permify.ui.fragment.PermissionFragment
import com.aylax.permify.utils.Util
import com.google.android.material.elevation.SurfaceColors

class MainActivity : AppCompatActivity(), ApplicationAdapter.OnClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        setContentView(binding.root)
        designs()
    }

    private fun designs() {
        window.statusBarColor = SurfaceColors.SURFACE_2.getColor(this)
        window.navigationBarColor = SurfaceColors.SURFACE_2.getColor(this)
        binding.apply {
            setSupportActionBar(toolbar)
            Util.setToolbarFont(toolbar)
            recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
            viewModel.loadApplications(system = false).observe(this@MainActivity) {
                recyclerView.adapter = ApplicationAdapter(it, this@MainActivity)
                recyclerView.visibility = View.VISIBLE
                indicator.visibility = View.GONE
            }
            toolbar.setBackgroundColor(SurfaceColors.SURFACE_2.getColor(this@MainActivity))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onItemClicked(app: Application) {
        PermissionFragment().apply {
            val bundle = Bundle()
            bundle.putString("pkg", app.pkg_name)
            bundle.putSerializable("data", ArrayList(app.permissions))
            arguments = bundle
            show(supportFragmentManager, this.javaClass.name)
        }
    }
}