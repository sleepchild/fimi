package sleepchild.view;

import android.widget.ImageView;
import android.content.Context;
import android.util.*;
import android.view.*;
import android.graphics.*;

public class ScaleImageView extends ImageView
{
    
    public ScaleImageView(Context ctx){
        super(ctx);
        this.ctx = ctx;
        init();
    }
    
    public ScaleImageView(Context ctx, AttributeSet attrs){
        this(ctx, attrs,0);
    }
    
    public ScaleImageView(Context ctx, AttributeSet attrs, int deft){
        super(ctx, attrs, deft);
        this.ctx = ctx;
        setAttrs();
        init();
    }
    
    private Context ctx;
    ScaleGestureDetector mScaleGesture;
    float scalefactor = 1.0f;
    
    
    private void setAttrs(){
        //
        
    }
    
    private void init(){
        //
        mScaleGesture = new ScaleGestureDetector(ctx, new ScaleListener());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        return mScaleGesture.onTouchEvent(event);
    }
    
    class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener{
        
        @Override
        public boolean onScale(ScaleGestureDetector detector)
        {
            scalefactor *= mScaleGesture.getScaleFactor();
            scalefactor = Math.max(0.5f, Math.min(scalefactor, 1.5f));
            zoom(scalefactor, scalefactor);
            return super.onScale(detector);
        }

        @Override
        public void onScaleEnd(ScaleGestureDetector detector)
        {
            // TODO: Implement this method
            super.onScaleEnd(detector);
            //zoom(scalefactor, scalefactor);
        }                 
        
        
        
    }
    
    public void clearZoom(){
        zoom(1.0f,1.0f);
    }
    
    public void zoom(float x, float y){
        
        setScaleX(x);
        setScaleY(y);
    }

    @Override
    public void setImageBitmap(Bitmap bm)
    {
        super.setImageBitmap(bm);
        clearZoom();
    }
    
    
    
}
