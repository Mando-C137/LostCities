package domain.players;

import domain.cards.Stapel;
import domain.main.PlayOption;
import domain.strategies.PlayStrategy;
import domain.strategies.RandomStrategy;
import domain.strategies.SimpleStrategy;
import domain.strategies.StrategyName;

public class AiPlayer extends AbstractPlayer {

  PlayStrategy strategy;

  public AiPlayer() {
    super("AI");
  }

  public AiPlayer(AiPlayer ai) {
    super(ai);

    if (ai.strategy.getStrategyName() == StrategyName.RANDOM) {
      this.strategy = new RandomStrategy(this);
    } else if (ai.strategy.getStrategyName() == StrategyName.RULEBASE) {
      this.strategy = new SimpleStrategy((SimpleStrategy) ai.strategy, this);
    } else {
      this.strategy = new RandomStrategy(this);
    }
  }

  @Override
  public PlayOption choosePlay() {
    return strategy.choosePlay();
  }

  @Override
  public Stapel chooseStapel() {
    return strategy.chooseStapel();
  }

  @Override
  public boolean isAI() {
    return true;
  }

  /**
   * Setter für die Strategie
   * 
   * @param newStrat
   */
  public void setStrategy(PlayStrategy newStrat) {
    this.strategy = newStrat;
  }

  /**
   * Getter für die Strategie
   * 
   * @return
   */
  public PlayStrategy getStrategy() {
    return this.strategy;
  }

}
