package domain.cheatmcts;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import domain.main.Game;
import domain.main.WholePlay;
import domain.players.AiPlayer;

public class CheatNode {

  private final WholePlay incomingAction;

  /**
   * der Spieler der gerade dran ist
   */
  private final int playerIndex;

  private final List<CheatNode> children;

  private final CheatNode parent;

  private double value;

  private int visitcount;

  public CheatNode(WholePlay action, int index, CheatNode parent) {
    this.children = new LinkedList<CheatNode>();
    this.parent = parent;
    this.playerIndex = index ^ 1;
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

    double value = (rootIndex == playerIndex) ? this.value : this.visitcount - this.value;

    return (double) (value / this.visitcount) + 1 * Math.sqrt(

        Math.log((double) this.parent.visitcount) / (double) this.visitcount);
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


}
