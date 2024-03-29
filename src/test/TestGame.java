package test;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import domain.main.Game;

class TestGame {

  @Test
  void test() {


    Game g = Game.twoRandoms();
    // PlayOption play = g.getPlayers().get(0).play();
    // g.externalPlay(play, g.getPlayers().get(0));
    Game copy = new Game(g);
    copy.replacePlayersWithSimpleAi();

    for (int i = 0; i < 2; i++) {
      g.getPlayers().get(i).getHandKarten().forEach(p -> System.out.print(" " + p));
      System.out.println();
      copy.getPlayers().get(i).getHandKarten().forEach(p -> System.out.print(" " + p));
      System.out.println();
      // System.out.println(copy.getPlayers().get(1).getHandKarten().size());
    }

    // System.out.println(g);
    // System.out.println(copy);

    assertEquals(g.calculateWinnerIndex(0), copy.calculateWinnerIndex(0));

  }


  @Test
  void testActions() {

    Game g = Game.twoRandoms();

    System.out.println(g.getPlayers().get(0).getAllActions().size());

    g.getPlayers().get(0).getAllActions().forEach(con -> System.out.println(con));

    assertTrue(g.getPlayers().get(0).getAllActions().size() <= 16);



  }

  @Test
  void testasdf() {



    System.out.println(0 ^ 1);



  }


}
