/**
 * The Game class represents a game of Battleship.
 * It manages the computer board and the user board, and provides methods to make moves and check for defeat.
 */
public class Game {
    private ComputerBoard computer;
    private UserBoard player;
    boolean superComputer;

    /**
     * constructor for the game class.
     * initializes the computer and player boards.
     */
    public Game() {
        computer = new ComputerBoard("compFleet.txt");
        player = new UserBoard("userFleet.txt");
    }

    /**
     * makes a move on player board.
     * @return an array of strings representing the result of the move
     */
    public String makePlayerMove(String move) {
        Move m = new Move(move);
        return computer.makePlayerMove(m);
    }

    /**
     * makes a move on computer board.
     * @param move the move to be made
     * @return a string representing the result of the move
     */
    public String[] makeComputerMove() {
        if (superComputer) {
            return player.makeAiMove();
        } else {
            return player.makeComputerMove();
        }
    }

    /**
     * checker to see if user has been defeated.
     * @return true if the user has been defeated, false otherwise
     */
    public boolean userDefeated() {
        return player.getFleet().gameOver();
    }

    /**
     * checker to see if computer has been defeated.
     * @return true if the computer has been defeated, false otherwise
     */
    public boolean computerDefeated() {
        return computer.getFleet().gameOver();
    }

    /**
     * toString method for the Game class.
     * @return a string representation of the game
     */
    @Override
    public String toString() {
        return "Computer Board\n" + computer.toString() + "\nPlayer Board\n" + player.toString();
    }

    /**
     * Getter for the computer board.
     * @return the computer board
     */
    public ComputerBoard getComputerBoard() {
        return computer;
    }

    /**
     * Getter for the player board.
     * @return the player board
     */
    public UserBoard getPlayerBoard() {
        return player;
    }

    /**
     * Set the computer to be a super computer.
     * 
     * @param superComputer true if the computer is a super computer, false otherwise
     */
    public void setComputer(boolean superComputer) {
        this.superComputer = superComputer;
    }
}
