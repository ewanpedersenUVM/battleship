public class ComputerBoard extends Board {

    /**
     * constructor for the ComputerBoard class.
     * initializes the board layout from the specified file.
     *
     * @param filename the name of the file containing the board layout
     */
    public ComputerBoard(String filename) {
        super(filename);
    }

    /**
     * takes a user input move and applies it to the board layout.
     *
     * @param move the move to be applied
     * @return either null if the move did not hit any ship, or a String indicating that a ship was sunk
     */
    public String makePlayerMove(Move move) {
        CellStatus cellStatus = applyMoveToLayout(move);
        if (cellStatus == CellStatus.NOTHING_HIT) {
            return null;
        } else if (cellStatus == CellStatus.AIRCRAFT_CARRIER_HIT) {
            if (getFleet().updateFleet(ShipType.ST_AIRCRAFT_CARRIER)) {
                // convert all layout cells with aircraft carrier hit to aircraft carrier sunk
                for (int i = 0; i < getLayout().size(); i++) {
                    for (int j = 0; j < getLayout().get(i).size(); j++) {
                        if (getLayout().get(i).get(j) == CellStatus.AIRCRAFT_CARRIER_HIT) {
                            getLayout().get(i).set(j, CellStatus.AIRCRAFT_CARRIER_SUNK);
                        }
                    }
                }
                return "You sank my Aircraft Carrier!";
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
                return "You sank my Battleship!";
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
                return "You sank my Cruiser!";
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
                return "You sank my Sub!";
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
                return "You sank my Destroyer!";
            }
        }
        return null;
    }

    /**
     * toString method for the computer board.
     *
     * @return a String representing the board layout
     */
    @Override
    public String toString() {
        String boardString = "  1 2 3 4 5 6 7 8 9 10\n";
        for (int i = 0; i < getLayout().size(); i++) {
            boardString += (char) ('A' + i) + " ";
            for (int j = 0; j < getLayout().get(i).size(); j++) {
                boardString += getLayout().get(i).get(j).toString().charAt(0) + " ";
            }
            boardString += "\n";
        }
        return boardString;
    }

}
