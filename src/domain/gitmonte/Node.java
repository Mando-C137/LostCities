package domain.gitmonte;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import domain.main.Game;
import domain.main.WholePlay;
import domain.players.AbstractPlayer;

public class Node {

  /**
   * Game associated with this node. *
   */
  private final Game game;
  /**
   * Move which created this node.
   */
  private final WholePlay move;
  /**
   * Player who made the move to create this state.
   */
  private final int playerIndex;
  /**
   * Children of this node.
   */
  private final List<Node> children = new ArrayList<Node>();
  /**
   * How many times we visited this node?
   */
  private int visits;
  /**
   * How many points we won starting from this node (move)?
   */
  private double points;

  private Node parent;

  Node(Game game, WholePlay move, int player, Node parent) {
    this.game = game;
    this.move = move;
    this.playerIndex = player;
    this.parent = parent;
  }

  /**
   * Expand children of this node.
   */
  public void expand() {
    int nextPlayer = this.playerIndex ^ 1;

    // If not - we branch on all his possible movements.
    List<WholePlay> possiblePlays = this.game.getPlayers().get(nextPlayer).getAllActions();

    for (WholePlay newPlay : possiblePlays) {
      Game nextBoard = new Game(game);
      AbstractPlayer pl = nextBoard.getPlayers().get(nextPlayer);
      nextBoard.unCheckedPlay(newPlay, pl);
      children.add(new Node(nextBoard, newPlay, nextPlayer, this));
    }
  }

  /**
   * Auswahlverfahren nach uct
   *
   * @return Knoten mit bestem UCT value
   */
  public Node selectBestChild() {

    return Collections.max(this.children, Comparator.comparing(c -> c.uctValue()

    ));

  }

  public void updateStats(double result) {
    visits++;
    points += result;
  }

  public boolean isLeaf() {
    return children.isEmpty();
  }

  public List<Node> getChildren() {
    return children;
  }

  public double uctValue() {
    return UCT.uctValue(this.parent.visits, this.points, this.visits);
  }

  public WholePlay getMove() {
    return this.move;
  }

  public Game getGame() {
    return this.game;
  }

  public int getPlayerIndex() {
    return this.playerIndex;
  }

  public int getVisitCount() {
    return this.visits;
  }

  /**
   * @return the best move according to visits
   */
  public Node finalSelection() {
    return Collections.max(this.children, Comparator.comparing(c -> c.visits));
  }

  public Node selectRandomChild() {
    return this.children.get((int) (Math.random() * this.children.size()));

  }

  public void printInfo() {
    System.out.println(this.children.size());


    this.children.stream().sorted(Comparator.comparing(c -> c.visits)).forEach(con -> {

      System.out.println(con.move);
      System.out.println("Wins\t\t" + con.points);
      System.out.println("VisitCount\t" + con.visits);

    });

  }
}
