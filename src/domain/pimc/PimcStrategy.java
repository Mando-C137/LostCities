package domain.pimc;

import domain.cards.Stapel;
import domain.main.Game;
import domain.main.PlayOption;
import domain.main.WholePlay;
import domain.players.AiPlayer;
import domain.strategies.HumanStrategy;
import domain.strategies.PlayStrategy;
import domain.strategies.StrategyName;

/**
 * Strategie, die PIMC-MCTS implementiert.
 * 
 * @author paulh
 *
 */
public class PimcStrategy implements PlayStrategy {

  /**
   * Der mit der Strategie asoziierte Spieler
   */
  private AiPlayer ai;

  /**
   * die nächste Aktion, die zu treffen ist.
   */
  WholePlay nextAction;

  public PimcStrategy(AiPlayer ai) {
    this.ai = ai;
  }

  /**
   * Generiert ein Spiel, in der PIMC vs Human spielt.
   * 
   * @return
   */
  public static Game PIMCvsHuman() {

    Game g = Game.twoWithoutStrategies();
    g.getPlayers().get(0).setStrategy(new PimcStrategy(g.getPlayers().get(0)));
    g.getPlayers().get(1).setStrategy(new HumanStrategy());

    return g;

  }

  @Override
  public PlayOption choosePlay() {

    Game copyGame = new Game(ai.getGame());
    copyGame.replaceStrategiesWithRandom();

    try {
      nextAction = Pimc.pimc(copyGame, ai.getIndex(), 30);
    } catch (InterruptedException e) {
      System.out.println("error");
      e.printStackTrace();
    }

    return nextAction.getOption();
  }

  @Override
  public Stapel chooseStapel() {

    return nextAction.getStapel();
  }

  @Override
  public StrategyName getStrategyName() {

    return StrategyName.PIMC;
  }



}
