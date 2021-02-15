package com.company;
import java.util.LinkedList;
import java.util.List;

public class Queue {

    private LinkedList list;

    public Queue() {
        list = new LinkedList();
    }
    public synchronized void enQuene(Object obj){
        list.addFirst(obj);
    }
    public synchronized Object deQueue(){
        if(list != null && list.size() > 0){
            return list.removeLast();
        }
        return null;
    }
    public boolean isEmpty()
    {
        return list.isEmpty();
    }

    public int getSize()
    {
        return list.size();
    }
    public synchronized void clear(){
        list.clear();
    }
    public synchronized List transferToList(){
        List list = (List) this.list.clone();
        this.list.clear();
        return list;
    }
    public synchronized void addAll(List list){
        this.list.addAll(list);
    }
}
