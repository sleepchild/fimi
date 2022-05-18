package sleepchild.fimi.io;

public class Buffer
{
    int pos;
    byte[] buffer = null;
    
    public Buffer(){
        buffer = new byte[0];
    }
    
    public void put(byte[] b, int count){
        int l = buffer.length;
        grow(count);
        for(int i=0;i< count;i++){
            buffer[l] = b[i];
            l++;
        }
        
    }
    
    private void grow(int size){
        byte[] copy = buffer;
        int l = copy.length + size;
        buffer = new byte[l];
        for(int i=0;i<copy.length;i++){
            buffer[i] = copy[i];
        }
    }
    
    public byte[] array(){
        return buffer;
    }
    
}
