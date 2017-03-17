package com.example.seedcommando_6.emspherevms;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;

/**
 * Created by seedcommando_6 on 2/25/2017.
 */

public class RoundImage {


    public static RoundedBitmapDrawable getRoundedBitmap(Bitmap bitmap)
    {
            Resources mResources;

            /*final Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
            final Canvas canvas = new Canvas(output);

            final int color = Color.RED;
            final Paint paint = new Paint();
            final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
            final RectF rectF = new RectF(rect);

            paint.setAntiAlias(true);
            canvas.drawARGB(0, 0, 0, 0);
            paint.setColor(color);
           // paint.setStyle(Paint.Style.STROKE);
           // paint.setColor(Color.GREEN);
           // paint.setStrokeWidth(3);
            canvas.drawOval(rectF, paint);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            canvas.drawBitmap(bitmap, rect, rect, paint);
            // bitmap.setDrawingCacheEnable(false);

            bitmap.recycle();


            return output;*/
            int bitmapWidthImage = bitmap.getWidth();
            int bitmapHeightImage = bitmap.getHeight();
            int borderWidthHalfImage = 4;

            int bitmapRadiusImage = Math.min(bitmapWidthImage,bitmapHeightImage)/2;
            int bitmapSquareWidthImage = Math.min(bitmapWidthImage,bitmapHeightImage);
            int newBitmapSquareWidthImage = bitmapSquareWidthImage+borderWidthHalfImage;

            Bitmap roundedImageBitmap = Bitmap.createBitmap(newBitmapSquareWidthImage,newBitmapSquareWidthImage,Bitmap.Config.ARGB_8888);
            Canvas mcanvas = new Canvas(roundedImageBitmap);
            mcanvas.drawColor(Color.RED);
               /* final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
                final RectF rectF = new RectF(rect);*/

            int i = borderWidthHalfImage + bitmapSquareWidthImage - bitmapWidthImage;
            int j = borderWidthHalfImage + bitmapSquareWidthImage - bitmapHeightImage;
               /* int i =bitmapWidthImage;
                int j =bitmapHeightImage;*/

            mcanvas.drawBitmap(bitmap, i, j, null);
            String white = "#f8d287";
           int color=Color.parseColor(white);
            Paint borderImagePaint = new Paint();
            borderImagePaint.setStyle(Paint.Style.STROKE);
            borderImagePaint.setStrokeWidth(borderWidthHalfImage*2);
            borderImagePaint.setColor(color);
            mcanvas.drawCircle(mcanvas.getWidth()/2, mcanvas.getWidth()/2, newBitmapSquareWidthImage/2, borderImagePaint);
            mResources=Resources.getSystem();
            RoundedBitmapDrawable roundedImageBitmapDrawable = RoundedBitmapDrawableFactory.create(mResources,roundedImageBitmap);
            roundedImageBitmapDrawable.setCornerRadius(bitmapRadiusImage);
            roundedImageBitmapDrawable.setAntiAlias(true);
            return roundedImageBitmapDrawable;


    }
        public static RoundedBitmapDrawable getRounded(Bitmap bitmap)
        {
                Resources mResources;

                /*final Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
                final Canvas canvas = new Canvas(output);
                final int color = Color.RED;
                final Paint paint = new Paint();
                final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
                final RectF rectF = new RectF(rect);

                paint.setAntiAlias(true);
                canvas.drawARGB(0, 0, 0, 0);
                paint.setColor(color);
                paint.setStyle(Paint.Style.STROKE);
                //paint.setColor(Color.GREEN);
                paint.setStrokeWidth(6);
                canvas.drawOval(rectF, paint);
                paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
                canvas.drawBitmap(bitmap, rect, rect, paint);*/
                // return output;

                int bitmapWidthImage = bitmap.getWidth();
                int bitmapHeightImage = bitmap.getHeight();
                int borderWidthHalfImage = 4;

                int bitmapRadiusImage = Math.min(bitmapWidthImage,bitmapHeightImage)/2;
                int bitmapSquareWidthImage = Math.min(bitmapWidthImage,bitmapHeightImage);
                int newBitmapSquareWidthImage = bitmapSquareWidthImage+borderWidthHalfImage;

                Bitmap roundedImageBitmap = Bitmap.createBitmap(newBitmapSquareWidthImage,newBitmapSquareWidthImage,Bitmap.Config.ARGB_8888);
                Canvas mcanvas = new Canvas(roundedImageBitmap);
                mcanvas.drawColor(Color.RED);
               /* final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
                final RectF rectF = new RectF(rect);*/

                int i = borderWidthHalfImage + bitmapSquareWidthImage - bitmapWidthImage;
                int j = borderWidthHalfImage + bitmapSquareWidthImage - bitmapHeightImage;
               /* int i =bitmapWidthImage;
                int j =bitmapHeightImage;*/

                mcanvas.drawBitmap(bitmap, i, j, null);

            String white = "#f8d287";
            int color=Color.parseColor(white);
                Paint borderImagePaint = new Paint();
                borderImagePaint.setStyle(Paint.Style.STROKE);
                borderImagePaint.setStrokeWidth(borderWidthHalfImage);
                borderImagePaint.setColor(color);
                mcanvas.drawCircle(mcanvas.getWidth()/2, mcanvas.getWidth()/2, newBitmapSquareWidthImage/2, borderImagePaint);
                 mResources=Resources.getSystem();
                RoundedBitmapDrawable roundedImageBitmapDrawable = RoundedBitmapDrawableFactory.create(mResources,roundedImageBitmap);
                roundedImageBitmapDrawable.setCornerRadius(bitmapRadiusImage);
                roundedImageBitmapDrawable.setAntiAlias(true);
                return roundedImageBitmapDrawable;


        }
}
