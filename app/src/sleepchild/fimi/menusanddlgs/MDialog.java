package sleepchild.fimi.menusanddlgs;

import android.app.Dialog;
import android.view.View;
import sleepchild.fimi.activity.MainActivity;
import android.widget.TextView;
import static android.widget.LinearLayout.LayoutParams;
import android.content.*;
import android.app.*;

public class MDialog extends Dialog implements View.OnClickListener
{
   
    private Activity mActivity;
    
    public MDialog(Activity act){
        super(act);
        mActivity = act;
    }
    
    public MDialog(MainActivity act, int layoutID){
        super(act);
        mActivity = act;
        setContentView(layoutID);
    }
    
    public <T extends View> T findView(int resid){
       return (T) findViewById(resid);
    }
    
    protected void setClickable(int resid){
        findViewById(resid).setOnClickListener(this);
    }
    
    protected void setText(int textviewId, String text){
        ((TextView) findViewById(textviewId)).setText(text);
    }
    
    public Activity getActivity(){
        return mActivity;
    }

    @Override
    public void onClick(View v){}
    
    public void showFullWidth(){
        super.show();
        getWindow().setBackgroundDrawable(null);
        getWindow().setLayout(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
    }
    
    
}
