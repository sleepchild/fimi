package sleepchild.fimi;
import java.util.concurrent.*;
import java.util.*;

public class TaskMang
{
    ExecutorService worker;
    
    /*
    */
    private List<Task> taskQueue = new ArrayList<>();
    /*
    */
    //List<Task> pendingTasksQueue = new ArrayList<>();
    //List<Task> completedTaskQueue = new ArrayList<>();
   
    public TaskMang(){
        worker = Executors.newFixedThreadPool(3);
        //
    }
    
    public void save(){
        //
    }
    
    public void queue(){
        //
    }
    
    public void runNext(Task task){
        //
    }
    
    public void getTasks(){
        //
    }
    
    
}
