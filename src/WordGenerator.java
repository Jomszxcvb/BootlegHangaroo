import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class WordGenerator {
    private List<String> words;

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

            File file = new File(filePath);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String word = scanner.nextLine();
                this.words.add(word);
            }
            scanner.close();

        } catch (FileNotFoundException e) {
            System.out.println("Words file not found.");
        }
    }

    public String generateWord() {
        Random random = new Random();
        int index = random.nextInt(this.words.size());
        return this.words.get(index);
    }
}
