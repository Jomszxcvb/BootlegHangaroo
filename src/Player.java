public class Player {
    private String name;
    private int health, token, score;
    
    public Player(){
        health = 3;
        token = 0;
        score = 0;
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
    public String getName() {
        return name;
    }
    
    public void decreaseHealth(){
        health -= 1;
    }
    public void increaseHealth(int x){
        health += x;
    }
}
