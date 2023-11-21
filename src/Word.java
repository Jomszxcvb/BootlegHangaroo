import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.ArrayList;

/**
 * The {@code Word} class represents a word in the word-guessing game, including its
 * description and the state of guessed letters.
 * 
 * <p>
 * The class is designed to store information about a word, its description, and
 * track the guessed letters in the word. It provides methods to manipulate and
 * retrieve this information.
 * </p>
 * 
 * <p>
 * The class uses a {@code HashMap} to keep track of each letter in the word and whether
 * it has been guessed. The key-value pairs consist of the letter and a boolean
 * indicating whether the letter has been guessed (true) or not (false).
 * </p>
 * 
 * @author Jommel Sabater
 * @version 1.0
 */
public class Word {

    /**
     * Enumeration representing XML tag names.
     */
    public enum TagName {
        WORD("word"), DESCRIPTION("description");

        private final String tagName;

        TagName(String tagName) {
            this.tagName = tagName;
        }

        public String getTagName() {
            return tagName;
        }
    }

    /**
     * Enumeration representing XML attribute names.
     */
    public enum Attributes {
        DIFFICULTY("difficulty");

        private final String attribute;

        Attributes(String attribute) {
            this.attribute = attribute;
        }

        public String getAttribute() {
            return attribute;
        }
    }

    /**
     * The actual word to be guessed.
     */
    private String word;

    /**
     * The description of the word.
     */
    private String description;

    public Word(String word, String description) {
        this.word = word;
        this.description = description;
    }
    /**
     * Sets a new word for the {@code Word} object.
     * 
     * @param word The new word to be set.
     */
    public void setWord(String word) {
        this.word = word;
    }

    /**
     * Sets a new description for the {@code Word} object.
     * 
     * @param description The new description to be set.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Retrieves the current word.
     * 
     * @return The word.
     */
    public String getWord() {
        return word;
    }

    /**
     * Retrieves the description of the word.
     * 
     * @return The description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Retrieves the state of the word.
     * 
     * @return True if the word has been guessed, false otherwise.
     */


//    public static void main(String[] args) {
//        Word word = new Word("hello", "A greeting.", "easy");
//        System.out.println(word.word);
//    }
}
