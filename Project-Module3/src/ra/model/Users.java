package ra.model;

import ra.config.Color;

import java.io.Serializable;

public class Users implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private String name;
    private String username;
    private String password;
    private String email;
    private boolean status = true;
    private RoleName role = RoleName.USER;

    public Users() {
    }

    public Users(int id, String name, String username, String password, String email, boolean status, RoleName role) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.email = email;
        this.status = status;
        this.role = role;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public RoleName getRole() {
        return role;
    }

    public void setRole(RoleName role) {
        this.role = role;
    }

    @Override
    public String toString() {
        String horizontalLine = "--------------------------------------------------";
        return Color.GREEN + horizontalLine +
                "\n" + Color.YELLOW + "User\tId: " + Color.RESET + id +
                "\t" + Color.YELLOW + "Name: " + Color.RESET + name +
                "\t" + Color.YELLOW + "Username: " + Color.RESET + username +
                "\t" + Color.YELLOW + "Password: " + Color.RESET + password +
                "\t" + Color.YELLOW + "Email: " + Color.RESET + email +
                "\t" + Color.YELLOW + "Status: " + Color.RESET + status +
                "\t" + Color.YELLOW + "Role: " + Color.RESET + role +
                "\n" + Color.GREEN + horizontalLine;

    }



}