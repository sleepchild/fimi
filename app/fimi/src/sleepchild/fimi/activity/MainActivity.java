package sleepchild.fimi.activity;

import android.os.Bundle;

import sleepchild.fimi.tabs.Tab;
import sleepchild.fimi.tabs.TabPicker;
import sleepchild.fimi.tabs.TabManager;
import sleepchild.fimi.menusanddlgs.TabOptionsDialog;
import sleepchild.fimi.NavDrawer;
import sleepchild.fimi.R;
import sleepchild.fimi.SPrefs;
import sleepchild.fimi.MFile;
import sleepchild.fimi.Utils;
import sleepchild.fimi.*;
import android.view.*;
import android.content.*;
import android.widget.*;
import sleepchild.view.*;
import android.graphics.*;

public class MainActivity extends BaseActivity
implements TabManager.TabEvents, TabPicker.OnItemClickListener
{
    private TabManager tabMan;
    TabPicker tabPicker;
    NavDrawer drawer;
    TabOptionsDialog taboptionsdlg;
    View root;
    TextView tvTabCounter;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_main);
        //
        init();

    }

    void init(){
        root = findView(R.id.root);
        
        tabPicker = new TabPicker(this);
        tabPicker.setOnItemClickListener(this);
        
        drawer = new NavDrawer(this);
        taboptionsdlg = new TabOptionsDialog(this);
        
        setClickable(R.id.bottom_nav_pickertoggle);
        setClickable(R.id.bottom_nav_search);
        setClickable(R.id.bottom_nav_show_drawer);
        setClickable(R.id.bottom_nav_showmenu);
        setClickable(R.id.bottom_nav_task);
        
        tvTabCounter = findView(R.id.bottom_nav_tvtabcounter_text);
        //
        tabMan = new TabManager(this);
        tabMan.addTabEventsListener(this);

        if(prefs.getLoadPreviousTabs()){
            boolean loaded = tabMan.loadOldTabs();
            if(!loaded){
                tabMan.newTab(Utils.DEFAULT_START_PATH);
            }
        }else{
            tabMan.newTab(Utils.DEFAULT_START_PATH);
        }
    }
    
    public SPrefs getPrefs(){
        return prefs;
    }

    public void showPicker(){
        tabPicker.show();
    }

    public void showDrawer(Tab tab){
        drawer.show(tab);
    }

    public void showTabOptions(Tab tab){
        taboptionsdlg.show(tab);
    }

    public TabManager getTabManager(){
        return tabMan;
    }

    public void onFileItemClicked(MFile fl){
        /*
        if(fl.isImageFile()){
            Intent i = new Intent(this, MediaViewerActivity.class);
            i.putExtra("path", fl.getAbsolutePath());
            startActivity(i);
        }else{
            CanOpener.open(ctx, fl);
        }
        //*/
        CanOpener.open(ctx, fl);
    }

    @Override
    public void onClick(View v)
    {
        int id = v.getId();
        switch(id){
            case R.id.bottom_nav_show_drawer:
                showDrawer(tabMan.getCurrent());
                break;
            case R.id.bottom_nav_pickertoggle:
                showPicker();
                break;
            case R.id.bottom_nav_search:
                //
                break;
            case R.id.bottom_nav_showmenu:
                showTabOptions(tabMan.getCurrent());
                break;
            case R.id.bottom_nav_task:
                //
                break;
                
        }
    }
    
    long lm = 0;
    void tm(){
        ThemeManager.Theme t = ThemeManager.getTheme();
        if(lm==t.lastmod){
            return;
        }
        lm = t.lastmod;
        
        root.setBackgroundColor(t.background);
        ti(t.icon, 
          R.id.bottom_nav_search,
          R.id.bottom_nav_showmenu,
          R.id.bottom_nav_show_drawer,
          R.id.bottom_nav_task,
          R.id.bottom_nav_tvtabcounter_text,
          R.id.t1);
    }
    
    void ti(int color, int... resid){
        for(int r : resid){
            View v = findViewById(r);
            if(v instanceof NavItem){
                ((NavItem) v).setColor(color);
            }else if(v instanceof TextView){
                ((TextView) v).setTextColor(color);
            }else if(v instanceof ImageView){
                ((ImageView) v).setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
            }
        }
    }

    @Override
    protected void onPause(){
        //
        super.onPause();
    }

    @Override
    protected void onResume(){
        super.onResume();
        tm();
        tabMan.reload();
    }

    @Override
    public void onBackPressed(){
        if(tabMan.handlesBack()){
            // 
        }else{
            super.onBackPressed();
        }
    }

    @Override
    public void onTabCountChanged(int total){
        tvTabCounter.setText(""+total);
    }

    @Override
    public void onTabPickerItemSelected(int pos, Tab tab){
        tabMan.show(tab);
    }

    @Override
    public void onTabPickerItemClosed(int pos, Tab tab)
    {
        tabMan.closeTab(tab);
    }
    

}
