public class Move {

  private int row;
  private int col;

  /**
   * Creates a Move object from two integers representing the indices in a two-
   * dimensional array.
   *
   * @param row The row index.
   * @param col The column index.
   */
  public Move(int row, int col) {
    this.row = row;
    this.col = col;
  }

  /** 
   * Creates a move object from a String consisting of a letter and a number.
   *
   * @param move The move in the format letter+number. ex. "A1"
   */
  public Move(String move) {
    // gets first char as row and then takes rest of string as column
    this.row = charToInt(move.charAt(0));
    this.col = Integer.parseInt(move.substring(1)) - 1;
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
   * Returns a 2 to 3-character string consisting of a letter in the range A-J followed by a
   * number in the range 1-10. Provides for ease of display of move values in an interface.
   *
   * @return The move in the format letter+number. ex. "A1"
   */
  @Override
  public String toString() {
    return intToChar(row) + "" + col;
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
