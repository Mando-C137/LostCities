package domain.pimc;

import java.util.List;
import domain.cheatmcts.CheatMCTS;
import domain.cheatmcts.CheatNode;
import domain.ismcts.Ismcts;
import domain.main.Game;

public class DeterminizationRunnable implements Runnable {

  private Game game;

  private List<CheatNode> firstLevel;

  /**
   * Konstuktor, setzen der ersten Ebene, die geupdatet wird und Determinisierung des Spiels
   * 
   * @param g das Spiel, das determisiert wird
   * @param firstLevel
   */
  public DeterminizationRunnable(Game g, List<CheatNode> firstLevel) {
    this.firstLevel = firstLevel;
    this.game = Ismcts.determinize(g, g.getTurn());

  }


  @Override
  public synchronized void run() {

    CheatNode root = CheatMCTS.cheat_MCTS(game, 5000, game.getTurn());

    for (CheatNode childOfRun : root.getChildren()) {

      for (CheatNode toUpdate : this.firstLevel) {

        if (childOfRun.getAction().equals(toUpdate.getAction())) {
          // System.out.println("update");
          toUpdate.addVisits(childOfRun.getVisits());
          break;
        }

      }


    }
  }



}
