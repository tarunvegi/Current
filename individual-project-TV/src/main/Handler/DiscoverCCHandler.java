package main.Handler;

import main.CreditCard.*;
import java.util.Date;

public class DiscoverCCHandler implements Handler{

    private Handler successor = null;

    @Override
   
    public CreditCard checkCreditCard(String cardNumber, Date expirationDate, String nameOfCardHolder) {
       
        int length = cardNumber.length();
      
        if (length==16 && cardNumber.startsWith("6011") && cardNumber.matches("[-+]?[0-9]*\\.?[0-9]+") ) {
        
            return new DiscoverCC(cardNumber, expirationDate, nameOfCardHolder, true, "Discover Card");
        }
        else {
            if (successor != null)
                return successor.checkCreditCard(cardNumber, expirationDate, nameOfCardHolder);
        }
        
        String message = "";
        if(length > 16)
        {
        	message = "Invalid: more than 19 digits";
        }
        else if (cardNumber.isBlank() || cardNumber == "null")
        {
        	message = "Invalid: empty/null card number";
        }
        else if(!cardNumber.matches("[-+]?[0-9]*\\.?[0-9]+"))
        {
        	message = "Invalid: non numeric characters";
        }
        
        else
        {
        	message = "Invalid: Not a possible card number";
        }
        
     
        return new DiscoverCC(cardNumber, expirationDate, nameOfCardHolder, false, message);
    }

    @Override
    public void setSuccessor(Handler next) {
        this.successor = next;
    }
}
