// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {

        Player player = new Player("Jom");
        WordGenerator wordGenerator = new WordGenerator(GameMode.CLASSIC);
        Stage stage = new Stage(wordGenerator, Difficulty.EASY);
        stage.playStage(player);


    }
}
