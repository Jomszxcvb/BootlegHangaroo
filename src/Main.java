import java.util.Scanner;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try (scanner) {
            WordGenerator wordGenerator = new WordGenerator();
            System.out.print(Art.TITLE);
            System.out.print(Art.KANGAROO_STATE[4]);
            System.out.print("Enter player name: ");
            Player player = new Player(scanner.nextLine());
            System.out.print(Art.SELECT_DIFFICULTY);
            System.out.print("Select: ");
            Difficulty difficulty = switch (scanner.next().charAt(0)) {
                case '1' -> Difficulty.EASY;
                case '2' -> Difficulty.MEDIUM;
                case '3' -> Difficulty.HARD;
                default -> throw new IllegalStateException("Unexpected value: " + scanner.next().charAt(0));
            };
            while (true) {
                player.setHealth(Player.MAX_HEALTH);
                System.out.println(Art.TITLE);
                System.out.print(Art.MENU);
                System.out.print("Enter: ");
                char choice = scanner.next().charAt(0);
                scanner.nextLine();
                switch (choice) {
                    case '1':
                        System.out.println("Enter your name: ");
                        player = new Player(scanner.next());
                        break;
                    case '2':
                        System.out.println(Art.GAME_MODE_CLASSIC);
                        System.out.print(Art.GAME_MODE_SURVIVAL);
                        System.out.print("Select [1] or [2]: ");
                        char GameMode = scanner.next().charAt(0);
                        Stage.refresh();
                        player.refresh();

                        switch (GameMode) {
                            case '1':
                                wordGenerator = new WordGenerator();
                                while (Stage.stageNumber <= 5 && player.getHealth() != 0) {
                                    System.out.print(Art.GAME_MODE_CLASSIC);
                                    new Stage(wordGenerator, difficulty).playStage(player);
                                }
                                if (player.getHealth() != 0) {
                                    System.out.print(Art.CONGRATULATIONS);

                                } else {
                                    System.out.print(Art.KANGAROO_STATE[3]);
                                    System.out.print(Art.GAME_OVER);
                                }
                                scanner.nextLine();
                                break;
                            case '2':
                                Leaderboard leaderboard = new Leaderboard();
                                wordGenerator = new WordGenerator();
                                while (player.getHealth() != 0) {
                                    new Stage(wordGenerator, difficulty).playStage(player);
                                }
                                System.out.print(Art.GAME_OVER);
                                System.out.println("Final score: " + player.getScore());
                                leaderboard.updateSurvival(player);
                                break;
                            default:
                                System.out.println("Invalid choice!");
                                break;
                        }


                        break;
                    case '3':
                        Leaderboard leaderboard = new Leaderboard();
                        leaderboard.displaySurvival();
                        break;
                    case '4':
                        System.out.print(Art.SELECT_DIFFICULTY);
                        difficulty = switch (scanner.next().charAt(0)) {
                            case '1' -> Difficulty.EASY;
                            case '2' -> Difficulty.MEDIUM;
                            case '3' -> Difficulty.HARD;
                            default -> throw new IllegalStateException("Unexpected value: " + scanner.next().charAt(0));
                        };
                        break;
                    case '5':
                        System.out.print(Art.TITLE);
                        System.out.println("Instructions");
                        System.out.print(Art.INSTRUCTIONS);
                        break;
                    default:
                        System.out.println("Invalid choice!");
                        break;
                }
                scanner.nextLine();
            }
        } catch (Exception e) {
            System.out.println("Error has occurred.");
        }



    }
}
