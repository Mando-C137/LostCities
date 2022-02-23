package domain.cards;

/**
 * Eine Wettkarte besteht lediglich aus eine Farbe.
 * 
 * @author paulh
 *
 */
public class WettCard extends AbstractCard {


  public WettCard(Color color) {
    super(color);
  }

  @Override
  public boolean isNumber() {

    return false;
  }

  @Override
  public String toString() {
    return " J" + this.color.toString().substring(0, 1);
  }
}
