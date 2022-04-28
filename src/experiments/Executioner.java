package experiments;

import java.time.LocalTime;
import domain.cheatmcts.CheatMCTSStrategy;
import domain.main.Game;
import domain.strategies.SimpleStrategy;

public class Executioner {

  private Game game;

  private Executioner() {

    this.experiment(10);
  }


  public static void main(String[] args) {
    Executioner exe = new Executioner();
  }


  void experiment(int num) {


    ExperimentInfo info = new ExperimentInfo();
    double start = System.currentTimeMillis();

    LocalTime in_six_hours = LocalTime.now().plusHours(9);

    System.out.println("start");
    int i = 0;

    for (; LocalTime.now().isBefore(in_six_hours); i++) {
      // for (; i < 100_000; i++) {
      game = Game.twoRandoms();

      game.getPlayers().get(0).setStrategy(new CheatMCTSStrategy(game.getPlayers().get(0)));

      game.getPlayers().get(1).setStrategy(new SimpleStrategy(game.getPlayers().get(1)));

      // for (AiPlayer p : game.getPlayers()) {
      // p.setStrategy(new SecondRandomStrategy(p));
      // }

      game.gameFlow();



      int diff = game.calculateDiff(0);

      info.diff += diff;
      if (diff > 0) {
        info.wins++;
      } else if (diff < 0) {
        info.losses++;
      } else {
        info.draws++;
      }
      info.numberOfGames++;


      if (i % 10 == 0) {
        info.printInfo();
      }



    }

    info.printInfo();


    System.out.println("cheat vs simple, simulations-stategie: simple, C=1.41");


  }



}


