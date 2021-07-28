import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

class FileParser {

    private GameEngine Save;

    void LoadData() {
        try {
            Save = GameEngine.getInstance();
            File Saves = new File("Saves.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(Saves);
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("Save");

            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                Element eElement = (Element) nNode;
                Save.NormalBestScore = Integer.parseInt(eElement.getElementsByTagName("Normal").item(0).getTextContent());
            }

            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                Element eElement = (Element) nNode;
                Save.ArcadeBestScore = Integer.parseInt(eElement.getElementsByTagName("Arcade").item(0).getTextContent());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    void SaveData() {
        try {
            Save = GameEngine.getInstance();
            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
            Document document = documentBuilder.newDocument();
            Element root = document.createElement("Saves");
            document.appendChild(root);


            Element Saves = document.createElement("Save");
            root.appendChild(Saves);


            Element Normal = document.createElement("Normal");
            Normal.appendChild(document.createTextNode(Integer.toString(Save.NormalBestScore)));
            Saves.appendChild(Normal);

            Element Arcade = document.createElement("Arcade");
            Arcade.appendChild(document.createTextNode(Integer.toString(Save.ArcadeBestScore)));
            Saves.appendChild(Arcade);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(new File("Saves.xml"));

            transformer.transform(domSource, streamResult);

        } catch (ParserConfigurationException | TransformerException pce) {
            pce.printStackTrace();

        }
    }
}
