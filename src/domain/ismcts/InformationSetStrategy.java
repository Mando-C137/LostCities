package domain.ismcts;

import domain.cards.Stapel;
import domain.main.Game;
import domain.main.PlayOption;
import domain.main.WholePlay;
import domain.strategies.PlayStrategy;
import domain.strategies.StrategyName;

/**
 * Strategie, die IS_MCTS implementiert
 * 
 * @author paulh
 *
 */
public class InformationSetStrategy implements PlayStrategy {
  /**
   * der näcshte Spielzug
   */
  WholePlay selectedPlay;
  /**
   * Das Spiel, das mit der Strategie asoziiert ist.
   */
  private Game game;

  /**
   * Setter für das Spiel
   * 
   * @param myGame
   */
  public void setGame(Game myGame) {
    this.game = myGame;
  }

  /**
   * Konstruktor, die das Spiel setzt.
   * 
   * @param myGame
   */
  public InformationSetStrategy(Game myGame) {
    this.game = myGame;
  }

  @Override
  public PlayOption choosePlay() {
    Game copyGame = new Game(game);
    copyGame.replacePlayersWithSimulateStrategy();
    selectedPlay = Ismcts.ISMCTS(copyGame, copyGame.getTurn(), 50_000);
    return selectedPlay.getOption();
  }

  @Override
  public Stapel chooseStapel() {

    return selectedPlay.getStapel();
  }

  @Override
  public StrategyName getStrategyName() {

    return StrategyName.IS_MCTS;
  }
}
