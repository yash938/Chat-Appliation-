import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {

    Socket socket;
    BufferedReader br;
    PrintWriter out;

    public Client() {
        try {
            // create a socket to connect to the server
            System.out.println("sending req to server");
            socket = new Socket("127.0.0.1", 7777);
            System.out.println("connection done..");

            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());

            // Start separate threads for reading and writing
            new Thread(this::startReading).start();
            new Thread(this::startWriting).start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void startReading() {
        // thread to continuously read data
        System.out.println("reading...");
        try{
        while (true) {
          
                String msg = br.readLine();
                if (msg.equals("exit")) {
                    System.out.println("Server terminate the chat");
                    socket.close();
                    break;
                }

                System.out.println("Server: " + msg);
            
        }
    }catch(Exception e){
        e.printStackTrace();
    }
    }

    public void startWriting() {
        // thread to read input from the user and send it to the server
        System.out.println("writer started");


        try{
        while (true && !socket.isClosed()) {
         
                BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));
                String content = br1.readLine();
                if(content.equals("exit")){
                    socket.close();
                    break;
                }
                out.println(content);
                out.flush();
         
        }
    }catch(Exception e){
        e.printStackTrace();
    }
    }

    public static void main(String[] args) {
        System.out.println("this is client");
        new Client(); // Create a new instance of Client
    }
}
