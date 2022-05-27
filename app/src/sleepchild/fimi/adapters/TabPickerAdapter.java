package sleepchild.fimi.adapters;

import android.widget.*;
import android.view.*;
import android.content.*;
import java.util.*;
import sleepchild.fimi.activity.*;
import sleepchild.fimi.tabs.*;
import sleepchild.fimi.*;
import android.graphics.*;
import sleepchild.fimi.utils.*;

public class TabPickerAdapter extends BaseAdapter{

    Context ctx;
    List<Tab> tablist = new ArrayList<>();
    LayoutInflater inf;
    Listener mlistener;
    ThemeManager.Theme tm;

    public TabPickerAdapter(Context ctx){
        this.ctx = ctx;
        inf = LayoutInflater.from(ctx);
    }

    public void update(List<Tab> list){
        tm = ThemeManager.getTheme();
        tablist = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount(){
        return tablist.size()+1;
    }

    @Override
    public Tab getItem(int pos){
        return tablist.get(pos);
    }

    @Override
    public long getItemId(int p1){
        return p1;
    }

    @Override
    public View getView(final int pos, View v, ViewGroup p3)
    {
        if(pos==getCount()-1){
            return nt();
        }
        
        final Tab tab = getItem(pos);

        v = inf.inflate(R.layout.adapteritem_tabpickeritem, null, false);
        View container = v.findViewById(R.id.adapteritem_tabpickeritem_container);
        View closer = v.findViewById(R.id.adapteritem_tabpickeritem_closer);
        TextView title = (TextView) v.findViewById(R.id.adapteritem_tabpicker_title);
        title.setText(tab.getDir().getAbsolutePath());
        
        container.setBackgroundColor(tm.text);
        title.setTextColor(tm.background);
        VUtils.tintIV(v, R.id.i1, tm.background);
        
        if(tab.isCurrentTab()){
            container.setBackgroundColor(tm.background);
            //int col = tm.text;// ctx.getResources().getColor(R.color.currentTabText);
            //Utils.addBorder(container, 6, col);
            title.setTextColor(tm.text);
            VUtils.tintIV(v, R.id.i1, tm.text);
        }
        
        v.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if(mlistener!=null){
                    mlistener.onTabPickerItemSelected(pos, tab);
                }
            }
        });
        
        closer.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if(mlistener!=null){
                    mlistener.onTabPickerItemClosed(pos, tab);
                }
            }
        });
        
        return v;
    }
    
    View nt(){// new tab
        View v = inf.inflate(R.layout.text_view, null, false);
        //v.setBackgroundColor(Color.TRANSPARENT);
        TextView tv = (TextView) v.findViewById(R.id.text_view_textview);
        tv.setText("New Tab");
        tv.setTextColor(Color.WHITE);
        tv.setGravity(Gravity.CENTER_HORIZONTAL);
        v.setOnClickListener(new View.OnClickListener(){
            public void onClick(View t){
                if(mlistener!=null){
                    mlistener.onTabPickerNewTab();
                }
            }
        });
        return v;
    }
    
    public void setListener(Listener l){
        mlistener = l;
    }
    
    public static interface Listener{
        public void onTabPickerItemSelected(int pos, Tab tab);
        public void onTabPickerItemClosed(int pos, Tab tab);
        public void onTabPickerNewTab();
    }

}
