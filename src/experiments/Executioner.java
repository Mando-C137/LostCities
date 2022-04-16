package experiments;

import domain.main.Game;
import domain.players.AiPlayer;
import domain.strategies.SecondRandomStrategy;

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


    for (int i = 0; i < 100_000; i++) {
      game = Game.twoRandoms();

      for (AiPlayer p : game.getPlayers()) {
        p.setStrategy(new SecondRandomStrategy(p));
      }

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


    }

    info.printInfo();
    System.out.println(SecondRandomStrategy.count);

    System.out.println("IS_MCTSS, iterationen 30_000, simulations-stategie: random");


  }
}


