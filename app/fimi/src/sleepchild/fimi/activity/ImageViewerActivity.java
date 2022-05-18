package sleepchild.fimi.activity;
import android.os.*;
import sleepchild.fimi.*;
import android.content.*;
import android.widget.*;
import android.graphics.*;
import android.view.*;

public class ImageViewerActivity extends BaseActivity
{
    String mPath=null;
    ImageView ivey;
    ProgressBar loader;
    
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_imageviewer);
        //
        init();
        //
        Intent i = getIntent();
        if(i!=null){
            mPath = clean(i.getDataString());
            if(mPath!=null){
                setImage(mPath);
            }else{
                // log
            }
        }else {
            // log
        }  
    }
    
    private void init(){
        ivey = findView(R.id.imageviewer_ivey);
        loader = findView(R.id.loader);
        //
    }
    
    private static String clean(String str){
        String ret = str;
        if(str.startsWith("file://")){
            ret = str.substring(7);
        }
        ret = ret.replace("%20"," ");
        return ret;
    }
    
    void setImage(final String path){
        if(mPath==null){
            return;
        }
        Imgur.getImage(path, new Imgur.ResultCallback(){
            public void onResult(Bitmap bmp){
                if(bmp==null){
                    toast("error: bmp==null");
                    return;
                }
                ivey.setImageBitmap(bmp);
                loader.setVisibility(View.GONE);
            }
        });
         
    }
    
    
}
