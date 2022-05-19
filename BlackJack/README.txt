Team Members -

Name - Piyush Kathuria 
BUID - U01034912
Email - pkath@bu.edu

Name - Balaji Udayakumar
BUID - U17952240
Email - balaji@bu.edu


Compilation Steps -

1. javac *.java
2. java Game


Class Descriptions -


1. Card.java - Class that contains all the attributes and function pertaining to a single card in a standard deck of cards.

2. Hand.java - Class that represents a single hand of a player. The class has a list of cards as a class variable and a function that returns the value of the hand.

3. Player.java - A class that contains methods and attributes common to all card based players. It serves as the base class for BlackJackPlayer.java, TriantaEnaPlayer.java and Dealer.java. It also contains the hit function common to players of all card games.

4. BlackJackPlayer.java - A class that inherits from Player.java and represents a single player for BlackJack. Contains the split and double functionality specific to BlackJack.

5. TriantaEnaPlayer.java - A class that inherits from Player.java and represents a single player for TriantaEna game. Contains the stand functionality specific to TriantaEna.

6. Dealer.java - A class that inherits from Player.java and represents the dealer for all card games. Dealer has functions that can access the pool.java.

7. Pool.java - A class that represents the pool. It is used by Dealer.java class.

8. Game.java - Class that contains the main function to run GameChoice.java.

9. GameChoice.java - Class that gives the user option to choose which game to play. It instantiates the objects for the chosen game.

10. CardGame.java - Class that serves as the base class for all card games. Contains common functionalities like creating a deck of cards.

11. BlackJackGame.java - Class that contains the gameplay and the game logic for playing multiple rounds of BlackJack. It inherits from the CardGame class.

12. TriantaEnaGame.java - Class that contains the gameplay and the game logic for playing multiple rounds of TriantaEna. It inherits from the CardGame class.