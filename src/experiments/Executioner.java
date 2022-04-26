package experiments;

import java.time.LocalTime;
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

    LocalTime in_six_hours = LocalTime.now().plusHours(4);
    int counter = 0;
    System.out.println("start");
    int i = 0;
    double averagePoints = 0;
    // for (; LocalTime.now().isBefore(in_six_hours); i++) {
    for (; i < 100_000; i++) {
      game = Game.twoRandoms();
      game.getPlayers().get(0).setStrategy(new SimpleStrategy(game.getPlayers().get(0)));

      // for (AiPlayer p : game.getPlayers()) {
      // p.setStrategy(new SecondRandomStrategy(p));
      // }

      game.gameFlow();
      int points = game.calculateScore(game.getPlayers().get(0));
      averagePoints += points;
      if (points > 0) {
        counter++;
      }

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


      // if (i % 10 == 0) {
      // info.printInfo();
      // }



    }

    // info.printInfo();
    averagePoints /= 100_000;

    System.out.println(averagePoints);
    System.out.println(counter);
    System.out.println((System.currentTimeMillis() - start) / 100);

    System.out.println("IS_MCTS vs simple, iterationen10, simulations-stategie: simple, C=0.7");


  }



}


