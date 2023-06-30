package com.example.javalaba9;

import java.io.*;
import java.net.Socket;

public class TestASClient {

    public static void main(String[] args) {
        try(Socket socket = new Socket("192.168.43.124",Protocol.PORT);
            BufferedReader br =new BufferedReader(new InputStreamReader(System.in));
            DataOutputStream oos = new DataOutputStream(socket.getOutputStream());
            DataInputStream ois = new DataInputStream(socket.getInputStream());	)
        {
            System.out.println("Client connected to socket.");
            System.out.println();
            System.out.println("Client writing channel = oos & reading channel = ois initialized.");
            System.out.println("Write word work if you start work");

            while(!socket.isOutputShutdown()){

                if(br.ready()){

                    String clientCommand = br.readLine();

                    oos.writeUTF(clientCommand);
                    oos.flush();

                    if(clientCommand.equalsIgnoreCase("quit")){

                       System.out.println("Client kills connections");

                       if(ois.available()!=0){
                            String in = ois.readUTF();
                            System.out.println(in);
                       }

                       break;
                    }

                   if(ois.available()!=0){
                        String in = ois.readUTF();
                        System.out.println(in);
                    }
                }
            }

            System.out.println("Closing connections & channels on clentSide - DONE.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}