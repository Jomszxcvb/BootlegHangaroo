
import java.util.*;

public class Stage {
    private String Word;
    private Map<Character, List<Integer>> charIndexMap;
    private static int stageNumber = 1;
    Stage(Player player, Constant.Difficulty level){
        WordGenerator wordGenerator = new WordGenerator(level);
        Word = wordGenerator.generateWord();
        charIndexMap = getCharIndexMap(Word, level);
        while(player.getHealth() != 0 && !charIndexMap.isEmpty()) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Stage " + stageNumber);
            System.out.println("Guess the word ");
            System.out.println(getWordWithBlanks(Word, charIndexMap));
            char guess = scanner.next().charAt(0);
            if(charIndexMap.containsKey(guess)){
                System.out.println("Correct");
                player.incScore();
                stageNumber++;
                charIndexMap.remove(guess);
            }
            else{
                System.out.println("Wrong");
                player.decHealth();
            }
        }

    }

    private String getWordWithBlanks(String string, Map<Character, List<Integer>> charIndexMap) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < string.length(); i++) {
            if (charIndexMap.containsKey(string.charAt(i))) {
                stringBuilder.append("_ ");
            } else {
                stringBuilder.append(string.charAt(i)+" ");
            }
        }
        return stringBuilder.toString();

    }

    private Map<Character, List<Integer>> getCharIndexMap(String string, Constant.Difficulty level){
        Random random = new Random();
        int limit = switch (level) {
            case EASY -> 3;
            case MEDIUM -> 2;
            case HARD -> 1;
        };
        int blockedCharactersSize = random.nextInt(string.length()-limit) + 1;
        int[] blockedCharactersIndexes = new int[blockedCharactersSize];
        Map<Character, List<Integer>> IndexMap = new HashMap<>();
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
            IndexMap.computeIfAbsent(currentChar, k -> new ArrayList<>());
            IndexMap.get(currentChar).add(i);
        }
       return IndexMap;
    }
    private boolean containsIndex(int[] blockedCharactersIndexes, int index) {
        for (int blockedCharactersIndex : blockedCharactersIndexes) {
            if (blockedCharactersIndex == index) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        Player player = new Player();
        Stage stage = new Stage(player, Constant.Difficulty.EASY);
    }

}




