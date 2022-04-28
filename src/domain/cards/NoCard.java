package domain.cards;

/**
 * 
 * 
 * dienst als Platzhalter, erfüllt für die Spiellogik keine Rolle.
 * 
 * @author paulh
 *
 */
public class NoCard extends AbstractCard {

  /**
   * Konstruktor zum Setzen der Farbe.
   */
  public NoCard(Color color) {
    super(color);

  }

  @Override
  public boolean isNumber() {

    return false;
  }

  @Override
  public String toString() {

    return "--";
  }

  @Override
  public int compareTo(AbstractCard o) {
    // darf nie aufgerufen werden
    return -1;
  }

  @Override
  public int getValue() {

    return 0;
  }

}
