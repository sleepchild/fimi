package sleepchild.fimi;
import java.io.*;
import java.util.*;

public class LogUtils
{
    static String logpathConsumable = "/sdcard/.sleepchild/fimi/logs/c/";
    private static boolean lcexOFF=false;
    public static void logConsumableException(String tag, Exception e){
        if(lcexOFF){
            return;
        }
        new File(logpathConsumable).mkdirs();
        String date = new Date().toGMTString().toLowerCase().replace(" ","_");
        String path = logpathConsumable+"w_"+date+".txt";
        //
        String data = "AuPod - warning - "+date+"\n\n"
            //+"This is classified as a warning because the exception was caught and either handled or igonred;\nthe following are the details;\n\n"
            +"TAG: "+tag+":\n";

        data+= "class:: "+e.getClass().getName();
        data+="\nexception:: "+e.toString();
        data +="\nmessage:: "+e.getMessage()+"\n______\n";
        StackTraceElement[] trace = e.getStackTrace();
        if(trace!=null){
            data+="trace1::\n";
            for(StackTraceElement t : trace){
                data += ">>"+t+"\n";
            }
        }

        Throwable ex = e.getCause();
        if(ex!=null){
            data+= "\n\n\n________\ncause message:: "+ex.getMessage()+"\n";
            trace = ex.getStackTrace();
            if(trace!=null){
                for(StackTraceElement t : trace){
                    data += ">>"+t.toString()+"\n";
                }
            }
        }

        data+="\n\neof";
        //
        try
        {
            FileOutputStream o = new FileOutputStream(path);
            o.write(data.getBytes());
            o.flush();
            o.close();
        }
        catch (IOException xe)
        {}
    }
}
