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
 *  <!-- Additional word entries -->
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

        FilePath(String path) {
            this.path = path;
        }

        public String getPath() {
            return path;
        }
    }

    private List<Word> easyWords = new ArrayList<Word>();
    private List<Word> mediumWords = new ArrayList<Word>();
    private List<Word> hardWords = new ArrayList<Word>();

    private List<Word> words = new ArrayList<Word>();

    private File wordsFile;

    /**
     * Constructor for WordGenerator class. Reads words from XML files
     * for each difficulty level and initializes word lists.
     */
    public WordGenerator(GameMode gameMode) {
        try {
            for (String path : Arrays.asList(FilePath.EASY_PATH.getPath(), FilePath.MEDIUM_PATH.getPath(), FilePath.HARD_PATH.getPath())) {
                
                this.wordsFile = new File(path);

                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.parse(this.wordsFile);
                doc.getDocumentElement().normalize();

                NodeList nList = doc.getElementsByTagName(Word.TagName.WORD.getTagName());

                for (int i = 0; i < nList.getLength(); i++) {
                    Node nNode = nList.item(i);
                    Element eElement = (Element) nNode;

                    String difficulty = eElement.getAttribute("difficulty");
                    String name = eElement.getElementsByTagName("name").item(0).getTextContent();
                    String description = eElement.getElementsByTagName("description").item(0).getTextContent();

                    switch (difficulty) {
                        case "easy":
                            this.easyWords.add(new Word(name, description));
                            break;
                        case "medium":
                            this.mediumWords.add(new Word(name, description));
                            break;
                        case "hard":
                            this.hardWords.add(new Word(name, description));
                            break;
                        default:
                            break;
                    }
                }
            } 
            Collections.shuffle(this.easyWords);
            Collections.shuffle(this.mediumWords);
            Collections.shuffle(this.hardWords);
            generateWords(gameMode);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Generates a list of words based on the specified game mode.
     * 
     * @param gameMode The game mode for which words need to be generated.
     * @return A list of words for the specified game mode.
     */
    private void generateWords(GameMode gameMode) {
        words = new ArrayList<Word>();

        if (gameMode == GameMode.CLASSIC) {
            for (int i = 0; i < GameMode.CLASSIC.MAX_WORDS_PER_DIFFICULTY; i++) {
                words.add(this.easyWords.get(i));
                words.add(this.mediumWords.get(i));
                words.add(this.hardWords.get(i));
            }
            Collections.shuffle(words);
        } 
        else if (gameMode == GameMode.SURVIVAL){
            for (int i = 0; i < GameMode.SURVIVAL.MAX_WORDS_PER_DIFFICULTY; i++) {
                words.add(this.easyWords.get(i));
                words.add(this.mediumWords.get(i));
                words.add(this.hardWords.get(i));
            }
            Collections.shuffle(words);
        }
    }

    public Word generateWord() {
        return words.get(Stage.getStageNumber() - 1);
    }

    /**
     * TEST CODE
    public static void main(String[] args) {
        WordGenerator wordGenerator = new WordGenerator();
        List<Word> words = wordGenerator.generateWords(GameMode.CLASSIC);
        for (Word word : words) {
            System.out.println(word.getWord());
        }
    }
    */
}