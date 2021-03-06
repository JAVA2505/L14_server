import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Main {
    private static final String WS = " ";

    private static final Map<String, User> registeredUsers = new HashMap<>();

    static{
        registeredUsers.put("Petr", new User("Petr", "localhost", "8888"));
    }

    public static void main(String[] args) {
        try {
            ServerSocket server = new ServerSocket(8899);
            while(true){
                Socket s = server.accept();
                ObjectInputStream ois =
                        new ObjectInputStream(s.getInputStream());
                String first = (String) ois.readObject();
                if("reg".equals(first)){
                    String regName = (String) ois.readObject();
                    String ip = (String) ois.readObject();
                    String port = (String) ois.readObject();
                    ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
                    if(!registeredUsers.containsKey(regName)){
                        User u = new User(regName, ip, port);
                        registeredUsers.put(regName, u);
                        System.out.println("User "+regName+" registered;");
                        oos.writeObject(regName+", you were registered!");
                    }else{
                        System.out.println("User "+regName+" already registered;");
                        oos.writeObject(regName+", you are already registered!");
                    }
                    oos.close();
                }else{
                    if(first.indexOf(WS) == first.lastIndexOf(WS)){
                        System.out.println("Incorrect incoming message;");
                    }else{
                        String from = first.substring(
                                0,
                                first.indexOf(WS));
                        String to = first.substring(
                                first.indexOf(WS)+1,
                                first.lastIndexOf(WS));
                        String message = first.substring(
                                first.lastIndexOf(WS)+1);
                        if(registeredUsers.containsKey(from)) {
                            System.out.println("FROM: " + from + "; TO: " + to + "; MSG: " + message + ";");
                        }
                        if (registeredUsers.containsKey(to)) {
                            User toUser = registeredUsers.get(to);
                            try (Socket sock = new Socket(toUser.getIp(), toUser.getPort())) {
                                ObjectOutputStream oos = new ObjectOutputStream(sock.getOutputStream());
                                oos.writeObject(from + " " + message);
                                oos.close();
                            }
                        }
                    }
                }
                ois.close();
                s.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
