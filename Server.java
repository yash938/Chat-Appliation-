import java.net.*;
import java.io.*;
class Server{
ServerSocket server;
Socket socket;
BufferedReader br;
PrintWriter out;
    public Server(){
   try {
    // Create a server socket on port 7777
    server = new ServerSocket(7777);
    //req aa rahi hogi client ki accept kese karenge
    System.out.println("server is ready to accept connections");
    System.out.println("waiting.......");
    socket = server.accept();
    //yeh cliet ka rerq accept kar raha hoga

    br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    out = new PrintWriter(socket.getOutputStream());

   StartReading();
   StartWriting();

   } catch (Exception e) {
    e.printStackTrace();
   }
    }



    public void StartReading(){
   //thread = read karke data rahega
   Runnable r1 = ()->{
   System.out.println("reading...");
   try{
    while (true) {
   
        String msg = br.readLine();
        if(msg.equals("exit")){
            System.out.println("client terminate the chat");
            socket.close();
            break;
        }

        System.out.println("client"+ msg);
    }
}catch(Exception e){
    e.printStackTrace();
}
    
   };
   new Thread(r1).start();
    }
    public void StartWriting(){
//thread = data user se lega fr send karega client tak
System.out.println("writer started");

Runnable r2 = ()->{
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
};
new Thread(r2).start();
    }
    public static void main(String[] args) {
        System.out.println("this is server");
        new Server();
    }
}