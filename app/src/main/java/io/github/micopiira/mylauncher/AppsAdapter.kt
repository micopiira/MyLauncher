package io.github.micopiira.mylauncher

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView

import butterknife.BindView
import butterknife.ButterKnife

internal class AppsAdapter(context: Context, apps: List<AppDetail>) : ArrayAdapter<AppDetail>(context, 0, apps) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView

        val app = getItem(position)

        val holder: ViewHolder

        if (convertView != null) {
            holder = convertView.tag as ViewHolder
        } else {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)
            holder = ViewHolder()
            holder.appLabel = convertView!!.findViewById(R.id.item_app_label) as TextView
            holder.appIcon = convertView.findViewById(R.id.item_app_icon) as ImageView

            convertView.tag = holder
        }

        holder.appIcon!!.setImageDrawable(app!!.icon)
        holder.appLabel!!.text = app.label

        val SP = PreferenceManager.getDefaultSharedPreferences(context)

        holder.appLabel!!.setTextColor(Color.parseColor(SP.getString("label_color", "#ffffff")))

        return convertView
    }

    private class ViewHolder {
        internal var appIcon: ImageView? = null
        internal var appLabel: TextView? = null
    }

}
