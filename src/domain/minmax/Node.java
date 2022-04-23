package domain.minmax;

import java.util.ArrayList;
import java.util.List;
import domain.cards.Stapel;
import domain.main.Game;
import domain.main.PlayOption;
import domain.main.WholePlay;
import domain.players.AiPlayer;


public class Node {

  private WholePlay action;
  private Game game;
  private double val;
  private List<Node> children;

  public Node(Game game, WholePlay action) {
    this.action = action;
    this.game = game;
    this.children = new ArrayList<Node>();
  }


  public double evaluate(int turn) {

    AiPlayer me = this.getGame().getPlayers().get(turn);

    AiPlayer notMe = this.getGame().getPlayers().get(turn ^ 1);

    return 0;

  }


  public Game getGame() {
    return this.game;
  }


  public double setVal(double evaluate) {
    this.val = evaluate;
    return this.val;
  }


  public List<Node> getChildren() {
    return this.children;
  }

  public List<WholePlay> getAllPossibleActions() {
    List<WholePlay> result = new ArrayList<WholePlay>();
    for (PlayOption p : this.game.getPlayers().get(this.game.getTurn()).getPlaySet()) {
      for (Stapel s : List.of(Stapel.NACHZIEHSTAPEL) /* hier kann auch drawSet sein */) {
        result.add(new WholePlay(p, s));
      }
    }

    return result;

  }


  public double getVal() {
    // TODO Auto-generated method stub
    return this.val;
  }


  public WholePlay getAction() {
    // TODO Auto-generated method stub
    return this.action;
  }
}
