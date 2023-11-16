/**
 * The {@code GameMode} enum represents different game modes in a word-guessing game,
 * each associated with a specific maximum number of words per difficulty level.
 *
 * <p>
 * The enum defines two game modes, SURVIVAL and CLASSIC, each with a predefined
 * maximum number of words per difficulty level. The maximum number of words is a
 * constant value specific to each game mode and can be accessed using the
 * {@code MAX_WORDS_PER_DIFFICULTY} field.
 * </p>
 *
 * <p>
 * The enum is designed to provide a convenient way to specify and manage the
 * characteristics of different game modes. 
 * </p>
 *
 * <p>
 * Example usage:
 * </p>
 * <pre>
 * {@code
 * // Accessing the maximum number of words for the SURVIVAL game mode
 * int survivalMaxWords = GameMode.SURVIVAL.MAX_WORDS_PER_DIFFICULTY;
 *
 * // Accessing the maximum number of words for the CLASSIC game mode
 * int classicMaxWords = GameMode.CLASSIC.MAX_WORDS_PER_DIFFICULTY;
 * }
 * </pre>
 *
 * @author Jommel Sabater
 * @version 1.0
 */
public enum GameMode {
    SURVIVAL(50),
    CLASSIC(5);

    public final int MAX_WORDS_PER_DIFFICULTY;

    GameMode(int maxWords) {
        this.MAX_WORDS_PER_DIFFICULTY = maxWords;
    }
}