package com.example.newicqandroid;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

//This class contains methods that helps the registerActivity logic
public class Utils {

    /*
    make picture rounded
     */
    static public Bitmap getCircleBitmap(Bitmap bitmap) {
        if(bitmap == null){
            return null;
        }
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        // canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        canvas.drawCircle(bitmap.getWidth() / 2, bitmap.getHeight() / 2,
                bitmap.getWidth() / 2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        Bitmap bmp = Bitmap.createScaledBitmap(output, 272, 352, false);
        return bmp;
        //return output;
    }

    static public Bitmap drawableToBitmap(Drawable img){
        Bitmap bitmap = null;

        if(!(img.getIntrinsicWidth() <= 0 || img.getIntrinsicHeight() <= 0)) {
          bitmap = Bitmap.createBitmap(img.getIntrinsicWidth(),
                  img.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }
        return bitmap;
    }

    static public String encodeImg(Bitmap bitmap)
    {
        ByteArrayOutputStream byteArrStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100, byteArrStream);
        byte[] b = byteArrStream.toByteArray();
        String strImage = Base64.encodeToString(b, Base64.DEFAULT);

        return strImage;
    }

    static public Bitmap decodeImg(String imgStr){
        byte[] decodedString = Base64.decode(imgStr, Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

        return bitmap;
    }
}
