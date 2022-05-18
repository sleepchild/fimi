package sleepchild.fimi.menusanddlgs;
import sleepchild.fimi.activity.*;
import java.io.*;
import sleepchild.fimi.*;

public class FileDetailsDialog extends MDialog
{
    private File file;
    private MainActivity act;
    FileDetailsDialog(MainActivity act, File file){
        super(act, R.layout.dlg_filedetails);
        this.file = file;
        this.act = act;
        //
        
          
    }
    
    
}
