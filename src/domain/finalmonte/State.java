package domain.finalmonte;

import java.util.ArrayList;
import java.util.List;
import domain.main.Game;
import domain.main.WholePlay;
import domain.players.AbstractPlayer;

public class State {

  private Game game;
  private int playerNo;
  private int visitCount;
  private double winScore;

  private WholePlay responsiblePlay;

  public State(State state) {
    this.game = new Game(state.getGame());
    this.playerNo = state.getPlayerNo();
    this.visitCount = state.getVisitCount();
    this.winScore = state.getWinScore();

  }

  public State(Game game) {
    this.game = new Game(game);
  }

  Game getGame() {
    return game;
  }

  void setGame(Game game) {
    this.game = game;
  }

  int getPlayerNo() {
    return playerNo;
  }

  void setPlayerNo(int playerNo) {
    this.playerNo = playerNo;
  }

  int getOpponent() {
    return playerNo ^ 1;
  }

  public int getVisitCount() {
    return visitCount;
  }

  public void setVisitCount(int visitCount) {
    this.visitCount = visitCount;
  }

  public double getWinScore() {
    return winScore;
  }

  void setWinScore(double winScore) {
    this.winScore = winScore;
  }

  public List<State> getAllPossibleStates() {
    List<State> possibleStates = new ArrayList<State>();

    List<WholePlay> allActions = this.game.getPlayers().get(playerNo).getAllActions();
    allActions.forEach(p -> {
      State newState = new State(this.game);
      AbstractPlayer player = newState.getGame().getPlayers().get(newState.getGame().getTurn());
      newState.getGame().unCheckedPlay(p, player);
      newState.setWholePlay(p);
      newState.setPlayerNo(this.playerNo ^ 1);
      possibleStates.add(newState);
    });
    return possibleStates;
  }

  void incrementVisit() {
    this.visitCount++;
  }

  void addScore(double score) {
    if (this.winScore != Integer.MIN_VALUE)
      this.winScore += score;
  }

  void randomPlay() {

    AbstractPlayer player = this.game.getPlayers().get(this.game.getTurn());
    List<WholePlay> allActions = player.getAllActions();
    int totalPossibilities = allActions.size();
    int selectRandom = (int) (Math.random() * totalPossibilities);
    this.game.unCheckedPlay(allActions.get(selectRandom), player);
  }

  public WholePlay getPlay() {
    return this.responsiblePlay;
  }

  public void setWholePlay(WholePlay thePlay) {
    this.responsiblePlay = thePlay;
  }



}
