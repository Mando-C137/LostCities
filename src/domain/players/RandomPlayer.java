package domain.players;

import java.util.LinkedList;
import java.util.Random;
import domain.Game;
import domain.cards.Stapel;

/**
 * Erbt von AbstractPlayer. Ein Spieler, der rein zufällig Karten legt und zieht.
 *
 */
public class RandomPlayer extends AbstractPlayer {

  /**
   * für den Namen des Spielers
   */
  static int count = 1;

  public RandomPlayer(Game game) {
    super(game, "AI Player " + count++);
  }

  /**
   * Der Randomplayer wählt ein zufälligen Spielzug aus allen Spielzügen aus.
   */
  public PlayOption play() {
    // choose a Random play

    LinkedList<PlayOption> plays = this.getPlaySet();
    // System.out.println("plays.size " + plays.size());
    PlayOption pl = plays.get(new Random().nextInt(plays.size()));
    return pl;

  }

  /**
   * Wählt zufälligen Stapel aus
   */
  @Override
  public Stapel chooseStapel() {
    LinkedList<Stapel> ls = this.getDrawSet();
    Random rand = new Random();
    Stapel res = ls.get(rand.nextInt(ls.size()));
    if (rand.nextInt(10) < 6) {
      res = Stapel.NACHZIEHSTAPEL;
    }
    return res;
  }

}
