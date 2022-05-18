package sleepchild.fimi;
import android.content.*;
import android.preference.*;

public class SPrefs
{
    private SharedPreferences prefs;
    private SharedPreferences.Editor edit;
    
    public SPrefs(Context ctx){
        prefs = PreferenceManager.getDefaultSharedPreferences(ctx);
        edit = prefs.edit();
    }
    
////////////////////////  
////////////////////////  
    
    public boolean getLoadPreviousTabs(){
        return getBool(K.LOAD_PREV_TABS, true);
    }
    
    public void setLastShownTabId(long tid){
        saveLong(K.LAST_SHOWN_TAB_ID, tid);
    }
    
    public long getLastShownTabId(){
        return getLong(K.LAST_SHOWN_TAB_ID, 0);
    }
    
    
//////////////////////////    
    ///////////Private/////////////
    
    private enum K{
        LOAD_PREV_TABS,
        LAST_SHOWN_TAB_ID
    }
    
    String ks(K key){
        return key.toString();
    }
    
    
    private boolean getBool(K key, boolean deft){
        return prefs.getBoolean(ks(key), deft);
    }
    
    private boolean saveBool(K key, boolean value){
        return edit.putBoolean(ks(key), value).commit();
    }
    
    private long getLong(K key, long deft){
        return prefs.getLong(ks(key), deft);
    }
    
    private boolean saveLong(K key, long value){
        return edit.putLong(ks(key), value).commit();
    }
    
    
    
}
