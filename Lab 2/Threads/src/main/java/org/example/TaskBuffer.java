package org.example;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class TaskBuffer {
    private LinkedList<Integer> queue;
    private LinkedList<String> buffer;
    TaskBuffer(LinkedList<Integer> queue, LinkedList<String> buffer){
        this.queue = queue;
        this.buffer = buffer;
    }

    public LinkedList<String> getBuffer() {
        return buffer;
    }

    public void setBuffer(LinkedList<String> buffer) {
        this.buffer = buffer;
    }

    public LinkedList<Integer> getQueue() {
        return queue;
    }

    public void setQueue(LinkedList<Integer> queue) {
        this.queue = queue;
    }

    public void addTask(int id, int number){
        synchronized(this.queue){
            if(id == 0) {
                synchronized(this.buffer){
                    this.buffer.add("JA dodaje do TASK BUFFER");
                }
            }
            else {
                synchronized(this.buffer){
                    this.buffer.add("Watek " + id + " dodaje do TASK BUFFER");
                }
            }
            this.queue.add(number);
            this.queue.notifyAll();
            if(id == 0) {
                synchronized(this.buffer){
                    this.buffer.add("JA skonczylem dodawac do TASK BUFFER");
                }
            }
            else {
                synchronized(this.buffer){
                    this.buffer.add("Watek " + id + " skonczyl dodawac do TASK BUFFER");
                }
            }
        }
    }

    public int getTask(int id) throws InterruptedException {
        synchronized (this.queue){
            synchronized(this.buffer){
                this.buffer.add("Watek " + id + " pobiera z TASK BUFFER");
            }
            while(this.queue.size()==0){
                synchronized(this.buffer){
                    this.buffer.add("Watek " + id + " musi poczekac na TASK BUFFER");
                }
                this.queue.wait();
            }
            int number = this.queue.poll();
            synchronized(this.buffer){
                this.buffer.add("Watek " + id + " konczy pobierac z TASK BUFFER");
            }
            return number;
        }
    }



}
