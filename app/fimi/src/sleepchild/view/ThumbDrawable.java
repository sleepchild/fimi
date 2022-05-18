package sleepchild.view;
import android.graphics.drawable.*;
import android.graphics.*;

public class ThumbDrawable extends Drawable
{
    int color = -1;
    int colorp = -1;
    Rect bound;
    Paint mPaint;
    Paint mPressPaint;
    
    boolean statePressed=false;
    
    public ThumbDrawable(){
        mPaint = new Paint();
        mPressPaint = new Paint();
        bound = new Rect(0,0,0,0);
    }
    
    public ThumbDrawable(int colorNormal, int colorPressed){
        mPaint = new Paint();
        mPaint.setColor(colorNormal);
        
        mPressPaint = new Paint();
        mPressPaint.setColor(colorPressed);
        
        bound = new Rect(0,0,0,0);
        
        color = colorNormal;
        colorp = colorPressed;
    }

    @Override
    public void draw(Canvas c){
        mPaint.setColor(color);
        mPressPaint.setColor(colorp);
        
        if(statePressed){
            c.drawRect(bound, mPressPaint);
        }else{
            c.drawRect(bound, mPaint);
        }
    }
    
    public void setColor(int color){
        this.color = color;
        mPaint.setColor(color);
        invalidateSelf();
    }
    
    public void setColorPressed(int color){
        mPressPaint.setColor(color);
        colorp = color;
    }

    @Override
    public void setBounds(Rect bounds){
        bound = bounds;
        super.setBounds(bounds);
    }

    @Override
    public void setBounds(int left, int top, int right, int bottom){
        bound.set(left, top, right, bottom);
        super.setBounds(left, top, right, bottom);
    }

    @Override
    protected void onBoundsChange(Rect bounds){
        bound = bounds;
        super.onBoundsChange(bounds);
    }

    @Override
    public void setAlpha(int value)
    {
        // TODO: Implement this method
    }

    @Override
    public void setColorFilter(ColorFilter cf)
    {
        // TODO: Implement this method
    }

    @Override
    public int getOpacity()
    {
        // TODO: Implement this method
        return 0;
    }
    
    public void setPressed(boolean press){
        statePressed = press;
        invalidateSelf();
    }

}
