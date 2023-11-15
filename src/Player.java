public class Player {
    private String name;
    private int health, token, score;
    
    Player(){
        health = 3;
        token = 0;
        score = 0;
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
        score += 1;
    }


}
