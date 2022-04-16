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

    return this.getValue() - o.getValue();

  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }

    if (this == null || o == null) {
      return false;
    }

    if (o.getClass() != this.getClass()) {
      return false;
    }

    AbstractCard a = (AbstractCard) o;

    if (a.isNumber()) {
      return false;
    }

    if (!a.getColor().equals(this.getColor())) {
      return false;
    }

    return true;

  }

  @Override
  public int getValue() {

    return 0;
  }

  @Override
  public int hashCode() {

    return (this.getColor().ordinal() + 1) + 31 * this.getValue();
  }


}
