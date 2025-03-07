package org.example;

public class CountingThread implements Runnable{

    private TaskBuffer taskBuffer;
    private ResultBuffer resultBuffer;

    private int id_thread;

    public TaskBuffer getTaskBuffer() {
        return taskBuffer;
    }

    public void setTaskBuffer(TaskBuffer taskBuffer) {
        this.taskBuffer = taskBuffer;
    }

    public ResultBuffer getResultBuffer() {
        return resultBuffer;
    }

    public void setResultBuffer(ResultBuffer resultBuffer) {
        this.resultBuffer = resultBuffer;
    }

    public int getId_thread() {
        return id_thread;
    }

    public void setId_thread(int id_thread) {
        this.id_thread = id_thread;
    }

    public CountingThread(TaskBuffer taskBuffer, ResultBuffer resultBuffer, int id_thread) {
        this.taskBuffer = taskBuffer;
        this.resultBuffer = resultBuffer;
        this.id_thread = id_thread;
    }

    @Override
    public void run(){
        while(!Thread.interrupted()){
            try{
                int parameter;
                boolean result;
                parameter = this.taskBuffer.getTask(this.id_thread);
                result = isPrimeNumber(parameter);
                Thread.sleep(4000);
                this.resultBuffer.addToResults(this.id_thread, parameter, result);
            } catch (InterruptedException e) {
                System.out.println("Watek " + this.id_thread + " zostaje przerwany!");
                break;
            }
        }
    }

    public boolean isPrimeNumber(int x){
        if(x==1 && x==0) return false;
        int i=2;
        while(i<=Math.sqrt(x)){
            if(x%i==0) return false;
            i++;
        }
        return true;
    }

}
