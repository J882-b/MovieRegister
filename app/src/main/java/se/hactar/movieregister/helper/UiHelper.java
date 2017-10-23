package se.hactar.movieregister.helper;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class UiHelper {

    public static void openExternal(final Context context, final String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        context.startActivity(intent);
    }
}
