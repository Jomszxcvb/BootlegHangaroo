import java.util.*;

public class Stage {
    private final Word word;
    public static int stageNumber = 0;
    public static final int MIN_STAGE = 1;
    private final Map<Character, List<Integer>> charIndexMap;
    private final String string;
    private boolean isGuessed = false;

    Stage(WordGenerator wordGenerator, Difficulty difficulty) {
        word = wordGenerator.generateWord(difficulty);
        string = word.getWord();
        Random random = new Random();
        charIndexMap = new HashMap<>();
        int limit = switch (difficulty) {
            case EASY -> 2;
            case MEDIUM -> 1;
            case HARD -> 0;
        };
        int blockedCharactersSize = random.nextInt(string.length() - limit) + 1; //Random number of blocked characters limit depends on difficulty level
        int[] blockedCharactersIndexes = new int[blockedCharactersSize];
        for (int i = 0; i < blockedCharactersSize; i++) {
            int index = random.nextInt(string.length());
            if (containsIndex(blockedCharactersIndexes, index)) {
                i--;
            } else {
                blockedCharactersIndexes[i] = index;
            }
        }
        for (int i = 0; i < blockedCharactersIndexes.length; i++) {
            char currentChar = string.charAt(blockedCharactersIndexes[i]);
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

    public String retrieveGuessedLetters() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < string.length(); i++) {
            if (charIndexMap.containsKey(string.charAt(i))) {
                stringBuilder.append("_ ");
            } else {
                stringBuilder.append(string.charAt(i)).append(" ");
            }
        }
        return stringBuilder.toString();
    }

    public boolean guessLetter(char letter) {
        if (charIndexMap.containsKey(letter)) {
            charIndexMap.remove(letter);
            if (charIndexMap.isEmpty()) {
                isGuessed = true;
            }
            return true;
        }
        return false;
    }

    public boolean getIsGuessed() {
        return isGuessed;
    }

    public static int getStageNumber() {
        return stageNumber;
    }

    public void playStage(Player player) {
        while (player.getHealth() != Player.NO_HEALTH && !getIsGuessed()) {
            System.out.println("Stage " + stageNumber);
            player.displayPlayerInfo();
            switch (player.getHealth()) {
                case 3 -> System.out.println(Art.KANGAROO_STATE[0]);
                case 2 -> System.out.println(Art.KANGAROO_STATE[1]);
                case 1 -> System.out.println(Art.KANGAROO_STATE[2]);
                case 0 -> System.out.println(Art.KANGAROO_STATE[3]);
            }
            System.out.println("Guess the word: ");
            System.out.println(retrieveGuessedLetters() +"\n"+ word.getDescription());
            System.out.println("Enter your guess: ");
            if (guessLetter(player.getChar())) {
                System.out.print(Art.CORRECT);
            } else {
                System.out.print(Art.WRONG);
                player.decHealth();
            }
        }
        if (getIsGuessed()) {
            System.out.println(Art.WIN);
            System.out.println(retrieveGuessedLetters() + "\n" + word.getDescription());
            player.incScore();
            stageNumber++;
        }
        else if (!getIsGuessed() && player.getHealth() != Player.NO_HEALTH) {
            System.out.println(Art.LOSE);
        }

    }

    static void refresh() {
        stageNumber = MIN_STAGE;
    }
}





