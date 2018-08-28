package io.github.micopiira.mylauncher

import android.app.Activity
import android.content.res.Configuration
import android.os.Bundle
import android.preference.PreferenceManager
import android.widget.SearchView

import kotlinx.android.synthetic.main.activity_apps_list.*


class HomeActivity : Activity() {

    private val isPortrait
        get() = resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_apps_list)

        val apps = AppRepository(this, getString(R.string.app_name)).findAll()
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)

        val appsAdapter = AppsAdapter(this, apps)
        apps_list.numColumns = Integer.parseInt(sharedPreferences.getString(if (isPortrait) "grid_columns_portrait" else "grid_columns_landscape", "4"))
        apps_list.adapter = appsAdapter
        apps_list.setOnItemClickListener { _, _, position, _ -> startActivity(appsAdapter.filteredApps[position].intent) }
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

    override fun onBackPressed() {}

}
