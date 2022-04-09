package domain.strategies;

import domain.cards.Stapel;
import domain.main.PlayOption;

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
