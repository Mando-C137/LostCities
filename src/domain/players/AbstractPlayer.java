package domain.players;

import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import domain.cards.AbstractCard;
import domain.cards.Color;
import domain.cards.Stapel;
import domain.main.Game;
import domain.main.PlayOption;
import domain.main.WholePlay;

/**
 * Ein Spieler spielt das Spiel (game). Er zieht Karten von dem Nachziehstapel und den
 * Ablagestaeplen und legt Karten an seinen Expeditionen und den Ablagestaepeln ab.
 * 
 */
public abstract class AbstractPlayer {

  /**
   * Der Name des Spielers
   */
  final private String name;

  /**
   * der Index, der angibt an welcher Stelle der Spieler in der Liste der game instanz ist .
   */
  private int myIndex;

  /**
   * das Spiel, das der Spieler spielt.
   */
  private Game game;

  /**
   * Die Expeditionen des Spielers
   */
  private Map<Color, Stack<AbstractCard>> expeditionen;

  /**
   * Die Handkarten des Spielers.
   */
  private List<AbstractCard> handKarten;

  /**
   * Letzte Ablage des Spielers, um zu vermeiden dass er auf den selben Stapel zieht, auf den er
   * abgelegt hat.
   */
  protected Stapel lastAblage;

  /**
   * Konstruktor: Setzen des Namen initialisieren der Objekte
   * 
   * @param name
   */
  public AbstractPlayer(String name) {
    this.name = name;
    this.lastAblage = null;
    initExpeditionen();
    initHandKarten();
  }


  public AbstractPlayer(AbstractPlayer copy) {
    this.name = copy.name;
    this.myIndex = copy.myIndex;
    this.lastAblage = copy.lastAblage;
    this.initExpeditionen();
    this.initHandKarten();
    this.copyFields(copy);
  }


  /**
   * Die Expeditionen werden initialisiert, d.h. es wird für jede Farbe ein leerer Stack angelegt.
   */
  private void initExpeditionen() {
    this.expeditionen = new HashMap<Color, Stack<AbstractCard>>();
    Stream.of(Color.orderedColors)
        .forEach(color -> this.expeditionen.put(color, new Stack<AbstractCard>()));
  }


  /**
   * lediglich erstellen der Liste der Handkarten, kein Ziehen von Karten.
   */
  private void initHandKarten() {
    this.handKarten = new LinkedList<AbstractCard>();
  }


  /**
   * Auswählen des Spielzuges, also dem Ablegen einer Karte.
   * 
   * @return die Playoption, die der Spieler spielet.
   */
  public abstract PlayOption choosePlay();

  public abstract Stapel chooseStapel();

  public abstract boolean isAI();

  public List<Stapel> getDrawSet() {

    List<Stapel> result = new LinkedList<Stapel>();
    result.add(Stapel.NACHZIEHSTAPEL);
    for (Color c : Color.values()) {
      if (!this.game.getAblageStapel(c).isEmpty()) {
        result.add(Stapel.toMiddle(c));
      }
    }

    if (this.lastAblage != null) {
      result.remove(this.lastAblage);
    }


    return result;
  }


  /**
   * Getter für die expeditionen des Spielers. Sollte eigentlich unmodifiable sein.
   * 
   * @return
   */
  public Map<Color, Stack<AbstractCard>> getExpeditionen() {
    return this.expeditionen;
  }

  /**
   * Getter für die Karten des Spielers, die er auf der Hand hat.
   * 
   * @return
   */
  public List<AbstractCard> getHandKarten() {
    return (this.handKarten);
  }

  /**
   * Herausfinden aller möglichen Spielzüge die ein Spieler an einem gegebenen Zustand spielen kann.
   * kann man auch in dieser Klasse lassen. /
   * 
   * @return
   */
  public List<PlayOption> getPlaySet() {

    List<PlayOption> result = new LinkedList<PlayOption>();

    this.getHandKarten().stream().forEach(card -> {

      /*
       * Man kann immer eine Karte in die Mitte legen
       */
      result.add(new PlayOption(Stapel.toMiddle(card.getColor()), card));

      /*
       * Falls eine Expedition noch nicht geöffnet/gestartet wurde, kann man dort eine starten
       */
      if (this.getExpeditionen().get(card.getColor()).isEmpty()) {
        result.add(new PlayOption(Stapel.toExpedition(card.getColor()), card));

        /*
         * Andernfalls, muss die Karte eine Wettcard sein oder man muss eine größere Nummer haben
         */
      } else {

        if (card.compareTo(this.expeditionen.get(card.getColor()).peek()) >= 0) {
          result.add(new PlayOption(Stapel.toExpedition(card.getColor()), card));
        }

      }

    });

    // System.out.println("Anzahl meiner Karten: " + this.getHandKarten().size());
    // System.out.println("Anzahl möglicher Plays: " + result.size());
    return result;
  }

  /**
   * Getter für den Namen
   * 
   * @return
   */
  public String getName() {
    return this.name;
  }

  /**
   * String representation der Expeditionen des Spielers. Eine Zeile, in der in festgelegter
   * Reihenfolge der Farben immer die oberste Karte steht.
   * 
   * @return
   */
  public String expenditionenString() {

    StringBuffer res = new StringBuffer();

    for (Color c : Color.orderedColors) {
      Stack<AbstractCard> currentStack = this.expeditionen.get(c);
      if (!currentStack.isEmpty()) {
        res.append(currentStack.peek().toString());
      } else {
        res.append("--");
      }
      res.append("\t");
    }

    return res.toString();
  }

  public void setLastPlay(Stapel abs) {
    this.lastAblage = abs;
  }

  public Map<Color, Stack<AbstractCard>> getEnemyExp() {
    return this.game.getPlayers().get(myIndex ^ 1).getExpeditionen();
  }

  public Game getGame() {
    return this.game;
  }

  public void setGame(Game g) {
    this.game = g;
  }

  public int getRemainingCards() {
    return this.game.getRemainingCards();
  }

  public Stapel getLastAblage() {
    return this.lastAblage;
  }


  public void copyFields(AbstractPlayer abs) {
    this.handKarten.addAll(abs.handKarten);

    for (Color c : Color.values()) {
      expeditionen.get(c).addAll(abs.expeditionen.get(c));
    }
  }


  public List<PlayOption> getOptimalPlaySet() {

    List<PlayOption> result = new LinkedList<PlayOption>();

    for (Color col : Color.values()) {
      /*
       * Falls eine Expedition noch nicht geöffnet/gestartet wurde, kann man dort eine starten
       */

      if (this.getExpeditionen().get(col).isEmpty()) {

        this.handKarten.stream().filter(pre -> pre.getColor() == col).min((u, v) -> u.compareTo(v))
            .ifPresent(opt -> result.add(new PlayOption(Stapel.toExpedition(col), opt)));

        /*
         * Andernfalls, muss die Karte eine Wettcard sein oder man muss eine größere Nummer haben
         */
      } else {
        this.handKarten.stream()
            .filter(pre -> pre.getColor() == col
                && pre.compareTo(this.getExpeditionen().get(col).peek()) >= 0)
            .min((u, v) -> u.compareTo(v))
            .ifPresent(opt -> result.add(new PlayOption(Stapel.toExpedition(col), opt)));
      }

    }


    this.getHandKarten().stream().forEach(card -> {

      /*
       * Man kann immer eine Karte in die Mitte legen
       */
      result.add(new PlayOption(Stapel.toMiddle(card.getColor()), card));

    });


    // System.out.println("Anzahl meiner Karten: " + this.getHandKarten().size());
    // System.out.println("Anzahl möglicher Plays: " + result.size());
    return result;
  }

  /**
   * gibt eine Liste aller möglichen Plays zurück Ein Play besteht dann aus dem Ablegen und dem
   * Ziehen
   * 
   * @return
   */
  public List<WholePlay> getAllActions() {

    // if (this.getRemainingCards() >= 20) {
    // return this.alternativeActionsForExpansion();
    // }

    // new Stapel[] {Stapel.NACHZIEHSTAPEL}
    LinkedList<WholePlay> result = new LinkedList<WholePlay>();
    this.setLastPlay(null);
    for (PlayOption p : this.getPlaySet()) {
      for (Stapel s : this.getDrawSet()) {
        if (p.getStapel() != s) {
          result.add(new WholePlay(p, s));
        }
      }
    }

    return result;
  }

  public void setIndex(int i) {
    this.myIndex = i;
  }

  public int getIndex() {
    return this.myIndex;
  }


  public List<WholePlay> alternativeActionsForExpansion() {

    List<WholePlay> result = new LinkedList<WholePlay>();

    List<PlayOption> options = new LinkedList<PlayOption>();

    this.expeditionen.forEach((col, ex) -> {

      List<AbstractCard> handOfColor = this.handKarten.stream()
          .filter(card -> card.getColor() == col).collect(Collectors.toList());

      if (!handOfColor.isEmpty()) {

        if (ex.isEmpty()) {
          AbstractCard min = handOfColor.stream().min(Comparator.naturalOrder()).get();
          options.add(new PlayOption(Stapel.toExpedition(min.getColor()), min));
        } else {

          List<AbstractCard> handsWithBiggerValue = handOfColor.stream()
              .filter(card -> card.getValue() >= ex.peek().getValue()).collect(Collectors.toList());

          if (!handsWithBiggerValue.isEmpty()) {
            AbstractCard min = handsWithBiggerValue.stream().min(Comparator.naturalOrder()).get();
            options.add(new PlayOption(Stapel.toExpedition(min.getColor()), min));
          }

        }

        if (!this.getEnemyExp().get(col).isEmpty()) {


          handOfColor.stream()
              .filter(card -> card.getValue() >= this.getEnemyExp().get(col).peek().getValue())
              .forEach(con -> options.add(new PlayOption(Stapel.toMiddle(con.getColor()), con)));

        } else {
          handOfColor.stream()
              .forEach(con -> options.add(new PlayOption(Stapel.toMiddle(con.getColor()), con)));
        }


      }



    });


    if (options.isEmpty()) {
      System.out.println("huge error");
    }
    for (PlayOption ablage : options) {
      for (Stapel stapel : this.getDrawSet()) {

        if (ablage.getStapel() != stapel) {
          result.add(new WholePlay(ablage, stapel));
        }
      }
    }


    return result;
  }



}
