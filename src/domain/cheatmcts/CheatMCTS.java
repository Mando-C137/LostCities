package domain.cheatmcts;

import java.util.List;
import domain.gitmonte.GitMonte;
import domain.main.Game;
import domain.main.WholePlay;
import domain.players.AiPlayer;

public class CheatMCTS {



  public static WholePlay cheat_MCTS(Game g, int iterations, int rootIndex) {

    CheatNode root = new CheatNode(null, rootIndex, null);

    for (int i = 0; i < iterations; i++) {

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

    WholePlay answer = root.finalSelection();

    root.printInfo();

    return answer;

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
    int playerIndex = g.getTurn() ^ 1;
    int nextPlayer = playerIndex ^ 1;

    while (!toSimulate.getGameEnd()) {

      AiPlayer abs = toSimulate.getPlayers().get(toSimulate.getTurn());
      WholePlay nextPlay = new WholePlay(abs.choosePlay(), abs.chooseStapel());
      toSimulate.unCheckedPlay(nextPlay, abs);

    }

    int winner = toSimulate.calculateWinnerIndex(rootIndex);


    // int diff = toSimulate.calculateDiff(rootIndex);
    //
    // if (winner == rootIndex) {
    // return 1;
    // } else if (winner == (rootIndex ^ 1)) {
    // return 0;
    // } else {
    // return 0.5;
    // }
    //
    if (winner == playerIndex) {
      return (playerIndex == rootIndex) ? GitMonte.POINTS_FOR_WIN : GitMonte.POINTS_FOR_LOSS; //
      // current
      // player
      // wins
    } else if (nextPlayer == winner) {
      return (nextPlayer == rootIndex) ? GitMonte.POINTS_FOR_WIN : GitMonte.POINTS_FOR_LOSS; //
      // opponent
      // // //
      // wins
    } else {
      return GitMonte.POINTS_FOR_DRAW; // draw
    }
  }



}
