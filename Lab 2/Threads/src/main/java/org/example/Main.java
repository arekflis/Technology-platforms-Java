package org.example;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        int thread_number = 0;
        thread_number = countNumberOfThreads(args[0]);
        System.out.println(thread_number);
        if(thread_number < 0) return;

        LinkedList<String> buffer = new LinkedList<>();
        LinkedList<Integer> kolejka = new LinkedList<>();
        Map<Integer, Boolean> kolejka_wynikow = new HashMap<>();
        TaskBuffer taskBuffer = new TaskBuffer(kolejka, buffer);
        ResultBuffer resultBuffer = new ResultBuffer(kolejka_wynikow, buffer);
        LinkedList<Thread> threads = new LinkedList<>();


        initializeThreads(thread_number,taskBuffer, resultBuffer, threads);

        System.out.println("Commands: ");
        System.out.println("number: add number(Integer) to buffor");
        System.out.println("'Exit' : close this app");

        actionThread(threads, taskBuffer);

        printResult(taskBuffer, resultBuffer, buffer);

    }

    public static int countNumberOfThreads(String text){
        if(text.length() > 0){
            boolean flag = true;
            for(int i=0; i<text.length();i++){
                if(text.charAt(i) < '0' || text.charAt(i) > '9') flag = false;
            }
            if(flag){
                return Integer.parseInt(text);
            }
            else{
                return -1;
            }
        }
        else{
            return -1;
        }
    }

    public static void initializeThreads(int thread_number, TaskBuffer taskBuffer, ResultBuffer resultBuffer, LinkedList<Thread> threads){
        for(int i=1; i<= thread_number; i++){
            AddingThread producer = new AddingThread(taskBuffer, i);
            Thread thread_producer = new Thread(producer);
            threads.add(thread_producer);
            CountingThread consumer = new CountingThread(taskBuffer,resultBuffer,i + thread_number);
            Thread thread_consumer = new Thread(consumer);
            threads.add(thread_consumer);
        }

        for(Thread th : threads){
            th.start();
        }
    }

    public static void printResult(TaskBuffer taskBuffer, ResultBuffer resultBuffer, LinkedList<String> buffer){
        for(String str:buffer){
            System.out.println(str);
        }
        System.out.println(taskBuffer.getQueue());
        System.out.println(resultBuffer.getResults());
    }

    public static void actionThread(LinkedList<Thread> threads, TaskBuffer taskBuffer){
        Scanner scanner = new Scanner(System.in);
        boolean flag = true;
        while(flag){
            String text = scanner.nextLine();
            if(Objects.equals(text, "Exit")){
                try {
                    for(Thread th:threads){
                        th.interrupt();
                        th.join();
                    }
                    flag = false;
                    scanner.close();
                } catch (InterruptedException e) {
                    break;
                }
            }
            else{
                boolean fl = true;
                for(int i=0; i<text.length();i++){
                    if(text.charAt(i) < '0' || text.charAt(i) > '9') fl = false;
                }
                if(fl){
                    taskBuffer.addTask(0, Integer.parseInt(text));
                    System.out.println("Dodano " + text + " do bufora");
                }
                else{
                    System.out.println("Nie jest to ani liczba ani 'Exit'");;
                }
            }
        }
    }
}