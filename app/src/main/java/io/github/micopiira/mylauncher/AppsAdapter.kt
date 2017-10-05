package io.github.micopiira.mylauncher

import android.content.Context
import android.graphics.Color
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView

internal class AppsAdapter(context: Context, apps: List<AppDetail>) : ArrayAdapter<AppDetail>(context, 0, apps) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        val app = getItem(position)
        val holder: ViewHolder

        if (convertView != null) {
            holder = convertView.tag as ViewHolder
        } else {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)
            holder = ViewHolder(
                    appLabel = convertView!!.findViewById(R.id.item_app_label) as TextView,
                    appIcon = convertView.findViewById(R.id.item_app_icon) as ImageView
            )
            convertView.tag = holder
        }

        holder.appIcon!!.setImageDrawable(app!!.icon)
        holder.appLabel!!.text = app.label

        val SP = PreferenceManager.getDefaultSharedPreferences(context)

        holder.appLabel.setTextColor(Color.parseColor(SP.getString("label_color", "#ffffff")))

        return convertView
    }

    private data class ViewHolder(
            internal val appIcon: ImageView? = null,
            internal val appLabel: TextView? = null
    )

}
