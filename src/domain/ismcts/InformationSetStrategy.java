package domain.ismcts;

import domain.cards.Stapel;
import domain.main.Game;
import domain.main.PlayOption;
import domain.main.WholePlay;
import domain.players.AiPlayer;
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
  private AiPlayer ai;



  /**
   * Konstruktor, die das Spiel setzt.
   * 
   * @param myGame
   */
  public InformationSetStrategy(AiPlayer ai) {
    this.ai = ai;
  }

  @Override
  public PlayOption choosePlay() {
    Game copyGame = new Game(ai.getGame());
    copyGame.replaceStrategiesWithRandom();

    // Evaluator eval = new Evaluator(new SimpleStrategy(this.ai));
    // Optional<PlayOption> pl = null;
    // if( (pl = eval.optionalDirectSuccessor() ).isPresent() ) {
    // return pl.get();
    // }
    //

    selectedPlay = Ismcts.ISMCTS(copyGame, copyGame.getTurn(), 10_000);
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
