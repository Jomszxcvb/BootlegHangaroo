import java.util.List;

public class Stage {
    private WordGenerator wordGenerator = new WordGenerator();
    private List<Word> words;

    private static int stageNumber = 1;

    public Stage(Player player, GameMode gameMode){
        words = wordGenerator.generateWords(gameMode);
        while (player.getHealth() != Player.NO_HEALTH && !words.get(stageNumber).isGuessed()) {
            play();

            if (words.get(stageNumber).guessLetter(player.guess())) {
                System.out.println("Correct!");
                player.incScore();
            } else {
                System.out.println("Wrong!");
                player.decHealth();
            }

            if (words.get(stageNumber).isGuessed()) {
                System.out.println("You guessed the word!");
                player.incToken();
                stageNumber++;
            }
        }
    }

    public void play(){
        System.out.println("Stage " + stageNumber);
        System.out.println("Health: " + Player.MAX_HEALTH);
        System.out.println("Score: " + Player.MIN_SCORE);
        System.out.println("Token: " + Player.MIN_TOKEN);
        System.out.println(words.get(stageNumber).retrieveGuessedLetters());
        System.out.println("Guess a letter: ");
    }

    /**
     * TEST CODE
    public static void main(String[] args) {
        Player player = new Player();
        Stage stage = new Stage(player, GameMode.CLASSIC);
        stage.play();
    }
    */
}




