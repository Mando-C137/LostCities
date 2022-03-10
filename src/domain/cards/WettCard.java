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
    return " J" + this.getColor().toString().substring(0, 1);
  }

  @Override
  public int compareTo(AbstractCard o) {

    if (!o.isNumber()) {
      return 0;
    } else if (o.isNumber()) {
      NumberCard num = (NumberCard) o;
      return -num.getValue();
    }

    return 0;
  }
}
