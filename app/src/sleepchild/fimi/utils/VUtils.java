package sleepchild.fimi.utils;

import android.graphics.*;
import android.graphics.drawable.*;
import android.content.res.*;
import java.util.*;
import java.io.*;
import android.view.*;
import android.content.*;
import android.widget.*;

public class VUtils
{
    private static class T{
        Bitmap bmp;
        int color;
        int id;
    }

    private static Map<Integer, T> cache = new HashMap<>();
    private VUtils(){}

    private static Bitmap tint2(Bitmap src, int color){
        //Bitmap dest = cache.get(
        Bitmap dest = Bitmap.createBitmap(src.getWidth(), src.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(dest);
        Paint mpaint = new Paint();
        //mpaint.setColor(color);

        //RectF
        mpaint.setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.SRC_ATOP));
        c.drawBitmap(src,0,0,mpaint);
        //BitmapDrawable b;
        dest.recycle();
        src.recycle();
        return dest;
    }

    public static Bitmap tintResource(Resources res, final int resid, final int color){
        T t = cache.get(resid);
        if(t!=null && t.id==resid && t.bmp!=null && t.color==color){
            return t.bmp;
        }
        t = new T();
        
        Bitmap src =BitmapFactory.decodeResource(res, resid);
        if(src==null){
            return null;
        }
        Bitmap dest = Bitmap.createBitmap(src.getWidth(), src.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(dest);
        Paint mpaint = new Paint();
        mpaint.setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.SRC_ATOP));
        c.drawBitmap(src,0,0,mpaint);
        //dest.recycle();
        //src.recycle();
        t.bmp = dest;
        t.color = color;
        t.id = resid;
        cache.put(resid, t);

        return dest;
    }
    
    public static void tintIV(View root, int vid, int drawableID, int color){
        ImageView ivey = (ImageView) root.findViewById(vid);
        Bitmap bmp = tintResource(root.getContext().getResources(), drawableID, color);
        if(bmp!=null){
            ivey.setImageBitmap(bmp);
        }
    }
    public static void tintIV(View root, int resid, int color){
        View v = root.findViewById(resid);
        if(v instanceof ImageView){
            tintIV((ImageView) v , color);
        }
    }
    
    public static void tintIV(ImageView v, int color){
        v.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
    }
}
