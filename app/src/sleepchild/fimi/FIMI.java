package sleepchild.fimi;
import java.util.*;
import java.io.*;
import android.graphics.drawable.*;
import android.content.*;
import android.graphics.*;
import android.os.*;

public class FIMI
{
    public static List<MFile> getFiles(String path){
        return getFiles(new File(path));
    }
    
    public static List<MFile> getFiles(File dir){
        List<MFile> list = new ArrayList<>();
        List<MFile> flist = new ArrayList<>();
        List<MFile> dlist = new ArrayList<>();
        
        if(dir.isDirectory()){
            for(File fl : dir.listFiles()){
                if(fl.isDirectory()){
                    dlist.add(new MFile(fl));
                }else{
                    flist.add(new MFile(fl));
                }
            }
            
            Utils.sort_mfile_09AZ(dlist);
            Utils.sort_mfile_09AZ(flist);
            
            list.addAll(dlist);
            list.addAll(flist);
            
        }
        return list;
    }
    
    public static String getFimiDirectory(){
        String dirp = Environment.getExternalStorageDirectory().getAbsolutePath();
        dirp += "/.sleepchild/fimi/";
        
        return dirp;
    }
    
    // remove
    private static void getThumbnail(final MFile fl,final float scale, final ValueCallback cb){
        App.queueWork(new Runnable(){
            public void run(){
                Bitmap bmp = BitmapFactory.decodeFile(fl.getAbsolutePath());
                //float scale = 0.2f;
                if(bmp.getWidth()>120 || bmp.getHeight()>120){
                    int w = Math.round(bmp.getWidth() * scale);
                    int h = Math.round(bmp.getHeight() * scale);
                    bmp = bmp.createScaledBitmap(bmp, w, h, false);
                }
                
                final Bitmap copy = bmp.copy(bmp.getConfig(), true);
                
                App.runOnUiThread(new Runnable(){
                    public void run(){
                        cb.onValue(copy);
                    }
                });
            }
        });
    }
    
    // remove
    private static Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(
            bm, 0, 0, width, height, matrix, false);
        //bm.recycle();
        return resizedBitmap;
    }
    
    public static int getIcon(MFile file){
        if(isAudio(file)){
            return R.drawable.ic_audiotrack_grey600_24dp;
        }else if(isImage(file)){
            return R.drawable.ic_image_grey600_24dp;
        }else if(isVideo(file)){
            return R.drawable.ic_movie_grey600_24dp;
        }else if(isArchive(file)){
            String n = file.getName();
            if(n.endsWith(".zip")){
                return R.drawable.ic_archive_zip;
            }if(n.endsWith(".gzip")||n.endsWith(".gz")){
                return R.drawable.ic_archive_gzip;
            }if(n.endsWith(".7z") ||n.endsWith(".7zip")){
                return R.drawable.ic_archive_7zip;
            }
            return R.drawable.ic_archive;
        }else{
            return R.drawable.ic_file;
        }
    }
    
    public static void delete(File fl){
        if(fl.isDirectory()){
            for(File f : fl.listFiles()){
                delete(f);
            }
        }
        fl.delete();
    }
    
    public static boolean isAudio(MFile fl){
        for(String ext : new String[]{".mp3",".wav",".ogg",".wma"}){
            if(fl.getName().toLowerCase().endsWith(ext)){
                return true;
            }
        }
        return false;
    }
    
    public static boolean isImage(File file){
        return isImage(file.getAbsolutePath());
    }
    
    public static boolean isImage(String path){
        for(String ext : new String[]{".jpeg",".jpg",".png",".webp",".gif"}){
            if(path.toLowerCase().endsWith(ext)){
                return true;
            }
        }
        return false;
    }
    
    public static boolean isVideo(MFile fl){
        for(String ext : new String[]{".mp4",".avi"}){
            if(fl.getName().toLowerCase().endsWith(ext)){
                return true;
            }
        }
        return false;
    }
    
    public static boolean isArchive(MFile fl){
        for(String ext : new String[]{".zip",".jar",".7z",".7zip",".gz",".gzip",".tar"}){
            if(fl.getName().toLowerCase().endsWith(ext)){
                return true;
            }
        }
        return false;
    }
    
    public static interface ValueCallback{
        public void onValue(Object obj);
    }
    
}
