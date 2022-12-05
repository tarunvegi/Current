package main.FileFormat;

import main.CreditCard.*;
import main.Handler.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;


import org.codehaus.jackson.map.ObjectMapper;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.File;
import java.util.ArrayList;

public class JsonFile extends FileFormat{
    @Override
    public ArrayList<CreditCard> readFromFile(String inputPath) {

        //create concrete handlers
        MasterCCHandler masterCCHandler = new MasterCCHandler();
        VisaCCHandler visaCCHandler = new VisaCCHandler();
        AmExCCHandler amExCCHandler = new AmExCCHandler();
        DiscoverCCHandler discoverCCHandler = new DiscoverCCHandler();

        //set successor
        masterCCHandler.setSuccessor(visaCCHandler);
        visaCCHandler.setSuccessor(amExCCHandler);
        amExCCHandler.setSuccessor(discoverCCHandler);

        ArrayList<CreditCard> creditCards = new ArrayList<>();
       System.out.println("json file is Given  and check the output file in folder");
        File jsonFile = new File(inputPath);

        JSONParser parser = new JSONParser();

        try {
            Object obj = parser.parse(new FileReader(jsonFile));
            // A JSON array.
            
            JSONObject cards = (JSONObject) obj;
                      
            JSONArray cardList = (JSONArray) cards.get("cards");

            for (int i=0; i<cardList.size(); i++){
                JSONObject creditCard = (JSONObject) cardList.get(i);
                String cardNumber = String.valueOf(creditCard.get("cardNumber"));
                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd");

                Date expirationDate = sdf.parse(String.valueOf(creditCard.get("expirationDate")));
                String nameOfCardHolder = (String) creditCard.get("cardHolderName");
               
                creditCards.add(masterCCHandler.checkCreditCard(cardNumber, expirationDate, nameOfCardHolder));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }



        return creditCards;
    }

    @Override
    public boolean writeToFile(ArrayList<CreditCard> creditCards, String outputPath) {

    

        try {
            FileWriter myWriter = new FileWriter(outputPath + "newoutputfile.json");
            JSONObject cardList = new JSONObject();
            ObjectMapper mapper = new ObjectMapper();
            myWriter.write("{");
			myWriter.write('\n');
            myWriter.write("\"cards\":");
            myWriter.write("[");
  			myWriter.write('\n');
  			for (int i = 0; i < creditCards.size(); i++) {
  				CreditCard cc = creditCards.get(i);
  				myWriter.write("{");
  				myWriter.write('\n');
  				myWriter.write("\"CardNumber\": "+ cc.getCardNumber() + ",");
  				myWriter.write('\n');
  				myWriter.write("\"CardType\": " + "\"" + cc.getType() + "\"");
  				myWriter.write('\n');

  				
  				if(i == creditCards.size() - 1) {
  					myWriter.write("}");
  				}
  				else {
  					myWriter.write("},");
  				}
  				myWriter.write('\n');
  				
  			}
  			myWriter.write("]");
  			myWriter.write("}");
  			myWriter.close();


        } catch (IOException e) {
            e.printStackTrace();
        }

        return true;
    }

}
