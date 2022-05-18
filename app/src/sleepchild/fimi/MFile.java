package sleepchild.fimi;

import java.io.File;
import android.graphics.*;
import android.graphics.drawable.*;

// todo: depricate
public class MFile extends File
{
    public Bitmap icons ;
    public Drawable icon;
    
    public MFile(String path){
       super(path); 
    }
    
    public MFile(File fl){
        super(fl.getAbsolutePath());
    }
    
    public boolean isAudioFile(){
        return FIMI.isAudio(this);
    }
    
    public boolean isImageFile(){
        return FIMI.isImage(this);
    }
    
    public boolean isVideoFile(){
        return FIMI.isVideo(this);
    }
    
    public boolean isArchiveFile(){
        return FIMI.isArchive(this);
    }
    
    public MFile getMParent(){
        return new MFile(getParent());
    }
    
}
