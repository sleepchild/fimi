package sleepchild.fimi.menusanddlgs;
import android.app.*;
import java.io.*;
import android.content.*;

public class ConfirmationDialog
{
    // File fl;
    Activity act;
    AlertDialog.Builder b;
    AlertDialog dlg;
    String title = "";
    String message = "";
    onConfirmListener listener;
    String txtp="ok", txtn="cancel";
    
    public ConfirmationDialog(Activity act){
        this.act = act;
    }
    
    public ConfirmationDialog(Activity act, onConfirmListener l){
        this.act = act;
        listener = l;
    }
    
    public ConfirmationDialog(Activity act, String title, String message){
        this.act = act;
        this.title = title;
        this.message = message;
    }
    
    public ConfirmationDialog(Activity act, String title, String message, onConfirmListener l){
        this.act = act;
        this.title = title;
        this.message = message;
    }
    
    public ConfirmationDialog setTitle(String text){
        title = text;
        return this;
    }
    
    public ConfirmationDialog setMessage(String text){
        message = text;
        return this;
    }
    
    public ConfirmationDialog onConfirm(onConfirmListener l){
        listener = l;
        return this;
    }
    
    public ConfirmationDialog positiveText(String text){
        txtp = text;
        return this;
    }
    
    public ConfirmationDialog negativeText(String text){
        txtn = text;
        return this;
    }
    
    private void init(){
        AlertDialog.Builder b = new AlertDialog.Builder(act);
        b.setTitle(title);
        b.setMessage(message);
        b.setPositiveButton(txtp, new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface p1, int p2)
            {
                if(listener!=null){
                    listener.onConfirm();
                }
                dlg.dismiss();
            }
        });
        b.setNegativeButton(txtn, new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface p1, int p2)
            {
                dlg.dismiss();
            }
        });
        dlg = b.create();
    }
    
    public void show(){
        init();
        dlg.show();
    }
    
    interface onConfirmListener{
        public void onConfirm();
    }
}
