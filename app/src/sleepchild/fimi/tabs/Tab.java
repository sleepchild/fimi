package sleepchild.fimi.tabs;

import android.view.*;
import android.content.*;
import java.util.*;
import android.widget.*;
import java.io.*;
import sleepchild.fimi.activity.*;
import sleepchild.fimi.adapters.*;
import sleepchild.fimi.*;
import sleepchild.fimi.menusanddlgs.*;
import sleepchild.fimi.utils.*;
import android.graphics.drawable.*;

public class Tab implements
    FileItemAdapter.OnFileItemClickListener
{

    private MainActivity act;
    TabManager mgr;
    //TabView tabView;

    private long id;
    View tabview;
    ListView list1;
    private FileItemAdapter adaptor;
    private TextView dirname;
    private MFile dir;
    boolean list1scrolling;

    public Tab(MainActivity a, TabManager tmagr){
        this.act = a;
        mgr = tmagr;
        this.id = new Date().getTime();
        
        //tabView = new TabView(this);
        tabview = LayoutInflater.from(act).inflate(R.layout.tabview, null, false);

        dirname = (TextView) tabview.findViewById(R.id.tabview_dirname);

        adaptor = new FileItemAdapter(act, this);
        list1 = (ListView) tabview.findViewById(R.id.tabview_list1);
        list1.setAdapter(adaptor);
        list1.setDividerHeight(0);
        //list1.setDivider(null);
        list1.setOnScrollListener(new AbsListView.OnScrollListener(){
            @Override
            public void onScrollStateChanged(AbsListView p1, int scrollstate)
            {
                if(scrollstate==AbsListView.OnScrollListener.SCROLL_STATE_IDLE){
                    list1scrolling = false;
                }else{
                    list1scrolling = true;
                }
            }

            @Override
            public void onScroll(AbsListView p1, int p2, int p3, int p4)
            {
                // TODO: Implement this method
            }
        });
        
        //
        //mgr.addTabEventsListener(this);
        dir = new MFile(Utils.DEFAULT_START_PATH);

    }

    private View fv(int resid){
        return tabview.findViewById(resid);
    }
    
    private void loadDir(MFile fl){
        dir = fl;
        load(dir.getAbsolutePath());
        dirname.setText(dir.getAbsolutePath());
    }
    
    private void loadFile(MFile fl){
        //
        act.onFileItemClicked(fl);
    }
    
    public void load(String path){
        dir = new MFile(path);
        adaptor.update(path);
        TabSpooler.get().saveTab(this);
        dirname.setText(dir.getAbsolutePath());
        list1.setSelection(0);
    }
    
    public void reload(){
        adaptor.update(dir.getAbsolutePath());
        tm();
    }
    
    public MFile getDir(){
        return dir;
    }

    public long getId(){
        return id;
    }

    public void setId(long id){
        this.id = id;
    }

    boolean isList1Scrolling(){
        return list1scrolling;
    }
    
    public boolean isCurrentTab(){
        return mgr.getCurrent()==this;
    }

    @Override
    public void onFileItemClick(MFile fl, int pos){
        
        if(fl.isDirectory()){
            loadDir(fl);
        }else{
            loadFile(fl);
        }
    }

    @Override
    public boolean onFileItemLongClick(MFile fl){
        act.toast("long press:"+ fl);
        return true;
    }

    @Override
    public void onShowFileOptions(MFile fl, int pos)
    {
        new FileItemOptions(act, fl, this).show();
    }

    public boolean goBack(){
        if(dir.getParentFile().canRead()){
            load(dir.getParent());
            return true;
        }
        return false;
    }

    public View getView(){
        return tabview;
    }

    public MainActivity getActivity(){
        return act;
    }
    
    public Context getContext(){
        return act.getApplicationContext();
    }
    
    private void tm(){
        ThemeManager.Theme t = ThemeManager.getTheme();
        tabview.findViewById(R.id.tabview_header).setBackgroundColor(t.header_bg);
        dirname.setTextColor(t.header_text);
        //list1.setDivider(new ColorDrawable(t.dividers));
       // reload();
      // BitmapUtils.tintIV(tabview, R.id.tabview_vmore, R.drawable.ic_bmore, t.icon);
    }  

    public void destroy(){
        //
    }

}
