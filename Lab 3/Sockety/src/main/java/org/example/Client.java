package org.example;

// A Java program for a Client
import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {

    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private ObjectOutputStream outObject;

    private int port;
    private String IPaddress;

    private int id;
    Client(String IPaddress, int port, int id){
        this.IPaddress = IPaddress;
        this.port = port;
        this.id =id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getIPaddress() {
        return IPaddress;
    }

    public void setIPaddress(String IPaddress) {
        this.IPaddress = IPaddress;
    }

    public void communicate(){
        startCommunication();

        try{
            out = new DataOutputStream(this.socket.getOutputStream());
            in = new DataInputStream(new BufferedInputStream(this.socket.getInputStream()));
            outObject = new ObjectOutputStream(this.socket.getOutputStream());

            String responseFromServer = "";
            while(responseFromServer.compareTo("Ready!") != 0){
                responseFromServer = in.readUTF();
            }
            System.out.println(responseFromServer + " for client " + this.id);
            Message mess = new Message(this.id);
            out.writeInt(this.id);
            out.writeInt(mess.getNumber());
            while(responseFromServer.compareTo("Ready for messages!") != 0){
                responseFromServer = in.readUTF();
            }
            System.out.println(responseFromServer + " from client " + this.id);
            while(mess.getNumber()>0){
                outObject.writeObject(mess);
                mess.setNumber(mess.getNumber()-1);
                Thread.sleep(3000);
            }
            while(responseFromServer.compareTo("Finished!") != 0){
                responseFromServer = in.readUTF();
            }
            System.out.println(responseFromServer + " for client " + this.id);
        }
        catch(IOException e){
            System.out.println("Error during connection!");
            System.out.println(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        endCommunication();
    }
    public void startCommunication(){
        try{
            this.socket = new Socket(this.IPaddress, this.port);
            System.out.println("Connected!");
        }
        catch(UnknownHostException u){
            System.out.println("Error connection!");
            System.out.println(u);
        }
        catch(IOException e){
            System.out.println("Error connection!");
            System.out.println(e);
        }
    }

    public void endCommunication(){
        try{
            this.in.close();
            this.out.close();
            this.outObject.close();
            this.socket.close();

        }
        catch(IOException e){
            System.out.println("End connection!");
            System.out.println(e);
        }
    }


    public static void main(String args[])
    {
        if (args.length == 0) {
            System.exit(1);
        }
        String number = args[0];
        System.out.println(args[0]);
        int j = 0;
        while(j<number.length()){
            if (number.charAt(j) < '0' || number.charAt(j) > '9'){
                System.exit(1);
            }
            j++;
        }
        int id = Integer.parseInt(args[0]);
        Client client = new Client("127.0.0.1", 5000, id);
        client.communicate();

        /*
        Client client3 = new Client("127.0.0.1", 5000, 3);
        client3.communicate();
        Client client4 = new Client("127.0.0.1", 5000, 4);
        client4.communicate();
        Client client5 = new Client("127.0.0.1", 5000, 5);
        client5.communicate();
        */
    }
}
