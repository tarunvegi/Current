package test;
import main.Handler.*;
import main.CreditCard.*;

import static org.junit.Assert.*;
import org.junit.Test;
import java.util.Date;

public class TestVisaCC {

    @Test
    public void testVisaCC() {

        System.out.println("\nTest for 	Visa CC is Running");

        Date expirationDate = new Date();
        String nameOfCardHolder = "Bob";
        String cardNumber1 = "4120000000000";
        String cardNumber2 = "8120000012340";
        String cardNumber3 = "4120000000000123";
        String cardNumber4 = "41200000000001234";
        String expectedCardType = "Visa Card";

       
        MasterCCHandler masterCCHandler = new MasterCCHandler();
        VisaCCHandler visaCCHandler = new VisaCCHandler();
        AmExCCHandler amExCCHandler = new AmExCCHandler();
        DiscoverCCHandler discoverCCHandler = new DiscoverCCHandler();
       
        masterCCHandler.setSuccessor(visaCCHandler);
        visaCCHandler.setSuccessor(amExCCHandler);
        amExCCHandler.setSuccessor(discoverCCHandler);
     
        CreditCard creditCard1 = masterCCHandler.checkCreditCard(cardNumber1, expirationDate, nameOfCardHolder);
        CreditCard creditCard2 = masterCCHandler.checkCreditCard(cardNumber2, expirationDate, nameOfCardHolder);
        CreditCard creditCard3 = masterCCHandler.checkCreditCard(cardNumber3, expirationDate, nameOfCardHolder);
        CreditCard creditCard4 = masterCCHandler.checkCreditCard(cardNumber4, expirationDate, nameOfCardHolder);


        //Check that card1 is a valid card
        assertTrue(creditCard1.getValid());

        //Check that card1 returns card type: "Visa Card"
        assertEquals(expectedCardType, creditCard1.getType());

        //Check that the first digit of visa card can only be 4
        assertFalse(creditCard2.getValid());

        //Check that the length of visa card can be 16 digits
        assertTrue(creditCard3.getValid());

        //Check that the length of visa card can only be 13 or 16 digits
        assertFalse(creditCard4.getValid());

    }

}
