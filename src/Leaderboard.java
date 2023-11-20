import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * The {@code Leaderboard} class manages a list of {@code Player} objects and provides methods
 * to update and retrieve information from a leaderboard stored in an XML file.
 *
 * <p>
 * The leaderboard is represented as an XML file, and the class allows for
 * reading the existing leaderboard, updating it with new player information,
 * and writing the updated leaderboard back to the XML file.
 * </p>
 *
 * <p>
 * This class employs the Document Object Model (DOM) for XML processing and
 * uses a list of {@code Player} objects to represent the current state of the leaderboard.
 * </p>
 *
 * @author Jommel Sabater
 * @version 1.0
 */
class Leaderboard {

    /**
     * The list of {@code Player} objects representing the leaderboard.
     */
    private List<Player> players = new ArrayList<Player>();

    /**
     * The file object representing the XML file used to store the leaderboard.
     */
    private File leaderboardFile;

    /**
     * The path to the default leaderboard XML file.
     */
    public static enum FilePath {
        LEADERBOARD_PATH("BootlegHangaroo/AppData/Leaderboard/Leaderboard.xml");

        private String path;

        FilePath(String path) {
            this.path = path;
        }

        public String getPath() {
            return path;
        }
    }

    /**
     * Enum representing values used in leaderboard operations.
     * 
     * <p>
     * This enum provides predefined values for specifying the maximum (MAX) and
     * minimum (MIN) limits used in leaderboard-related operations, such as
     * restricting the number of top players displayed.
     * </p>
     * 
     * <p>
     * Each enum constant is associated with an integer value, and this value can be
     * retrieved using the {@code getValue()} method.
     * </p>
     */
    public static enum LeaderboardValue {
        MAX(10),
        MIN(0);

        private final int value;

        private LeaderboardValue(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    /**
     * Enumeration representing tag names used in the XML structure of the leaderboard.
     * 
     * <p>
     * This enumeration defines constants for key elements in the XML representation of
     * the leaderboard. Each constant corresponds to a specific tag name, facilitating
     * easy and consistent access to these names throughout the codebase.
     * </p>
     * 
     * <p>
     * Each enum constant is associated with a string value, and this value can be
     * retrieved using the {@code getTagName()} method.
     * </p>
     */
    public static enum TagName {
        /**
         * Represents the root element of the XML structure, typically encapsulating the
         * entire leaderboard data.
         */
        LEADERBOARD("leaderboard"),

        /**
         * Represents an individual player element within the XML structure, containing
         * information about a player's name and score.
         */
        PLAYER("player"),

        /**
         * Represents the name element within a player element, storing the player's name.
         */
        NAME("name"),

        /**
         * Represents the score element within a player element, storing the player's score.
         */
        SCORE("score");

        private final String tagName;

        private TagName(String tagName) {
            this.tagName = tagName;
        }
        
        public String getTagName() {
            return tagName;
        }
    }

    /**
     * Constructs a new {@code Leaderboard} object by reading the existing XML file and
     * populating the list of {@code Player}s.
     * 
     * <p>
     * This constructor initializes a {@code Leaderboard} object by parsing an XML file
     * representing the current state of the leaderboard. It reads the player data
     * from the XML file and creates corresponding {@code Player} objects, populating the
     * list of players within the leaderboard.
     * </p>
     * 
     * <p>
     * The XML file is expected to follow a specific structure, with player
     * information encapsulated within player elements under the root leaderboard
     * element. The constructor utilizes the Java Document Object Model (DOM) to
     * parse the XML file and extract player information.
     * </p>
     * 
     * <p>
     * For each player element in the XML file, the constructor extracts the name
     * and score information and creates a new {@code Player} object. These {@code Player} objects
     * are then added to the internal list of players, providing an in-memory
     * representation of the leaderboard.
     * </p>
     * 
     * <p>
     * If any errors occur during the initialization process, such as an
     * incorrectly formatted XML file or I/O issues, an exception is caught, and
     * the stack trace is printed. It is essential to handle exceptions appropriately
     * based on the application's requirements.
     * </p>
     * 
     * @throws Exception If an error occurs during the initialization process,
     *                   providing information about the nature of the exception.
     */
    Leaderboard() {
        try {
            // Initialize the file object representing the XML file
            leaderboardFile = new File(FilePath.LEADERBOARD_PATH.getPath());
            
            // Ensure the file exists or create a new one
            if (!leaderboardFile.exists()) {
                leaderboardFile.createNewFile();
            }

            // Create a DocumentBuilder to parse the XML file using DOM
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

            // Parse the XML file and normalize the document
            Document doc = dBuilder.parse(leaderboardFile);
            doc.getDocumentElement().normalize();

            // Extract player data from player elements in the XML file
            NodeList nList = doc.getElementsByTagName(TagName.PLAYER.getTagName());
            for (int i = 0; i < nList.getLength(); i++) {
                Node nNode = nList.item(i);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;

                    // Extract name and score information from the XML elements
                    String name = eElement.getElementsByTagName(TagName.NAME.getTagName()).item(0).getTextContent();
                    int score = Integer.parseInt(eElement.getElementsByTagName(TagName.SCORE.getTagName()).item(0).getTextContent());
                    
                    // Create a new Player object and add it to the list of players
                    if (name != null && score != 0) {
                        players.add(new Player(name, score));
                    } else {
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Updates the leaderboard with the provided {@code Player} object.
     * 
     * <p>
     * This method takes a {@code Player} object representing a player's current state and
     * updates the leaderboard accordingly. If a player with the same name already
     * exists in the leaderboard, the method updates that player's score. Otherwise,
     * it adds the current player to the leaderboard. After the update, the method
     * sorts the players based on their scores and writes the changes back to the XML
     * file.
     * </p>
     * 
     * <p>
     * The update process involves iterating through the list of players, checking
     * if a player with the same name as the provided player exists. If found, the
     * existing player's score is updated; otherwise, the current player is added to
     * the list.
     * </p>
     * 
     * <p>
     * The leaderboard is then sorted based on player scores using a descending order
     * comparator. Finally, the updated leaderboard is written to the XML file.
     * </p>
     * 
     * <p>
     * Note: The update process may modify the list of players directly and might
     * result in a concurrent modification exception. It's recommended to use a
     * thread-safe list or synchronize access to the list if there is a possibility
     * of concurrent updates.
     * </p>
     * 
     * @param currPlayer The {@code Player} object to be updated or added to the leaderboard.
     * @throws Exception If an error occurs during the update process.
     */
    public void updateLeaderboard(Player currPlayer) {
        Iterator<Player> iterator = players.iterator();
        boolean playerExists = false;

        // Iterate through the list of players
        while (iterator.hasNext()) {
            Player player = iterator.next();

            // If a player with the same name exists, update the player's score
            if (player.getName().equals(currPlayer.getName())) {
                player.setScore(currPlayer.getScore());
                playerExists = true;
            }
        }

        // If the player does not exist, add the player to the list
        if (!playerExists) {
            players.add(currPlayer);
        }

        // Sort the players based on scores in descending order
        Collections.sort(players.subList(LeaderboardValue.MIN.getValue(), players.size()), 
            new Comparator<Player>() {
                public int compare(Player p1, Player p2) {
                    return Integer.compare(p2.getScore(), p1.getScore());
                }
            });
        
        // Limit the number of players to the maximum allowed
        if (players.size() > LeaderboardValue.MAX.getValue()) {
            players = players.subList(LeaderboardValue.MIN.getValue(), LeaderboardValue.MAX.getValue());
        }

        try {
            // Create a new XML document
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();

            // Create the root element for the leaderboard
            Element rootElement = doc.createElement(TagName.LEADERBOARD.getTagName());
            doc.appendChild(rootElement);

            // Iterate through the sorted players and create XML elements for each player
            for (Player player : players.subList(LeaderboardValue.MIN.getValue(), players.size())) {
                Element playerElement = doc.createElement(TagName.PLAYER.getTagName());
                rootElement.appendChild(playerElement);
                Element nameElement = doc.createElement(TagName.NAME.getTagName());
                nameElement.appendChild(doc.createTextNode(player.getName()));
                playerElement.appendChild(nameElement);
                Element scoreElement = doc.createElement(TagName.SCORE.getTagName());
                scoreElement.appendChild(doc.createTextNode(Integer.toString(player.getScore())));
                playerElement.appendChild(scoreElement);

                // Normalize the document before writing to file
                doc.getDocumentElement().normalize();

                // Transform the document to XML and write to the leaderboard file
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                DOMSource source = new DOMSource(doc);
                StreamResult result = new StreamResult(leaderboardFile);
                transformer.transform(source, result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    public static void main(String[] args) {
//        Leaderboard leaderboard = new Leaderboard();
//        Player player = new Player("Jommel");
//        leaderboard.updateLeaderboard(player);
//    }
}
