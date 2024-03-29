package domain.cards;

import java.util.Arrays;

/**
 * Mit Stapel ist alles gemeint, worauf ein Spieler seine Karten ablegen kann
 * 
 * @author paulh
 *
 */
public enum Stapel {
  NACHZIEHSTAPEL(null), YELLOWMIDDLE(Color.YELLOW), WHITEMIDDLE(Color.WHITE), BLUEMIDDLE(
      Color.BLUE), GREENMIDDLE(Color.GREEN), REDMIDDLE(Color.RED), YELLOWEXPEDITION(
          Color.YELLOW), WHITEEXPEDITION(Color.WHITE), BLUEEXPEDITION(
              Color.BLUE), GREENEXPEDITION(Color.GREEN), REDEXPEDITION(Color.RED);

  /**
   * Ein Array, das eine feste Ordnung über den Ablagestaepeln festlegt
   */
  public static final Stapel orderedMiddle[] =
      {YELLOWMIDDLE, WHITEMIDDLE, BLUEMIDDLE, GREENMIDDLE, REDMIDDLE};

  public static final Stapel alleZiehStapel[] =
      {YELLOWMIDDLE, WHITEMIDDLE, BLUEMIDDLE, GREENMIDDLE, REDMIDDLE, NACHZIEHSTAPEL};

  /**
   * Ein Array,das eine feste Ordnung über den Expeditionen festlegt.
   */
  public static final Stapel orderedExpeditions[] =
      {YELLOWEXPEDITION, WHITEEXPEDITION, BLUEEXPEDITION, GREENEXPEDITION, REDEXPEDITION};

  /**
   * Farbe des Stapels
   */
  private final Color color;

  /**
   * Konstruktor zum Setzen der Farbe
   */
  private Stapel(Color c) {
    this.color = c;
  }


  /**
   * Convertiert das Enum Color zu einem Middle-Stapel
   * 
   * @return
   */
  public static Stapel toMiddle(Color colorP) {

    switch (colorP) {
      case RED:
        return Stapel.REDMIDDLE;
      case WHITE:
        return Stapel.WHITEMIDDLE;
      case GREEN:
        return Stapel.GREENMIDDLE;
      case BLUE:
        return Stapel.BLUEMIDDLE;
      case YELLOW:
        return Stapel.YELLOWMIDDLE;
      default:
        return Stapel.NACHZIEHSTAPEL;
    }

  }

  /**
   * Convertiert das Enum Color zu dem Enum Stapel ( hier: die enum Instanzen die Expeditionen sind)
   * 
   * @return
   */
  public static Stapel toExpedition(Color colorP) {

    switch (colorP) {
      case RED:
        return Stapel.REDEXPEDITION;
      case WHITE:
        return Stapel.WHITEEXPEDITION;
      case GREEN:
        return Stapel.GREENEXPEDITION;
      case BLUE:
        return Stapel.BLUEEXPEDITION;
      case YELLOW:
        return Stapel.YELLOWEXPEDITION;
      default:
        System.out.println("colorToExpedition default");
        return null;

    }

  }

  /**
   * Getter für die Farbe
   * 
   * @return die Farbe
   */
  public Color getColor() {
    return this.color;
  }

  /**
   * gibt an ob, ein Stapel, in der Mitte ist, also ein Ablagestapel anzeigt.
   * 
   * @return
   */
  public boolean isMiddle() {

    return Arrays.asList(orderedMiddle).contains(this);
  }

  /**
   * gibt an ob, ein Stapel, in der Mitte ist, also eine Expedition anzeigt.
   * 
   * @return
   */
  public boolean isExpedition() {

    return Arrays.asList(orderedExpeditions).contains(this);
  }



}
