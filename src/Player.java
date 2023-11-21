import java.util.Scanner;

public class Player {
    private String name;
    private int health;
    private int token;
    private int score;

    public static final int MAX_HEALTH = 3;
    public static final int NO_HEALTH = 0;
    public static final int MIN_TOKEN = 0;
    public static final int MIN_SCORE = 0;
    
    
    Player(){
        name = "Player";
        health = MAX_HEALTH;
        token = MIN_TOKEN;
        score = MIN_SCORE;
    }

    Player (String name){
        this.name = name;
        health = MAX_HEALTH;
        token = MIN_TOKEN;
        score = MIN_SCORE;
    }

    Player (String name, int score){
        this.name = name;
        this.score = score;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setHealth(int health) {
        this.health = health;
    }
    public void setToken(int token) {
        this.token = token;
    }
    public void setScore(int score) {
        this.score = score;
    }

    public String getName() {
        return name;
    }
    public int getHealth() {
        return health;
    }
    public int getToken() {
        return token;
    }
    public int getScore() {
        return score;
    }
    
    public void decHealth(){
        health -= 1;
    }
    public void incHealth(int x){
        health += x;
    }

    public void incScore() {
        score += 1000;
    }
    public void incToken() {
        token += 1;
    }
    public void decToken() {
        token -= 1;
    }

    public char getChar() {
        Scanner sc = new Scanner(System.in);
        return Character.toUpperCase(sc.next().charAt(0));
    }

    public void displayPlayerInfo() {
        System.out.println("Name: " + name);
        System.out.println("Health: " + health);
        System.out.println("Token: " + token);
        System.out.println("Score: " + score);
    }

    void refresh() {
        name = "Player";
        health = MAX_HEALTH;
        token = MIN_TOKEN;
        score = MIN_SCORE;
    }
}
