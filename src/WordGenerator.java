import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Collections;


/**
 * This class is responsible for generating a random word from a text file.
 * The text file is chosen based on the difficulty of the game.
 * 
 * @author J.R. Sabater 
 */
public class WordGenerator {
    private List<String> words;
    static int counter = 0;

    /**
     * Constructor for objects of class WordGenerator
     * Stores 
     * @param difficulty
     */
    public WordGenerator(Constant.Difficulty difficulty) {
        this.words = new ArrayList<>();

        try {
            String filePath;
            switch (difficulty) {
                case EASY:
                    filePath = "Words/Easy.txt";
                    break;
                case MEDIUM:
                    filePath = "Words/Medium.txt";
                    break;
                case HARD:
                    filePath = "Words/Hard.txt";
                    break;
                default:
                    filePath = "Words/Easy.txt";
                    break;
            }

            // Read from file
            File file = new File(filePath);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                this.words.add(line);
            }
            scanner.close();

            shuffleWords();

        } catch (FileNotFoundException e) {
            System.out.println("Words file not found.");
        }
    }

    public String generateWord() {
        return words.get(counter++);
    }

    public void shuffleWords() {
        Collections.shuffle(this.words);
    }

    /** TEST CODE
    public static void main(String[] args) {
        WordGenerator wordGenerator = new WordGenerator(Constant.Difficulty.EASY);
        while (WordGenerator.counter < wordGenerator.words.size()) {
            System.out.println(wordGenerator.generateWord());
        }
    }
    */
}
