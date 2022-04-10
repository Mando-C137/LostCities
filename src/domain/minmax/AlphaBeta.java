package domain.minmax;

import domain.main.Game;
import domain.main.WholePlay;
import domain.players.AbstractPlayer;

public class AlphaBeta {


  private int turn;


  public AlphaBeta(int turn) {
    this.turn = turn;
  }

  /**
   * MinMaxBaumTiefe
   */
  private static int FAVOURED_DEPTH = 5;


  public WholePlay getBestPlay(Game g) {



    Node node = new Node(g, null);
    double bestOption = this.alphabeta(node, 0, Integer.MIN_VALUE, Integer.MAX_VALUE, true);

    Node arr = node.getChildren().stream().filter(ch -> ch.getVal() == bestOption).findFirst()
        .orElseGet(() -> {

          return node.getChildren().get(0);
        });

    return arr.getAction();


  }

  private static int counter = 0;


  public double alphabeta(Node node, int depth, double alpha, double beta, boolean max) {
    // System.out.println(counter++);
    double value = Integer.MIN_VALUE;

    // wenn man an einem blattknoten ist oder das spiel fertig ist
    // base case
    if (depth == FAVOURED_DEPTH || node.getGame().getGameEnd()) {
      return node.setVal(node.evaluate(turn));
    }

    // mögliche plays rausfinden und die daraus resultierenden zustände speichern

    for (WholePlay action : node.getAllPossibleActions()) {
      Game copyGame = new Game(node.getGame());
      int turn = copyGame.getTurn();
      AbstractPlayer player = copyGame.getPlayers().get(turn);
      copyGame.unCheckedPlay(action, player);
      node.getChildren().add(new Node(copyGame, action));
    }

    // wenn man an einem max knoten ist
    if (max) {// (* maximizing player *)
      value = Integer.MIN_VALUE;
      for (Node state : node.getChildren()) { // false
        value = Math.max(value, alphabeta(state, depth + 1, alpha, beta, false));
        if (value >= beta) {
          break;
        }
        alpha = Math.max(value, alpha);
      }

      return node.setVal(value);

      // wenn man einem min knoten ist
    } else { // (* minimizing player *)
      value = Integer.MAX_VALUE;
      for (Node state : node.getChildren()) { // true
        value = Math.min(value, alphabeta(state, depth + 1, alpha, beta, true));
        if (value <= alpha) {
          break;
        }
        beta = Math.min(value, beta);
      }

      return node.setVal(value);
    }

  }

  public double minimax(Node node, int depth, boolean max) {
    System.out.println(counter++);

    double value = Integer.MIN_VALUE;

    // wenn man an einem blattknoten ist oder das spiel fertig ist
    // base case
    if (depth == FAVOURED_DEPTH || node.getGame().getGameEnd()) {

      return node.setVal(node.evaluate(turn));

    }

    // mögliche plays rausfinden und die daraus resultierenden zustände speichern

    for (WholePlay action : node.getAllPossibleActions()) {

      Game copyGame = new Game(node.getGame());
      int turn = copyGame.getTurn();
      AbstractPlayer player = copyGame.getPlayers().get(turn);
      copyGame.unCheckedPlay(action, player);
      node.getChildren().add(new Node(copyGame, action));
    }

    // wenn man an einem max knoten ist
    if (max) {
      value = Integer.MIN_VALUE;
      for (Node state : node.getChildren()) { // false
        value = Math.max(value, minimax(state, depth + 1, false));
      }

      return node.setVal(value);

      // wenn man einem min knoten ist
    } else { // (* minimizing player *)
      value = Integer.MAX_VALUE;
      for (Node state : node.getChildren()) { // true
        value = Math.min(value, minimax(state, depth + 1, true));
      }

      return node.setVal(value);
    }

  }

}
