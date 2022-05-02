package domain.cheatmcts;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import domain.main.Game;
import domain.main.WholePlay;
import domain.players.AiPlayer;

/**
 * Ein Knote für MCTS-Bäume ( nicht IS-MCTS)
 * 
 * @author paulh
 *
 */
public class CheatNode {

  /**
   * Parameter C für die UCT-Formel
   */
  private static double C = 1.0;

  /**
   * Die Aktion, die zu dem Knoten geführt hat
   */
  private final WholePlay incomingAction;

  /**
   * der Spieler der gerade dran ist
   */
  private final int playerIndex;

  /**
   * Die Kinderknoten dieses Knoten
   */
  private final List<CheatNode> children;

  /**
   * Der Elternknoten
   */
  private final CheatNode parent;

  /**
   * Der Wert dieses Knoten
   */
  private double value;

  /**
   * Die Anzahl an visits des Knoten
   */
  private int visitcount;

  /**
   * Konstruktor: Setzen der Aktion, des Spielerindex und Elterknoten
   * 
   * @param action die Aktion, die diese Aktion erzeugt hat
   * @param index der Index des Spielers, der am Zug ist
   * @param parent der Elterknoten
   */
  public CheatNode(WholePlay action, int index, CheatNode parent) {
    this.children = new LinkedList<CheatNode>();
    this.parent = parent;
    this.playerIndex = index;
    this.incomingAction = action;
    this.value = 0;
    this.visitcount = 0;
  }

  public WholePlay finalSelection() {

    CheatNode best = Collections.max(this.children, Comparator.comparing(c -> c.visitcount));

    return best.incomingAction;
  }

  public CheatNode selectBestChild(Game d, int rootIndex) {


    CheatNode selectedChild = this;

    while (!d.getGameEnd() && selectedChild.unexpandendChildren(d).isEmpty()) {

      selectedChild =
          Collections.max(selectedChild.children, Comparator.comparing(c -> c.uctValue(rootIndex)));

      d.unCheckedPlay(selectedChild.incomingAction, d.getPlayers().get(d.getTurn()));

    }

    return selectedChild;


  }

  private double uctValue(int rootIndex) {

    if (this.visitcount == 0) {
      return Integer.MAX_VALUE;
    }

    double value = (rootIndex != playerIndex) ? this.value : this.visitcount - this.value;

    double uct = (double) (value / this.visitcount) + 1.41 * Math.sqrt(

        Math.log((double) this.parent.visitcount) / (double) this.visitcount);


    return uct;
  }

  public List<WholePlay> unexpandendChildren(Game d) {
    List<WholePlay> allActions = d.getPlayers().get(d.getTurn()).getAllActions();

    List<WholePlay> alreadyExistingActions =
        this.children.stream().map(node -> node.incomingAction).collect(Collectors.toList());

    allActions.removeAll(alreadyExistingActions);

    return allActions;


  }

  public CheatNode expand(Game currentGame, List<WholePlay> unexpandedChildren) {

    int randomIndex = (int) (Math.random() * unexpandedChildren.size());

    WholePlay theAction = unexpandedChildren.get(randomIndex);

    AiPlayer pl = currentGame.getPlayers().get(currentGame.getTurn());

    currentGame.unCheckedPlay(theAction, pl);

    CheatNode newChild = new CheatNode(theAction, currentGame.getTurn(), this);

    this.children.add(newChild);

    return newChild;


  }

  public void backpropagate(double simulationResult) {
    CheatNode current = this;

    while (current != null) {
      current.visitcount++;
      current.value += simulationResult;

      current = current.parent;

    }



  }



  public void printInfo() {

    System.out.println(this.children.size());


    this.children.stream().sorted(Comparator.comparing(c -> c.visitcount)).forEach(con -> {

      System.out.println(con.incomingAction);
      System.out.println("Wins\t\t" + con.value);
      System.out.println("VisitCount\t" + con.visitcount);

    });

  }

  public List<CheatNode> getChildren() {

    return this.children;
  }

  public WholePlay getAction() {

    return this.incomingAction;
  }

  public int getVisits() {

    return this.visitcount;
  }

  public void addVisits(int visits) {
    this.visitcount += visits;

  }

  /**
   * Setter für den Paramter C
   * 
   * @param c
   */
  public static void setParameter(double c) {
    C = c;

  }


}
