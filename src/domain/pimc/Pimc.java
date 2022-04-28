package domain.pimc;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import domain.cheatmcts.CheatNode;
import domain.main.Game;
import domain.main.WholePlay;

/**
 * Implementierung von Perfect Information Monte Carlo mit purem MCTS
 * 
 * @author paulh
 *
 */
public class Pimc {


  /**
   * 
   * @param game das aktuelle Spiel
   * @param index Index des Spielers, der am Zug ist
   * @param anzahlDeterminiserungen Anzahl an versch. Determisierungen, die per MCTS gelöst werden
   *        sollen
   * @return Die ausgeawählte Aktion
   * @throws InterruptedException
   */
  public static WholePlay pimc(Game game, int index, int anzahlDeterminiserungen)
      throws InterruptedException {

    List<CheatNode> firstLevel = getFirstLevel(game);

    List<Thread> allRuns = new LinkedList<Thread>();

    // List<DeterminizationRunnable> runnables = new LinkedList<DeterminizationRunnable>();

    for (int i = 0; i < anzahlDeterminiserungen; i++) {

      DeterminizationRunnable runnable = new DeterminizationRunnable(game, firstLevel);
      Thread thread = new Thread(runnable);
      thread.start();
      allRuns.add(thread);
      // runnables.add(runnable);
    }

    for (Thread runnable : allRuns) {

      runnable.join();
      // System.out.println(runnable.getName() + " ended.");
    }


    for (CheatNode node : firstLevel.stream().sorted(Comparator.comparing(c -> c.getVisits()))
        .collect(Collectors.toList())) {

      System.out.println(node.getAction() + " : " + node.getVisits());
      System.out.println("--------");
    }


    return Collections.max(firstLevel, Comparator.comparing(c -> c.getVisits())).getAction();
  }

  /**
   * generiert die für alle Determinisierungen erste Ebene des Spiels, die auch für alle die selbe
   * ist
   * 
   * @param game die Determinisierung
   * @return die Liste an Knoten
   */
  public static List<CheatNode> getFirstLevel(Game game) {

    List<CheatNode> result = new LinkedList<CheatNode>();

    List<WholePlay> allActions = game.getPlayers().get(game.getTurn()).getAllActions();

    allActions.forEach(action -> {
      result.add(new CheatNode(action, game.getTurn(), null));
    });


    return result;

  }



}
