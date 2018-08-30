package io.github.micopiira.mylauncher

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import android.preference.PreferenceManager
import android.provider.Settings
import android.provider.Settings.Global.getString
import android.util.Log
import android.widget.SearchView

import kotlinx.android.synthetic.main.activity_apps_list.*


class MyReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d(javaClass.name, "Received")
        HomeActivity.appsAdapter.filteredApps = AppRepository(context!!, context.getString(R.string.app_name)).findAll()
        HomeActivity.appsAdapter.notifyDataSetChanged()
    }
}

class HomeActivity : Activity() {



    companion object {
        lateinit var appsAdapter: AppsAdapter
    }

    private val isPortrait
        get() = resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_apps_list)

        val apps = AppRepository(this, getString(R.string.app_name)).findAll()
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)

        appsAdapter = AppsAdapter(this, apps)
        apps_list.numColumns = Integer.parseInt(sharedPreferences.getString(if (isPortrait) "grid_columns_portrait" else "grid_columns_landscape", "4"))
        apps_list.adapter = appsAdapter
        apps_list.setOnItemClickListener { _, _, position, _ -> startActivity(appsAdapter.filteredApps[position].intent) }
        apps_list.setOnItemLongClickListener { _, _, position, _ ->
            startActivity(Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).setData(Uri.fromParts("package", appsAdapter.filteredApps[position].name, null)))
            return@setOnItemLongClickListener true
        }
        searchview.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                appsAdapter.filter.filter(p0)
                return false
            }
        })

    }

    override fun onResume() {

        super.onResume()

        if (PreferenceManager.getDefaultSharedPreferences(this).getBoolean("needsReload", false)) {
            PreferenceManager.getDefaultSharedPreferences(this).edit().putBoolean("needsReload",  false).apply()
            recreate()
            return
        }

    }

    override fun onBackPressed() {}

}
