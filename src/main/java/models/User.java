package models;

public class User {
    private String email;
    private String password;
    private String name;
    private String createdAt;
    private String updatedAt;

    public User(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public User(String email, String password, String name, String createdAt, String updatedAt) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public User(String email, String name) {
        this.email = email;
        this.name = name;
    }

    public User(String email) {
        this.email = email;
    }

    public User() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return String.format("{email:%s password:%s name:%s}", this.email, this.password, this.name);
    }
}
