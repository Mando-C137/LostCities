package domain.cards;

import java.util.Arrays;

/**
 * Mit Stapel ist alles gemeint, worauf ein Spieler seine Karten ablegen kann
 * 
 * @author paulh
 *
 */
public enum Stapel {
  NACHZIEHSTAPEL, YELLOWMIDDLE, WHITEMIDDLE, BLUEMIDDLE, GREENMIDDLE, REDMIDDLE, YELLOWEXPEDITION, WHITEEXPEDITION, BLUEEXPEDITION, GREENEXPEDITION, REDEXPEDITION;

  /**
   * Ein Array, das eine feste Ordnung über den Ablagestaepeln festlegt
   */
  public static final Stapel orderedMiddle[] =
      {YELLOWMIDDLE, WHITEMIDDLE, BLUEMIDDLE, GREENMIDDLE, REDMIDDLE};

  /**
   * Ein Array,das eine feste Ordnung über den Expeditionen festlegt.
   */
  public static final Stapel orderedExpeditions[] =
      {YELLOWEXPEDITION, WHITEEXPEDITION, BLUEEXPEDITION, GREENEXPEDITION, REDEXPEDITION};


  /**
   * Convertiert das Enum Color zu dem Enum Stapel hier: die enum Instanten die auf middle enden
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
        return null;
    }

  }

  /**
   * Convertiert das Enum Color zu dem Enum Stapel hier: die enum Instanzen die auf expedition enden
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
        return null;



    }

  }

  public Color getColor() {
    if (this.toString().matches(".*MIDDLE")) {
      return Color.valueOf(this.toString().replace("MIDDLE", ""));
    } else if (this.toString().matches(".*EXPEDITION")) {
      return Color.valueOf(this.toString().replace("EXPEDITION", ""));
    } else {
      return null;
    }
  }

  public boolean isMiddle() {

    return Arrays.asList(orderedMiddle).contains(this);
  }

  public boolean isExpedition() {

    return Arrays.asList(orderedExpeditions).contains(this);
  }



}
