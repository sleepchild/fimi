package sleepchild.fimi;
import android.graphics.*;
import java.util.*;
import java.util.concurrent.*;
import android.os.*;

/*  Async only image loader with cache
*/
public class Imgur
{
    private static Imgur deft;
    
    private ExecutorService worker;
    
    private Handler handle = new Handler(Looper.getMainLooper());
    
    private Map<String, Bitmap> dcache;
    
    private Map<String , Bitmap> dthumbCache;
    
    private Imgur(){
        worker = Executors.newFixedThreadPool(3);
        dcache = new HashMap<>();
        dthumbCache = new HashMap<>();
    }
    
    private static Imgur getInstance(){
        Imgur inst = deft;
        if(inst==null){
            synchronized(Imgur.class){
                inst = Imgur.deft;
                if(inst==null){
                    inst = Imgur.deft = new Imgur();
                }
            }
        }
        return inst;
    }
    
    public static void getImage(final String path, final ResultCallback cb){
        getInstance().igetImage(path, cb);// if we make getInstance public, we can avoid all this!
    }
    private void igetImage(final String path, final ResultCallback cb){
        worker.submit(new Runnable(){
            public void run(){
                Bitmap bmp = dcache.get(path);
                if(bmp==null){
                    bmp = BitmapFactory.decodeFile(path);
                    if(bmp!=null){
                        dcache.put(path, bmp);
                    }
                }
                final Bitmap result = bmp;
                handle.post(new Runnable(){
                    public void run(){
                        cb.onResult(result);
                    }
                });
            }
        });
    }
    
    public static void getThumbnail(String path, int dsWidth, int dsHeight, ResultCallback cb){
        getInstance().igetThumbnail(path, dsWidth, dsHeight,cb);// if we make getInstance public, we can avoid all this!
    }
    private void igetThumbnail(final String path, final int dsw, final int dsh, final ResultCallback cb){
        worker.submit(new Runnable(){
            public void run(){
                Bitmap bmp = dthumbCache.get(path);
                if(bmp==null){
                    bmp = BitmapFactory.decodeFile(path);
                    if(bmp!=null){
                        if(bmp.getWidth() > dsw || bmp.getHeight()>dsh){
                            bmp = Bitmap.createScaledBitmap(bmp, dsw, dsh, false);
                        }
                    }
                    if(bmp!=null){
                        dthumbCache.put(path, bmp);
                    }
                }
                final Bitmap result = bmp;
                handle.post(new Runnable(){
                    public void run(){
                        cb.onResult(result);
                    }
                });
            }
        });
    }
    
    public static interface ResultCallback{
        public void onResult(Bitmap bmp);
    }
    
}
