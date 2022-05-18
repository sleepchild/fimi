package sleepchild.fimi.adapters;

import android.widget.*;
import android.view.*;
import android.content.*;
import java.util.*;
import sleepchild.fimi.activity.*;
import sleepchild.fimi.tabs.*;
import sleepchild.fimi.*;

public class TabPickerAdapter extends BaseAdapter{

    Context ctx;
    List<Tab> tablist = new ArrayList<>();
    LayoutInflater inf;
    Listener mlistener;

    public TabPickerAdapter(Context ctx){
        this.ctx = ctx;
        inf = LayoutInflater.from(ctx);
    }

    public void update(List<Tab> list){
        tablist = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount(){
        return tablist.size();
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
        final Tab tab = getItem(pos);

        v = inf.inflate(R.layout.adapteritem_tabpickeritem, null, false);
        View container = v.findViewById(R.id.adapteritem_tabpickeritem_container);
        
        TextView title = (TextView) v.findViewById(R.id.adapteritem_tabpicker_title);
        title.setText(tab.getDir().getAbsolutePath());
        //
        if(tab.isCurrentTab()){
            int col = ctx.getResources().getColor(R.color.primary);
            Utils.addBorder(container, 6, col);
            title.setTextColor(col);
        }
        
        v.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if(mlistener!=null){
                    mlistener.onTabPickerItemSelected(pos, tab);
                }
            }
        });
        
        View closer = v.findViewById(R.id.adapteritem_tabpickeritem_closer);
        closer.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if(mlistener!=null){
                    mlistener.onTabPickerItemClosed(pos, tab);
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
    }

}
