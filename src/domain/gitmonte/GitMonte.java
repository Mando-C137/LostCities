package domain.gitmonte;

import java.util.ArrayList;
import java.util.List;
import domain.main.Game;
import domain.main.WholePlay;
import domain.players.AbstractPlayer;



/**
 * 
 */
public class GitMonte {

  private final static int PLAY_OUTS = 4_000;
  private final static double time = 5_000;

  public final static double POINTS_FOR_WIN = 1;
  public final static double POINTS_FOR_DRAW = 0.5;
  public final static double POINTS_FOR_LOSS = 0;

  private final int myIndex;

  public GitMonte(int myIndex) {
    this.myIndex = myIndex;
  }

  public WholePlay computeMove(Game game) {

    Node root = new Node(game, null, myIndex ^ 1, null);
    int iter = 0;
    for (double start = System.currentTimeMillis(); System.currentTimeMillis()
        - start < GitMonte.time;) {
      fourSteps(root);
      iter++;

    }

    // root.getChildren().forEach(con -> {
    //
    //
    // System.out.println("---------------");
    // System.out.println("visits " + con.getVisitCount());
    // System.out.println(con.uctValue());
    // System.out.println(con.getMove());
    // System.out.println("---------------");
    //
    // });

    System.out.println("iterations " + iter);

    // Find the best move in terms of the highest probability of win
    Node best = root.finalSelection();

    root.printInfo();

    return best.getMove();
  }



  /**
   * Perform 4 steps of MCTS: selection, expansion, simulation and propagation.
   */
  public void fourSteps(Node root) {

    List<Node> visited = new ArrayList<Node>();
    Node current = root;
    visited.add(current);

    // 1. Selection
    // Find the most promising leaf to extend
    while (!current.isLeaf()) {
      current = current.selectBestChild();
      visited.add(current);
    }

    Node bestChild;
    // 2. Expansion
    if (!current.getGame().getGameEnd()) {
      current.expand();
      bestChild = current.selectRandomChild();
      visited.add(bestChild);
    } else {
      bestChild = current;
    }


    // 3. Simulation
    double result = rollout(bestChild);

    // 4. Propagation
    for (Node node : visited) {
      node.updateStats(result);
    }
  }

  /**
   *
   * @param rootNode
   * @return rollut resulat entweder sieg niederlage oder loss.
   */
  public double rollout(Node node) {


    Game game = new Game(node.getGame());
    int player = node.getPlayerIndex();
    int nextPlayer = player ^ 1;

    while (!game.getGameEnd()) {

      AbstractPlayer abs = game.getPlayers().get(game.getTurn());
      WholePlay nextPlay = new WholePlay(abs.choosePlay(), abs.chooseStapel());
      game.unCheckedPlay(nextPlay, abs);

    }

    int winner = game.calculateWinnerIndex(myIndex);

    if (winner == player) {
      return getValueOfWinner(player); // current player wins
    } else if (nextPlayer == winner) {
      return getValueOfWinner(nextPlayer); // opponent wins
    } else {
      return POINTS_FOR_DRAW; // draw
    }

  }



  /**
   * gibt passenden wert zum propagieren zurück
   **/
  public double getValueOfWinner(int player) {
    return (player == myIndex) ? POINTS_FOR_WIN : POINTS_FOR_LOSS;
  }

}
