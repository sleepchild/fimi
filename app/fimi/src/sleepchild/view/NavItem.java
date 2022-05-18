package sleepchild.view;

import android.widget.*;
import android.content.*;
import android.util.*;
import android.view.*;
import android.content.res.*;

import sleepchild.fimi.R;
import android.graphics.*;

public class NavItem extends LinearLayout
{

    public NavItem(Context context) {
        this(context,null);
    }

    public NavItem(Context context, AttributeSet attrs) {
        this(context,attrs,0);
    }

    public NavItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.ctx = context;
        getAttrs(attrs,defStyleAttr);
        init();
    }

    Context ctx;
    int backgroundIcon=0;
    int iconSize=0;
    ImageView ic;
 
    //int mWidth, mHeight;

    void getAttrs(AttributeSet attrs, int deft){
        TypedArray a = ctx.getTheme().obtainStyledAttributes(attrs, R.styleable.NavBarItem,0,0);
        backgroundIcon = a.getResourceId(R.styleable.NavBarItem_icon,0);
        iconSize = a.getDimensionPixelSize(R.styleable.NavBarItem_iconSize,0);
        a.recycle();
    }
    
    public void setIcon(int resid){
        ic.setBackgroundResource(resid);
    }
    
    public void setColor(int color){
        ic.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
    }

    void init(){
        //mWidth = getWidth() - getPaddingLeft() - getPaddingRight();
        //setLayoutParams(new ViewGroup.LayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)));
        
        LayoutInflater inflator = LayoutInflater.from(ctx);
        View parent = inflator.inflate(R.layout.customview_navbaritem,this, true);
        ic = (ImageView) parent.findViewById(R.id.customview_navbaritem_ImageView);
        
        if(iconSize!=0){
            ViewGroup.LayoutParams lp = new LayoutParams(iconSize, iconSize);
            ic.setLayoutParams(lp);
        }
        if(backgroundIcon!=0){
            ic.setImageResource(backgroundIcon);
        }
        
        
        //addView(parent);
        
    }
    
}
