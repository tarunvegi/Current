package main.Handler;

import main.CreditCard.*;
import java.util.Date;

public class VisaCCHandler implements Handler{

    private Handler successor = null;

    @Override
   
    public CreditCard checkCreditCard(String cardNumber, Date expirationDate, String nameOfCardHolder) {
               int length;
        int firstDigit;
        try {
        	firstDigit = Character.getNumericValue(cardNumber.charAt(0));
            length = cardNumber.length();
		} catch (StringIndexOutOfBoundsException e) {
			
			firstDigit = 0;
            length = 0;
		}
        if ((length==16 || length==13) && firstDigit==4) {
         
            return new VisaCC(cardNumber, expirationDate, nameOfCardHolder, true, "Visa Card");
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
