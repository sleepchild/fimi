package sleepchild.fimi.activity;

import android.app.Activity;
import android.os.Bundle;
import android.content.Context;
import android.view.View;
import android.widget.Toast;
import android.view.inputmethod.InputMethodManager;
import android.content.Intent;
import sleepchild.fimi.*;

public class BaseActivity extends Activity implements View.OnClickListener{

    protected Context ctx;
    protected InputMethodManager imm;
    protected SPrefs prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        ctx = getApplicationContext();
        prefs = new SPrefs(ctx);
        
    }

    public <T extends View> T findView(int resid){
        return (T)findViewById(resid);
    }
    
    protected void setClickable(int resid){
        findViewById(resid).setOnClickListener(this);
    }

    public void toast(String msg){
        Toast.makeText(ctx,msg,500).show();
    }

    public void startActivity(Class<?> clazz){
        startActivity(new Intent(this, clazz));
    }

    @Override
    public void onClick(View p1){}
    

}
