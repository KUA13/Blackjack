/**
 * This class defines Card objects. Each Card object will have a suit, a 
 * cardName and a cardValue associated with it. The value for these variables 
 * will be contained in three separate array in the main() method of the 
 * BlackjackGameSimulator file.
 *  
 * @author Kamal Abro
 *
 */

public class Card
{
   String suit;
   String cardName;
   int cardValue;
   
   public Card(String suit, String cardName, int cardValue)
   {
      this.suit = suit;
      this.cardName = cardName;
      this.cardValue = cardValue;
   }

   public String getSuit()
   {
      return suit;
   }
   public void setSuit(String suit)
   {
      this.suit = suit;
   }
   public String getCardName()
   {
      return cardName;
   }
   public void setCardName(String cardName)
   {
      this.cardName = cardName;
   }
   public int getCardValue()
   {
      return cardValue;
   }
   public void setCardValue(int cardValue)
   {
      this.cardValue = cardValue;
   }
}//end Card class
