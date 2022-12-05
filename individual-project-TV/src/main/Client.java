package main;

import main.CreditCard.*;
import main.FileFormat.*;

import java.util.ArrayList;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) {

        ArrayList<CreditCard> creditCards;
        FileFormat newFile;

        System.out.println("Give your input file name ::::");
        Scanner userInput = new Scanner(System.in);

        if (userInput.hasNextLine()) {
            String typeOfFile = userInput.nextLine();
           
            newFile = FileFactory.makeFile(typeOfFile);

            if (newFile != null){
            	
                creditCards = newFile.readFromFile("C:/Users/Checkout/Desktop/TV/individual-project/src/" + typeOfFile);
              
                if (newFile.writeToFile(creditCards, "C:/Users/Checkout/Desktop/TV/individual-project/src/")){
                
                }
            }
            else {
                System.out.println("Error");
            }
        }

    }

}
