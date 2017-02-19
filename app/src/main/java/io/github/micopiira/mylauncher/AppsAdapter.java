package io.github.micopiira.mylauncher;

import android.content.Context;
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
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        AppDetail app = getItem(position);

        ViewHolder holder;

        if (convertView != null) {
            holder = (ViewHolder) convertView.getTag();
        } else {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        holder.appIcon.setImageDrawable(app.getIcon());
        holder.appLabel.setText(app.getLabel());

        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.item_app_icon) ImageView appIcon;
        @BindView(R.id.item_app_label) TextView appLabel;
        @SuppressWarnings("WeakerAccess")
        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

}
