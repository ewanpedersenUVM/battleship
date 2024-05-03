/**
 * The Fleet class represents a fleet of ships in the Battleship game.
 * It contains methods to initialize the ships, update the fleet when a ship is hit,
 * and check if all ships in the fleet have been sunk.
 */
public class Fleet {

  private Ship battleShip;
  private Ship aircraftCarrier;
  private Ship cruiser;
  private Ship sub;
  private Ship destroyer;

  /**
   * Constructor for Fleet. Initializes the ships in the fleet.
   */
  public Fleet() {
    battleShip = new Battleship();
    aircraftCarrier = new AircraftCarrier();
    cruiser = new Cruiser();
    sub = new Sub();
    destroyer = new Destroyer();
  }

  /**
   * Informs the appropriate ship that it has been hit, and returns true if this sank the
   * ship, and false if it did not.
   *
   * @param shipType The type of ship that was hit.
   * @return True if the ship was sunk, false if it was not.
   */
  public boolean updateFleet(ShipType shipType) {
    switch (shipType) {
      case ST_BATTLESHIP:
        return battleShip.hit();
      case ST_AIRCRAFT_CARRIER:
        return aircraftCarrier.hit();
      case ST_CRUISER:
        return cruiser.hit();
      case ST_SUB:
        return sub.hit();
      case ST_DESTROYER:
        return destroyer.hit();
      default:
        return false;
    }
  }

  /**
   * Method to check if the game is over.
   *
   * @return True if all ships in the fleet have been sunk, false if they have not.
   */
  public boolean gameOver() {
    return battleShip.getSunk() && aircraftCarrier.getSunk() && cruiser.getSunk() && sub.getSunk() && destroyer.getSunk();
  }

}
