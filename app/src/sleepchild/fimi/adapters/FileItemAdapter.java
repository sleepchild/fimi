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
    boolean EMTY_DIR = false;

    public FileItemAdapter(MainActivity ctx, OnFileItemClickListener l){
        clickListener = l;
        this.inflator = LayoutInflater.from(ctx);
        res = ctx.getResources();
    }

    public void update(String path){
        update(FIMI.getFiles(path));
    }

    public void update(List<MFile> list){
        EMTY_DIR = false;
        fileList = list;
        
        if(list.size()==0){
            EMTY_DIR = true;
            fileList.add(new MFile("empty"));
        }
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
        
        if(EMTY_DIR){
            View ed = inflator.inflate(R.layout.emptyfolder, null);
            ((TextView) ed.findViewById(R.id.emptyfolder_text)).setTextColor(tm.text);
            return ed;
        }
        
        final MFile fl = fileList.get(pos);
        final View v = inflator.inflate(R.layout.adapteritem_fileitem, null, false);
        
        if(pos==0){
            v.findViewById(R.id.aifi_divider).setVisibility(View.GONE);
        }
        
        TextView title = (TextView) v.findViewById(R.id.adapteritem_fileitem_title);
        title.setText(fl.getName());
        title.setTextColor(tm.text);
        
        final ImageView icon = (ImageView) v.findViewById(R.id.adapteritem_fileitem_icon);
        TextView zise = (TextView) v.findViewById(R.id.adapteritem_fileitem_size);
        zise.setTextColor(tm.text);
        
        if(fl.isFile()){
            
            zise.setText( Utils.formatSize(fl.length()) );
            zise.setTextColor(tm.text);
            
            TextView date = (TextView) v.findViewById(R.id.adapteritem_fileitem_date);
            date.setText(Utils.formatDate(fl));
            date.setTextColor(tm.text);
            
            
            if(fl.isImageFile()){
                Imgur.getThumbnail(fl.getAbsolutePath(), 120, 120, new Imgur.ResultCallback(){
                    public void onResult(Bitmap bmp){
                        if(bmp!=null){
                            if(fl.getAbsolutePath().endsWith(".png")){
                                icon.setBackgroundColor(Color.parseColor("#50cccccc"));
                            }else{
                                icon.setBackground(null);
                            }
                            icon.setImageBitmap(bmp);
                        }
                    }
                });
            }else{
                int resid = FIMI.getIcon(fl);
                Bitmap ic = VUtils.tintResource(res, resid, tm.icon);
                if(ic!=null){
                    icon.setImageBitmap(ic);
                }else{
                    icon.setImageResource(R.drawable.ic_file);
                }
                //icon.setBackgroundResource(FIMI.getIcon(fl));
            }
            
        }else if(fl.isDirectory()){
            Bitmap ic = VUtils.tintResource(res, R.drawable.ic_folder, tm.icon);
            if(ic!=null){
                icon.setImageBitmap(ic);
            }else{
                icon.setImageResource(R.drawable.ic_folder);
            }
            zise.setText(Utils.getDirectoryInfo(fl));
            
        }
        
        final ImageView icmore = (ImageView) v.findViewById(R.id.adapteritem_fileitem_vmore_icon);
        Bitmap ic = VUtils.tintResource(res, R.drawable.ic_bmore_2, tm.icon);
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
                        v.setBackgroundColor(Color.parseColor("#50cccccc"));
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
