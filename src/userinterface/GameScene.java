package userinterface;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;
import domain.cards.AbstractCard;
import domain.cards.Color;
import domain.cards.Stapel;
import domain.main.Game;
import domain.main.PlayOption;
import domain.main.WholePlay;
import domain.players.AbstractPlayer;
import domain.players.AiPlayer;
import experiments.ExperimentInfo;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class GameScene extends AnchorPane {

  static int counter = 0;

  static String borderStyle = "; -fx-stroke: black; -fx-stroke-width: 5;";

  private AbstractCard selectedCard = null;

  private Stapel playStapel = null;

  private Stapel drawStapel = null;

  private Label lastPlayLabel;

  private Game game;

  private AiPlayer myPlayer;

  private AiPlayer opp;

  private OppExpeditionenLabels oppEx;

  private MyExpeditionenLabels myEx;

  private AblageLabels middle;

  private boolean playMode;

  private boolean drawMode;

  private Label scoreBoard;

  private static final int height = 110;

  ExperimentInfo info = new ExperimentInfo();

  public GameScene(Game theGame) {

    super();
    this.setPrefHeight(600);
    this.setPrefWidth(600);
    initStaepels();
    this.game = theGame;
    this.drawMode = false;
    this.playMode = false;
    this.myPlayer = game.getPlayers().get(1);
    this.opp = game.getPlayers().get(0);
    this.middle = new AblageLabels(game, this);
    this.myEx = new MyExpeditionenLabels(myPlayer, this);
    this.oppEx = new OppExpeditionenLabels(this, opp);

    update();
    addButton();

    this.initLastPlay();

  }

  private void initLastPlay() {
    this.lastPlayLabel = new Label("LastPLay");
    lastPlayLabel.setLayoutX(380);
    lastPlayLabel.setLayoutY(130);
    this.getChildren().add(lastPlayLabel);

  }

  private void updateLastPlay(WholePlay play) {

    this.lastPlayLabel.setText("EnemyPlay\nlege " + play.getOption().getCard() + " auf "
        + play.getOption().getStapel() + "\n ziehen von " + play.getStapel());
  }

  private void addButton() {
    Button b = new Button("OPPONENT");
    this.getChildren().add(b);
    b.setLayoutX(20);
    b.setLayoutY(15);

    GameScene temp = this;
    b.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent e) {

        // ProgressIndicator progress = new ProgressIndicator();
        // temp.getChildren().add(progress);
        // progress.setLayoutX(30);
        // progress.setLayoutY(30);
        WholePlay pl = game.externalRound(opp);
        temp.updateLastPlay(pl);
        playMode = true;
        drawMode = false;
        update();
        temp.getChildren().remove(b);

      }
    });


  }

  private void updateCards() {
    this.oppEx.update();
    this.myEx.update();
    this.myEx.updateListeners(this.playMode, this.drawMode);
  }

  private void update() {

    this.middle.update();
    this.updateCards();
    if (drawMode) {
      middle.activateDraw();
    }

    updateScores();
    System.out.println(this.opp.getHandKarten());
  }



  private void addNewGameButton() {

    Button newGameButton = new Button("Start new Game");
    newGameButton.setLayoutX(200);
    newGameButton.setLayoutY(15);


    newGameButton.setOnAction(event -> {

      this.getChildren().removeAll(this.getChildren());

      this.initLastPlay();
      this.initLastPlay();
      this.initStaepels();
      this.game = Game.ISMCTSvsME();
      this.drawMode = false;
      this.playMode = false;
      this.myPlayer = game.getPlayers().get(1);
      this.opp = game.getPlayers().get(0);
      this.middle = new AblageLabels(game, this);
      this.myEx = new MyExpeditionenLabels(myPlayer, this);
      this.oppEx = new OppExpeditionenLabels(this, opp);

      update();
      addButton();


    });

    this.getChildren().add(newGameButton);

  }

  private void initStaepels() {
    Group recs = new Group();
    Rectangle all = new Rectangle(600, 600);
    all.setFill(Paint.valueOf("#ffd7ac"));
    recs.getChildren().add(all);
    for (int i = 0; i < 4; i++) {
      Rectangle r = new Rectangle(325, 60);
      r.setLayoutX(44);
      r.setLayoutY(height + i * 80);
      recs.getChildren().add(r);
      if (i == 3) {
        r.setWidth(515);
        r.setLayoutY(445);
      }
      r.setFill(Paint.valueOf("#8f4d14"));
    }

    this.getChildren().add(recs);

  }

  static String ColorToString(Color c) {
    switch (c) {
      case BLUE:
        return "deepskyblue";
      case GREEN:
        return "palegreen;";
      case RED:
        return "tomato;";
      case WHITE:
        return "white";
      case YELLOW:
        return "yellow";
      default:
        return "orange";
    }
  }

  /**
   * 
   */
  void executePlay() {
    game.externalPlay(new PlayOption(playStapel, selectedCard), myPlayer);
    this.playMode = false;
    this.drawMode = true;
    playStapel = null;
    selectedCard = null;
    update();


  }

  void executeDraw() {
    game.externalDraw(drawStapel, myPlayer);

    playStapel = null;
    selectedCard = null;
    drawStapel = null;
    if (!game.getGameEnd()) {
      WholePlay pl = this.game.externalRound(opp);
      this.updateLastPlay(pl);
      this.playMode = true;
      this.drawMode = false;
    } else {
      this.playMode = false;
      this.drawMode = false;
    }

    if (game.getGameEnd()) {

      int diff = game.calculateDiff(0);
      info.applyGame(diff);
      info.addGame(game.calculateScores());
      this.playMode = false;
      this.drawMode = false;
      this.addNewGameButton();

      info.printInfo();
      this.calcExp();
      System.err.println("counter = " + counter);
    }
    update();
  }

  private void calcExp() {
    for (Stack<AbstractCard> st : this.game.getPlayers().get(0).getExpeditionen().values()) {

      if (!st.isEmpty()) {
        counter++;
      }


    }

  }

  void setSelectedCard(AbstractCard abs) {
    this.selectedCard = abs;
  }

  AbstractCard getSelectedCard() {
    return this.selectedCard;
  }

  public void setDrawMode(boolean b) {
    this.drawMode = b;
  }

  public boolean getDrawMode() {
    return this.drawMode;
  }

  public void updateStapelListeners() {

    if (this.selectedCard != null) {
      List<Stapel> s = new LinkedList<Stapel>();
      s.add(Stapel.toMiddle(selectedCard.getColor()));
      this.middle.updateAblageStapel(s);

      s = this.myPlayer.getPlaySet().stream()
          .filter(p -> p.getCard().equals(selectedCard) && p.getStapel().isExpedition())
          .map(p -> p.getStapel()).collect(Collectors.toList());

      this.myEx.activatePlay(s);
      return;
    }

    this.middle.updateAblageStapel(new LinkedList<Stapel>());
    this.myEx.activatePlay(new LinkedList<Stapel>());

  }

  public boolean getPlayMode() {
    return this.playMode;
  }

  public void setPlayStapel(Stapel st) {
    this.playStapel = st;
  }

  public void setDrawStapel(Stapel s) {
    this.drawStapel = s;
  }

  public AbstractPlayer getMyPlayer() {
    return this.myPlayer;
  }

  public void updateScores() {
    String scores = this.game.calculateScores();

    if (this.scoreBoard == null || !this.getChildren().contains(scoreBoard)) {
      this.scoreBoard = new Label();
      this.scoreBoard.setLayoutX(380);
      this.scoreBoard.setLayoutY(10);
      this.scoreBoard.setPrefHeight(90);
      this.scoreBoard.setMaxWidth(200);
      this.scoreBoard.setStyle(
          "-fx-alignment: center-left; -fx-background-color: burlywood ; -fx-font-size: 15; "
              + "fx-font-family: Calibri;" + "-fx-border-width: 2; " + "-fx-border-color: brown; ");
      this.scoreBoard.setPrefWidth(200);
      this.getChildren().add(scoreBoard);
    }

    this.scoreBoard.setText(scores);
  }

}
