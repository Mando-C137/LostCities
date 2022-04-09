package domain.strategies;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import domain.cards.Color;
import domain.cards.Stapel;
import domain.main.PlayOption;
import domain.players.AiPlayer;

/**
 * implementiert PlayStrategy. Ein Spieler, der rein zufällig Karten legt und zieht.
 *
 */
public class RandomStrategy implements PlayStrategy {

  public static final String name = "Rand";

  private AiPlayer player;

  private Stapel lastPlay;

  public RandomStrategy(AiPlayer player) {
    this.player = player;
  }

  /**
   * Der Randomplayer wählt ein zufälligen Spielzug aus allen Spielzügen aus.
   */
  @Override
  public PlayOption choosePlay() {
    // System.out.println(this.player.getHandKarten());
    // choose a Random play
    List<PlayOption> plays = player.getPlaySet();
    // System.out.println("plays.size " + plays.size());
    PlayOption pl = plays.get(new Random().nextInt(plays.size()));
    this.lastPlay = pl.getStapel();

    return pl;

  }

  /**
   * Wählt zufälligen Stapel aus
   */
  @Override
  public Stapel chooseStapel() {

    if ((double) Math.random() < 0.5) {
      return Stapel.NACHZIEHSTAPEL;
    }

    List<Stapel> result = new LinkedList<Stapel>();
    for (Color c : Color.values()) {
      if (!this.player.getGame().getAblageStapel(c).isEmpty()) {
        result.add(Stapel.toMiddle(c));
      }
    }

    result.remove(this.lastPlay);

    if (result.isEmpty()) {
      return Stapel.NACHZIEHSTAPEL;
    }

    int randomIndex = (int) (Math.random() * result.size());
    Stapel s = result.get(randomIndex);

    return s;
  }


  @Override
  public StrategyName getStrategyName() {

    return StrategyName.RANDOM;
  }
}
