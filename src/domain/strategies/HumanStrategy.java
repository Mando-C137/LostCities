package domain.strategies;

import domain.cards.Stapel;
import domain.main.PlayOption;

/**
 * Strategie eines Menschen , gibt nur ungültige Daten zurück
 * 
 * @author paulh
 *
 */
public class HumanStrategy implements PlayStrategy {

  @Override
  public StrategyName getStrategyName() {
    return StrategyName.HUMAN;
  }

  @Override
  public Stapel chooseStapel() {
    return null;
  }

  @Override
  public PlayOption choosePlay() {
    return null;
  }
}
