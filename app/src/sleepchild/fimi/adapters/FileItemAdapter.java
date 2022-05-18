package sleepchild.fimi.adapters;

//public class 
import android.widget.*;
import android.view.*;
import java.io.*;
import java.util.*;
import android.content.*;
import android.graphics.*;
import android.graphics.drawable.*;
import sleepchild.fimi.activity.*;
import sleepchild.fimi.*;
import sleepchild.fimi.utils.*;
import android.content.res.*;

public class FileItemAdapter extends BaseAdapter
{
    private List<MFile> fileList = new ArrayList<>();
    private LayoutInflater inflator;
    OnFileItemClickListener clickListener;
    Resources res;

    public FileItemAdapter(MainActivity ctx, OnFileItemClickListener l){
        clickListener = l;
        this.inflator = LayoutInflater.from(ctx);
        res = ctx.getResources();
    }

    public void update(String path){
        update(FIMI.getFiles(path));
    }

    public void update(List<MFile> list){
        fileList = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount(){
        return fileList.size();
    }

    @Override
    public MFile getItem(int pos){
        return fileList.get(pos);
    }

    @Override
    public long getItemId(int p1){
        return p1;
    }

    //View v;
    @Override
    public View getView(final int pos, final View p2, ViewGroup p3){

        ThemeManager.Theme tm = ThemeManager.getTheme();
        
        final MFile fl = fileList.get(pos);
        final View v = inflator.inflate(R.layout.adapteritem_fileitem, null, false);
        TextView title = (TextView) v.findViewById(R.id.adapteritem_fileitem_title);
        title.setText(fl.getName());
        title.setTextColor(tm.text);
        
        final ImageView icon = (ImageView) v.findViewById(R.id.adapteritem_fileitem_icon);
        
        if(fl.isFile()){
            TextView zise = (TextView) v.findViewById(R.id.adapteritem_fileitem_details);
            String sp = Utils.formatSize(fl.length());
            zise.setText(sp);
            zise.setTextColor(tm.text);
            
            if(fl.isImageFile()){
                Imgur.getThumbnail(fl.getAbsolutePath(), 120, 120, new Imgur.ResultCallback(){
                    public void onResult(Bitmap bmp){
                        if(bmp!=null){
                            icon.setBackground(null);
                            icon.setImageBitmap(bmp);
                        }
                    }
                });
            }else{
                int resid = FIMI.getIcon(fl);
                Bitmap ic = BitmapUtils.tintResource(res, resid, tm.icon);
                if(ic!=null){
                    icon.setImageBitmap(ic);
                }else{
                    icon.setImageResource(R.drawable.ic_file);
                }
                //icon.setBackgroundResource(FIMI.getIcon(fl));
            }
            
        }else if(fl.isDirectory()){
            Bitmap ic = BitmapUtils.tintResource(res, R.drawable.ic_folder, tm.icon);
            if(ic!=null){
                icon.setImageBitmap(ic);
            }else{
                icon.setImageResource(R.drawable.ic_folder);
            }
        }
        
        final ImageView icmore = (ImageView) v.findViewById(R.id.adapteritem_fileitem_vmore_icon);
        Bitmap ic = BitmapUtils.tintResource(res, R.drawable.ic_bmore_2, tm.icon);
        if(ic!=null){
            icmore.setImageBitmap(ic);
        }else{
            icmore.setImageResource(R.drawable.ic_bmore_2);
        }
        
        v.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if(clickListener!=null){
                    clickListener.onFileItemClick(fl, pos);
                }
            }
        });
        
        v.setOnLongClickListener(new View.OnLongClickListener(){
            public boolean onLongClick(View v){
                if(clickListener!=null){
                    return clickListener.onFileItemLongClick(fl);
                }
                return false;
            }
        });
        
        v.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View p1, MotionEvent ev)
            {
                int a = ev.getAction();
                switch(a){
                    case MotionEvent.ACTION_DOWN:
                        v.setBackgroundColor(Color.parseColor("#30000000"));
                        break;
                    default:
                        v.setBackgroundColor(Color.TRANSPARENT);
                        break;
                }
                return false;
            }
        });

        v.findViewById(R.id.adapteritem_fileitem_vmore).setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if(clickListener!=null){
                    clickListener.onShowFileOptions(fl, pos);
                }
            }
        });
        
        v.setTag(fl);
        return v;
    }
    
    public interface OnFileItemClickListener{
        public void onFileItemClick(MFile fl, int pos);
        public boolean onFileItemLongClick(MFile fl);
        public void onShowFileOptions(MFile fl, int pos);
    }



}
