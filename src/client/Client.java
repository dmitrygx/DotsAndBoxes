package client;

import sun.net.ConnectionResetException;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class Client {

    protected int serverPort = 8080;
    private String address = "127.0.0.1";
    protected Socket clientSocket = null;

    BufferedReader buffread;

    boolean gameExit = false;


    public Client(int serverPort) {
        this.serverPort = serverPort;
        try {
            InetAddress ipAddress = InetAddress.getByName(address);
            clientSocket = new Socket(ipAddress, serverPort);
            buffread = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (Exception x) {
            x.printStackTrace();
        }
    }

    public void sendMail(String msg) {
        PrintWriter writer;
        try {
            writer = new PrintWriter(clientSocket.getOutputStream(), true);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        writer.println(msg);
    }


    public Socket getClientSocket() {
        return clientSocket;
    }

    public void setClientSocket(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public String recvMail() {
        try {
            String msg;
            try {
                if ((msg = buffread.readLine()) != null) {
                    return msg;
                }
            } catch (ConnectionResetException sockex) {
                gameExit = true;
                clientSocket.close();
            }
        } catch (IOException e) {
            //report exception somewhere.
            e.printStackTrace();
        }
        return null;
    }

    public boolean isGameExit(){
        return gameExit;
    }

}
