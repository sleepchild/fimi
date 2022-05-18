package sleepchild.fimi;
import java.util.concurrent.*;
import android.os.*;

public class App
{
    private static volatile App deftInstance;
    private ExecutorService worker;
    private Handler mHandle;
    
    private App(){
        worker = Executors.newFixedThreadPool(3);
        mHandle = new Handler(Looper.getMainLooper());
    }
    
    public static App get(){
        App inst=deftInstance;
        if(inst==null){
            synchronized(App.class){
                inst = App.deftInstance;
                if(inst==null){
                    inst = App.deftInstance = new App();
                }
            }
        }
        return deftInstance;
    }
    
    public static String getAppDirectory(){
        return "/sdcard/.sleepchild/fimi/";
    }
    
    public static void runOnUiThread(Runnable runnable){
        getUiHandler().post(runnable);
    }

    public static void runOnUiThread(Runnable runnable, int delay){
        getUiHandler().postDelayed(runnable, delay);
    }

    // runs the task imediately
    public static void runInBackground(Runnable runnable){
        new Thread(runnable).start();
    }

    // add the task to the work queue
    public static void queueWork(Runnable runnable){
        get().worker.submit(runnable);
    }
    
    public static Handler getUiHandler(){
        return get().mHandle;
    }
}
