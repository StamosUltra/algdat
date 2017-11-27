/**
 * Created by joaki on 10.10.2017.
 */
public class Queue {
    private Object[] array;
    private int start = 0;
    private int end = 0;
    private int objects = 0;

    public Queue(int size) {
        array = new Object[size];
    }

    public boolean empty(){
        return objects == 0;
    }

    public boolean full() {
        return objects == array.length;
    }

    public void add(Object o) {
        if(full()) return;
        array[end] = o;
        end = (end+1)%array.length;
        objects++;
    }

    public Object next() {
        if(!empty()) {
            Object o = array[start];
            start = (start+1)%array.length;
            objects--;
            return o;
        }
        else return null;
    }

    public Object check() {
        if (!empty()) return array[start];
        else return null;
    }
}
