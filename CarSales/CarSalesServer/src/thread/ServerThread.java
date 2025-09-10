/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package thread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author user
 */
public class ServerThread  extends Thread{
    private ServerSocket serverSocket;
    private List<HandleClientThread> clients;

    public ServerThread() throws Exception {
        serverSocket = new ServerSocket(9000);
        clients = new ArrayList<>();
    }
    
    
    @Override
    public void run() {
        while (!serverSocket.isClosed()) {            
            System.out.println("Waiting...");
            try {
                Socket socket = serverSocket.accept();
                System.out.println("Client connected");
                HandleClientThread hct = new HandleClientThread(socket, this);
                hct.start();
                clients.add(hct);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        stopAllClientThreads();
    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }
   
    private void stopAllClientThreads(){
        for (HandleClientThread client : clients) {
            try {
                client.getSocket().close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    public void login(HandleClientThread client) {
        clients.add(client);
    }

    public void logout(HandleClientThread client) {
        clients.remove(client);
    }
    
}
