package org.example;

import java.util.Random;

public class AddingThread implements Runnable{

    private TaskBuffer buffer;
    private int id_thread;
    AddingThread(TaskBuffer buffer, int id_thread){
        this.buffer=buffer;
        this.id_thread = id_thread;
    }

    public TaskBuffer getBuffer() {
        return buffer;
    }

    public void setBuffer(TaskBuffer buffer) {
        this.buffer = buffer;
    }

    public int getId_thread() {
        return id_thread;
    }

    public void setId_thread(int id_thread) {
        this.id_thread = id_thread;
    }

    @Override
    public void run(){
        while(!Thread.interrupted()){
            try {
                Random random = new Random();
                int count = random.nextInt(100001);
                this.buffer.addTask(this.id_thread, count);
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                System.out.println("Watek " + this.id_thread + " zostaje przerwany!");
                break;
            }
        }
    }


}
