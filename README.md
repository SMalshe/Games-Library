# Games Library
This is a small collection of Java games I’ve been building, along with some basic reusable classes. The main goal was to learn and experiment with object-oriented design, game logic, and a bit of AI. Right now, everything runs in the console, but the structure should make it easy to add more games over time.
## What’s Included
Game class - Main menu that accesses the other classes to run games
Number Guesser – a simple guessing game where the player tries to find the hidden number.
Tic-Tac-Toe – a full game against an AI that uses minimax with alpha-beta pruning, so it plays optimally.
Blackjack – a full implementation of the classic card game, where the player can hit or stand against a dealer AI that follows standard rules, but will sometimes make risky plays. Features include ace value adjustment, bust detection, and replay options.
Card and Deck classes – generic building blocks that can be reused for card-based games
## How It’s Organized
src/  
  │── Card.java - Represents a single playing card  
  │── Deck.java - A deck with shuffle/draw methods  
  │── Game.java - Base class/interface for all games  
  │── NumberGuesser.java - The number guessing game  
  │── TicTacToe.java - Tic-Tac-Toe with an AI opponent  
  │── TreeNode.java - Helper for Tic-Tac-Toe AI game tree  
## Running the Games
Just open the project in your favorite IDE and run the classes directly.
## How Things Work
All games share a common base (Game), so adding new ones is straightforward.
The Tic-Tac-Toe bot evaluates moves with minimax and pruning, which means it won’t lose. Best case, you force a draw.
Number Guesser is simple but a good example of input handling.
Blackjack is the classic card game of 
## Possible Next Steps
Some things I’d like to add in the future:
- A GUI so everything isn’t just in the console.
- More games (Blackjack, Poker, Connect Four, Checkers).
## Contributing
This project is mainly for learning and experimenting, but if you’ve got suggestions or ideas for new games, feel free to open an issue or PR.
