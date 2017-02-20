package io.github.micopiira.mylauncher;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends Activity implements SharedPreferences.OnSharedPreferenceChangeListener {

    @BindView(R.id.apps_list) GridView appList;

    private List<AppDetail> getApps() {
        Intent i = new Intent(Intent.ACTION_MAIN, null);
        i.addCategory(Intent.CATEGORY_LAUNCHER);

        List<ResolveInfo> resolveInfoList = getPackageManager().queryIntentActivities(i, 0);
        List<AppDetail> apps = new ArrayList<>();
        for (ResolveInfo resolveInfo : resolveInfoList) {
            AppDetail app = new AppDetail();
            app.setLabel(resolveInfo.loadLabel(getPackageManager()).toString());
            app.setName(resolveInfo.activityInfo.packageName);
            app.setIcon(resolveInfo.activityInfo.loadIcon(getPackageManager()));
            apps.add(app);
        }
        Collections.sort(apps, (o1, o2) -> o1.getLabel().compareToIgnoreCase(o2.getName()));
        return apps;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apps_list);
        ButterKnife.bind(this);

        appList.setAdapter(new AppsAdapter(this, getApps()));
        appList.setOnItemClickListener((av, v, pos, id) -> {
            Intent i = getPackageManager().getLaunchIntentForPackage(getApps().get(pos).getName());
            startActivity(i);
        });
        appList.setOnItemLongClickListener((parent, view, position, id) -> {
            Intent i = new Intent(this, PreferencesActivity.class);
            startActivity(i);
            return true;
        });

    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        recreate();
    }
}
