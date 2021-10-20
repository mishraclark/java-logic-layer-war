import java.util.ArrayList;
import java.util.Collections;

public class Java_War{
  /*@description main calls the functions which make up the war game in the appropriate order.*/
  public static void main(String[] args) {
    ArrayList<Card> deck = deckFactory();
    ArrayList<ArrayList<Card>> playerDecks = getPlayerDecks(deck);
    ArrayList<Card> player1Deck = playerDecks.get(0);
    ArrayList<Card> player2Deck = playerDecks.get(1);
    gameFunction(player1Deck, player2Deck);
  }
/*@description creates a card class which stores a rank, a suit, and a value.
*/
  static class Card {

    private final String rank;
    private final String suit;
    private final Integer value;
/*@constructor
* @param {String} rank
* @param {String} suit
* @param {Integer} value
* */
    public Card(String rank, String suit, Integer value) {
      this.rank = rank;
      this.suit = suit;
      this.value = value;
    }
/*@description creates a method for retrieving the value of a Card*/
  public Integer getValue() {
      return value;
    }
/*@description creates a method for naming a Card by combining its rank and suit Strings*/
    public String toString() {
      return (this.rank + " of " + this.suit);
    }
  }
/*@description Creates an arraylist of card objects representing a standard playing deck.
Each of the 52 Objects of the Card Class are assigned a suit, rank, and value. The resulting arraylist is shuffled before being returned.
@returns ArrayList<Card> deck
*/
  public static ArrayList<Card> deckFactory() {
    String[] suit = {"Hearts", "Clubs", "Diamonds", "Spades"};
    String[] rank = {"Ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King"
    };
    Integer[] value = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13};
    ArrayList<Card> deck = new ArrayList<>();

    int ctr = 0;
    for (int i = 0; i < suit.length; i++) {
      for (int j = 0; j < rank.length; j++) {
        int k = j;
        Card card1 = new Card(rank[j], suit[i], value[k]);
        deck.add(card1);
        ++ctr;
      }
    }

    Collections.shuffle(deck);
    return deck;
  }
/*@description Creates an arraylist of card objects as a deck for each player by dividing the given deck. The resulting arraylists are returned in another arraylist.
* @param {ArrayList<Card>} deck
* @returns {ArrayList<ArrayList<Card>>}
* */
  public static ArrayList<ArrayList<Card>> getPlayerDecks(ArrayList<Card> deck) {
    ArrayList<Card> player1Deck = new ArrayList<>();
    ArrayList<Card> player2Deck = new ArrayList<>();

    for (int ctr = 0; ctr < deck.size(); ctr++) {
      if (ctr < deck.size() / 2) {
        player1Deck.add(deck.get(ctr));
      } else
        player2Deck.add(deck.get(ctr));
    }

    ArrayList<ArrayList<Card>> result = new ArrayList<ArrayList<Card>>();
    result.add(player1Deck);
    result.add(player2Deck);
    return result;
  }
/*@description handles the logic of a war instance within the larger game. War instances are repeated until one player's third card has a higher value than the other.
* Ends the game if one player's deck runs out of cards and awards the game to the player with the most cards,
* @param {ArrayList<Card>} player1Deck
* @param {ArrayList<Card>} player2Deck
* @param {ArrayList<Card>} cardsInPlay
* @param {Card} player1Card
* @param {Card} player2Card
* */
  public static void warFunction(ArrayList<Card> player1Deck, ArrayList<Card> player2Deck, ArrayList<Card> cardsInPlay, Card player1Card, Card player2Card) {
    System.out.println("War Declared!");
    if (player1Deck.size() >= 3 && player2Deck.size() >= 3) {
      player1Card = player1Deck.remove(2);
      player2Card = player2Deck.remove(2);
      cardsInPlay.add(player1Card);
      cardsInPlay.add(player2Card);
      cardsInPlay.add(player1Deck.remove(0));
      cardsInPlay.add(player1Deck.remove(0));
      cardsInPlay.add(player2Deck.remove(0));
      cardsInPlay.add(player2Deck.remove(0));}
    if (player1Deck.size() < 3) {
      player1Deck.clear();
      return;
    }
    if (player2Deck.size() < 3) {
      player2Deck.clear();
      return;
    }
    Collections.shuffle(cardsInPlay);
    System.out.println("Player 1: X X " + player1Card.toString());
    System.out.println("Player 2: X X " + player2Card.toString());
    if (player1Card.getValue() > player2Card.getValue()) {
      for (int i = 0; i < cardsInPlay.size(); i++) {
        player1Deck.add(cardsInPlay.get(i));
      }
      System.out.println("Player 1 Wins the War");
      cardsInPlay.clear();
    }
    else if (player1Card.getValue() < player2Card.getValue()) {
      for (int i = 0; i < cardsInPlay.size(); i++) {
        player2Deck.add(cardsInPlay.get(i));
      }
      System.out.println("Player 2 Wins the War");
      cardsInPlay.clear();
    }
    else if (player1Card.getValue() == player2Card.getValue()) {
      warFunction(player1Deck,player2Deck,cardsInPlay,player1Card,player2Card);
    }

  }
/*@description handles the execution of the rounds, calling in the warFunction for rounds that are ties.
* Ends the game when one player runs out of cards and awards the game to the other player.
* @param {ArrayList<Card>} player1Deck
* @param {ArrayList<Card>} player2Deck
* */
public static void gameFunction(ArrayList<Card> player1Deck, ArrayList<Card> player2Deck) {
  int x = 1;
  while (!player1Deck.isEmpty() && !player2Deck.isEmpty()) {
    System.out.println("Round " + x + " of The War");
    Card player1Card = player1Deck.remove(0);
    Card player2Card = player2Deck.remove(0);
    ArrayList<Card> cardsInPlay = new ArrayList<>();
    cardsInPlay.add(player1Card);
    cardsInPlay.add(player2Card);
    Collections.shuffle(cardsInPlay);
    System.out.println("Player 1: " + player1Card.toString());
    System.out.println("Player 2: " + player2Card.toString());
    if (player1Card.getValue() > player2Card.getValue()) {
      player1Deck.add(cardsInPlay.remove(0));
      player1Deck.add(cardsInPlay.remove(0));
      System.out.println("Player 1 Wins The Round");
    }
    if (player1Card.getValue() < player2Card.getValue()) {
      player2Deck.add(cardsInPlay.remove(0));
      player2Deck.add(cardsInPlay.remove(0));
      System.out.println("Player 2 Wins The Round");
    }
    if (player1Card.getValue() == player2Card.getValue()) {
      warFunction(player1Deck, player2Deck, cardsInPlay, player1Card, player2Card);
    }
    x++;
    if (player1Deck.isEmpty()) {
      System.out.println("Player 2 Wins The Game!");
    }
    if (player2Deck.isEmpty()) {
      System.out.println(("Player 1 Wins The Game!"));
    }

  }
}}

