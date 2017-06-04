package testapp.andrey.com.testapp;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.DisplayMetrics;
import android.widget.ImageView;

import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Андрей on 04.06.2017.
 */

public class Utils {
    private static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH);
    public static Date toDate(String dateString) throws ParseException {
        return dateFormat.parse(dateString);
    }

    public static class DownloadLogoTask extends AsyncTask<String, Void, Bitmap> {
        private Activity activity;
        private ImageView imageView;

        public DownloadLogoTask(Activity activity, ImageView imageView) {
            this.activity = activity;
            this.imageView = imageView;
        }

        protected Bitmap doInBackground(String... urls) {
            String urlDisplay = urls[0];
            Bitmap icon = null;
            try {
                InputStream in = new java.net.URL(urlDisplay).openStream();
                icon = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return icon;
        }

        protected void onPostExecute(Bitmap result) {
            if (result == null) {
                return;
            }
            DisplayMetrics displayMetrics = new DisplayMetrics();
            activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            float width = displayMetrics.widthPixels / 3.0f;
            float height = result.getHeight() / (result.getWidth() / width);
            Bitmap scaledBitmap = Bitmap.createScaledBitmap(result, (int)width, (int)height, true);
            imageView.setImageBitmap(scaledBitmap);
        }
    }
}
