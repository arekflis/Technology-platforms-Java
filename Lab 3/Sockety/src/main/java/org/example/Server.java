package org.example;
// A Java program for a Server
import java.net.*;
import java.io.*;

public class Server
{

    private ServerSocket server;

    private int port;
    private final int maxClients = 5;
    private int clientsFinished;
    private int clientsStarted;
    Server(int port){
        this.port = port;
        this.clientsFinished = 0;
        this.clientsStarted = 0;
    }

    public int getClientsFinished() {
        return clientsFinished;
    }

    public void setClientsFinished(int clientsFinished) {
        this.clientsFinished = clientsFinished;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void startConnection(){
        try{
            this.server = new ServerSocket(this.port);
            System.out.println("Server started!");

            System.out.println("Waiting for clients!");

            int id = 1;
            while(this.clientsFinished < maxClients){
                if(this.clientsStarted < maxClients){
                    this.clientsStarted++;
                    Socket clientSocket = server.accept();
                    ServerThread serverThread = new ServerThread(this, clientSocket, id);
                    Thread thread = new Thread(serverThread);
                    thread.start();
                    id++;
                    System.out.println("Client accept!");
                }
                else{
                    Thread.sleep(2000);
                }
            }
            finishConnection();
        }
        catch(IOException e){
            System.out.println("Server did not start!");
            System.out.println(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void finishConnection(){
        try{
            System.out.println("Closing server!");
            this.server.close();
        }
        catch(IOException e){
            System.out.println("Error during closing connection!");
            System.out.println(e);
        }

    }

    public static void main(String[] args) throws InterruptedException {
        Server server = new Server(5000);
        server.startConnection();
    }
}
