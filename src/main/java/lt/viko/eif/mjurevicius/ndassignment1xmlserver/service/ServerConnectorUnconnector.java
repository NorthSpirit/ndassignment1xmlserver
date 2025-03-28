package lt.viko.eif.mjurevicius.ndassignment1xmlserver.service;

import lt.viko.eif.mjurevicius.ndassignment1xmlserver.model.Foodorder;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ServerConnectorUnconnector {
    private static DataOutputStream dos = null;
    private static DataInputStream dis = null;
    private static Socket clientSocket = null;
    private volatile boolean active = true;

    public void start(int port)
    {

        try (ServerSocket serverSocket = new ServerSocket(port);) {
            new Thread(() -> deactivateListener()).start();
            while (active) {
                System.out.println("Waiting for client...");
                System.out.println("Server is Starting in port: " + port);
                clientSocket = serverSocket.accept();
                System.out.println("Connected");
                dis = new DataInputStream(clientSocket.getInputStream());
                dos = new DataOutputStream(clientSocket.getOutputStream());

                String filename = dis.readUTF() + ".xml";
                receiveFile(filename);

                //Marshalling part... ._.
                //Foodorder foodorder = XMLTransformationService.detransformFoodorder(filename);
                Foodorder foodorder = XMLTransformationService.detransform(Foodorder.class, filename );
                System.out.println("Thing received: " + foodorder);
                XMLTransformationService.transformAppendFoodorders(foodorder, "Food_orders");
                //... ._.
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        } finally {
            stop();
        }
    }

    private void receiveFile(String fileName)
            throws IOException {
        int bytes = 0;
        FileOutputStream fileOutputStream = new FileOutputStream(fileName);
        long size = dis.readLong();
        byte[] buffer = new byte[4 *1024];
        while (size > 0 && (bytes = dis.read(buffer, 0, (int) Math.min(buffer.length, size))) != -1) {
            fileOutputStream.write(buffer, 0, bytes);
            size -= bytes;
        }
        System.out.println("File is received");
        fileOutputStream.close();
    }

    private void deactivateListener()
    {
        Scanner input = new Scanner(System.in);
        String choice = input.nextLine();
        while(active){
            choice = input.nextLine();
            if (choice.equalsIgnoreCase("stop")){
                System.out.println("Server stopped");
                active = false;
                stop();
            }
        }
    }

    private void stop()
    {
        try {
            dis.close();
            dos.close();
            clientSocket.close();
        } catch (IOException ex){
            System.out.println("Exception occurred: " + ex.getMessage());
        }
    }
}
