
import java.util.Random;
import java.util.Scanner;
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
            for(int i=0; i<wordIntoCharArray.length; i++){
                System.out.print(wordIntoCharArray[i]);
                if(i<wordIntoCharArray.length-1){
                    System.out.print(" ");
                }
            }

        }
        stageNumber += 1;
    }
}
