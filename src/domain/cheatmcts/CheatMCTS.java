package domain.cheatmcts;

import java.util.List;
import domain.main.Game;
import domain.main.WholePlay;
import domain.players.AiPlayer;

/**
 * Pures MCTS, dabei wird eine Determinisierung eines Spiels eingegeben und dieses als vollstaendig
 * beobachtbares Problem gelöst
 * 
 * @author paulh
 *
 */
public class CheatMCTS {

  /**
   * 
   * @param g die Game Instanz
   * @param iterations die Anzahl an iterationen, die MCTS haben soll
   * @param rootIndex der Index des Spielers, der an der Reihe ist
   * @return der Wurzelknoten des Spielbaumes
   */
  public static CheatNode cheat_MCTS(Game g, final int millis, final int rootIndex) {

    CheatNode root = new CheatNode(null, rootIndex, null);

    for (double start = System.currentTimeMillis(); System.currentTimeMillis() - start <= millis;) {

      Game currentGame = new Game(g);
      CheatNode selectedChild = root.selectBestChild(currentGame, rootIndex);

      List<WholePlay> unexpandedChildren = selectedChild.unexpandendChildren(currentGame);
      CheatNode toSimulate = selectedChild;
      if (!unexpandedChildren.isEmpty() && !currentGame.getGameEnd()) {
        toSimulate = selectedChild.expand(currentGame, unexpandedChildren);
      }


      double simulationResult = CheatMCTS.simulateBinary(currentGame, rootIndex);
      toSimulate.backpropagate(simulationResult);

    }
    // root.printInfo();
    return root;



  }

  /**
   * Simuliert ein Spiel: Resultat entweder 1 , 0, oder 0.5
   * 
   * @param g
   * @param rootIndex
   * @return
   */
  private static double simulateBinary(Game g, int rootIndex) {

    Game toSimulate = new Game(g);
    toSimulate.replacePlayersWithSimpleAi();


    while (!toSimulate.getGameEnd() && toSimulate.getZuege() < 100) {

      AiPlayer abs = toSimulate.getPlayers().get(toSimulate.getTurn());
      WholePlay nextPlay = new WholePlay(abs.choosePlay(), abs.chooseStapel());
      toSimulate.unCheckedPlay(nextPlay, abs);

    }


    double result = toSimulate.calculateWinnerIndex(rootIndex);

    if (result == rootIndex) {
      return 1;
    } else if (result == (rootIndex ^ 1)) {
      return 0;
    } else {
      return 0.5;
    }



    // if (winner == playerIndex) {
    // return (playerIndex == rootIndex) ? GitMonte.POINTS_FOR_WIN : GitMonte.POINTS_FOR_LOSS; //
    // // current
    // // player
    // // wins
    // } else if (nextPlayer == winner) {
    // return (nextPlayer == rootIndex) ? GitMonte.POINTS_FOR_WIN : GitMonte.POINTS_FOR_LOSS; //
    // // opponent
    // // // //
    // // wins
    // } else {
    // return GitMonte.POINTS_FOR_DRAW; // draw
    // }
  }



}
