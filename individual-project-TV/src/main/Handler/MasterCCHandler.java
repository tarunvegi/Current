package main.Handler;

import main.CreditCard.*;
import java.util.Date;

public class MasterCCHandler implements Handler{

    private Handler successor = null;

    @Override
   
    public CreditCard checkCreditCard(String cardNumber, Date expirationDate, String nameOfCardHolder) {
      
        int length;
        int firstDigit;
        int secondDigit;
        try {
        	firstDigit = Character.getNumericValue(cardNumber.charAt(0));
            secondDigit = Character.getNumericValue(cardNumber.charAt(1));
            length = cardNumber.length();
		} catch (StringIndexOutOfBoundsException e) {
			
			firstDigit = 0;
            secondDigit = 0;
            length = 0;
		}
        
        if (length==16 && firstDigit==5 && secondDigit<=5 && secondDigit>=1) {
         
            return new MasterCC(cardNumber, expirationDate, nameOfCardHolder, true, "Master Card");
        }
        else {
            if (successor != null)
                return successor.checkCreditCard(cardNumber, expirationDate, nameOfCardHolder);
        }
        return null;
    }

    @Override
    public void setSuccessor(Handler next) {
        this.successor = next;
    }
}
