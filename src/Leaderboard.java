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


class Leaderboard {
    private List<Player> players;
    File leaderboardFile;

    Leaderboard() {
        try {
            leaderboardFile = new File("Leaderboard.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(leaderboardFile);
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("player");
            for (int i = 0; i < nList.getLength(); i++) {
                Node nNode = nList.item(i);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    String name = eElement.getElementsByTagName("name").item(0).getTextContent();
                    int score = Integer.parseInt(eElement.getElementsByTagName("score").item(0).getTextContent());
                    players.add(new Player(name, score));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateLeaderboard(Player currPlayer) {
        for (Player player : players) {
            if (player.getName().equals(currPlayer.getName())) {
                player.setScore(currPlayer.getScore());
            }
            else {
                players.add(currPlayer);
            }
        }

        Collections.sort(players.subList(0, Constant.MAX_LEADERBOARD), new Comparator<Player>() {
            public int compare(Player p1, Player p2) {
                return Integer.compare(p2.getScore(), p1.getScore());
            }
        });

        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();
            Element rootElement = doc.createElement("leaderboard");
            doc.appendChild(rootElement);
            for (Player player : players.subList(0, Constant.MAX_LEADERBOARD)) {
                Element playerElement = doc.createElement("player");
                rootElement.appendChild(playerElement);
                Element nameElement = doc.createElement("name");
                nameElement.appendChild(doc.createTextNode(player.getName()));
                playerElement.appendChild(nameElement);
                Element scoreElement = doc.createElement("score");
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