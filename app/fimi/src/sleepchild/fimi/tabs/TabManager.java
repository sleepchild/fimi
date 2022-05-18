package sleepchild.fimi.tabs;

import java.util.*;
import android.widget.*;
import android.view.*;
import sleepchild.fimi.activity.*;
import sleepchild.fimi.tabs.*;
import sleepchild.fimi.*;

public class TabManager
{
    MainActivity act;
    private FrameLayout tabHolder;
    private List<Tab> tablist = new ArrayList<>();
    private Tab currentTab;
    List<TabEvents> tabevents = new ArrayList<>();
    private boolean lodld;

    public TabManager(MainActivity ctx){
        this.act = ctx;
        tabHolder = (FrameLayout) act.findViewById(R.id.main_act_tabholder);
        //
    }

    //
    public boolean loadOldTabs(){
        if(lodld) return false;
        lodld=true;
        return getPreviousTabs();
    }

    public void addTabEventsListener(TabEvents listener){
        tabevents.add(listener);
    }

    public void removeTabEventsListener(TabEvents listener){
        tabevents.remove(listener);
    }

    public Tab newTab(String startPath){
        Tab tab = createTab(startPath);
        _show(tab);
        return tab;
    }

    public void newBackgroundTab(String startPath){
        Tab tab = createTab(startPath);
        //
    }

    public void show(Tab tab){
        _show(tab);// why??
    }

    public void closeTab(Tab tab){
        removeTabInternal(tab);
    }

    public List<Tab> getTabs(){
        return tablist;
    }

    public Tab getCurrent(){
        return currentTab;
    }

    public boolean handlesBack(){
        if(currentTab!=null){
            return(currentTab.goBack());
        }
        return false;
    }
    
    public void reload(){
        if(currentTab!=null){
            currentTab.reload();
        }
    }
    
    //// 
    private Tab createTab(String path){
        Tab tab = new Tab(act, this);
        tab.load(path);
        tablist.add(tab);
        notifyTabCountChange();
        return tab;
    }

    private void removeTabInternal(Tab tab){
        int ix = tablist.indexOf(tab);
        tablist.remove(tab);

        //*
        if(currentTab == tab){
            if(tablist.size()==0){
                currentTab = createTab(Utils.DEFAULT_START_PATH);
            }else{
                int m = tablist.size()-1;

                if(ix>m){
                    currentTab = tablist.get(m);
                }else{
                    currentTab = tablist.get(ix);
                }
            }
            show(currentTab);
        }
        //*/

        TabSpooler.get().removeTab(tab);
        tab.destroy();
        
        tab = null;

        notifyTabCountChange();
        //
    }

    public Tab getNewTab(){
        Tab tab = new Tab(act, this);
        return tab;
    }

    private void _show(Tab tab){
        try{
            tabHolder.removeAllViews();
            tabHolder.addView(tab.getView());
            currentTab = tab;
            act.getPrefs().setLastShownTabId(tab.getId());
            tab.reload();
        }catch(Exception e){}
    }

    private void notifyTabCountChange(){
        for(TabEvents l : tabevents){
            l.onTabCountChanged(tablist.size());
        }
    }

    private boolean getPreviousTabs(){
        List<Tab> list = TabSpooler.get().getPreviousTabs(this);
        Tab mTab = null;
        long lid = act.getPrefs().getLastShownTabId();
        
        if(list!=null && !list.isEmpty()){
            tablist.addAll(list);
            if(lid>0){
                for(Tab t : tablist){
                    if(t.getId()==lid){
                        mTab = t;
                        break;
                    }
                }
            }
            Utils.sort_tabs_date_asc(tablist);
            
            if(mTab==null){
                mTab = tablist.get(tablist.size()-1);
            }
            
            _show(mTab);
            notifyTabCountChange();
            return true;
        }else{
            return false;
        }
    }

    public static interface TabEvents{
        public void onTabCountChanged(int total);
    }
}
