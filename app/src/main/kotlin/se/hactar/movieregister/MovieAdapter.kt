package se.hactar.movieregister

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import se.hactar.movieregister.data.Movie

/**
 * Created by dilbert on 2015-10-14.
 */
class MovieAdapter(context: Context?, resource: Int, objects: MutableList<Movie>?) : ArrayAdapter<Movie>(context, resource, objects) {
    val mInflater = LayoutInflater.from(context)
    val mResource = resource

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        val view: View?

        if (convertView == null) {
            view = mInflater.inflate(mResource, parent, false);
        } else {
            view = convertView;
        }

        val textView = view as TextView
        textView.text = getItem(position).name
        return view;
    }
}