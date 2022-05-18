package sleepchild.fimi.menusanddlgs;

import sleepchild.fimi.activity.MainActivity;
import sleepchild.fimi.R;
import android.widget.ActionMenuView.*;
import java.io.*;
import android.widget.*;
import android.view.*;
import sleepchild.fimi.*;
import sleepchild.fimi.tabs.*;

public class FileItemOptions extends MDialog
{
    File file;
    LinearLayout optionsContainer;
    MainActivity act;
    Tab tab;
    
    public FileItemOptions(MainActivity act, File file, Tab tab){
        super(act, R.layout.dlg_fileitem_options);
        this.act = act;
        this.file = file;
        this.tab = tab;
        //
        setText(R.id.dlg_fileitem_options_title, file.getName() );
        optionsContainer = findView(R.id.dlg_fileitem_options_container);
        
        prepOptions();
        
    }
    
    void prepOptions(){
        //
        addItem(itm.DETAILS);
        addItem(itm.RENAME);
        addItem(itm.COPY);
        addItem(itm.MOVE);
        
        if(file.isDirectory()){
            addItem(itm.OPEN_NEW_TAB);
        }else{
            addItem(itm.SHARE);
        }
        addItem(itm.DELETE);
    }
    
    void addItem(String title){
        View p = getLayoutInflater().inflate(R.layout.text_view, null, false);
        TextView tv = (TextView) p.findViewById(R.id.text_view_textview);
        tv.setText(title);
        p.setOnClickListener(this);
        p.setTag(title);
        optionsContainer.addView(p);
    }

    @Override
    public void onClick(View v){
        String tag = v.getTag().toString();
        switch(tag){
            case itm.COPY:
                //
                act.showPicker();
                break;
            case itm.MOVE:
                //
                break;
            case itm.OPEN_NEW_TAB:
                act.getTabManager().newTab(file.getAbsolutePath());
                break;
            case itm.DELETE:
                String fd = "file";
                if(file.isDirectory()){
                    fd = "folder";
                }
                new ConfirmationDialog(act)
                    .setTitle("Are you sure to delete "+fd+"?")
                    .setMessage(file.getName()+"\n- "+file.getAbsolutePath())
                    .positiveText("delete")
                    .onConfirm(new ConfirmationDialog.onConfirmListener(){
                        public void onConfirm(){
                            FIMI.delete(file);
                            tab.reload();
                            act.toast("file deleted!");
                        }
                    })
                    .show();
                break;
        }
        dismiss();
    }

    @Override
    public void show(){
        super.show();
    }
    
    
    private final class itm{
        final static String COPY = "copy";
        final static String MOVE = "move";
        final static String RENAME = "rename";
        final static String DELETE = "delete";
        final static String DETAILS = "details";
        
        // files
        final static String SHARE = "share...";
        
        // dirs
        final static String OPEN_NEW_TAB = "open in new tab";
        
        
    }
    
    
}
