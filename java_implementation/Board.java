import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;

/**
 * The Board class represents the game board for the Battleship game.
 * It contains the layout of the board, the fleet of ships, and methods to interact with the board.
 */
public abstract class Board {

  private ArrayList<ArrayList<CellStatus>> layout;
  private Fleet fleet;
  public static final int SIZE = 10;

  /**
   * board constructor, intializes board layout and fleet
   *
   * @param filename The name of the file containing the ship placements.
   */
  public Board (String filename) {
    // Initialize layout and set all cells to CellStatus.NOTHING
    layout = new ArrayList<ArrayList<CellStatus>>();
    for (int i = 0; i < SIZE; i++) {
      ArrayList<CellStatus> row = new ArrayList<CellStatus>();
      for (int j = 0; j < SIZE; j++) {
      row.add(CellStatus.NOTHING);
      }
      layout.add(row);
    }

    // read ship placements from file
    try {
      // open the file and create a scanner object
      File file = new File(filename);
      Scanner scanner = new Scanner(file);

      // read file line by line until the end
      while (scanner.hasNextLine()) {
        String line = scanner.nextLine();

        //split the line into an array separated by spaces
        String[] lineArray = line.split(" ");

        //get the ship type
        String shipType = lineArray[0];

        //get the start location
        String startLocation = lineArray[1];

        //get the end location
        String endLocation = lineArray[2];

        //convert the start and end locations to coordinates
        int startRow = charToInt(startLocation.charAt(0));
        int startCol = Integer.parseInt(startLocation.substring(1)) - 1;  //subtract 1 to fix index stuff

        int endRow = charToInt(endLocation.charAt(0));
        int endCol = Integer.parseInt(endLocation.substring(1)) - 1;  //subtract 1 to fix index stuff

        // get the ship length for the type
        int shipLength = 0;
        switch (shipType) {
          case "A":
            shipLength = 5;
            break;
          case "B":
            shipLength = 4;
            break;
          case "C":
            shipLength = 3;
            break;
          case "S":
            shipLength = 3;
            break;
          case "D":
            shipLength = 2;
            break;
          }

        // match the correct cell status to the ship type
        CellStatus cellStatus;
        switch (shipType) {
          case "A":
            cellStatus = CellStatus.AIRCRAFT_CARRIER;
            break;
          case "B":
            cellStatus = CellStatus.BATTLESHIP;
            break;
          case "C":
            cellStatus = CellStatus.CRUISER;
            break;
          case "S":
            cellStatus = CellStatus.SUB;
            break;
          case "D":
            cellStatus = CellStatus.DESTROYER;
            break;
          default:
            cellStatus = CellStatus.NOTHING;
            break;
        }

        // validate that the ship is straight and of the correct length
        if (startRow == endRow) {
          if (endCol - startCol == shipLength - 1) {
            for (int i = startCol; i <= endCol; i++) {
              layout.get(startRow).set(i, cellStatus);
            }
          }
        } else if (startCol == endCol) {
          if (endRow - startRow == shipLength - 1) {
            for (int i = startRow; i <= endRow; i++) {
              layout.get(i).set(startCol, cellStatus);
            }
          }
        } else {
          System.out.println("Invalid");
        }
      }
    } catch (Exception e) {
      System.out.println("File wasn't found");
      e.printStackTrace();
    }

    // Initialize the fleet object
    fleet = new Fleet();
  }

  /**
   * Applies the specified move to the layout and returns the resulting cell status.
   *
   * @param move The move to apply.
   * @return The cell status after applying the move.
   */
  public CellStatus applyMoveToLayout(Move move) {
    // get the row and col of the move 
    int row = move.row() - 1; // convert from human readable index to 0-based index
    int col = move.col() - 1; // convert from human readable index to 0-based index

    // get the cell status of the move
    CellStatus cellStatus = layout.get(row).get(col);

    // if cell is nothing, set it to a miss
    if (cellStatus == CellStatus.NOTHING) {
      layout.get(row).set(col, CellStatus.NOTHING_HIT);
      cellStatus = CellStatus.NOTHING_HIT;
    } else if (cellStatus == CellStatus.AIRCRAFT_CARRIER){
      layout.get(row).set(col, CellStatus.AIRCRAFT_CARRIER_HIT);
      cellStatus = CellStatus.AIRCRAFT_CARRIER_HIT;
    } else if (cellStatus == CellStatus.BATTLESHIP){
      layout.get(row).set(col, CellStatus.BATTLESHIP_HIT);
      cellStatus = CellStatus.BATTLESHIP_HIT;
    } else if (cellStatus == CellStatus.CRUISER){
      layout.get(row).set(col, CellStatus.CRUISER_HIT);
      cellStatus = CellStatus.CRUISER_HIT;
    } else if (cellStatus == CellStatus.SUB){
      layout.get(row).set(col, CellStatus.SUB_HIT);
      cellStatus = CellStatus.SUB_HIT;
    } else if (cellStatus == CellStatus.DESTROYER){
      layout.get(row).set(col, CellStatus.DESTROYER_HIT);
      cellStatus = CellStatus.DESTROYER_HIT;
    }
    
    return cellStatus;
  }
  

  /**
   * Checks if the specified move is available on the board.
   * A move is available if the cell has not been hit or sunk.
   *
   * @param move The move to check.
   * @return true if the move is available, false otherwise.
   */
  public boolean moveAvailable(Move move) {
    int row = move.row() - 1; // convert from human readable index to 0-based index
    int col = move.col() - 1; // convert from human readable index to 0-based index
    CellStatus cellStatus = layout.get(row).get(col);

    // if cell has not been hit or sunk, return true
    if (cellStatus == CellStatus.NOTHING || cellStatus == CellStatus.AIRCRAFT_CARRIER 
        || cellStatus == CellStatus.BATTLESHIP || cellStatus == CellStatus.CRUISER || cellStatus == CellStatus.SUB || cellStatus == CellStatus.DESTROYER) {
      return true;
    }
    return false;
  }
  
  /**
   * Returns a reference to the layout of the board.
   * Note: This method returns a reference to the actual layout, not a deep copy.
   *
   * @return The layout of the board.
   */
  public ArrayList<ArrayList<CellStatus>> getLayout() {
    return layout;
  }

  /**
   * Returns a reference to the fleet of ships on the board.
   * Note: This method returns a reference to the actual fleet, not a deep copy.
   *
   * @return The fleet of ships on the board.
   */
  public Fleet getFleet() {
    return fleet;
  }
  
  /**
   * Checks if the game is over.
   * The game is over if all ships in the fleet are sunk.
   *
   * @return true if the game is over, false otherwise.
   */
  public boolean gameOver() {
    return fleet.gameOver();
  }
  
  /**
   * Converts the specified integer to the corresponding capital letter from A to J.
   *
   * @param i The integer to convert.
   * @return The corresponding capital letter.
   */
  private static char intToChar(int i) {
    return (char) (i + 'A');
  }

  /**
   * Converts the specified character to the corresponding integer from 0 to 9.
   *
   * @param c The character to convert.
   * @return The corresponding integer.
   */
  private static int charToInt(char c) {
    return c - 'A';
  }
}
