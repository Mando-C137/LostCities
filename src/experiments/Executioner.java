package experiments;

import domain.main.Game;

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


    for (; System.currentTimeMillis() - info.start <= 1_800_000;) {
      game = Game.ISMCTSvsRANDOM();
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

    System.out.println("IS_MCTSS, iterationen 30_000, simulations-stategie: random");


  }
}


