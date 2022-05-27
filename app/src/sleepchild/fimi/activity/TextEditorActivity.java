package sleepchild.fimi.activity;
import android.os.*;
import sleepchild.fimi.*;
import android.content.*;
import android.widget.*;
import android.view.*;
import java.io.*;
import java.util.*;

public class TextEditorActivity extends BaseActivity
{
    EditText ete;
    TextView tvtitle;
    String fname ;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        // TODO: Implement this method
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_texteditor);
        //
        ete = findView(R.id.act_texteditor_ete);
        tvtitle = findView(R.id.act_texteditor_title);
        //
        ete.postDelayed(new Runnable(){
            public void run(){
                //ete.setEnabled(true);
                runn();
            }
        },1);
        //
    }
    
    void runn(){
        Intent i = getIntent();
        if(i!=null){
            String path = Utils.clean( i.getDataString());
            getText(path);
            fname = new File(path).getName();
            tvtitle.setText(fname);
        }
    }
    
    
    
    void getText(final String path){
        //
        App.runInBackground(new Runnable(){
            public void run(){
                String dat = "";                
                try
                {
                    BufferedInputStream bs = new BufferedInputStream(new FileInputStream(path));
                    byte[] buff = new byte[8 * 1024];
                    int red=0;
                    int len=0;
                    
                    while((red = bs.read(buff,0,buff.length)) != -1){
                        len++;
                        dat += new String(buff,0,red);
                    }
                    
                    buff=null;
                    bs.close();
                }
                catch (FileNotFoundException e)
                {
                    dat = e.getMessage() + "\n" + dat;
                }catch(IOException ioe){
                    dat = ioe.getMessage()+"\n"+dat;
                }

                final String data = dat;
                App.runOnUiThread(new Runnable(){
                    public void run(){
                        ete.setText(data);
                        ete.setEnabled(true);
                        findViewById(R.id.act_texteditor_loader).setVisibility(View.GONE);
                    }
                });
            }
        });
    }
    
}
