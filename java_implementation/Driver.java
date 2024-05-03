import java.util.Scanner;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

/**
 * The Driver class is responsible for running the Battleship game.
 * It handles the game setup, turn management, and game loop.
 */
public class Driver {
    /**
     * main method/entry point for the program.
     * It initializes the game, determines the first turn, and starts the game loop.
     *
     * @param args the command line arguments, although in this case, they are not used.
     */
    public static void main(String[] args) {
        // game declaration
        Game battleship = new Game();
        int turn = 0;

        // welcome message
        System.out.println("Welcome to Battleship!");
        System.out.println("You will be playing against the computer.");

        // coin flip to determine who goes first
        System.out.println("We will use a coin flip to determine who goes first.\n");
        Scanner keyboard = new Scanner(System.in);
        System.out.println("Please enter 'H' for heads or 'T' for tails: ");
        String userGuess = keyboard.nextLine();
        Random rand = new Random();
        int coinFlip = rand.nextInt(2);
        if (coinFlip == 0) {
            System.out.println("\nThe coin flip was heads.");
            if (userGuess.equals("H")) {
                System.out.println("You won the coin flip! You will go first.");
                turn = 1;
            } else {
                System.out.println("You lost the coin flip! The computer will go first.");
                turn = 2;
            }
        } else {
            System.out.println("\nThe coin flip was tails.");
            if (userGuess.equals("T")) {
                System.out.println("You won the coin flip! You will go first.");
                turn = 1;
            } else {
                System.out.println("You lost the coin flip! The computer will go first.");
                turn = 2;
            }
        }

        // Move tracker
        ArrayList<String> playerMoves = new ArrayList<String>();

        // main game loop
        while (battleship.userDefeated() == false && battleship.computerDefeated() == false) {
            if (turn == 1) {
                // board printing
                System.out.println("Your turn!\n");
                System.out.println("Your board:");
                System.out.println(battleship.getPlayerBoard());
                System.out.println("Computer board:");
                System.out.println(battleship.getComputerBoard()+"\n");

                // user move with input validation
                String userMove;
                do {
                    System.out.println("Please enter your move in the form 'A1': ");
                    userMove = keyboard.nextLine();

                    // make letter uppercase
                    userMove = userMove.toUpperCase();

                    // if length is not 2 or second char is not a number, invalid move
                    // this is because it could be 3 chars if number is 10, but the second char would have to be a number in that case
                    if ((userMove.length() != 2) && (userMove.charAt(1) < '0' || userMove.charAt(1) > '9')) {
                        System.out.println("Incorrect number of characters. Please enter a move in the form 'A1'.");
                    } else {
                        if (userMove.charAt(0) < 'A' || userMove.charAt(0) > 'J') {
                            System.out.println("First char out of bounds. Please enter a move in the form 'A1'.");
                        } else {
                            int secondNumber = Character.getNumericValue(userMove.charAt(1));
                            if (secondNumber < 1 || secondNumber > 11) {
                                System.out.println("Invalid move. Please enter a move in the form 'A1'.");
                            } else {
                                // forfeit move if already made, append to arraylist if not
                                if (playerMoves.contains(userMove)) {
                                    System.out.println("You have already made this move. Forfeit turn.");
                                } else {
                                    playerMoves.add(userMove);
                                }
                                break;
                            }
                        }
                    }
                } while (true);

                // makes the users move and prints it, if the user sunk a ship, it will print that as well
                String result = battleship.makeComputerMove(userMove);
                if (result != null) {
                    System.out.println(result);
                }
                turn = 2;
            } else if (turn == 2) {
                System.out.println("Computer's turn!");
                String[] result = battleship.makePlayerMove();
                System.out.println("Computer entered: " + result[0]);
                if (result[1] != null) {
                    System.out.println(result[1]);
                }
                turn = 1;
            } else {
                System.out.println("You should not be here!");
            }
        }

        if (battleship.userDefeated() == true) {
            System.out.println("You lost! The computer has defeated you.");
        } else {
            System.out.println("You won! You have defeated the computer.");
        }
    }
}
