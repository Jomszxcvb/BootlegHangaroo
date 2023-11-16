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
import java.util.List;
import java.util.Collections;
import java.util.Comparator;

/**
 * The Leaderboard class manages a list of Player objects and provides methods
 * to update and retrieve information from a leaderboard stored in an XML file.
 *
 * <p>
 * The leaderboard is represented as an XML file, and the class allows for
 * reading the existing leaderboard, updating it with new player information,
 * and writing the updated leaderboard back to the XML file.
 * </p>
 *
 * @author Jommel Sabater
 */
class Leaderboard {

    /**
     * The list of Player objects representing the leaderboard.
     */
    private List<Player> players;

    /**
     * The file object representing the XML file used to store the leaderboard.
     */
    private File leaderboardFile;

    /**
     * The path to the default leaderboard XML file.
     */
    public static final String LEADERBOARD_PATH = "Leaderboard.xml";

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
     * Constructs a new Leaderboard object by reading the existing XML file and
     * populating the list of players.
     * 
     * <p>
     * This constructor initializes a Leaderboard object by parsing an XML file
     * representing the current state of the leaderboard. It reads the player data
     * from the XML file and creates corresponding Player objects, populating the
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
     * and score information and creates a new Player object. These Player objects
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
            leaderboardFile = new File(LEADERBOARD_PATH);

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
                    players.add(new Player(name, score));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Updates the leaderboard with the provided Player object.
     * 
     * <p>
     * This method takes a Player object representing a player's current state and
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
     * @param currPlayer The Player object to be updated or added to the leaderboard.
     * @throws Exception If an error occurs during the update process.
     */
    public void updateLeaderboard(Player currPlayer) {
        // Check if the player with the same name already exists in the leaderboard
        for (Player player : players) {
            if (player.getName().equals(currPlayer.getName())) {
                // Update the existing player's score
                player.setScore(currPlayer.getScore());
            }
            else {
                // Add the current player to the leaderboard if not found
                players.add(currPlayer);
            }
        }

        // Sort the players based on scores in descending order
        Collections.sort(players.subList(LeaderboardValue.MIN.getValue(), LeaderboardValue.MAX.getValue()), 
            new Comparator<Player>() {
                public int compare(Player p1, Player p2) {
                    return Integer.compare(p2.getScore(), p1.getScore());
                }
            });

        try {
            // Create a new XML document
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();

            // Create the root element for the leaderboard
            Element rootElement = doc.createElement(TagName.LEADERBOARD.getTagName());
            doc.appendChild(rootElement);

            // Iterate through the sorted players and create XML elements for each player
            for (Player player : players.subList(LeaderboardValue.MIN.getValue(), LeaderboardValue.MAX.getValue())) {
                Element playerElement = doc.createElement(TagName.PLAYER.getTagName());
                rootElement.appendChild(playerElement);
                Element nameElement = doc.createElement(TagName.NAME.getTagName());
                nameElement.appendChild(doc.createTextNode(player.getName()));
                playerElement.appendChild(nameElement);
                Element scoreElement = doc.createElement(TagName.SCORE.getTagName());
                scoreElement.appendChild(doc.createTextNode(Integer.toString(player.getScore())));
                playerElement.appendChild(scoreElement);

                doc.getDocumentElement().normalize();
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


}