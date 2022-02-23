package domain.cards;

/**
 * Eine Nummerkarte ist einer der zwei Varianten, die eine Karte sein kann. Sie besteht aus einer
 * Farbe (Color enum) und einem Wert (int)
 * 
 * @author paulh
 *
 */
public class NumberCard extends AbstractCard {

  /**
   * Der Wert für die karte
   */
  final int val;

  /**
   * Setzen der Eigenschaften der Karte
   * 
   * @param c
   * @param val
   */
  public NumberCard(Color c, int val) {
    super(c);
    this.val = val;
  }

  @Override
  public boolean isNumber() {

    return true;
  }

  /**
   * Der erste Buchstabe der Farbe und der Wert.
   */
  @Override
  public String toString() {
    return " " + this.color.toString().substring(0, 1) + "" + this.val;

  }

  /**
   * Getter für den Wert der Karte.
   */
  public int getValue() {
    return this.val;
  }



}
