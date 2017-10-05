package io.github.micopiira.mylauncher

import android.app.Activity
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Bundle
import android.preference.PreferenceManager
import android.widget.GridView

import butterknife.BindString
import butterknife.BindView
import butterknife.ButterKnife

class HomeActivity : Activity(), SharedPreferences.OnSharedPreferenceChangeListener {

    @BindView(R.id.apps_list) lateinit var appList: GridView

    @BindString(R.string.app_name) lateinit var appName: String

    private val isPortrait: Boolean
        get() = resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_apps_list)
        ButterKnife.bind(this)

        val apps = AppRepository(this, appName).findAll()
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)

        appList.numColumns = Integer.parseInt(sharedPreferences.getString(if (isPortrait) "grid_columns_portrait" else "grid_columns_landscape", "4"))
        appList.adapter = AppsAdapter(this, apps)
        appList.setOnItemClickListener { _, _, position, _ -> startActivity(apps[position].intent) }

    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String) {
        recreate()
    }
}
