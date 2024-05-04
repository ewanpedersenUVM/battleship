import java.util.ArrayList;
import java.util.List;

/**
 * The Ai class represents the AI for the Battleship game. It uses a probability map to determine the best move to make.
 * I also did a reinforcement learning version, but I don't think you would be able to grade that easily as it would just be a bunch of weight!
 */
public class Ai {
    private ArrayList<Move> moves;
    private ArrayList<ArrayList<CellStatus>> board;

    // probability map for the AI
    private double[][] probabilityMap;
    // all shots taken by the AI
    private int[][] shotsMap;
    // all ships that the AI has hit, excluding misses and sunk ships
    private int[][] shipsMap;
    // coordinates that the AI has sunk
    private ArrayList<int[]> sunkCoords;

    private static final int[] SHIP_LENGTHS = {2, 3, 3, 4, 5};
    private static final String[] SHIP_NAMES = {"Destroyer", "Submarine", "Cruiser", "Battleship", "Aircraft Carrier"};

    /**
     * Constructor for the Ai class.
     * @param board
     */
    public Ai(ArrayList<ArrayList<CellStatus>> board) {
        this.board = board;
        moves = new ArrayList<Move>();
        for (int i = 0; i < Board.SIZE; i++) {
            for (int j = 0; j < Board.SIZE; j++) {
                moves.add(new Move(i, j));
            }
        }
        shotsMap = new int[Board.SIZE][Board.SIZE];
    }

    /**
     * Makes a move from the main method.
     * @param board the board to make a move on
     * @return the move to make
     */
    public Move makeMoveFromMain(ArrayList<ArrayList<CellStatus>> board) {
        updateBoard(board);
        convertCellBoardtoMaps(board);
        generateProbMap();
        int[] coords = getHighestProb();
        Move move = new Move(coords[0], coords[1]);
        // System.out.println(printProbMap());
        return move;
    }

    /**
     * Takes the board and extracts the shotsMap, shipsMap, and sunkCoords.
     * @param board
     */
    public void convertCellBoardtoMaps(ArrayList<ArrayList<CellStatus>> board) {
        shotsMap = new int[10][10];
        shipsMap = new int[10][10];
        sunkCoords = new ArrayList<int[]>();
        for (int i = 0; i < Board.SIZE; i++) {
            for (int j = 0; j < Board.SIZE; j++) {
                CellStatus cell = board.get(i).get(j);
                switch(cell) {
                    case AIRCRAFT_CARRIER:
                        break;
                    case AIRCRAFT_CARRIER_HIT:
                        this.shotsMap[i][j] = 1;
                        this.shipsMap[i][j] = 1;
                        break;
                    case AIRCRAFT_CARRIER_SUNK:
                        this.shotsMap[i][j] = 1;
                        this.shipsMap[i][j] = 1;
                        this.sunkCoords.add(new int[]{i, j});
                        break;
                    case BATTLESHIP:
                        break;
                    case BATTLESHIP_HIT:
                        this.shotsMap[i][j] = 1;
                        this.shipsMap[i][j] = 1;
                        break;
                    case BATTLESHIP_SUNK:
                        this.shotsMap[i][j] = 1;
                        this.shipsMap[i][j] = 1;
                        this.sunkCoords.add(new int[]{i, j});
                        break;
                    case CRUISER:
                        break;
                    case CRUISER_HIT:
                        this.shotsMap[i][j] = 1;
                        this.shipsMap[i][j] = 1;
                        break;
                    case CRUISER_SUNK:
                        this.shotsMap[i][j] = 1;
                        this.shipsMap[i][j] = 1;
                        this.sunkCoords.add(new int[]{i, j});
                        break;
                    case DESTROYER:
                        break;
                    case DESTROYER_HIT:
                        this.shotsMap[i][j] = 1;
                        this.shipsMap[i][j] = 1;
                        break;
                    case DESTROYER_SUNK:
                        this.shotsMap[i][j] = 1;
                        this.shipsMap[i][j] = 1;
                        this.sunkCoords.add(new int[]{i, j});
                        break;
                    case SUB:
                        break;
                    case SUB_HIT:
                        this.shotsMap[i][j] = 1;
                        this.shipsMap[i][j] = 1;
                        break;
                    case SUB_SUNK:
                        this.shotsMap[i][j] = 1;
                        this.shipsMap[i][j] = 1;
                        this.sunkCoords.add(new int[]{i, j});
                        break;
                    case NOTHING:
                        break;
                    case NOTHING_HIT:
                        this.shotsMap[i][j] = 1;
                        break;
                } 
            }
        }
    }

    /**
     * Updates the board for the AI.
     * @param board
     */
    public void updateBoard(ArrayList<ArrayList<CellStatus>> board) {
        this.board = board;
    }

    /**
     * Gets the index of a value in an array.
     * @param array the array to search
     * @param value the value to search for
     */
    public int getIndex(String[] array, String value) {
        for (int i = 0; i < array.length; i++) {
            if (array[i].equals(value)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Generates the probability map for the AI.
     */
    public void generateProbMap() {
        probabilityMap = new double[10][10];

        for (int ship = 0; ship < SHIP_NAMES.length; ship++) {
            // gets the lenghs of the ships, i would have used fleet getters but i was testing this in isolation
            String shipName = SHIP_NAMES[ship];
            int shipSize = SHIP_LENGTHS[ship];
            int usedSize = shipSize - 1;

            // checks all possible locations for the ship
            for(int row = 0; row < 10; row++) {
                for(int col = 0; col < 10; col++) {
                    if (this.shotsMap[row][col] != 1) {
                        // possible ship end locations, arraylist of tuples
                        ArrayList<int[][]> posssibleEnds = new ArrayList<int[][]>();

                        if (row - usedSize >= 0) {
                            posssibleEnds.add(new int[][]{{row - usedSize, col}, {row + 1, col + 1}});
                        }
                        if (row + usedSize <= 9) {
                            posssibleEnds.add(new int[][]{{row, col}, {row + usedSize + 1, col + 1}});
                        }
                        if (col - usedSize >= 0) {
                            posssibleEnds.add(new int[][]{{row, col - usedSize}, {row + 1, col + 1}});
                        }
                        if (col + usedSize <= 9) {
                            posssibleEnds.add(new int[][]{{row, col}, {row + 1, col + usedSize + 1}});
                        }

                        // checks if the ship can be placed in the possible locations
                        for (int[][] endpoint : posssibleEnds) {
                            int startRow = endpoint[0][0];
                            int startCol = endpoint[0][1];
                            int endRow = endpoint[1][0];
                            int endCol = endpoint[1][1];
                            boolean allZero = true;
                            for (int i = startRow; i < endRow; i++) {
                                for (int j = startCol; j < endCol; j++) {
                                    if (shotsMap[i][j] != 0) {
                                        allZero = false;
                                        break;
                                    }
                                }
                                if (!allZero) {
                                    break;
                                }
                            }
                            if (allZero) {
                                for (int i = startRow; i < endRow; i++) {
                                    for (int j = startCol; j < endCol; j++) {
                                        probabilityMap[i][j]++;
                                        
                                    }
                                }
                            }
                        }
                    }
                        
                        // increasing probability for hitting cells near successful hits
                        if ((shotsMap[row][col] == 1) && (shipsMap[row][col] == 1) && !(sunkCoordsContains(row, col))) {
                            // check all directions
                            int[][] directions = {{-1,0}, {1,0}, {0,-1}, {0,1}};
                            for (int[] direction : directions) {
                                int newRow = row + direction[0];
                                int newCol = col + direction[1];
                                if (newRow >= 0 && newRow <= 9 && newCol >= 0 && newCol <= 9 && shotsMap[newRow][newCol] == 0) {
                                    int oppositeRow = row - direction[0];
                                    int oppositeCol = col - direction[1];
                                    if (oppositeRow >= 0 && oppositeRow <= 9 && oppositeCol >= 0 &&
                                        oppositeCol <= 9 && sunkCoords.contains(new int[]{oppositeRow, oppositeCol}) &&
                                        shotsMap[oppositeRow][oppositeCol] == 1 && shipsMap[oppositeRow][oppositeCol] == 1) {
                                        
                                        System.out.println("we got here!");
                                        probabilityMap[newRow][newCol] += 15;
                                    } else {
                                        probabilityMap[newRow][newCol] += 10;
                                    }
                                }
                            }
                        }

                        // decrease probablity for missing to zero
                        if (shotsMap[row][col] == 1 && shipsMap[row][col] != 1) {
                            probabilityMap[row][col] = 0;
                        }


                    
                }
            }
        }
        // just in case!
        this.probabilityMap = probabilityMap;
    }

    /** 
     * checker for sunkCoords
     * 
     * @param row the row to check
     * @param col the col to check
     * @return true if the coordinates are in sunkCoords, false otherwise
     */
    public boolean sunkCoordsContains(int row, int col) {
        for (int[] coord : sunkCoords) {
            if (coord[0] == row && coord[1] == col) {
                return true;
            }
        }
        return false;
    }

    /**
     * Gets the highest probability on the probability map.
     * @return the coordinates of the highest probability
     */
    public int[] getHighestProb() {
        double max = 0;
        int[] coords = new int[2];
        for (int i = 0; i < probabilityMap.length; i++) {
            for (int j = 0; j < probabilityMap[i].length; j++) {
                if (probabilityMap[i][j] > max) {
                    max = probabilityMap[i][j];
                    coords[0] = i;
                    coords[1] = j;
                }
            }
        }
        return coords;
    }

    /**
     * (for debugging) Prints the probability map for the AI.
     * @return the probability map as a string
     */
    public String printProbMap() {
        String printString = "";
        for (int i = 0; i < probabilityMap.length; i++) {
            for (int j = 0; j < probabilityMap[i].length; j++) {
                printString += probabilityMap[i][j] + " ";
            }
            printString += "\n";
        }
        printString += "\n\n\n";

        //shots map
        for (int i = 0; i < shotsMap.length; i++) {
            for (int j = 0; j < shotsMap[i].length; j++) {
                printString += shotsMap[i][j] + " ";
            }
            printString += "\n";
        }
        printString += "\n\n\n";

        //ships map
        for (int i = 0; i < shipsMap.length; i++) {
            for (int j = 0; j < shipsMap[i].length; j++) {
                printString += shipsMap[i][j] + " ";
            }
            printString += "\n";
        }
        return printString;
    }

}