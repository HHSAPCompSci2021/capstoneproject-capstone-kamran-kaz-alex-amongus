package ServerClient;

public class ForceClosedMessage extends Thread {
    public void run() {
      System.out.println("Server Shutting Down...");
      System.out.println("Exit code 0");
    }
 }