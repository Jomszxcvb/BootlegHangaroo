import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.Collections;

/**
 * The {@code WordGenerator} class is responsible for generating random words from XML files
 * based on the difficulty level of the game. It reads words from separate files for
 * easy, medium, and hard difficulty levels and provides methods to generate lists
 * of words for different game modes.
 * 
 * The XML file structure is assumed to have the following format:
 * <pre> {@code
 * <words>
 *  <word difficulty="easy">
 *      <name>ExampleWord</name>
 *      <description>Example description for the word.</description>
 *  </word>
 *  //<!-- Additional word entries -->
 * </words>
 * }</pre>
 * 
 * The class includes constants for file paths.
 * It also maintains separate lists of words for each difficulty level.
 * 
 * @author J.R. Sabater
 * @version 1.0
 */
public class WordGenerator {

    /**
     * Enumeration representing different file paths for each difficulty level.
     */
    public static enum FilePath {
        EASY_PATH("BootlegHangaroo/AppData/Words/Easy.xml"),
        MEDIUM_PATH("BootlegHangaroo/AppData/Words/Medium.xml"),
        HARD_PATH("BootlegHangaroo/AppData/Words/Hard.xml");

        private String path;

        /**
         * Constructs a FilePath with the specified path.
         * 
         * @param path The file path for the difficulty level.
         */
        FilePath(String path) {
            this.path = path;
        }

        /**
         * Gets the file path for the difficulty level.
         * 
         * @return The file path for the difficulty level.
         */
        public String getPath() {
            return path;
        }
    }

    private static final int FIRST_INDEX = 0;
    private final List<Word> easyWords;
    private final List<Word> mediumWords;
    private final List<Word> hardWords;
    /**
     * Constructor for WordGenerator class. Reads words from XML files
     * for each difficulty level and initializes word lists.
     */
    public WordGenerator(GameMode gameMode) {

        easyWords = new ArrayList<Word>();
        mediumWords = new ArrayList<Word>();
        hardWords = new ArrayList<Word>();

        try {
            for (String path : Arrays.asList(FilePath.EASY_PATH.getPath(), FilePath.MEDIUM_PATH.getPath(), FilePath.HARD_PATH.getPath())) {
                
                File wordsFile = new File(path);

                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.parse(wordsFile);
                doc.getDocumentElement().normalize();

                NodeList nList = doc.getElementsByTagName(Word.TagName.WORD.getTagName());

                for (int i = 0; i < nList.getLength(); i++) {
                    Node nNode = nList.item(i);
                    Element eElement = (Element) nNode;

                    String wordDifficulty = eElement.getAttribute("difficulty");
                    String name = eElement.getElementsByTagName("name").item(0).getTextContent();
                    String description = eElement.getElementsByTagName("description").item(0).getTextContent();

                    switch (wordDifficulty.toLowerCase()) {
                        case "easy":
                            easyWords.add(new Word(name, description, Difficulty.EASY));
                            break;
                        case "medium":
                            mediumWords.add(new Word(name, description, Difficulty.MEDIUM));
                            break;
                        case "hard":
                            hardWords.add(new Word(name, description, Difficulty.HARD));
                            break;
                        default:
                            break;
                    }
                }
            } 
            Collections.shuffle(this.easyWords);
            Collections.shuffle(this.mediumWords);
            Collections.shuffle(this.hardWords);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Generates a random word based on the specified difficulty level.
     * 
     * @param difficulty The difficulty level for which to generate the word.
     * @return A randomly generated word.
     */
    public Word generateWord(Difficulty difficulty) {
        Word word = null;

        if (difficulty == Difficulty.EASY) {
            word = easyWords.get(FIRST_INDEX);
            removeWord(difficulty, FIRST_INDEX);
        } else if (difficulty == Difficulty.MEDIUM) {
            word = mediumWords.get(FIRST_INDEX);
            removeWord(difficulty, FIRST_INDEX);
        } else if (difficulty == Difficulty.HARD) {
            word = hardWords.get(FIRST_INDEX);
            removeWord(difficulty, FIRST_INDEX);
        }

        return word;
    }

    /**
     * Removes the specified word from the list of words for the given difficulty level.
     * 
     * @param difficulty The difficulty level of the word to be removed.
     * @param index The index of the word to be removed.
     */
    private void removeWord(Difficulty difficulty, int index) {
        if (difficulty == Difficulty.EASY) {
            this.easyWords.remove(FIRST_INDEX);
        } else if (difficulty == Difficulty.MEDIUM) {
            this.mediumWords.remove(FIRST_INDEX);
        } else if (difficulty == Difficulty.HARD) {
            this.hardWords.remove(FIRST_INDEX);
        }
    }

    public static void main(String[] args) {
        WordGenerator wg = new WordGenerator(GameMode.CLASSIC);
        for (int i = 0; i < 10; i++) {
            System.out.println(wg.generateWord(Difficulty.EASY).getWord());
        }
        return;
    }
}
