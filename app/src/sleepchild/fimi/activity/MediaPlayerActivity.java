package sleepchild.fimi.activity;
import android.os.*;
import sleepchild.fimi.*;
import android.webkit.*;
import android.content.*;

public class MediaPlayerActivity extends BaseActivity
{
    WebView web1;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_mediaplayer);
        
        web1 = findView(R.id.act_mediaplayer_web1);
        
        Intent i = getIntent();
        if(i!=null){
            String path = i.getDataString();
            if(path!=null){
                web1.loadUrl(path);
            }
        }
        
    }
    
    
    
}
