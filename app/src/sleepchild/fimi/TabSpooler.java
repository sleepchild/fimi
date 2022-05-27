package sleepchild.fimi;

import java.util.*;
import android.webkit.*;
import java.io.*;
import org.json.*;
import sleepchild.fimi.tabs.*;

/* 
* save/delete/restore tabs across app restart/ re-launch and even full re-installs
*/
public class TabSpooler
{
    private String tabsDirp;
    private File tabsDirFile;
    private String lidPath = "";
    
    private static volatile TabSpooler deft;
    private TabSpooler(){
        tabsDirp = FIMI.getFimiDirectory() + "/tabs/m1/";
        tabsDirFile = new File(tabsDirp);
        lidPath = FIMI.getFimiDirectory()+"/tabs/ltidx";
    }
    
    private enum K{
        ID,
        LAST_PATH,
        LAST_SHOWN_TAB,
    }
    
    private String k(K key){
        return key.toString();
    }
    
    public static TabSpooler get(){
        TabSpooler inst = deft;
        if(inst==null){
            synchronized(TabSpooler.class){
                inst = TabSpooler.deft;
                if(inst==null){
                    inst = TabSpooler.deft = new TabSpooler();
                }
            }
        }
        return inst;
    }
    
    public synchronized void saveTab(Tab tab){
        String fname = getPath(tab);
        File f = new File(fname);
        if(f.exists()){
            f.delete();
        }
        
        JSONObject o = new JSONObject();
        try
        {
            o.put(k(K.ID), tab.getId());
            o.put(k(K.LAST_PATH), tab.getDir().getAbsolutePath());
            
            _write(fname, o.toString().getBytes());
            
        }catch (JSONException e){}

    }
    
    public synchronized void removeTab(Tab tab){
        String path = getPath(tab);
        if(exists(path)){
            delete(path);
        }
    }
    
    
//  /////////////
 
    public List<Tab> getPreviousTabs(TabManager tmgr){
        List<Tab> list = new ArrayList<>();
        if(exists(tabsDirp)){
            for(File fl : tabsDirFile.listFiles()){
                Tab t = fileToTab(tmgr, fl);
                if(t!=null){
                    list.add(t);
                }
            }
        }
        return list;
    }
    
    
    /////////////
    
    private Tab fileToTab(TabManager tmgr ,File fl){
        String json = _read(fl);
        Tab tab = tmgr.getNewTab();
        
        try
        {
            JSONObject o = new JSONObject(json);
            
            String path = o.getString(k(K.LAST_PATH));
            long id = o.getLong(k(K.ID));
            
            tab.setId(id);
            tab.load(path);
            
            return tab;
            
        }
        catch (JSONException e)
        {
            XApplication.toast(e.getMessage());
        }
        return null;
    }
    
    private void _write(String path, byte[] data){
        try
        {
            FileOutputStream o = new FileOutputStream(path);
            o.write(data);
            o.flush();
            o.close();
        }
        catch (IOException e)
        {}
    }
    
    private String _read(File fl){
        String data="";
        try{
            BufferedReader br = new BufferedReader(new FileReader(fl));
            String line;
            while( (line = br.readLine()) !=null){
                data += line +"\n";
            }
            br.close();
        }
        catch (IOException e){
            data = null;
        }
        
        return data;

    }
    
    private String getPath(Tab tab){
        tabsDirFile.mkdirs();
        return tabsDirp+tab.getId();
    }
    
    private boolean exists(String path){
        return new File(path).exists();
    }
    
    private boolean delete(String path){
        return new File(path).delete();
    }
    
    
}
