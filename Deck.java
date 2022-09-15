/**
 * This class is used to generate Deck objects. Each deck object is defined 
 * as an ArrayList of Card objects. Decks objects are used to form the playing 
 * deck, as well as the hands of both the player and dealer.
 * This class also contains many methods that are useful in creating a 
 * functioning Blackjack game.
 * 
 * @author Kamal Abro
 * @version 1.0
 */

import java.util.ArrayList;
import java.util.Random;

public class Deck
{
   //ArrayList of Card objects used to form a deck or hand of cards
   private ArrayList<Card> cards;

   public Deck()
   {
      super();
      this.cards = new ArrayList<Card>();
   }
   
   /**
    * Generates a single playing deck of cards to be used during a blackjack
    * game
    * 
    * @param suits An array of Strings used to assign a suit to each Card
    * @param cardNames An array of Strings used to assign a cardName to each 
    * Card
    * @param cardValues An array of integers used to assign a cardValue to each
    * card
    */
   public void makeDeck(String[] suits, String[] cardNames, int[] cardValues)
   {
      int i;
      int j;
      for (i = 0; i < suits.length; i++)
      {
         for (j = 0; j < cardNames.length; j++)
         {
            String suit = suits[i];
            String cardName = cardNames[j];
            int cardValue = cardValues[j];
            this.cards.add(new Card(suit, cardName, cardValue));
         }
      }
   }
   
   /**
    * Takes an ArrayList of Card objects and shuffles the order of Card 
    * objects in the ArrayList.
    */
   public void shuffle()
   {
      Random rdm = new Random();
      ArrayList<Card> tempDeck = new ArrayList<Card>();
      int rdmCardIndex;
      int ogDeckSize = this.cards.size();
      for (int i = 0; i < ogDeckSize; i++)
      {
         rdmCardIndex = rdm.nextInt(this.cards.size());
         tempDeck.add(this.cards.get(rdmCardIndex));
         this.cards.remove(rdmCardIndex);
      }
      this.cards = tempDeck;
   }
   
   /**
    * Creates a String that can be printed out to show every Card in a Deck
    * object
    * 
    * @return cardListOutput The String that shows every Card in a Deck
    */
   public String toString()
   {
      String cardListOutput = "";
      for (Card cards : this.cards)
      {
         cardListOutput += cards.getCardName() + " of " + cards.getSuit() + 
               " " + cards.getCardValue() + "\n";
      }
      return cardListOutput;
   }
   
   /**
    * Gets the top card (index 0) of the ArrayList in a Deck
    * 
    * @return topCard The card at the top of a Deck
    */
   public Card drawCard()
   {
      Card topCard = this.cards.get(0);
      return topCard;
   }
   
   /**
    * Takes a Card object and adds it to the end of an ArrayList in a Deck 
    * object
    * 
    * @param drawnCard The card to be added to a Deck object
    */
   public void addCard(Card drawnCard)
   {
      this.cards.add(drawnCard);
   }
   
   /**
    * Removes the top card (index 0) of a Deck object
    */
   public void removeCard()
   {
      this.cards.remove(0);
   }
   
   /**
    * Sums the value of every Card in a Deck
    * 
    * @return totalValue The total value of all cards in a Deck object
    */
   public int getHandValue()
   {
      int totalValue = 0;
      for (Card cards : this.cards)
      {
         totalValue += cards.getCardValue();
      }
      return totalValue;
   }
   
   /**
    * Determines the size of a Deck object
    * 
    * @return handSize The size of the Deck object
    */
   public int getHandSize()
   {
      int handSize = this.cards.size();
      return handSize;
   }
   
   /**
    * Determines if a Deck object contains an Ace with a value of 11 and stops
    * checking once a single Ace with a value of 11 is found.
    * 
    * @return conatainsAces Returns true if the Deck contains at least one Ace 
    * with a value of 11, 
    */
   public boolean checkForAces()
   {
      boolean containsAces = false;
      for (Card cards : this.cards)
      {
         int cardValue = cards.getCardValue();
         if (cardValue == 11)
         {
            containsAces = true;
            break;
         }
         else
         {
            containsAces = false;
         }
      }
      return containsAces;
   }
   
   /**
    * Reduces the value of one Ace card in a Deck object from 11 to 1
    */
   public void reduceAceValue()
   { 
      int newAceValue = 1;
      for (Card cards : this.cards)
      {
         if (cards.getCardValue() == 11)
         {
            cards.setCardValue(newAceValue);
            break;
         }
      }
   }
}//end Deck class
