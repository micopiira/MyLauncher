package io.github.micopiira.mylauncher;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends Activity {
    private PackageManager manager;
    private final List<AppDetail> apps = new ArrayList<>();
    private GridView list;

    private void loadListView() {
        list.setAdapter(new ArrayAdapter<AppDetail>(this, R.layout.list_item, apps) {
            @NonNull
            @Override
            public View getView(int position, View convertView, @NonNull ViewGroup parent) {
                if (convertView == null) {
                    convertView = getLayoutInflater().inflate(R.layout.list_item, null);
                }

                ImageView appIcon = (ImageView) convertView.findViewById(R.id.item_app_icon);
                appIcon.setImageDrawable(apps.get(position).icon);

                TextView appLabel = (TextView) convertView.findViewById(R.id.item_app_label);
                appLabel.setText(apps.get(position).label);

                return convertView;
            }
        });
        list.setOnTouchListener((view, motionEvent) -> false);
    }

    private void loadApps() {

        Intent i = new Intent(Intent.ACTION_MAIN, null);
        i.addCategory(Intent.CATEGORY_LAUNCHER);

        List<ResolveInfo> resolveInfoList = manager.queryIntentActivities(i, 0);
        for (ResolveInfo resolveInfo : resolveInfoList) {
            AppDetail app = new AppDetail();
            app.label = resolveInfo.loadLabel(manager);
            app.name = resolveInfo.activityInfo.packageName;
            app.icon = resolveInfo.activityInfo.loadIcon(manager);
            apps.add(app);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apps_list);
        manager = getPackageManager();
        list = (GridView) findViewById(R.id.apps_list);
        loadApps();
        loadListView();
        list.setOnItemClickListener((av, v, pos, id) -> {
            Intent i = manager.getLaunchIntentForPackage(apps.get(pos).name.toString());
            // HomeActivity.this.startActivity(i);
        });
    }

}
