package se.hactar.movieregister.helper

import android.content.Context
import android.content.Intent
import android.net.Uri

object UiHelper {

    fun openExternal(context: Context, url: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        context.startActivity(intent)
    }
}
