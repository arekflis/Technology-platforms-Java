package org.example;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerThread implements Runnable{

    private DataInputStream in;
    private DataOutputStream out;

    private ObjectInputStream inObject;

    private Socket clientSocket;
    private Server server;
    private int id_thread;

    ServerThread(Server server, Socket clientSocket, int id_thread){
        this.clientSocket = clientSocket;
        this.id_thread = id_thread;
        this.server = server;
    }

    public int getId_thread() {
        return id_thread;
    }

    public void setId_thread(int id_thread) {
        this.id_thread = id_thread;
    }

    @Override
    public void run(){
        try{
            out = new DataOutputStream(this.clientSocket.getOutputStream());
            in = new DataInputStream(new BufferedInputStream(this.clientSocket.getInputStream()));
            inObject = new ObjectInputStream(this.clientSocket.getInputStream());
            out.writeUTF("Ready!");
            int clientID = in.readInt();
            int countMessages = in.readInt();
            System.out.println("Thread number " + this.id_thread + " received from client " + clientID +" number: " + countMessages);
            out.writeUTF("Ready for messages!");
            while(countMessages > 0){
                Object obj;
                Message mess = null;
                obj = inObject.readObject();
                if(obj instanceof Message){
                    mess = (Message) obj;
                }
                else{
                    System.out.println("Sending messages was finished with error!");
                    finish();
                }
                System.out.println("Thread number " + this.id_thread + " received from client " + clientID + ": " + mess.getContent());
                countMessages--;
            }
            out.writeUTF("Finished!");
            finish();
            this.server.setClientsFinished(this.server.getClientsFinished()+1);
        }
        catch(IOException e){
            System.out.println("Error during connection!");
            System.out.println(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void finish(){
        try{
            System.out.println("Thread number " + this.id_thread + " closing connection!");
            this.in.close();
            this.out.close();
            this.inObject.close();
            this.clientSocket.close();
        }
        catch(IOException e){
            System.out.println("Error during closing connection!");
            System.out.println(e);
        }
    }
}
