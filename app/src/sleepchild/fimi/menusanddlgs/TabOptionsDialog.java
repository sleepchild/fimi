package sleepchild.fimi.menusanddlgs;

import android.app.Dialog;
import android.view.View;
import android.view.Gravity;
import sleepchild.fimi.activity.MainActivity;
import sleepchild.fimi.tabs.Tab;

import sleepchild.fimi.R;
import sleepchild.fimi.*;
import sleepchild.fimi.activity.*;

public class TabOptionsDialog extends MDialog// implements View.OnClickListener
{
    Tab tab;
    MainActivity act;

    public TabOptionsDialog(MainActivity act){
        super(act);
        this.act = act;
        setContentView(R.layout.dlg_tab_options);
        //
        setClickable(R.id.taboptionsmenu_newtab);
        setClickable(R.id.taboptionsmenu_closetab);
        setClickable(R.id.taboptionsmenu_exitapp);
        setClickable(R.id.taboptionsmenu_settings);
    }

    public void show(Tab tab){
        this.tab = tab;
        show();
    }

    @Override
    public void show(){
        super.show();
        getWindow().setGravity(Gravity.BOTTOM|Gravity.RIGHT);

    }

    @Override
    public void onClick(View v)
    {
        int id = v.getId();
        switch(id){
            case R.id.taboptionsmenu_newtab:
                act.getTabManager().newTab(Utils.DEFAULT_START_PATH);
                break;
            case R.id.taboptionsmenu_closetab:
                act.getTabManager().closeTab(tab);
                break;
            case R.id.taboptionsmenu_exitapp:
                act.finishAffinity();
                break;
            case R.id.taboptionsmenu_settings:
                act.startActivity(SettingsActivity.class);
                break;
        }
        dismiss();
    }




}
