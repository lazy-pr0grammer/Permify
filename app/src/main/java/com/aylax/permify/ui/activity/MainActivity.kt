package com.aylax.permify.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.aylax.permify.R
import com.aylax.permify.data.model.Application
import com.aylax.permify.databinding.ActivityMainBinding
import com.aylax.permify.ui.adapter.ApplicationAdapter
import com.aylax.permify.utils.Util

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val packageManager = packageManager
        val apps = mutableListOf<Application>()
        val intent = Intent(Intent.ACTION_MAIN, null)
        intent.addCategory(Intent.CATEGORY_LAUNCHER)

        packageManager.queryIntentActivities(intent, 0).forEach { resolveInfo ->
            val appInfo = Application(
                resolveInfo.loadLabel(packageManager).toString(),
                resolveInfo.activityInfo.packageName,
                resolveInfo.activityInfo.applicationInfo.loadIcon(packageManager)
            )
            apps.add(appInfo)
        }
        setSupportActionBar(binding.toolbar)
        Util.setToolbarFont(binding.toolbar)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = ApplicationAdapter(apps)


        /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
            .setAnchorView(R.id.toolbar)
            .setAction("Action", null).show()

         */
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}