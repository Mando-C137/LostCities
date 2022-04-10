package domain.strategies;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import domain.cards.AbstractCard;
import domain.cards.Color;
import domain.cards.Stapel;
import domain.main.Game;
import domain.main.PlayOption;
import domain.players.AiPlayer;
import domain.players.ai.Evaluator;


public class SimpleStrategy implements PlayStrategy {


  public static final String name = "Simple";

  public static final int StartPhase = 0;
  public static final int MiddlePhase = 1;
  public static final int EndPhase = 2;


  /**
   * Phase des Spiels gibt die Taktik vor.
   */
  private int gamePhase;

  private AiPlayer player;

  private Evaluator evaluator;

  private ArrayList<AbstractCard> enemyHandModel = new ArrayList<AbstractCard>();

  private Game game;

  public SimpleStrategy(AiPlayer player) {
    this.game = player.getGame();
    this.player = player;
    this.gamePhase = StartPhase;
    this.evaluator = new Evaluator(this);


  }

  public SimpleStrategy(SimpleStrategy strat, AiPlayer player) {
    this.game = strat.game;
    this.player = player;
    this.gamePhase = strat.gamePhase;
    this.evaluator = new Evaluator(this);
  }

  @Override
  public PlayOption choosePlay() {

    // if (this.game.getLastPlay() != null) {
    // if (this.game.getLastPlay().getStapel().isExpedition()) {
    // this.statistics.onEnemyExp(this.game.getLastPlay().getCard());
    // } else {
    // this.statistics.onAblage(this.game.getLastPlay().getCard());
    // }
    // }
    //
    // if (this.game.getLastDraw() != null) {
    // this.statistics.EnemyDrawsFromAblage(this.game.getLastDraw());
    // }

    if (this.player.getRemainingCards() == 15) {
      this.gamePhase = EndPhase;
    }

    return this.evaluator.choosePlayOption();
  }

  @Override
  public Stapel chooseStapel() {
    List<Color> ls = this.iNeedThoseFromAblage();
    if (!ls.isEmpty()) {
      return Stapel.toMiddle(ls.get((int) (Math.random() * ls.size())));
    }
    return Stapel.NACHZIEHSTAPEL;
  }



  private List<Color> iNeedThoseFromAblage() {
    LinkedList<Color> ls = new LinkedList<Color>();
    for (Color c : Color.values()) {
      if (this.player.getLastAblage() != null) {
        if (this.player.getLastAblage().equals(Stapel.toMiddle(c))) {
          continue;
        }
      }
      if (!this.player.getExpeditionen().get(c).isEmpty()) {
        Optional<AbstractCard> opt = this.game.peekAblageStapel(c);
        if (opt.isPresent()) {
          AbstractCard poss = opt.get();
          if (poss.compareTo(this.player.getExpeditionen().get(c).peek()) >= 0) {

            if (poss.getValue() != 0) {
              ls.add(c);
            }
            if (poss.getValue() == 0 && this.evaluator.expectedValueOfExpedition(c) >= 35) {
              // System.out.println(this.evaluator.expectedValueOfExpedition(c));
              ls.add(c);
            }

          }
        }
      }

    }

    return ls;
  }



  public AiPlayer getPlayer() {
    return this.player;
  }



  public int getMode() {
    return this.gamePhase;
  }

  @Override
  public StrategyName getStrategyName() {

    return StrategyName.RULEBASE;
  }

  public List<AbstractCard> getEnemyModel() {
    return this.enemyHandModel;
  }



}
