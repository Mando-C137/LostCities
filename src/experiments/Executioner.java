package experiments;

import java.time.LocalTime;
import domain.main.Game;
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



    LocalTime in_six_hours = LocalTime.now().plusHours(6);

    int i = 0;
    for (; !LocalTime.now().isAfter(in_six_hours); i++) {
      game = Game.ISMCTSvsME();
      game.getPlayers().get(1).setStrategy(new SecondRandomStrategy(game.getPlayers().get(1)));

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


    System.out.println("IS_MCTS, iterationen 30_000, simulations-stategie: random, C=0.7");


  }



}


