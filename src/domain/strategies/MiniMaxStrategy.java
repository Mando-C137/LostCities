package domain.strategies;

import domain.cards.Stapel;
import domain.main.Game;
import domain.main.PlayOption;
import domain.main.WholePlay;
import domain.minmax.AlphaBeta;
import domain.players.AiPlayer;

public class MiniMaxStrategy implements PlayStrategy {

  private WholePlay nextPlay;

  private Game game;

  public MiniMaxStrategy(AiPlayer aiPlayer) {

    this.game = aiPlayer.getGame();

  }

  @Override
  public PlayOption choosePlay() {

    Game g = new Game(game);
    g.replaceStrategiesWithRandom();

    AlphaBeta ab = new AlphaBeta(g.getTurn());
    nextPlay = ab.getBestPlay(g);

    return nextPlay.getOption();
  }

  @Override
  public Stapel chooseStapel() {

    return nextPlay.getStapel();
  }

  @Override
  public StrategyName getStrategyName() {

    return StrategyName.CHEAT_MINMAX;
  }

}
