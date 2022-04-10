/**
 * 
 */
package domain.main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Stack;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import domain.cards.AbstractCard;
import domain.cards.Color;
import domain.cards.NoCard;
import domain.cards.NumberCard;
import domain.cards.Stapel;
import domain.cards.WettCard;
import domain.cheatmcts.CheatMCTSStrategy;
import domain.exception.GameException;
import domain.gitmonte.GitStrategy;
import domain.ismcts.InformationSetStrategy;
import domain.players.AbstractPlayer;
import domain.players.AiPlayer;
import domain.strategies.HumanStrategy;
import domain.strategies.RandomStrategy;
import domain.strategies.SimpleStrategy;
import domain.strategies.SimulationStrategy;

/**
 * Die Gameinstanz, von der man das Spiel steuert und Referenzen auf alle nötigen Objekte hat.
 *
 */
public class Game {

  /**
   * Der Nachziehstapel
   */
  private Stack<AbstractCard> nachZiehStapel;

  /**
   * Eine Map, die jede Farbe einem Stack aus Karten zuordnet.
   */
  private HashMap<Color, Stack<AbstractCard>> ablageStaepels;

  /**
   * Die 2 Spieler, die das Spiel spielen.
   */
  private List<AiPlayer> players;

  /**
   * gameEnd zeigt, ob das Spiel beendet ist.
   */
  private boolean gameEnd;

  /**
   * index wer grade dran ist
   */
  private int turn;

  private PlayOption lastPlay;

  private AbstractCard lastDraw;


  /**
   * Konstruktor: Initialisierung aller nötigen Objekte, Generieren des Nachziehstapels
   */
  public Game() {
    this.gameEnd = false;
    this.players = new ArrayList<AiPlayer>();
    initStaepel();
    generateNachziehStapel();

  }

  public static Game twoRandoms() {
    Game g = new Game();

    AiPlayer one = new AiPlayer();
    one.setIndex(0);
    one.setGame(g);
    one.setStrategy(new RandomStrategy(one));
    g.players.add(one);

    AiPlayer two = new AiPlayer();
    two.setIndex(1);
    two.setStrategy(new RandomStrategy(two));
    two.setGame(g);
    g.players.add(two);

    g.drawCards();
    return g;
  }

  public static Game DifferentMCTS() {
    Game g = new Game();

    AiPlayer one = new AiPlayer();
    one.setIndex(0);
    one.setGame(g);
    one.setStrategy(new GitStrategy(g));
    g.players.add(one);

    AiPlayer two = new AiPlayer();
    two.setGame(g);
    two.setIndex(1);
    two.setStrategy(new GitStrategy(g));
    g.players.add(two);

    g.drawCards();
    return g;
  }

  public static Game SimpleVsMCTS() {
    Game g = new Game();

    g.players = new ArrayList<AiPlayer>();
    AiPlayer one = new AiPlayer();
    one.setIndex(0);
    one.setGame(g);
    one.setStrategy(new SimpleStrategy(one));
    g.players.add(one);

    AiPlayer two = new AiPlayer();
    two.setGame(g);
    two.setIndex(1);
    two.setStrategy(new GitStrategy(g));
    g.players.add(two);

    g.drawCards();
    return g;
  }

  public static Game SimpleVsMe() {

    Game g = new Game();

    g.players = new ArrayList<AiPlayer>();
    AiPlayer one = new AiPlayer();
    one.setIndex(0);
    one.setGame(g);
    one.setStrategy(new SimpleStrategy(one));
    g.players.add(one);

    AiPlayer two = new AiPlayer();
    two.setGame(g);
    two.setIndex(1);
    two.setStrategy(new HumanStrategy());
    g.players.add(two);


    g.drawCards();
    return g;

  }

  public static Game ISMCTSvsME() {
    Game g = new Game();

    g.players = new ArrayList<AiPlayer>();
    AiPlayer one = new AiPlayer();
    one.setIndex(0);
    one.setGame(g);
    one.setStrategy(new InformationSetStrategy(g));
    g.players.add(one);

    AiPlayer two = new AiPlayer();
    two.setGame(g);
    two.setIndex(1);
    two.setStrategy(new HumanStrategy());
    g.players.add(two);


    g.drawCards();
    return g;

  }

  public static Game twoWithoutStrategies() {
    Game g = new Game();

    g.players = new ArrayList<AiPlayer>();
    AiPlayer one = new AiPlayer();
    one.setIndex(0);
    one.setGame(g);

    g.players.add(one);

    AiPlayer two = new AiPlayer();
    two.setGame(g);
    two.setIndex(1);

    g.players.add(two);


    g.drawCards();
    return g;
  }

  public static Game ISMCTSvsRANDOM() {
    Game g = twoWithoutStrategies();

    g.getPlayers().get(0).setStrategy(new InformationSetStrategy(g));
    g.getPlayers().get(1).setStrategy(new RandomStrategy(g.getPlayers().get(1)));
    return g;
  }

  public static Game CHEATvsME() {
    Game g = twoWithoutStrategies();

    g.getPlayers().get(0).setStrategy(new CheatMCTSStrategy(g.getPlayers().get(0)));
    g.getPlayers().get(1).setStrategy(new HumanStrategy());
    return g;
  }



  public static Game MctsVsMe() {
    Game g = new Game();

    AiPlayer one = new AiPlayer();
    one.setIndex(0);
    one.setGame(g);
    one.setStrategy(new GitStrategy(g));
    g.players.add(one);

    AiPlayer two = new AiPlayer();
    two.setIndex(1);
    two.setGame(g);
    two.setStrategy(new HumanStrategy());
    g.players.add(two);

    g.drawCards();
    return g;
  }


  public Game(Game g) {
    this.gameEnd = g.gameEnd;
    this.turn = g.turn;
    this.ablageStaepels = new HashMap<Color, Stack<AbstractCard>>();
    for (Color c : Color.values()) {
      this.ablageStaepels.put(c, new Stack<AbstractCard>());
      this.ablageStaepels.get(c).addAll(g.ablageStaepels.get(c));

    }

    this.players = new ArrayList<AiPlayer>();
    this.players.add(new AiPlayer(g.getPlayers().get(0)));
    this.players.add(new AiPlayer(g.getPlayers().get(1)));

    this.players.forEach(con -> con.setGame(this));


    this.nachZiehStapel = new Stack<AbstractCard>();
    this.nachZiehStapel.addAll(g.nachZiehStapel);

  }



  private void drawCards() {

    for (AbstractPlayer player : players) {
      IntStream.range(0, 8).forEach(num -> this.addCardtoPlayer(Stapel.NACHZIEHSTAPEL, player));
    }

    this.turn = 0;
  }

  /**
   * erstellt den Stapel mit den offiziellen Karten
   */
  private void generateNachziehStapel() {

    nachZiehStapel = new Stack<AbstractCard>();

    // alle karten generieren in einer Liste
    ArrayList<AbstractCard> allCards = new ArrayList<AbstractCard>();
    for (Color c : Color.values()) {
      IntStream.range(2, 11).forEach(num -> allCards.add(new NumberCard(c, num)));
      IntStream.range(1, 4).forEach(num -> allCards.add(new WettCard(c)));

    }

    // die Karten zufällig auf den Stapel geben
    Random rand = new Random();
    while (!allCards.isEmpty()) {
      AbstractCard remove = allCards.remove(rand.nextInt(allCards.size()));
      this.nachZiehStapel.add(remove);

    }

  }

  /**
   * initialisiert die Staepel, auf denen die Karten abgelegt werden können.
   */
  private void initStaepel() {
    this.ablageStaepels = new HashMap<Color, Stack<AbstractCard>>();
    Stream.of(Color.values()).forEach(c -> this.ablageStaepels.put(c, new Stack<AbstractCard>()));
  }


  @Override
  public String toString() {
    StringBuffer res = new StringBuffer();
    res.append("-----------------------------------------\n");
    res.append("\t\t Y\tW\tB\tG\tR\n");
    for (AiPlayer abs : this.players) {
      res.append(abs.getStrategy().getStrategyName().toString().substring(0, 4) + "\t\t");
      res.append(abs.expenditionenString() + "\n");
    }
    res.append("Middle\t\t");
    for (Color col : Color.values()) {
      res.append(this.peekAblageStapel(col).orElseGet(() -> new NoCard(col)));
      res.append("\t");
    }

    res.append("\n-----------------------------------------");


    return res.toString();
  }

  /**
   * liefert das oberste Element des Ablagestapels mit der spezifizierten Farbe c, falls noch nichts
   * auf dem Stapel ist das Optional leer.
   * 
   * @param c
   * @return
   */
  public Optional<AbstractCard> peekAblageStapel(Color c) {
    AbstractCard card = null;
    if (this.ablageStaepels != null && this.ablageStaepels.get(c) != null)
      if (!this.ablageStaepels.get(c).isEmpty()) {
        card = this.ablageStaepels.get(c).peek();
      }
    return Optional.ofNullable(card);
  }


  public Optional<AbstractCard> returnCard(Stapel stapel) {
    AbstractCard returnAnswer = null;
    Color c = stapel.getColor();

    if (this.ablageStaepels != null && this.ablageStaepels.get(c) != null)
      if (!this.ablageStaepels.get(stapel.getColor()).isEmpty()) {
        returnAnswer = this.ablageStaepels.get(c).pop();
      } else {
        throw new GameException.EmptyStapelException(stapel);
      }

    return Optional.ofNullable(returnAnswer);

  }


  /**
   * entfernt die oberste Karte von dem NachZiehstapel und gibt sie zurück
   *
   * @return die oberste Karte von dem NachziehStapel
   */
  public Optional<AbstractCard> returnCardFromNachziehStapel() {

    AbstractCard returnAnswer = null;

    if (this.nachZiehStapel.isEmpty()) {
      throw new GameException.EmptyStapelException(Stapel.NACHZIEHSTAPEL);
    } else {
      returnAnswer = this.nachZiehStapel.pop();
    }
    return Optional.ofNullable(returnAnswer);
  }

  public void gameFlow() {

    int i = 0;
    int index = 0;
    double time = System.currentTimeMillis();


    for (; !this.getGameEnd(); i++) {

      // Index wechselt immer : 0 ^ 1 = 1 und 1 ^ 1= 0

      AbstractPlayer top = players.get(turn);
      // System.out.println("actions");
      // top.getAllActions().forEach(con -> System.out.println(con));

      // System.out.println("Es spielt nun" + this.turn);
      // System.out.println((top.getHandKarten()));
      PlayOption nextPlay = top.choosePlay();
      this.makePlay(nextPlay, top);
      this.addCardtoPlayer(top.chooseStapel(), top);
      // System.out.println(this);
      System.out.println("Nachziehstapelkartenanzahl : " + this.getRemainingCards());


    }

  }



  public List<AbstractCard> getAblageStapel(Color c) {
    return this.ablageStaepels.get(c);
  }

  public void externalRound(AbstractPlayer p) {
    this.makePlay(p.choosePlay(), p);
    this.addCardtoPlayer(p.chooseStapel(), p);

  }

  public void checkedPlay(WholePlay w, AiPlayer p) {
    this.makePlay(w.playOption, p);
    this.addCardtoPlayer(w.s, p);
  }

  public void externalPlay(PlayOption opt, AbstractPlayer abs) {
    this.makePlay(opt, abs);
  }

  public void externalDraw(Stapel s, AbstractPlayer abs) {
    this.addCardtoPlayer(s, abs);
  }

  private void makePlay(PlayOption play, AbstractPlayer player) {

    if (!play.getCard().getColor().equals(play.getStapel().getColor())) {
      throw new GameException.IllegalPlayException(play.getCard(), play.getStapel());
    }

    AbstractCard cardToPlay = play.getCard();

    if (!player.getHandKarten().contains(cardToPlay)) {
      throw new GameException.DoNotOwnException(player, cardToPlay);
    }

    if (Arrays.asList(Stapel.orderedExpeditions).contains(play.getStapel())) {
      if (checkExpeditionPlay(play, player)) {
        player.getHandKarten().remove(cardToPlay);
        player.getExpeditionen().get(cardToPlay.getColor()).push(cardToPlay);
      } else {
        throw new GameException.IllegalPlayException(cardToPlay, play.getStapel());
      }
    } else {
      player.getHandKarten().remove(cardToPlay);
      this.ablageStaepels.get(cardToPlay.getColor()).push(cardToPlay);
    }



    player.setLastPlay(play.getStapel());
    this.lastPlay = play;

    // System.out.println(player.getName() + " | " + play.getCard() + " on " + play.getStapel());

  }

  private boolean checkExpeditionPlay(PlayOption play, AbstractPlayer player) {

    if (player.getExpeditionen().get(play.getCard().getColor()).isEmpty()) {
      return true;
    } else {

      AbstractCard peek = player.getExpeditionen().get(play.getCard().getColor()).peek();

      return play.getCard().compareTo(peek) >= 0;

    }



  }

  private void addCardtoPlayer(Stapel stapel, AbstractPlayer abstractPlayer) {
    // System.out.println(abstractPlayer.getName() + " zieht von Stapel" + stapel);

    Optional<AbstractCard> abs;
    if (stapel.isMiddle()) {

      if (abstractPlayer.getLastAblage() != null) {
        if (abstractPlayer.getLastAblage().equals(stapel)) {
          throw new GameException.SameCardException();
        }
      }

      abs = this.returnCard(stapel);



    } else {
      abs = this.returnCardFromNachziehStapel();
    }

    if (abs.isPresent()) {

      AbstractCard card = abs.get();
      abstractPlayer.getHandKarten().add(card);

      this.lastDraw = stapel.isMiddle() ? card : null;

      // benachrichtige den anderen Spieler von dem Draw


    } else {
      throw new GameException("karte konnte nicht hinzugefuegt werden");
    }

    if (nachZiehStapel.isEmpty()) {
      this.gameEnd = true;
      calculateScores();
    }

    this.turn = 1 ^ this.turn;
    abstractPlayer.setLastPlay(null);

  }

  public Stack<AbstractCard> stapelToStack(Stapel st) {

    switch (st) {
      case BLUEMIDDLE:
        return this.ablageStaepels.get(Color.BLUE);
      case GREENMIDDLE:
        return this.ablageStaepels.get(Color.GREEN);
      case REDMIDDLE:
        return this.ablageStaepels.get(Color.RED);
      case WHITEMIDDLE:
        return this.ablageStaepels.get(Color.WHITE);
      case YELLOWMIDDLE:
        return this.ablageStaepels.get(Color.YELLOW);
      case NACHZIEHSTAPEL:
        return this.nachZiehStapel;
      default:
        System.out.println("kann nicht von einer Expedition ziehen");
        return null;
    }


  }

  public List<AiPlayer> getPlayers() {
    return this.players;
  }

  public Optional<AbstractCard> peekNachziehStapel() {

    if (this.nachZiehStapel.isEmpty()) {
      return Optional.ofNullable(null);
    }


    return Optional.ofNullable(this.nachZiehStapel.peek());
  }

  public boolean getGameEnd() {
    this.gameEnd = this.nachZiehStapel.size() == 0;
    return this.gameEnd;
  }

  public String calculateScores() {

    StringBuffer sb = new StringBuffer();
    int wholeSum = 0;
    int fact = 1;
    int singleSum = 0;
    for (AbstractPlayer p : this.players) {
      wholeSum = 0;
      A: for (Stack<AbstractCard> st : p.getExpeditionen().values()) {

        fact = 1;
        singleSum = -20;
        if (st.size() == 0) {
          continue A;
        }

        for (AbstractCard c : st) {
          if (!c.isNumber()) {
            fact++;
          } else {
            singleSum += ((NumberCard) c).getValue();
          }
        }

        singleSum *= fact;
        if (st.size() >= 8) {
          singleSum += 20;
        }
        wholeSum += singleSum;

      }
      if (p.isAI())
        sb.append(
            " " + ((AiPlayer) p).getStrategy().getStrategyName() + "'s Punkte: " + wholeSum + "\n");

    }

    return sb.toString();


  }

  public int getRemainingCards() {
    return this.nachZiehStapel.size();
  }

  public int getTurn() {
    return this.turn;
  }

  public int calculateWinnerIndex(int perspectiveIndex) {

    int diff = this.calculateScore(this.players.get(perspectiveIndex))
        - this.calculateScore(this.players.get(perspectiveIndex ^ 1));

    if (diff > 0) {
      return perspectiveIndex;
    } else if (diff == 0) {
      return -1;
    } else {
      return perspectiveIndex ^ 1;
    }

  }

  public int calculateDiff(int index) {


    return this.calculateScore(this.players.get(index))
        - this.calculateScore(this.players.get(index ^ 1));
  }

  public int calculateScore(AbstractPlayer p1) {
    int wholeSum = 0;
    int fact = 1;
    int singleSum = -20;
    for (Stack<AbstractCard> st : p1.getExpeditionen().values()) {

      fact = 1;
      singleSum = -20;
      if (st.size() == 0) {
        continue;
      }

      for (AbstractCard c : st) {
        if (!c.isNumber()) {
          fact++;
        } else {
          singleSum += ((NumberCard) c).getValue();
        }
      }

      singleSum *= fact;
      if (st.size() >= 8) {
        singleSum += 20;
      }
      wholeSum += singleSum;

    }

    return wholeSum;
  }

  /**
   * unsicher wird nichts gecheckt ob es ein korrekter Play ist.
   * 
   * @param play
   * @param player
   */
  public void unCheckedPlay(WholePlay play, AbstractPlayer player) {
    PlayOption p = play.getOption();

    // Ablegen
    if (p.getStapel().isExpedition()) {
      player.getExpeditionen().get(p.getCard().getColor()).add(p.getCard());
    } else {
      this.ablageStaepels.get(p.getCard().getColor()).add(p.getCard());
    }

    if (!player.getHandKarten().remove(p.getCard())) {
      System.out.println(player.getHandKarten());
      System.out.println(play);
      throw new GameException.DoNotOwnException(player, p.getCard());
    }


    Stapel ziehStapel = play.getStapel();
    AbstractCard drawedCard = null;

    if (ziehStapel.equals(Stapel.NACHZIEHSTAPEL)) {

      player.getHandKarten().add(this.nachZiehStapel.pop());

      this.gameEnd = this.nachZiehStapel.isEmpty();
    } else {
      drawedCard = this.ablageStaepels.get(ziehStapel.getColor()).pop();

      player.getHandKarten().add(drawedCard);

    }

    this.lastPlay = p;
    this.lastDraw = drawedCard;
    this.turn = this.turn ^ 1;
    player.setLastPlay(null);

  }

  public void replaceStrategiesWithRandom() {

    this.players.forEach(p -> {

      p.setStrategy(new RandomStrategy(p));

    });

  }

  public void replacePlayersWithSimpleAi() {

    this.players.forEach(p -> {

      p.setStrategy(new SimpleStrategy(p));

    });

  }

  public void replacePlayersWithSimulateStrategy() {

    this.players.forEach(p -> {

      p.setStrategy(new SimulationStrategy(p));

    });

  }



  public AbstractCard getLastDraw() {
    return this.lastDraw;
  }

  public PlayOption getLastPlay() {
    return this.lastPlay;
  }

  public Stack<AbstractCard> getNachziehstapel() {

    return this.nachZiehStapel;
  }



}
