package domain.cards;

/**
 * kann auch noch gelöscht werden
 * 
 * soll als Platzhalter dienen, wenn an bestimmten stacks noch nichts liegt
 * 
 * @author paulh
 *
 */
public class NoCard extends AbstractCard {

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

}
