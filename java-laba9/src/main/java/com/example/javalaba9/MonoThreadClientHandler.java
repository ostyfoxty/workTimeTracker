package com.example.javalaba9;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class MonoThreadClientHandler implements Runnable {

    private static Socket clientDialog;

    public MonoThreadClientHandler(Socket client) {
        MonoThreadClientHandler.clientDialog = client;
    }

    @Override
    public void run() {

        try {
            DataInputStream in = new DataInputStream(clientDialog.getInputStream());
            System.out.println("DataInputStream created");

            DataOutputStream out = new DataOutputStream(clientDialog.getOutputStream());
            System.out.println("DataOutputStream  created");


            while (!clientDialog.isClosed()) {
                try {
                    String entry = in.readUTF();
                    clientDialog.setSoTimeout(30000);
                    System.out.println("READ from clientDialog message - " + entry);

                    if (entry.equalsIgnoreCase("quit")) {
                        System.out.println("Client initialize connections suicide ...");
                        out.writeUTF("Server reply - " + entry + " - OK");
                        break;
                    } else if (entry.equalsIgnoreCase("work")) {
                        System.out.println("Client is working");
                        out.writeUTF("Server reply - " + entry + " - OK");
                        Thread.sleep(60000);
                    }
                }
                catch (SocketTimeoutException e){
                    System.out.println("Client is not working");
                    break;
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }



            System.out.println("Client disconnected");
            System.out.println("Closing connections & channels.");


            in.close();
            out.close();

            clientDialog.close();

            System.out.println("Closing connections & channels - DONE.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}