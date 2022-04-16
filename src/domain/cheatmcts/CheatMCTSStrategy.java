package domain.cheatmcts;

import domain.cards.Stapel;
import domain.main.Game;
import domain.main.PlayOption;
import domain.main.WholePlay;
import domain.players.AiPlayer;
import domain.strategies.PlayStrategy;
import domain.strategies.StrategyName;

/**
 * Eine Strategie, die ein Spiel als vollstaendig beobachtbares Problem löst. damit keiner richtige
 * Loesung
 * 
 * @author paulh
 *
 */
public class CheatMCTSStrategy implements PlayStrategy {

  /**
   * Der Spieler, der diese Strategie verwendet
   */
  private AiPlayer ai;

  /**
   * Der nächste Zug des Spielers
   */
  WholePlay nextPlay;

  /**
   * Konstruktor zum Setzen der Spielervariable
   * 
   * @param ai Spieler
   */
  public CheatMCTSStrategy(AiPlayer ai) {
    this.ai = ai;
  }

  @Override
  public PlayOption choosePlay() {


    Game copyGame = new Game(ai.getGame());
    // copyGame.replacePlayersWithSimulateStrategy();
    copyGame.replaceStrategiesWithRandom();

    // new GitMonte(copyGame.getTurn()).computeMove(copyGame);

    CheatNode root = CheatMCTS.cheat_MCTS(copyGame, 50_000, copyGame.getTurn());

    nextPlay = root.finalSelection();

    return nextPlay.getOption();
  }

  @Override
  public Stapel chooseStapel() {

    return nextPlay.getStapel();
  }

  @Override
  public StrategyName getStrategyName() {

    return StrategyName.CHEATMCTS;
  }

}
