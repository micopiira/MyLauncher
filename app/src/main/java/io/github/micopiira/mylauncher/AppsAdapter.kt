package io.github.micopiira.mylauncher

import android.content.Context
import android.graphics.Color
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*

class AppsAdapter(context: Context, private val apps: List<AppDetail>) : ArrayAdapter<AppDetail>(context, 0, apps), Filterable {

    var filteredApps = apps;

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(p0: CharSequence?): FilterResults {
                val filterResults = FilterResults()
                filterResults.values = apps.filter {
                    if (p0 != null && p0.isNotEmpty()) it.label.contains(p0, true) || it.label.contains(p0, true) else true
                }
                return filterResults;
            }

            override fun publishResults(p0: CharSequence, p1: FilterResults) {
                filteredApps = p1.values as List<AppDetail>
                notifyDataSetChanged()
            }
        }
    }


    override fun getCount(): Int {
        return filteredApps.size
    }

    override fun getItem(position: Int): AppDetail {
        return filteredApps[position]
    }

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

        holder.appIcon!!.setImageDrawable(app.icon)
        holder.appLabel!!.text = app.label

        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

        holder.appLabel.setTextColor(Color.parseColor(sharedPreferences.getString("label_color", "#ffffff")))

        return convertView
    }

    private data class ViewHolder(
            internal val appIcon: ImageView? = null,
            internal val appLabel: TextView? = null
    )

}
