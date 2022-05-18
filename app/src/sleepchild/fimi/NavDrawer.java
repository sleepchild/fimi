package sleepchild.fimi;
import android.app.*;
import android.widget.AbsListView.*;
import android.view.*;
import android.widget.*;
import sleepchild.fimi.activity.*;
import sleepchild.fimi.tabs.*;

public class NavDrawer
{
    Dialog dlg;
    Tab currentTab;
    LinearLayout container;
    TextView tvTabDirName, tvTabDirPath;
    ThemeManager.Theme tm;;
    
    public NavDrawer(MainActivity act){
        //
        dlg = new Dialog(act);
        dlg.setContentView(R.layout.nav_drawer);
        
        tm = ThemeManager.getTheme();
        
        container = fv(R.id.nav_container);
        container.postDelayed(new Runnable(){
            public void run(){
                container.setTranslationX(-(container.getWidth()*1.5f));
            }
        },10);
        
        tvTabDirPath = fv(R.id.nav_drawer_tabdirpath);
        tvTabDirName = fv(R.id.nav_drawer_tabdirname);
        
    }
    
    <T extends View> T fv(int resid){
        return (T)dlg.findViewById(resid);
    }
    
    public void show(Tab tab){
        tm = ThemeManager.getTheme();
        currentTab = tab;
        container.setTranslationX(-(container.getWidth()*1.5f));
        dlg.show();
        dlg.getWindow().setLayout(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        dlg.getWindow().setGravity(Gravity.LEFT);
        dlg.getWindow().setBackgroundDrawable(null);
        
        container.postDelayed(new Runnable(){
            public void run(){
                container.animate().translationX(0).setDuration(300).start();
            }
        },10);
        
        updateInfo();
        
    }
    
    void updateInfo(){
        MFile cd = currentTab.getDir();
        if(cd!=null){
            tvTabDirPath.setText(currentTab.getDir().getAbsolutePath());
            tvTabDirName.setText(currentTab.getDir().getName());
        }
        
        tvTabDirName.setTextColor(tm.header_text);
        tvTabDirPath.setTextColor(tm.header_text);
        dlg.findViewById(R.id.navdrawer_header).setBackgroundColor(tm.header_bg);
        
    }
    
    public void close(){
        dlg.dismiss();
        currentTab = null;
    }
    
}
