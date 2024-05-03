/**
 * The move class represents a move within the game of battleship.
 * It contains the row and column of the move, as well as methods for converting between
 * the human-readable format of the move and the internal format.
 */
public class Move {

  private int row;
  private int col;

  /**
   * constructs a Move object from two integers representing the indices in a two-
   * dimensional array.
   *
   * @param row The row index.
   * @param col The column index.
   */
  public Move(int row, int col) {
    this.row = row + 1; // one added because we want human readable index not 0-based index
    this.col = col + 1; // one added because we want human readable index not 0-based index
  }

  /** 
   * Creates a move object from a String consisting of a letter and a number.
   *
   * @param move The move in the format letter+number. ex. "A1"
   */
  public Move(String move) {
    // gets first char as row and then takes rest of string as column
    this.row = charToInt(move.charAt(0));
    this.col = Integer.parseInt(move.substring(1)); // one is not removed because we want human readable index not 0-based index
  }

  /**
   * Accessor for row. Using 'row' rather than 'getRow' allows for more compact code when
   * manipulating ArrayLists.
   *
  * @return The row index.
  */
  public int row() {
    return row;
  }

  /**
   * Accessor for col. Using 'col' rather than 'getCol' allows for more compact code when
   * manipulating ArrayLists.
   *
   * @return The column index.
   */
  public int col() {
    return col;
  }

  /**
   * creates a 2 to 3-character string consisting of a letter in the range A-J followed by a
   * number in the range 1-10. Provides for ease of display of move values in an interface.
   *
   * @return The move in the format letter+number. ex. "A1"
   */
  @Override
  public String toString() {
    // number at row and col for debugging
    // System.out.println("row: " + row + " col: " + col);
    // System.out.println(charToInt(intToChar(row)));
    return intToChar(row) + "" + col;
  }

  /**
  * Take int input and converts to corresponding capital letter from A-J.
  * Uses typecasting to convert int to char.
  * also subtracts 1 to the int because we want human readable index not 0-based index
  */
  private static char intToChar(int i) {
    return (char) ((i-1) + 'A');
  }

  /**
  * Take char input and converts to corresponding integer from 1-9.
  * Uses ASCII values to convert char to int.
  * also adds 1 to the int because we want human readable index not 0-based index
  */
  private static int charToInt(char c) {
    return c - 'A' + 1;
  }
}
