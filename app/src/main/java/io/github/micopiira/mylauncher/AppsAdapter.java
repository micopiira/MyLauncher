package io.github.micopiira.mylauncher;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

class AppsAdapter extends ArrayAdapter<AppDetail> {

    AppsAdapter(Context context, List<AppDetail> apps) {
        super(context, 0, apps);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        AppDetail app = getItem(position);

        ViewHolder holder;

        if (convertView != null) {
            holder = (ViewHolder) convertView.getTag();
        } else {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
            holder = new ViewHolder();
            holder.appLabel = (TextView) convertView.findViewById(R.id.item_app_label);
            holder.appIcon = (ImageView) convertView.findViewById(R.id.item_app_icon);

            convertView.setTag(holder);
        }

        holder.appIcon.setImageDrawable(app.getIcon());
        holder.appLabel.setText(app.getLabel());

        SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(getContext());

        holder.appLabel.setTextColor(Color.parseColor(SP.getString("label_color", "#ffffff")));

        return convertView;
    }

    private static class ViewHolder {
        ImageView appIcon;
        TextView appLabel;
    }

}
