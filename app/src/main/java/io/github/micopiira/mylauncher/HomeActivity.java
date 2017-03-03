package io.github.micopiira.mylauncher;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.GridView;

import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends Activity implements SharedPreferences.OnSharedPreferenceChangeListener {

    @BindView(R.id.apps_list) GridView appList;
    @BindString(R.string.app_name) String appName;

    private boolean isPortrait() {
        return getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apps_list);
        ButterKnife.bind(this);

        AppRepository appRepository = new AppRepository(this, appName);
        List<AppDetail> apps = appRepository.findAll();
        SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(this);


        appList.setNumColumns(Integer.parseInt(SP.getString(isPortrait() ? "grid_columns_portrait" : "grid_columns_landscape", "4")));

        appList.setAdapter(new AppsAdapter(this, apps));
        appList.setOnItemClickListener((parent, view, position, id) -> {
            startActivity(apps.get(position).getIntent());
        });

    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        recreate();
    }
}
