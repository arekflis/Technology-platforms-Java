package org.example;

import java.io.Serializable;
import java.util.Scanner;

public class Message implements Serializable {

    private int number;

    private String content;

    Message(int id_client){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please give number of messages(" + id_client + ")!");
        this.number = scanner.nextInt();
        System.out.println("Please give your message(" + id_client + ")!");
        scanner.nextLine();
        this.content = scanner.nextLine();
    }


    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
