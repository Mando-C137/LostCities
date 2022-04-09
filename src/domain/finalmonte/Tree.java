package domain.finalmonte;

import domain.main.Game;

public class Tree {
  Node root;

  public Tree(Game game) {
    root = new Node(new State(game));
  }

  public Tree(Node root) {
    this.root = root;
  }

  public Node getRoot() {
    return root;
  }

  public void setRoot(Node root) {
    this.root = root;
  }

  public void addChild(Node parent, Node child) {
    parent.getChildArray().add(child);
  }

}
