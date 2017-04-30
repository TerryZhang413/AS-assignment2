package Ozlympic;

public class User {
    private String userID;
    private String name;
    private int age;
    private String state;

    public User(String userID, String name, int age, String state) {
        this.userID = userID;
        this.name = name;
        this.age = age;
        this.state = state;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

}
