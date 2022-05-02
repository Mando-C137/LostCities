package demo;

import domain.cheatmcts.CheatNode;
import domain.ismcts.Informationset;
import domain.main.Game;
import domain.main.WholePlay;

public class Demogames {

  private static void compute() {

    Informationset.setParameter(1);
    CheatNode.setParameter(1);

    Game g = Game.twoRandoms();

    // g = Game.ISMCTSvsRANDOM();

    // g = Game.ISMCTSvsSIMPLE();

    // g = Game.ISMCTSvsPIMC();

    // g = Game.PIMCvsRandom();

    // g = Game.PIMCvsSIMPLE();

    g = Game.ISMCTSvsCheat();

    // g = Game.SIMPLEvsRandom();


    while (!g.getGameEnd() && g.getZuege() < 100) {

      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {

      }

      g.getPlayers().forEach(pl -> {

        System.out.print(pl.getName() + " : ");
        System.out.println(pl.getHandKarten());

      });
      System.out.println(g);

      System.out.println(g.getPlayers().get(g.getTurn()).getName() + " ist an der Reihe");

      WholePlay pl = g.externalRound(g.getPlayers().get(g.getTurn()));

      System.out
          .println("Er legt " + pl.getOption().getCard() + " auf " + pl.getOption().getStapel());
      System.out.println("Darauf zieht er von Stapel " + pl.getStapel());
    }

    System.out.println(g);
    System.out.println("Spiel ist beendet");

    System.out.println(g.calculateScores());

  }

  public static void main(String[] args) {
    Demogames.compute();
  }

}
