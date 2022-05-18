package sleepchild.fimi.tabs;

import sleepchild.fimi.R;
import sleepchild.fimi.activity.MainActivity;
import android.widget.ListView;
import sleepchild.fimi.adapters.TabPickerAdapter;
import android.app.Dialog;
import android.view.View;
import android.widget.AdapterView;
import android.view.Gravity;
import android.widget.AbsListView.LayoutParams;
import android.graphics.drawable.ColorDrawable;
import android.graphics.Color;

public class TabPicker{
    MainActivity act;
    ListView list48;
    TabPickerAdapter adapter;

    View root;
    Dialog dlg;
    OnItemClickListener mListener;

    public TabPicker(MainActivity act){
        this.act = act;

        init();
    }

    private void init(){
        root = act.getLayoutInflater().inflate(R.layout.tabpicker, null, false);
        dlg = new Dialog(act);
        dlg.setContentView(root);

        list48 = (ListView) fv(R.id.tabpicker_list48);
        list48.postDelayed(new Runnable(){
                public void run(){
                    list48.setTranslationY(Math.round(list48.getHeight()*1.5));
                }
            },0);
        adapter = new TabPickerAdapter(act);
        adapter.setListener(new TabPickerAdapter.Listener(){
            public void onTabPickerItemSelected(int pos, Tab tab){
                if(mListener!=null){
                    mListener.onTabPickerItemSelected(pos, tab);
                }
                close();
            }
            
            public void onTabPickerItemClosed(int pos, Tab tab){
                if(tab.isCurrentTab()){
                    close();
                }
                if(mListener!=null){
                    mListener.onTabPickerItemClosed(pos, tab);
                }
                adapter.update(act.getTabManager().getTabs());
            }
        });
        list48.setAdapter(adapter);
        
    }

    public void show(){
        list48.setTranslationY(Math.round(list48.getHeight()*1.5));
        adapter.update(act.getTabManager().getTabs());

        dlg.show();

        dlg.getWindow().setGravity(Gravity.BOTTOM);
        dlg.getWindow().setLayout(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        dlg.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00009999")));
        //dlg.getWindow().getDecorView().setBackgroundColor(Color.parseColor("#009999"));
        //
        list48.postDelayed(new Runnable(){
            public void run(){
                list48.animate().translationY(0).setDuration(300).start();
            }
        },0);

    }

    public void close(){
        dlg.dismiss();
    }

    private View fv(int resid){
        return root.findViewById(resid);
    }
    
    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }
    
    public static interface OnItemClickListener{
        public void onTabPickerItemSelected(int pos, Tab tab);
        public void onTabPickerItemClosed(int pos, Tab tab);
    }

}
