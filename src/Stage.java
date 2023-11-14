
import java.util.*;

public class Stage {
    private final String word = "testing";
    private int level;
    private static int stageNumber = 1;
    Stage(Player player, int level){
        Random random = new Random();
        Scanner scanner = new Scanner(System.in);
        int blankLettersSize = random.nextInt(word.length()) + 1; //This may need to be changed in order to adjust proper game difficulty
        int[] blankLettersIndexes = new int[blankLettersSize];
        for(int i=0; i<blankLettersSize; i++){
            blankLettersIndexes[i] = random.nextInt(word.length());
        }
        char[] wordIntoCharArray = word.toCharArray();
        for (int blankLettersIndex : blankLettersIndexes) {
            wordIntoCharArray[blankLettersIndex] = '_';
        }
        while(player.getHealth()!=0 ){
            System.out.println("Guess the word: ");

            for (int i = 0; i < wordIntoCharArray.length; i++) {
                if (containsIndex(blankLettersIndexes, i)) {
                    System.out.print("_ ");
                } else {
                    System.out.print(wordIntoCharArray[i] + " ");
                }
            }

            String userInput = scanner.next();
            char[] userChars = userInput.toCharArray();

            for (int i = 0; i < userChars.length; i++) {
                wordIntoCharArray[blankLettersIndexes[i]] = userChars[i];
            }

            if (String.valueOf(wordIntoCharArray).equals(word)) {
                System.out.println("Congratulations! You guessed the word.");
                break;
            }
            else{
                player.decreaseHealth();
                System.out.println("Wrong! You have " + player.getHealth() + " health left.");
            }
        stageNumber += 1;
    }
}
    private boolean containsIndex(int[] blankLettersIndexes, int index) {
        for (int i : blankLettersIndexes) {
            if (i == index) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        Player player = new Player();
        Stage stage = new Stage(player, 1);
    }
}
