package main.Handler;

import main.CreditCard.*;
import java.util.Date;

public class AmExCCHandler implements Handler{

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
        if (length==15 && firstDigit==3 && (secondDigit==4 || secondDigit==7)) {
          
            return new AmExCC(cardNumber, expirationDate, nameOfCardHolder, true, "AmericanExpress Card");
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
