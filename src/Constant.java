public class Constant {
    enum Difficulty {
        EASY, MEDIUM, HARD
    }

    enum FilePath {
        LEADERBOARD_PATH("Leaderboard.xml"),
        EASY_PATH("Words/Easy.txt"),
        MEDIUM_PATH("Words/Medium.txt"),
        HARD_PATH("Words/Hard.txt");

        private String path;

        FilePath(String path) {
            this.path = path;
        }

        public String getPath() {
            return path;
        }
    }

    public static final int MAX_LEADERBOARD = 10;
}
