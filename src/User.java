import java.util.Objects;

public class User {
    private String name;
    private String ip;
    private int port;

    public User(String name, String ip, String port) {
        this.name = name;
        this.ip = ip;
        this.port = Integer.parseInt(port);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = Integer.parseInt(port);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return port == user.port &&
                name.equals(user.name) &&
                ip.equals(user.ip);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, ip, port);
    }
}
