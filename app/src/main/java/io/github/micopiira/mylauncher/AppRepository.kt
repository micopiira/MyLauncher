package io.github.micopiira.mylauncher

import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager

class AppRepository(private val context: Context, private val appName: String) : CrudRepository<AppDetail> {

    private val packageManager: PackageManager = context.packageManager
    private val applicationInfo: ApplicationInfo = context.applicationInfo

    override fun count(): Long {
        return findAll().size.toLong()
    }

    override fun findAll(): List<AppDetail> {
        val i = Intent(Intent.ACTION_MAIN, null).addCategory(Intent.CATEGORY_LAUNCHER)

        return packageManager.queryIntentActivities(i, 0).map {
            AppDetail(
                    label = it.loadLabel(packageManager).toString(),
                    name = it.activityInfo.packageName,
                    icon = it.activityInfo.loadIcon(packageManager),
                    intent = packageManager.getLaunchIntentForPackage(it.activityInfo.packageName)
            )
        }.plus(AppDetail(
                label = appName + " settings",
                icon = packageManager.getApplicationIcon(applicationInfo),
                intent = Intent(context, PreferencesActivity::class.java)
        )).sortedBy { it.label }
    }
}
