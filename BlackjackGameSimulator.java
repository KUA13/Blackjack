/**
 * This class contains the main() method and is responsible running the 
 * blackjack game. It uses the Card and Deck classes to generate a playing 
 * deck of cards and hands for the player and the dealer. It prompts the user 
 * for how much money they would like to play with. A do-while loop is used to
 * loop the game until the user no longer wishes to play or they run out of 
 * money. The user is prompted for how much they would like to bet on a round 
 * of blackjack, which is subtracted from their total funds. 
 * 
 * The dealCards() method is defined in this class to create the starting 
 * hands for the dealer and player. This class also contains methods to add a 
 * card to an individual's hand when they want to hit, a method to print the 
 * dealer's starting hand, which also output a hidden card, and a method that 
 * can print either the player's or dealer's hand. The game logic is located 
 * in the main() method.
 * 
 * @author Kamal Abro
 * @version 1.0
 */

import java.util.*;

public class BlackjackGameSimulator
{
   public static void main(String[] args)
   {
      //Array holding card suits
      String[] suits = {"Spades", "Clubs", "Hearts", "Diamonds"};
      //Array holding card names
      String[] cardNames = {"Ace", "Two", "Three", "Four", "Five", "Six",
            "Seven", "Eight", "Nine", "Ten", "Jack", "Queen", "King"};
      //Array holding card values
      int[] cardValues = {11, 2, 3, 4, 5, 6, 7, 8, 9, 10, 10, 10, 10};
      
      Scanner input = new Scanner(System.in);
      System.out.println("How much would you like to play with? ");
      double cash = input.nextDouble();
      
      int playGame; //Used to start a new game
      //Creates a new game until player no longer wishes to play or runs out 
      //of money
      do
      {
         Deck playingDeck = new Deck();
         playingDeck.makeDeck(suits, cardNames, cardValues);
         playingDeck.shuffle();
         Deck playerHand = new Deck();
         Deck dealerHand = new Deck();
         //Gets player bet
         System.out.println("How much would you like to bet? ");
         double bet = input.nextDouble();
         //Gets a valid bet from player
         while (bet > cash)
         {
            System.out.println("CANNOT BET MORE THAN AVAILABE CASH!");
            System.out.println("How much would you like to bet? ");
            bet = input.nextDouble();
         }
         
         cash -= bet; 
         double winnings = 0;
         dealCards(playingDeck, playerHand, dealerHand);
         //Value of hands
         int playerValue = playerHand.getHandValue();
         int dealerValue = dealerHand.getHandValue();
         //Used to determine value of aces
         boolean playerHasAces = playerHand.checkForAces();
         boolean dealerHasAces;
         //Changes the value of an ace from 11 to 1 if a value of 11 would
         //cause a bust
         while (playerHasAces == true && playerValue > 21)
         {
            playerHand.reduceAceValue();
            playerHasAces = playerHand.checkForAces();
            playerValue = playerHand.getHandValue();
         }
         
         //Prints starting hands
         printDealerStart(dealerHand);
         System.out.println();
         int printDealer = 1; //Used to print dealer header
         int printPlayer = 2; //Used to print player header
         printHand(playerHand, printPlayer, playerValue);
         System.out.println();
         //Checks if dealer has natural 21
         boolean dealerNat21 = checkNatBlackjack(dealerHand, playingDeck);
         //Ends round if both player and dealer have 21
         if(dealerNat21 == true && playerValue == 21)
         {
            hit(playingDeck, dealerHand);
            dealerValue = dealerHand.getHandValue();
            printHand(dealerHand, printDealer, dealerValue);
            System.out.println();
            printHand(playerHand, printPlayer, playerValue);
            winnings = bet;
            cash += winnings;
            System.out.println("PUSH!");
            System.out.println("YOU WON $" + winnings);
            System.out.println("CURRENT CASH: $" + cash);
         }
         //Player wins if they have 21 and dealer doesn't
         else if (playerValue == 21)
         {
            System.out.println("BLACKJACK!");
            winnings = 2.5 * bet;
            cash += winnings;
            System.out.println("YOU WON $" + winnings);
            System.out.println("CURRENT CASH: $" + cash);
         }
         //Gives player options if round has not yet ended
         else if (playerValue < 21)
         {
            int hitOrStand; //Used to determine if player wishes to hit
            //Prompts user for hit or stand until they choose to stand or they
            //exceed 20
            do
            {
               //Ends round if dealer has natural 21 and player does not
               if (dealerNat21 == true)
               {
                  hit(playingDeck, dealerHand);
                  break;
               }
               System.out.println("HIT(1) OR STAND(2)");
               hitOrStand = input.nextInt();
               if (hitOrStand == 1)
               {
                  hit(playingDeck, playerHand);
                  playerValue = playerHand.getHandValue();
                  playerHasAces = playerHand.checkForAces();
                  //Lowers the value of an ace if a value of 11 would cause a 
                  //bust
                  while (playerHasAces == true && playerValue > 21)
                  {
                     playerHand.reduceAceValue();
                     playerHasAces = playerHand.checkForAces();
                     playerValue = playerHand.getHandValue();
                  }
                  
                  //Prevents player from hitting when they have 21 or over
                  if (playerValue >= 21)
                  {
                     printHand(dealerHand, printDealer, dealerValue);
                     System.out.println();
                     printHand(playerHand, printPlayer, playerValue);
                     break;
                  }
               }
               else if (hitOrStand == 2)
               {
                  //Lets dealer hit if they have less than 17
                  while (dealerValue < 17)
                  {
                     hit(playingDeck, dealerHand);
                     dealerValue = dealerHand.getHandValue();
                     dealerHasAces = dealerHand.checkForAces();
                     //Lowers the value of an ace if a value of 11 would cause
                     //a bust
                     while (dealerHasAces == true && dealerValue > 21)
                     {
                        dealerHand.reduceAceValue();
                        dealerHasAces = dealerHand.checkForAces();
                        dealerValue = dealerHand.getHandValue();
                     }
                  }
               }
               printHand(dealerHand, printDealer, dealerValue);
               System.out.println();
               printHand(playerHand, printPlayer, playerValue);
            }
            while (hitOrStand == 1);
            
            playerValue = playerHand.getHandValue();
            dealerValue = dealerHand.getHandValue();
            //Determines game outcome and pays player correct amount based on 
            //outcome
            if (playerValue == dealerValue && dealerValue <= 21)
            {
               System.out.println("PUSH!");
               winnings = bet;
               cash += winnings;
            }
            else if (playerValue > 21)
            {
               System.out.println("PLAYER BUST");
            }
            else if (playerValue <= 21 && dealerValue > 21)
            {
               System.out.println("YOU WIN!");
               winnings = 2 * bet;
               cash += winnings;
            }
            else if (playerValue > dealerValue && playerValue <= 21)
            {
               System.out.println("YOU WIN!");
               winnings = 2 * bet;
               cash += winnings;
            }
            else if (playerValue < dealerValue && dealerValue <= 21)
            {
               System.out.println("YOU LOSE!");
            }
            System.out.println("WINNINGS: $" + winnings);
            System.out.println("WALLET: $" + cash);
         }
         System.out.println();
         //Determines if player wants to play again
         System.out.println("PLAY AGAIN? YES(1) OR NO(2)");
         playGame = input.nextInt();
         
         if (cash == 0) //Terminates program if player is out of cash
         {
            System.out.println("Sorry, you are out of money.");
            break;
         }
      }
      while (playGame == 1);
      input.close();
   }//end main()
   
   /**
    * This method takes cards from the playing deck and deals cards to the 
    * player and the dealer in the correct order.
    * 
    * @param playingDeck The deck of cards used to deal cards
    * @param playerHand The collection of cards in the player's hand
    * @param dealerHand The collection of cards in the dealer's hand
    */
   public static void dealCards(Deck playingDeck, Deck playerHand, 
         Deck dealerHand)
   {
         Card drawnCard = playingDeck.drawCard();
         playerHand.addCard(drawnCard);
         playingDeck.removeCard();
         drawnCard = playingDeck.drawCard();
         dealerHand.addCard(drawnCard);
         playingDeck.removeCard();
         drawnCard = playingDeck.drawCard();
         playerHand.addCard(drawnCard);
         playingDeck.removeCard();    
   }
   
   /**
    * Takes the card at the top of the playing deck, adds it to the player's
    * hand and removes it from the playing deck
    * 
    * @param playingDeck The playing deck
    * @param hand The deck object representing the hand receiving a card
    */
   public static void hit(Deck playingDeck, Deck hand)
   {
      Card drawnCard = playingDeck.drawCard();
      hand.addCard(drawnCard);
      playingDeck.removeCard();
   }
   
   /**
    * Outputs the dealer's hand with their second card hidden from the player
    * 
    * @param dealerHand The Deck object representing the dealer's hand
    */
   public static void printDealerStart(Deck dealerHand)
   {
      System.out.println("DEALER HAND");
      System.out.print(dealerHand);
      System.out.println("[HIDDEN CARD]");
      System.out.println("Total Value: " + dealerHand.getHandValue());
   }
   
   /**
    * Outputs a Deck object representing the hand of either the player or 
    * dealer
    * 
    * @param hand The hand that is being output
    * @param x Integer that determines what header will be printed out
    * @param totalValue The total value of the hand being output
    */
   public static void printHand(Deck hand, int x, int totalValue)
   {
      if (x == 1)
      {
         System.out.println("DEALER HAND");
      }
      else if (x == 2)
      {
         System.out.println("PLAYER HAND");
      }
      System.out.print(hand);
      System.out.println("Total Value: " + totalValue);
   }
   
   /**
    * Determines if dealer has a natural 21
    * 
    * @param dealerHand Deck object representing dealer's hand
    * @param playingDeck Deck object representing playing deck
    * @return natBlackjack Returns true if dealer has natural 21
    */
   public static boolean checkNatBlackjack(Deck dealerHand, Deck playingDeck)
   {
      boolean natBlackjack = false;
      int hiddenValue = dealerHand.getHandValue() + 
            playingDeck.drawCard().getCardValue();
      if (hiddenValue == 21)
      {
         natBlackjack = true;
      }
      return natBlackjack;
   }
}//end BlackjackGameSimulator class
