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

    private Map<Character, List<Integer>> charIndexMap;
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
    private boolean isGuessed;

    public Word(String word, String description, Difficulty difficulty) {
        this.word = word;
        this.description = description;
        isGuessed = false;
        Random random = new Random();
        charIndexMap = new HashMap<>();
        int limit = switch (difficulty) {
            case EASY -> 3;
            case MEDIUM -> 2;
            case HARD -> 1;
        };
        int blockedCharactersSize = random.nextInt(word.length()-limit) + 1; //Random number of blocked characters limit depends on difficulty level
        int[] blockedCharactersIndexes = new int[blockedCharactersSize];
        for (int i = 0; i < blockedCharactersSize; i++) {
            int index = random.nextInt(word.length());
            if (containsIndex(blockedCharactersIndexes, index)) {
                i--;
            } else {
                blockedCharactersIndexes[i] = index;
            }
        }
        for (int i = 0; i < blockedCharactersIndexes.length; i++) {
            char currentChar = word.charAt(blockedCharactersIndexes[i]);
            charIndexMap.computeIfAbsent(currentChar, k -> new ArrayList<>());
            charIndexMap.get(currentChar).add(i);
        }
    }

    private boolean containsIndex(int[] blockedCharactersIndexes, int index) { // Helper function for getCharIndexMap to check if the index is already in the array
        for (int blockedCharactersIndex : blockedCharactersIndexes) {
            if (blockedCharactersIndex == index) {
                return true;
            }
        }
        return false;
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
    public boolean isGuessed() {
        return isGuessed;
    }

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
    public String retrieveGuessedLetters() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < word.length(); i++) {
            if (charIndexMap.containsKey(word.charAt(i))) {
                stringBuilder.append("_ ");
            } else {
                stringBuilder.append(word.charAt(i)).append(" ");
            }
        }
        return stringBuilder.toString();
    }



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
    public boolean guessLetter(char letter) {
        if (charIndexMap.containsKey(letter)){
            charIndexMap.remove(letter);
            if(charIndexMap.isEmpty()){
                isGuessed = true;
            }
            return true;
        }
        return false;
    }

//    public static void main(String[] args) {
//        Word word = new Word("hello", "A greeting.", "easy");
//        System.out.println(word.word);
//    }
}
