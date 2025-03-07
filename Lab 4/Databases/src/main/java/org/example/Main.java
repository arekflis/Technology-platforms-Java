package org.example;

import java.util.Scanner;


public class Main {

    public static final int end = 9;

    public static void main(String[] args) {
        Database database = new Database();
        try{
            int command = 0;
            while(command != end){
                menu();
                Scanner scanner = new Scanner(System.in);
                command = scanner.nextInt();
                database.functionals(command);
            }
        }
        catch(Exception e){
            e.printStackTrace();
            System.exit(-1);
        }
        database.finish();
    }

    public static void menu(){
        System.out.println("Command:");
        System.out.println("1 - Print database");
        System.out.println("2 - Add new Tower");
        System.out.println("3 - Add new Mage");
        System.out.println("4 - Print mages with level more than [int]");
        System.out.println("5 - Print towers with higher less than [int]");
        System.out.println("6 - Print mages with level more than [int] from Tower [String]");
        System.out.println("7 - Delete mage");
        System.out.println("8 - Delete tower [WARNING with mages]");
        System.out.println("9 - Exit");
    }
}