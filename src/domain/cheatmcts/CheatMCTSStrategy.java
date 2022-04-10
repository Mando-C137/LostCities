package domain.cheatmcts;

import domain.cards.Stapel;
import domain.gitmonte.GitMonte;
import domain.main.Game;
import domain.main.PlayOption;
import domain.main.WholePlay;
import domain.players.AiPlayer;
import domain.strategies.PlayStrategy;
import domain.strategies.StrategyName;

public class CheatMCTSStrategy implements PlayStrategy {


  private AiPlayer ai;

  WholePlay nextPlay;

  public CheatMCTSStrategy(AiPlayer ai) {
    this.ai = ai;
  }

  @Override
  public PlayOption choosePlay() {


    Game copyGame = new Game(ai.getGame());
    copyGame.replacePlayersWithSimulateStrategy();


    new GitMonte(copyGame.getTurn()).computeMove(copyGame);

    nextPlay = CheatMCTS.cheat_MCTS(copyGame, 50_000, copyGame.getTurn());

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
