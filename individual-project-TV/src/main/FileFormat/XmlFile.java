package main.FileFormat;

import main.CreditCard.*;
import main.Handler.*;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import java.util.Date;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class XmlFile extends FileFormat{

    @Override
    public ArrayList<CreditCard> readFromFile(String inputPath) {

       
        MasterCCHandler masterCCHandler = new MasterCCHandler();
        VisaCCHandler visaCCHandler = new VisaCCHandler();
        AmExCCHandler amExCCHandler = new AmExCCHandler();
        DiscoverCCHandler discoverCCHandler = new DiscoverCCHandler();

       
        masterCCHandler.setSuccessor(visaCCHandler);
        visaCCHandler.setSuccessor(amExCCHandler);
        amExCCHandler.setSuccessor(discoverCCHandler);

        ArrayList<CreditCard> creditCards = new ArrayList<>();

        System.out.println("Xml file is Given  and check the output file in folder");
        File xmlFile = new File(inputPath);
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd");

        try {
           
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(xmlFile);
            doc.getDocumentElement();

            NodeList nodeList = doc.getElementsByTagName("CARD");

          
            for (int i = 0; i < nodeList.getLength(); i++) {

                Node nNode = nodeList.item(i);
                String CARD_NUMBER;
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    try {
                        CARD_NUMBER = String.format("%.0f", Double.parseDouble(eElement.getElementsByTagName("CARD_NUMBER").item(0).getTextContent()));
                    }
                    catch (NumberFormatException e) {
						// TODO: handle exception
                    	 CARD_NUMBER = eElement.getElementsByTagName("CARD_NUMBER").item(0).getTextContent();
					}
                    Date EXPIRATION_DATE = dateFormat.parse(eElement.getElementsByTagName("EXPIRATION_DATE").item(0).getTextContent());
                    String CARD_HOLDER_NAME = eElement.getElementsByTagName("CARD_HOLDER_NAME").item(0).getTextContent();

                 
                    creditCards.add(masterCCHandler.checkCreditCard(CARD_NUMBER, EXPIRATION_DATE, CARD_HOLDER_NAME));
                }
            }
        }
        catch (Exception e) {
            System.out.println(e);
        }



        return creditCards;
    }

    @Override
    public boolean writeToFile(ArrayList<CreditCard> creditCards, String outputPath) {

     
        File xmlFile = new File(outputPath + "newoutputfile.xml");

        try {
         
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db= dbf.newDocumentBuilder();
            Document doc = db.newDocument();

            Element rootElement = doc.createElementNS("", "CARDS");
            doc.appendChild(rootElement);

        
            for (CreditCard creditCard : creditCards) {
                Element rowElement = doc.createElement("CARD");
                Element nodeCardNumber = doc.createElement("CARD_NUMBER");
                nodeCardNumber.appendChild(doc.createTextNode(creditCard.getCardNumber()));
                rowElement.appendChild(nodeCardNumber);
                Element nodeType = doc.createElement("CARD_TYPE");
                nodeType.appendChild(doc.createTextNode(creditCard.getType()));
                rowElement.appendChild(nodeType);

                rootElement.appendChild(rowElement);
            }
            
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource domSource = new DOMSource(doc);
            StreamResult outputFile = new StreamResult(xmlFile);
            transformer.transform(domSource, outputFile);

        }
        catch(Exception e) {
            e.printStackTrace();
        }

        return true;
    }
}