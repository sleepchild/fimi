package sleepchild.fimi;
import android.widget.*;
import android.content.*;
import android.view.*;
import android.graphics.drawable.*;
import android.graphics.*;
import java.text.*;
import java.util.*;
import java.io.*;
import sleepchild.fimi.tabs.*;
import android.content.res.*;

public class Utils {
    //
    public static final String DEFAULT_START_PATH = "/sdcard/";
    
    public Utils(){
        //
    }
    
    public static void addBorder(View v, int width, int color){
        if(v==null)return;
        Drawable m = v.getBackground();
        Integer bgcol = null;
        if(m!=null && m instanceof ColorDrawable){
            bgcol = ((ColorDrawable) m).getColor();
        }
        GradientDrawable d = new GradientDrawable();
        if(bgcol!=null){
            d.setColor(bgcol);
        }
        d.setStroke(width, color);
        v.setBackground(d);
    }
    
    public static Bitmap getBitmap(String path){
        Bitmap b = BitmapFactory.decodeFile(path);
        if(b!=null){
            return b;
        }
        return null;
    }
    
    public static int color(String color){
        return Color.parseColor(color);
    }
    
    public static int color(int r, int g, int b){
        return Color.rgb(r, g, b);
    }
    
    public static int color(int a, int r, int g, int b){
        return Color.argb(a, r, g, b);
    }
    
    public static String formatSize(long bytes){
        long b = bytes;
        long kb = 1024;
        long mb = kb*kb ;
        long gb = kb*kb*kb;
        long tb = kb*kb*kb*kb;
        
        DecimalFormat df = new DecimalFormat("0.00");
        
        if(b<kb){
            if(b==0){
                return b+"B";
            }
            return df.format( b) +"B";
        }else if(b<(mb)){
            return df.format( b/ (float) kb) +"KB";
        }else if(b<(gb)){
            return df.format( b/ (float) mb) +"MB";
        }else if(b<(tb)){
            return df.format( b/ (float) gb) +"GB";
        }
        return df.format( b)+"B";
    }
    
    // /*
    
    public static void sort_string_09AZ(List<String> list){
        Collections.sort(list, new Comparator<String>(){
            @Override
            public int compare(String p1, String p2)
            {
                return p1.compareToIgnoreCase(p2);
            }
        });
    }
    
    //*
    public static void sort_mfile_09AZ(List<MFile> list){
        Collections.sort(list, new Comparator<MFile>(){
            @Override
            public int compare(MFile p1, MFile p2){
                return p1.getName().compareToIgnoreCase(p2.getName());
            }
        });
    }
    
    /* sort tabs by date; latest tab at the bottom
    */
    public static void sort_tabs_date_asc(List<Tab> list){
        Collections.sort(list, new Comparator<Tab>(){
            @Override
            public int compare(Tab p1, Tab p2){
                return (p1.getId()+"").compareToIgnoreCase((""+p2.getId()));
            }
        });
    }
    
    
    //*/
    
    public static void sort_file_09az(List<File> list){
        Collections.sort(list, new Comparator<File>(){
            @Override
            public int compare(File p1, File p2)
            {
                return p1.getName().compareToIgnoreCase(p2.getName());
            }
        });
    }
    
    //*/
    
    public static String getDirectoryInfo(File fl){
        if(fl.isDirectory()){
            int d=0;
            int f=0;
            for(File j : fl.listFiles()){
                if(j.isFile()){
                    f++;
                }else{
                    d++;
                }
            }
            return d+(d==1 ? " folder, ":" folders, ") +f+ (f==1 ? " file":" files");
            
        }
        return  "";
    }
    
    public static String formatDate(File fl){
        return new java.util.Date(fl.lastModified()).toLocaleString().toLowerCase();
        //
    }
    
    public static boolean touch(String path, String text, boolean overwrite){
        try{
            if(overwrite==false && new File(path).exists()){
                return false;
            }
            /*
            if(overwrite==true && new File(path).exists()){
                new File(path).delete();
            }
            //*/
            FileOutputStream o = new FileOutputStream(path);
            o.write(text.getBytes());
            o.flush();
            o.close();
            return true;
            
        }catch (IOException e){}finally{
            //
        }
        return false;
    }
    
    public static String readText(String path){
        BufferedReader br = null;
        String data = "";
        try{
            br = new BufferedReader(new FileReader(path));
            String line;
            while((line = br.readLine()) != null){
                data+=line+"\n";
            }
        }catch (IOException e){
            data = null;
        }finally{
            if(br!=null){
                try{
                    br.close();
                }catch (IOException e){}
            }
        }
        return data;
    }
    
    public static int dp2px(final float dpValue) {
        final float scale = Resources.getSystem().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
    
    public static String clean(String str){
        String ret = str;
        if(str.startsWith("file://")){
            ret = str.substring(7);
        }
        ret = ret.replace("%20"," ").replace("%26","&");

        return ret;
    }
    
    public static String readTextFile(String path){
        if(path.startsWith("file://")){
            path = path.substring(7);
        }
        BufferedReader br = null;
        try
        {
            br = new BufferedReader(new FileReader(path));
            String line;
            String data="";
            while((line = br.readLine()) != null){
                data+=line+"\n";
            }
            br.close();
            return data;
        }
        catch (FileNotFoundException e)
        {}catch(IOException ioe){}
        finally{
            if(br!=null){try{br.close();}catch(IOException ioe){}}
        }
        return "";
    }
    
    public static void touch(String path, String data){
        touch(path, data.getBytes());
    }
    
    public static void touch(String path, byte[] data){
        FileOutputStream o = null;
        try
        {
            o = new FileOutputStream(path);
            o.write(data);
            o.flush();
            o.close();
        }
        catch (FileNotFoundException e)
        {}catch(IOException ioe){}
        finally{
            if(o!=null){try{o.close();}catch(IOException ioe){}}
        }
    }
    
}
