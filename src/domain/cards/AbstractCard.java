package domain.cards;

/**
 * Eine Karte ist entweder eine Nummerkarte, eine Wettkarke oder eine NoCard also ein Platzhalter
 * 
 * @author paulh
 *
 */
public abstract class AbstractCard implements Comparable<AbstractCard> {

  /**
   * Farbe der Karte
   */
  private final Color color;

  /**
   * Konstruktor, der die Farbe setzt.
   * 
   * @param color
   */
  public AbstractCard(Color color) {
    this.color = color;
  }

  /**
   * Der Wert des Knoten im Sinne der Punktzahl
   * 
   * @return der Wert der Karte
   */
  public abstract int getValue();

  /**
   * gibt, an ob es sich um eine Wettkarte oder Expeditionskarte handelt.
   * 
   * @return true, falls es sich um eine Nummerkarte handelt, ansonsten false
   */
  public abstract boolean isNumber();

  /**
   * Getter für die Farbe
   * 
   * @return die Farbe der Karte.
   */
  public Color getColor() {
    return this.color;
  }



}


