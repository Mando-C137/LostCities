package domain.players;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;
import java.util.stream.Stream;
import domain.Game;
import domain.cards.AbstractCard;
import domain.cards.Color;
import domain.cards.NumberCard;
import domain.cards.Stapel;

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

  private Game game;

  private HashMap<Color, Stack<AbstractCard>> expeditionen;

  private LinkedList<AbstractCard> handKarten;

  protected Stapel lastPlay;

  public AbstractPlayer(Game game, String name) {
    this.game = game;
    this.name = name;
    this.lastPlay = null;
    initExpeditionen();
    initHandKarten();
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
   * Initialisierung der Handkarten: Der Spieler zieht acht Karten von dem Nachziehstapel.
   */
  private void initHandKarten() {
    this.handKarten = new LinkedList<AbstractCard>();


  }


  /**
   * Auswählen des Spielzuges, also dem Ablegen einer Karte.
   * 
   * @return die Playoption, die der Spieler spielet.
   */
  public abstract PlayOption play();

  public abstract Stapel chooseStapel();

  // /**
  // * Ziehen einer Karte. Momentan wird nur vom Nachziehstapel gezogen TODO
  // */
  // public void drawCard() {
  // Optional<AbstractCard> opt = game.returnCardFromNachziehStapel();
  //
  // if (opt.isPresent()) {
  // this.handKarten.add(opt.get());
  // } else {
  // System.out.println("Problem beim Ziehen der Karte");
  // }
  //
  // }
  //
  // public void drawCard(Stapel stapel) {
  // Optional<AbstractCard> opt = game.returnCard(stapel);
  //
  // if (opt.isPresent()) {
  // this.handKarten.add(opt.get());
  // } else {
  // System.out.println("Problem beim Ziehen der Karte");
  // }
  //
  // }

  public LinkedList<Stapel> getDrawSet() {

    LinkedList<Stapel> result = new LinkedList<Stapel>();


    for (Color c : Color.orderedColors) {
      if (this.game.getAblageStapel(c).size() > 0) {
        result.add(Stapel.toMiddle(c));
      }

    }

    if (this.lastPlay != null) {
      result.remove(this.lastPlay);
    }


    result.add(Stapel.NACHZIEHSTAPEL);
    this.lastPlay = null;
    return result;
  }

  // /**
  // * Getter für die game instanz
  // *
  // * @return
  // */
  // public Game getGame() {
  // return this.game;
  // }

  /**
   * Getter für die expeditionen des Spielers. Sollte eigentlich unmodifiable sein.
   * 
   * @return
   */
  public HashMap<Color, Stack<AbstractCard>> getExpeditionen() {
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
  public LinkedList<PlayOption> getPlaySet() {

    LinkedList<PlayOption> result = new LinkedList<PlayOption>();

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
        if (card.isNumber()) {
          NumberCard currentCard = (NumberCard) card;
          AbstractCard peek = this.getExpeditionen().get(card.getColor()).peek();
          /*
           * Falls beide eine Nummer sind, muss man eine größere Zahl haben
           */
          if (peek.isNumber()) {
            NumberCard num = (NumberCard) peek;
            if (num.getValue() < currentCard.getValue()) {
              result.add(new PlayOption(Stapel.toExpedition(card.getColor()), card));
            }
          } else {
            /*
             * Auf eine Wettkarte kann man eine Nummerkarte legen
             */
            result.add(new PlayOption(Stapel.toExpedition(card.getColor()), card));
          }
        } else {
          /*
           * Man kann eine Wettkarte nur als erstes oder auf andere Wettkarten legen
           */
          if (!this.getExpeditionen().get(card.getColor()).peek().isNumber()) {
            result.add(new PlayOption(Stapel.toExpedition(card.getColor()), card));
          }

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
      }
      res.append("\t");
    }

    return res.toString();
  }

  public void setLastPlay(Stapel abs) {
    if (abs != null)
      this.lastPlay = abs;
  }



}
