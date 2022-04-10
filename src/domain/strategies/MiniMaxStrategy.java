package domain.strategies;

import domain.cards.Stapel;
import domain.main.Game;
import domain.main.PlayOption;
import domain.main.WholePlay;
import domain.minmax.AlphaBeta;
import domain.players.AiPlayer;
import domain.players.ai.Model;

public class MiniMaxStrategy implements PlayStrategy {


  private WholePlay nextPlay;

  private Game game;

  private Model model;

  private int turn;


  public MiniMaxStrategy(AiPlayer aiPlayer) {

    this.game = aiPlayer.getGame();
    this.model = new Model(aiPlayer);
    this.turn = game.getTurn();
  }

  @Override
  public PlayOption choosePlay() {

    this.game.getPlayers().get(turn).getHandKarten().forEach(con -> System.out.print(con + " "));
    System.out.println();

    // if (this.game.getLastDraw() != null) {
    // this.model.getStatistics().EnemyDrawsFromAblage(this.game.getLastDraw());
    // }
    //
    // if (this.game.getLastPlay() != null) {
    //
    // if (this.game.getLastPlay().getStapel().isExpedition()) {
    // this.model.getStatistics().onEnemyExp(this.game.getLastPlay().getCard());
    // } else {
    // this.model.getStatistics().onAblage(this.game.getLastPlay().getCard());
    // }
    //
    // }
    if (this.game.getLastPlay() != null)
      this.model.getEnemyModel().remove(this.game.getLastPlay().getCard());

    if (this.game.getLastDraw() != null)
      this.model.getEnemyModel().add(this.game.getLastDraw());


    Game g = new Game(game);
    g.replaceStrategiesWithRandom();

    AlphaBeta ab = new AlphaBeta(turn);
    nextPlay = ab.getBestPlay(g);

    return nextPlay.getOption();
  }

  @Override
  public Stapel chooseStapel() {
    // TODO Auto-generated method stub
    return nextPlay.getStapel();
  }

  @Override
  public StrategyName getStrategyName() {
    // TODO Auto-generated method stub
    return StrategyName.CHEAT_MINMAX;
  }

}
