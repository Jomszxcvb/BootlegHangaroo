import java.util.List;
import java.util.Scanner;

public class Stage {
    private Word word;
    private static int stageNumber;
    public static final int MIN_STAGE = 1;

    Stage(GameMode gameMode){
        WordGenerator wordGenerator = new WordGenerator(gameMode);
        stageNumber = MIN_STAGE;
        try{
            word = wordGenerator.generateWord();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public static int getStageNumber() {
        return stageNumber;
    }

    public void playStage(Player player) {
        while (player.getHealth() != Player.NO_HEALTH) {
            if (!word.isGuessed()) {
                System.out.println("Stage " + stageNumber);
                player.displayPlayerInfo();
                System.out.println("Guess the word: ");
                System.out.println(word.retrieveGuessedLetters());
                System.out.println("Enter your guess: ");
                if (word.guessLetter(player.getChar())) {
                    System.out.println("Correct!");
                }
                else {
                    System.out.println("Wrong!");
                    player.decHealth();
                }
            }
            else {
                System.out.println("You guessed the word!");
                System.out.println("The word is: " + word.getWord());
                player.incScore();
                stageNumber++;
            }
        }


    }
    //Test Code

//    public static void main(String[] args) {
//        Player player = new Player("Jom");
//    }
}





