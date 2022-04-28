package domain.strategies;

import domain.cards.Stapel;
import domain.main.PlayOption;

/**
 * Die Strategie, mit der ein <code> AiPlayer </code> seine Züge auswählt
 * 
 */
public interface PlayStrategy {
  /**
   * berechnet den AblagePlay
   * 
   * @return der AblagePlay
   */
  public PlayOption choosePlay();

  /**
   * berechnet den Stapel, von dem gezogen werden soll
   * 
   * @return der Stapel
   */
  public Stapel chooseStapel();

  /**
   * 
   * @return Name der Strategie
   */
  public StrategyName getStrategyName();


}
