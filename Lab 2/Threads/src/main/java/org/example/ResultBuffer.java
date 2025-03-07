package org.example;

import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class ResultBuffer {
    private Map<Integer,Boolean> results;
    private LinkedList<String> buffer;
    public ResultBuffer(Map<Integer,Boolean> results, LinkedList<String> buffer) {
        this.results = results;
        this.buffer = buffer;
    }

    public LinkedList<String> getBuffer() {
        return buffer;
    }

    public void setBuffer(LinkedList<String> buffer) {
        this.buffer = buffer;
    }

    public Map<Integer,Boolean> getResults() {
        return results;
    }

    public void setResults(Map<Integer,Boolean> results) {
        this.results = results;
    }

    public void addToResults(int id, int number, boolean result){
        synchronized (this.results){
            synchronized(this.buffer) {
                this.buffer.add("Watek " + id + " dodaje do RESULT BUFFER");
            }
            this.results.put(number, result);
            synchronized(this.buffer) {
                this.buffer.add("Watek " + id + " konczy dodawac do RESULT BUFFER");
            }
        }
    }



}
