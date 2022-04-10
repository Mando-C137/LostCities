package domain.gitmonte;

import domain.cards.Stapel;
import domain.main.Game;
import domain.main.PlayOption;
import domain.main.WholePlay;
import domain.strategies.PlayStrategy;
import domain.strategies.StrategyName;

public class GitStrategy implements PlayStrategy {

  private WholePlay nextPlay;

  private Game game;

  public GitStrategy(Game game) {
    this.game = game;
  }

  @Override
  public PlayOption choosePlay() {

    Game gameToEvaluate = new Game(game);
    gameToEvaluate.replaceStrategiesWithRandom();
    int myPlayer = gameToEvaluate.getTurn();

    GitMonte eval = new GitMonte(myPlayer);

    this.nextPlay = eval.computeMove(game);

    return this.nextPlay.getOption();
  }

  @Override
  public Stapel chooseStapel() {
    return this.nextPlay.getStapel();
  }

  @Override
  public StrategyName getStrategyName() {

    return StrategyName.CHEATMCTS;
  }

}
