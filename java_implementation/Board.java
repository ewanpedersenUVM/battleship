import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;

public abstract class Board {

  private ArrayList<ArrayList<CellStatus>> layout;
  private Fleet fleet;
  public static final int SIZE = 10;

  /**
   * Constructor for Board. Initializes the layout of the board and the fleet.
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

    try {
      File file = new File(filename);
      Scanner scanner = new Scanner(file);

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
      e.printStackTrace();
    }

    // Initialize the fleet object
    fleet = new Fleet();
  }

  public CellStatus applyMoveToLayout(Move move) {
    // get the row and col of the move 
    int row = move.row();
    int col = move.col();

    // get the cell status of the mov
    CellStatus cellStatus = layout.get(row).get(col);

    // if cell is nothing, set it to a miss
    if (cellStatus == CellStatus.NOTHING_HIT) {
      layout.get(row).set(col, CellStatus.MISS);
    } else if (cellStatus == CellStatus.AIRCRAFT_CARRIER){
      layout.get(row).set(col, CellStatus.HIT_AIRCRAFT_CARRIER);
    } else if (cellStatus == CellStatus.BATTLESHIP){
      layout.get(row).set(col, CellStatus.HIT_BATTLESHIP);
    } else if (cellStatus == CellStatus.CRUISER){
      layout.get(row).set(col, CellStatus.HIT_CRUISER);
    } else if (cellStatus == CellStatus.SUB){
      layout.get(row).set(col, CellStatus.HIT_SUB);
    } else if (cellStatus == CellStatus.DESTROYER){
      layout.get(row).set(col, CellStatus.HIT_DESTROYER);
    }
    
    return cellStatus;
  }
  

  // takes move as input at returns if the move is available or not(not sunk or hit)
  public boolean moveAvailable(Move move) {
    int row = move.row();
    int col = move.col();
    CellStatus cellStatus = layout.get(row).get(col);

    // if cell has not been hit or sunk, return true
    if (cellStatus == CellStatus.NOTHING || cellStatus == CellStatus.AIRCRAFT_CARRIER || cellStatus == CellStatus.BATTLESHIP || cellStatus == CellStatus.CRUISER || cellStatus == CellStatus.SUB || cellStatus == CellStatus.DESTROYER) {
      return true;
    }
    return false;
  }
  
  /**
  * Take int input and converts to corresponding capital letter from A-J.
  * Uses typecasting to convert int to char.
  */
  private static char intToChar(int i) {
    return (char) (i + 'A');
  }

  /**
  * Take char input and converts to corresponding integer from 0-9.
  * Uses ASCII values to convert char to int.
  */
  private static int charToInt(char c) {
    return c - 'A';
  }
} 
