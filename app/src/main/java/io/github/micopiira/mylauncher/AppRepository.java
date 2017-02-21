package io.github.micopiira.mylauncher;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindString;

public class AppRepository implements CrudRepository<AppDetail> {

    private final PackageManager packageManager;
    private final Context context;
    private final ApplicationInfo applicationInfo;
    private final String appName;

    public AppRepository(Context context, String appName) {
        this.packageManager = context.getPackageManager();
        this.context = context;
        this.applicationInfo = context.getApplicationInfo();
        this.appName = appName;
    }

    @Override
    public long count() {
        return findAll().size();
    }

    @Override
    public List<AppDetail> findAll() {
        Intent i = new Intent(Intent.ACTION_MAIN, null);
        i.addCategory(Intent.CATEGORY_LAUNCHER);

        List<ResolveInfo> resolveInfoList = packageManager.queryIntentActivities(i, 0);
        List<AppDetail> apps = new ArrayList<>();
        for (ResolveInfo resolveInfo : resolveInfoList) {
            AppDetail app = new AppDetail();
            app.setLabel(resolveInfo.loadLabel(packageManager).toString());
            app.setName(resolveInfo.activityInfo.packageName);
            app.setIcon(resolveInfo.activityInfo.loadIcon(packageManager));
            app.setIntent(packageManager.getLaunchIntentForPackage(resolveInfo.activityInfo.packageName));
            apps.add(app);
        }
        AppDetail launcherSettings = new AppDetail();
        launcherSettings.setLabel(appName + " settings");
        launcherSettings.setIcon(packageManager.getApplicationIcon(applicationInfo));
        launcherSettings.setIntent(new Intent(context, PreferencesActivity.class));
        apps.add(launcherSettings);
        Collections.sort(apps, (o1, o2) -> o1.getLabel().compareToIgnoreCase(o2.getLabel()));
        return apps;
    }
}
