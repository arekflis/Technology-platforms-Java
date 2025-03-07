package org.example;

public class Main {
    public static void main(String[] args) {
        Client client2 = new Client("127.0.0.1", 5000, 2);
        client2.communicate();
    }
}