package domain.finalmonte;

import java.util.List;
import domain.cards.Stapel;
import domain.gitmonte.UCT;
import domain.main.Game;
import domain.main.PlayOption;
import domain.main.WholePlay;
import domain.players.AiPlayer;

public class FinalMonte {

  private static final int WIN_SCORE = 10;

  private static final int DRAW = -1;

  private int level;

  private int opponent;

  private double time;

  public FinalMonte(double time) {
    this.time = time;
  }

  public int getLevel() {
    return level;
  }

  public void setLevel(int level) {
    this.level = level;
  }


  public WholePlay findNextMove(Game game, int playerNo) {
    long start = System.currentTimeMillis();

    game.replaceStrategiesWithRandom();
    opponent = playerNo ^ 1;
    Tree tree = new Tree(game);
    Node rootNode = tree.getRoot();
    rootNode.getState().setGame(game);
    rootNode.getState().setPlayerNo(playerNo);

    while (System.currentTimeMillis() - start < this.time) {
      // Phase 1 - Selection
      Node promisingNode = selectPromisingNode(rootNode);
      // Phase 2 - Expansion
      if (!promisingNode.getState().getGame().getGameEnd())
        expandNode(promisingNode);

      // Phase 3 - Simulation
      Node nodeToExplore = promisingNode;
      if (promisingNode.getChildArray().size() > 0) {
        nodeToExplore = promisingNode.getRandomChildNode();
      }
      int playoutResult = simulateRandomPlayout(nodeToExplore);
      // Phase 4 - Update
      backPropogation(nodeToExplore, playoutResult);
    }

    Node winnerNode = rootNode.getChildWithMaxScore();

    // rootNode.getChildArray().forEach(con -> {
    //
    // System.out.println(con.getState().getPlay());
    // System.out.println(con.state.getVisitCount());
    // System.out.println(UCT.uctValue(winnerNode.state.getVisitCount(), con.state.getWinScore(),
    // con.state.getVisitCount()));
    // });



    return winnerNode.getState().getPlay();
  }

  private Node selectPromisingNode(Node rootNode) {
    Node node = rootNode;
    while (node.getChildArray().size() != 0) {
      node = UCT.findBestNodeWithUCT(node);
    }
    return node;
  }

  private void expandNode(Node node) {
    List<State> possibleStates = node.getState().getAllPossibleStates();
    possibleStates.forEach(state -> {
      Node newNode = new Node(state);
      newNode.setParent(node);
      node.getChildArray().add(newNode);
    });
  }

  private void backPropogation(Node nodeToExplore, int playerNo) {
    Node tempNode = nodeToExplore;
    while (tempNode != null) {
      tempNode.getState().incrementVisit();
      if (tempNode.getState().getPlayerNo() == playerNo)
        tempNode.getState().addScore(1);
      else if (playerNo == FinalMonte.DRAW)
        tempNode = tempNode.getParent();
    }
  }

  private int simulateRandomPlayout(Node node) {
    Game tempState = new Game(node.getState().getGame());
    int winnerStatus = -1;
    if (tempState.getGameEnd() && (winnerStatus = tempState.calculateWinnerIndex(0)) == opponent) {
      node.getParent().getState().setWinScore(Integer.MIN_VALUE);
      return winnerStatus;
    }

    while (!tempState.getGameEnd()) {
      AiPlayer ai = tempState.getPlayers().get(tempState.getTurn());


      PlayOption option = ai.choosePlay();
      Stapel s = ai.chooseStapel();
      WholePlay temp = new WholePlay(option, s);

      tempState.unCheckedPlay(temp, ai);
    }
    return tempState.calculateWinnerIndex(0);
  }

}
