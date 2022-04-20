package domain.players;

import java.util.ArrayList;
import java.util.List;
import domain.cards.AbstractCard;
import domain.cards.Stapel;
import domain.main.PlayOption;
import domain.strategies.PlayStrategy;
import domain.strategies.RandomStrategy;
import domain.strategies.SimpleStrategy;
import domain.strategies.StrategyName;

public class AiPlayer extends AbstractPlayer {

  PlayStrategy strategy;

  List<AbstractCard> model;

  public AiPlayer() {
    super("AI");
    this.model = new ArrayList<AbstractCard>();
  }

  public AiPlayer(AiPlayer ai) {
    super(ai);

    this.model = new ArrayList<AbstractCard>(ai.model);

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

  public void removeCardFromModel(AbstractCard remove) {
    this.model.remove(remove);
  }

  public void addCardToModel(AbstractCard add) {
    this.model.add(add);
  }

  public void setModel(List<AbstractCard> list) {
    this.model = list;
  }

  public void OPCARDS() {
    super.op();
  }

  public String getName() {
    return this.strategy.getStrategyName().toString();
  }

}
