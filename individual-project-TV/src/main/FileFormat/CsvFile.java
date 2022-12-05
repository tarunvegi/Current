package main.FileFormat;

import main.CreditCard.*;
import main.Handler.*;

import java.io.*;
import java.text.ParseException;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class CsvFile extends FileFormat{

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

        System.out.println("CSV file is Given and check the output file in folder");
        File csvFile = new File(inputPath);

        String line;
        String csvSplitBy = ",";
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        int i = 1;

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))){
            while ((line = br.readLine()) != null){
                if (i > 1){
                    String[] record = line.split(csvSplitBy);
                    String card;
                    String cardNumber;

                    try {
                    	card =record[0];
                    	try {
                    		cardNumber = String.format("%.0f", Double.parseDouble(record[0]));
                    	}
                    	catch (NumberFormatException e) {
							// TODO: handle exception
                    		cardNumber = record[0];
                    		
						}
                    }
                    catch (ArrayIndexOutOfBoundsException e) {
						// TODO: handle exception
                    	cardNumber="null";
					}
 
                    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd");

                    try {
                    	Date expirationDate = sdf.parse(record[1]);
                        String nameOfCardHolder = record[2];
                        creditCards.add(masterCCHandler.checkCreditCard(cardNumber, expirationDate, nameOfCardHolder));

                    }
                    catch (ArrayIndexOutOfBoundsException e) {
                    	
						
					}
                    
                    
                    
                    
                }
                i++;
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }



        return creditCards;
    }

    @Override
    public boolean writeToFile(ArrayList<CreditCard> creditCards, String outputPath) {


        String COMMA_DELIMITER = ",";
        String NEW_LINE_SEPARATOR = "\n";
        File csvFile = new File(outputPath + "newoutputfile.csv");

        try (PrintWriter printWriter = new PrintWriter(csvFile)) {
            StringBuilder sb = new StringBuilder();
            //first line TAG
            sb.append("cardNumber");
            sb.append(COMMA_DELIMITER);
            sb.append("cardType");
            sb.append(",");
        
            sb.append(NEW_LINE_SEPARATOR);

            for (CreditCard creditCard : creditCards) {
                sb.append(creditCard.getCardNumber());
                sb.append(COMMA_DELIMITER);
                sb.append(creditCard.getType());

                sb.append(NEW_LINE_SEPARATOR);
            }
            printWriter.write(sb.toString());
        }
        catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }

        return true;
    }
}