import java.util.HashMap;

/**
 * The Word class represents a word in a word-guessing game, including its
 * description and the state of guessed letters.
 * 
 * <p>
 * The class is designed to store information about a word, its description, and
 * track the guessed letters in the word. It provides methods to manipulate and
 * retrieve this information.
 * </p>
 * 
 * <p>
 * The class uses a HashMap to keep track of each letter in the word and whether
 * it has been guessed. The key-value pairs consist of the letter and a boolean
 * indicating whether the letter has been guessed (true) or not (false).
 * </p>
 * 
 * @author Jommel Sabater
 */
public class Word {

    /**
     * The actual word to be guessed.
     */
    private String word;

    /**
     * The description of the word.
     */
    private String description;

    /**
     * A HashMap to track the guessed state of each letter in the word.
     * 
     * <p>
     * The keys are characters representing individual letters in the word, and the
     * values are booleans indicating whether the corresponding letter has been
     * guessed (true) or not (false).
     * </p>
     */
    private HashMap<Character, Boolean> letters;

    /**
     * Constructs a new Word object with the specified word and description.
     * Initializes the letters HashMap with each letter in the word set to false
     * (not guessed).
     * 
     * @param word        The word to be guessed.
     * @param description The description of the word.
     */
    public Word(String word, String description) {
        this.word = word;
        this.description = description;
        
        // Initialize the letters HashMap with each letter set to false
        letters = new HashMap<>();
        for (int i = 0; i < word.length(); i++) {
            letters.put(word.charAt(i), false);
        }
    }

    /**
     * Sets a new word for the Word object.
     * 
     * @param word The new word to be set.
     */
    public void setWord(String word) {
        this.word = word;
    }

    /**
     * Sets a new description for the Word object.
     * 
     * @param description The new description to be set.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Resets the state of guessed letters in the word. Sets each letter to the
     * initial state of not guessed (false).
     */
    public void setLetters() {
        for (int i = 0; i < word.length(); i++) {
            letters.put(word.charAt(i), false);
        }
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
     * Retrives the state of guessed letters in the word.
     * 
     * <p>
     * Constructs a string representing the current state of guessed letters in the
     * word. Guessed letters are displayed, and unguessed letters are represented
     * by underscores.
     * </p>
     * 
     * @return A string displaying guessed and unguessed letters.
     */
    public String retrieveGuessedLetters() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < word.length(); i++) {
            if (letters.get(word.charAt(i))) {
                sb.append(word.charAt(i));
            }
            else {
                sb.append("_");
            }
            sb.append(" ");
        }
        return sb.toString();
    }

    /**
     * Checks if a guessed letter is in the word and updates its guessed state.
     * 
     * <p>
     * If the guessed letter is in the word, the method updates the corresponding
     * entry in the letters HashMap to true and returns true. If the guessed letter
     * is not in the word, the method returns false.
     * </p>
     * 
     * @param letter The letter guessed by the player.
     * @return True if the letter is in the word, false otherwise.
     */
    public boolean guessLetter(char letter) {
        if (letters.containsKey(letter)) {
            letters.put(letter, true);
            return true;
        }
        return false;
    }
}
