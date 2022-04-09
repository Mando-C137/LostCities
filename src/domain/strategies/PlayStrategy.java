package domain.strategies;

import domain.cards.Stapel;
import domain.main.PlayOption;

public interface PlayStrategy {

  public PlayOption choosePlay();

  public Stapel chooseStapel();

  public StrategyName getStrategyName();


}
