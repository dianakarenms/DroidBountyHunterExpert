package training.edu.models;

/**
 * @author Giovani Gonzalez
 * Created by darkgeat on 27/08/2017.
 */

public class Fugitivo {
    private int id;
    private String name;
    private String status;
    private String photo;
    private int notification;

    public Fugitivo(int id, String name, String status, String photo, int notification) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.photo = photo;
        this.notification = notification;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public int getNotification() {
        return notification;
    }

    public void setNotification(int notification) {
        this.notification = notification;
    }
}
