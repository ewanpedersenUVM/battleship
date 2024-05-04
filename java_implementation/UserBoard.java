import java.util.ArrayList;
import java.util.Random;

/**
 * The UserBoard maintains a list of all possible moves. Initially, it will be all locations on the
 * Board. When the computer takes a turn, it randomly selects an item from this list and
 * removes it from the list.
 * 
 * Represents the user's game board in the Battleship game.
 * Extends the Board class and provides additional functionality for the user's moves.
 */
public class UserBoard extends Board {
  private ArrayList<Move> moves;
  private Random rand;
  private Ai ai;

  /**
   * constructor for the UserBoard class.
   * Initializes the Random object and the ArrayList of all possible Moves.
   *
   * @param filename the filename of the board layout file
   */
  public UserBoard(String filename) {
    super(filename);
    rand = new Random();
    moves = new ArrayList<Move>();
    for (int i = 0; i < SIZE; i++) {
      for (int j = 0; j < SIZE; j++) {
        moves.add(new Move(i, j));
      }
    }
    ai = new Ai(getLayout());
  }

  /**
   * makes a move on the computer board.
   * The move is randomly selected from the list of all possible moves.
   *
   * @return an array of two Strings. The first is the move the computer made in user readable form.
   *         The second is either null, or, if the move resulted in a ship being sunk,
   *         a string indicating the sunk ship.
   */
  public String[] makeComputerMove() {
    // gets random move from pickRandomMove
    Move move = pickRandomMove();

    // uses the move and updates the layout
    CellStatus cellStatus = applyMoveToLayout(move);

    // if the move was a hit, check if ship was sunk, updateFleet returns true if ship was sunk
    if (cellStatus == CellStatus.NOTHING_HIT) {
      // since nothing was hit, return the move and null
      return new String[] {move.toString(), null};
    } else if (cellStatus == CellStatus.AIRCRAFT_CARRIER_HIT) {
      // if the ship was sunk, return the move and the ship that was sunk
      if (getFleet().updateFleet(ShipType.ST_AIRCRAFT_CARRIER)) {
        // convert all layout cells with aircraft carrier hit to aircraft carrier sunk
        for (int i = 0; i < getLayout().size(); i++) {
          for (int j = 0; j < getLayout().get(i).size(); j++) {
              if (getLayout().get(i).get(j) == CellStatus.AIRCRAFT_CARRIER_HIT) {
                  getLayout().get(i).set(j, CellStatus.AIRCRAFT_CARRIER_SUNK);
              }
          }
        }
        return new String[] {move.toString(), "You sank my Aircraft Carrier!"};
      }
    } else if (cellStatus == CellStatus.BATTLESHIP_HIT) {
      if (getFleet().updateFleet(ShipType.ST_BATTLESHIP)) {
        // convert all layout cells with battleship hit to battleship sunk
        for (int i = 0; i < getLayout().size(); i++) {
          for (int j = 0; j < getLayout().get(i).size(); j++) {
              if (getLayout().get(i).get(j) == CellStatus.BATTLESHIP_HIT) {
                  getLayout().get(i).set(j, CellStatus.BATTLESHIP_SUNK);
              }
          }
        }
        return new String[] {move.toString(), "You sank my Battleship!"};
      }
    } else if (cellStatus == CellStatus.CRUISER_HIT) {
      if (getFleet().updateFleet(ShipType.ST_CRUISER)) {
        // convert all layout cells with cruiser hit to cruiser sunk
        for (int i = 0; i < getLayout().size(); i++) {
          for (int j = 0; j < getLayout().get(i).size(); j++) {
              if (getLayout().get(i).get(j) == CellStatus.CRUISER_HIT) {
                  getLayout().get(i).set(j, CellStatus.CRUISER_SUNK);
              }
          }
        }
        return new String[] {move.toString(), "You sank my Cruiser!"};
      }
    } else if (cellStatus == CellStatus.SUB_HIT) {
      if (getFleet().updateFleet(ShipType.ST_SUB)) {
        // convert all layout cells with sub hit to sub sunk
        for (int i = 0; i < getLayout().size(); i++) {
          for (int j = 0; j < getLayout().get(i).size(); j++) {
              if (getLayout().get(i).get(j) == CellStatus.SUB_HIT) {
                  getLayout().get(i).set(j, CellStatus.SUB_SUNK);
              }
          }
        }
        return new String[] {move.toString(), "You sank my Sub!"};
      }
    } else if (cellStatus == CellStatus.DESTROYER_HIT) {
      if (getFleet().updateFleet(ShipType.ST_DESTROYER)) {
        // convert all layout cells with destroyer hit to destroyer sunk
        for (int i = 0; i < getLayout().size(); i++) {
          for (int j = 0; j < getLayout().get(i).size(); j++) {
              if (getLayout().get(i).get(j) == CellStatus.DESTROYER_HIT) {
                  getLayout().get(i).set(j, CellStatus.DESTROYER_SUNK);
              }
          }
        }
        return new String[] {move.toString(), "You sank my Destroyer!"};
      }
    }
    // this should be reached only if the move was a hit, but no ship was sunk, or if something went wrong
    // if nothing was sunk, return the move and null
    return new String[] {move.toString(), null};
  }

    /** 
   * FOR BATTLESHIP AI
   * makes a move on the computer board, but uses the AI class to predict the next move instead of randomly selecting one.
   * 
   * @return an array of two Strings. The first is the move the computer made in user readable form.
   *        The second is either null, or, if the move resulted in a ship being sunk,
   *       a string indicating the sunk ship.
   */
  public String[] makeAiMove() {
    // gets move from AI class and passes the current layout
    Move move = ai.makeMoveFromMain(getLayout());

    //uses the move and updates the layout
    CellStatus cellStatus = applyMoveToLayout(move);

    // if the move was a hit, check if ship was sunk, updateFleet returns true if ship was sunk
    if (cellStatus == CellStatus.NOTHING_HIT) {
      // since nothing was hit, return the move and null
      return new String[] {move.toString(), null};
    } else if (cellStatus == CellStatus.AIRCRAFT_CARRIER_HIT) {
      // if the ship was sunk, return the move and the ship that was sunk
      if (getFleet().updateFleet(ShipType.ST_AIRCRAFT_CARRIER)) {
        // convert all layout cells with aircraft carrier hit to aircraft carrier sunk
        for (int i = 0; i < getLayout().size(); i++) {
          for (int j = 0; j < getLayout().get(i).size(); j++) {
              if (getLayout().get(i).get(j) == CellStatus.AIRCRAFT_CARRIER_HIT) {
                  getLayout().get(i).set(j, CellStatus.AIRCRAFT_CARRIER_SUNK);
              }
          }
        }
        return new String[] {move.toString(), "You sank my Aircraft Carrier!"};
      }
    } else if (cellStatus == CellStatus.BATTLESHIP_HIT) {
      if (getFleet().updateFleet(ShipType.ST_BATTLESHIP)) {
        // convert all layout cells with battleship hit to battleship sunk
        for (int i = 0; i < getLayout().size(); i++) {
          for (int j = 0; j < getLayout().get(i).size(); j++) {
              if (getLayout().get(i).get(j) == CellStatus.BATTLESHIP_HIT) {
                  getLayout().get(i).set(j, CellStatus.BATTLESHIP_SUNK);
              }
          }
        }
        return new String[] {move.toString(), "You sank my Battleship!"};
      }
    } else if (cellStatus == CellStatus.CRUISER_HIT) {
      if (getFleet().updateFleet(ShipType.ST_CRUISER)) {
        // convert all layout cells with cruiser hit to cruiser sunk
        for (int i = 0; i < getLayout().size(); i++) {
          for (int j = 0; j < getLayout().get(i).size(); j++) {
              if (getLayout().get(i).get(j) == CellStatus.CRUISER_HIT) {
                  getLayout().get(i).set(j, CellStatus.CRUISER_SUNK);
              }
          }
        }
        return new String[] {move.toString(), "You sank my Cruiser!"};
      }
    } else if (cellStatus == CellStatus.SUB_HIT) {
      if (getFleet().updateFleet(ShipType.ST_SUB)) {
        // convert all layout cells with sub hit to sub sunk
        for (int i = 0; i < getLayout().size(); i++) {
          for (int j = 0; j < getLayout().get(i).size(); j++) {
              if (getLayout().get(i).get(j) == CellStatus.SUB_HIT) {
                  getLayout().get(i).set(j, CellStatus.SUB_SUNK);
              }
          }
        }
        return new String[] {move.toString(), "You sank my Sub!"};
      }
    } else if (cellStatus == CellStatus.DESTROYER_HIT) {
      if (getFleet().updateFleet(ShipType.ST_DESTROYER)) {
        // convert all layout cells with destroyer hit to destroyer sunk
        for (int i = 0; i < getLayout().size(); i++) {
          for (int j = 0; j < getLayout().get(i).size(); j++) {
              if (getLayout().get(i).get(j) == CellStatus.DESTROYER_HIT) {
                  getLayout().get(i).set(j, CellStatus.DESTROYER_SUNK);
              }
          }
        }
        return new String[] {move.toString(), "You sank my Destroyer!"};
      }
    }

    // this should be reached only if the move was a hit, but no ship was sunk, or if something went wrong
    // if nothing was sunk, return the move and null
    return new String[] {move.toString(), null};
  }

    
    


  /**
   * Picks a random move from the list, removes it from the list, and then returns it.
   *
   * @return the randomly picked move
   */
  public Move pickRandomMove() {
    // selects a random move from the list of possible moves
    int index = rand.nextInt(moves.size());

    // removed from list so it can't be picked again
    Move move = moves.remove(index);

    return move;
  }

  /**
   * toString method for the UserBoard class.
   * Displays the second character of the String returned by the toString method overridden in CellStatus.
   *
   * @return a String representation of the UserBoard
   */
  @Override
  public String toString() {
    // top row of numbers
    String boardString = "  1 2 3 4 5 6 7 8 9 10\n";

    for (int i = 0; i < getLayout().size(); i++) {
      // add current iterator to char 'A' to get the letter of the row (A-J)
      boardString += (char) ('A' + i) + " ";

      // second iterator to go through the columns
      for (int j = 0; j < getLayout().get(i).size(); j++) {
        //gets the cellsatatus at the current iterator's row and column, gets string representation, and then gets the second character
        boardString += getLayout().get(i).get(j).toString().charAt(1) + " ";
      }
      boardString += "\n";
    }
    return boardString;
  }
}
