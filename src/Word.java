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
    public static enum TagName {
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
    public static enum Attributes {
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

    public Word(String word, String description, Difficulty difficulty) {
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


    /**
     * Retrieves the state of guessed letters in the word.
     * 
     * <p>
     * Constructs a string representing the current state of guessed letters in the
     * word. Guessed letters are displayed, and unguessed letters are represented
     * by underscores.
     * </p>
     * 
     * @return A string displaying guessed and unguessed letters.
     */




    /**
     * Checks if a guessed letter is in the word and updates its guessed state.
     *
     * <p>
     * If the guessed letter is in the word, the method updates the corresponding
     * entry in the letters {@code HashMap} to true and returns true. If the guessed letter
     * is not in the word, the method returns false.
     * </p>
     *
     * @param letter The letter guessed by the player.
     * @return True if the letter is in the word, false otherwise.
     */


//    public static void main(String[] args) {
//        Word word = new Word("hello", "A greeting.", "easy");
//        System.out.println(word.word);
//    }
}
