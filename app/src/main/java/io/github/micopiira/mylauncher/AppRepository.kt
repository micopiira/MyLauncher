package io.github.micopiira.mylauncher

import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo

import java.util.ArrayList
import java.util.Collections

import butterknife.BindString

class AppRepository(private val context: Context, private val appName: String) : CrudRepository<AppDetail> {

    private val packageManager: PackageManager
    private val applicationInfo: ApplicationInfo

    init {
        this.packageManager = context.packageManager
        this.applicationInfo = context.applicationInfo
    }

    override fun count(): Long {
        return findAll().size.toLong()
    }

    override fun findAll(): List<AppDetail> {
        val i = Intent(Intent.ACTION_MAIN, null)
        i.addCategory(Intent.CATEGORY_LAUNCHER)

        val resolveInfoList = packageManager.queryIntentActivities(i, 0)
        val apps = ArrayList<AppDetail>()
        for (resolveInfo in resolveInfoList) {
            val app = AppDetail()
            app.label = resolveInfo.loadLabel(packageManager).toString()
            app.name = resolveInfo.activityInfo.packageName
            app.icon = resolveInfo.activityInfo.loadIcon(packageManager)
            app.intent = packageManager.getLaunchIntentForPackage(resolveInfo.activityInfo.packageName)
            apps.add(app)
        }
        val launcherSettings = AppDetail()
        launcherSettings.label = appName + " settings"
        launcherSettings.icon = packageManager.getApplicationIcon(applicationInfo)
        launcherSettings.intent = Intent(context, PreferencesActivity::class.java)
        apps.add(launcherSettings)
        Collections.sort(apps) { o1, o2 -> o1.label!!.compareTo(o2.label!!, ignoreCase = true) }
        return apps
    }
}
