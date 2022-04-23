package domain.players.ai.stats;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Stack;
import java.util.stream.IntStream;
import domain.cards.AbstractCard;
import domain.cards.Color;
import domain.cards.NumberCard;
import domain.cards.WettCard;
import domain.players.AbstractPlayer;

public class Statistics {

  AbstractPlayer p;

  Map<AbstractCard, StatisticalCard> probCards;

  public Statistics(AbstractPlayer p) {
    this.p = p;
    probCards = StatisticalCard.initStats();
    init();
  }

  public Statistics(Statistics s, AbstractPlayer p) {
    this.p = p;
    this.probCards = StatisticalCard.initStats();
    for (Entry<AbstractCard, StatisticalCard> en : s.probCards.entrySet()) {
      this.probCards.put(en.getKey(), en.getValue());
    }
  }

  private void init() {
    this.p.getHandKarten().forEach(c -> this.onMyHand(c));
  }

  /**
   * Gegner legt von seiner Hand auf die Expedition
   * 
   * @param a
   */
  public void onEnemyExp(AbstractCard a) {

    StatisticalCard s = probCards.get(a);
    s.probToGet = 0;
    s.status = Status.EnemyExpedition;
  }

  /**
   * Ich nehme etwas auf in meine Hand
   * 
   * @param a
   */
  private void onMyHand(AbstractCard a) {
    StatisticalCard s = probCards.get(a);
    s.probToGet = 1;
    s.status = Status.MyHand;
  }

  /**
   * wenn der Gegner von dem Ablagestapel etwas zieht
   * 
   * @param a
   */
  public void EnemyDrawsFromAblage(AbstractCard a) {
    StatisticalCard s = probCards.get(a);
    s.status = Status.EnemyHand;
  }


  /**
   * Ich lege die Karte auf meine Expedition ab.
   * 
   * @param a
   */
  public void onMyExpedition(AbstractCard a) {
    StatisticalCard s = probCards.get(a);
    s.status = Status.MyExpedition;
  }

  /**
   * Einer der Spieler legt die Karte auf einen AblageStapel
   * 
   * @param a
   */
  public void onAblage(AbstractCard a) {
    StatisticalCard s = probCards.get(a);
    s.status = Status.Ablage;
  }

  public static List<AbstractCard> allCards() {
    ArrayList<AbstractCard> allCards = new ArrayList<AbstractCard>();
    for (Color c : Color.values()) {
      IntStream.range(2, 11).forEach(num -> allCards.add(new NumberCard(c, num)));
      IntStream.range(1, 4).forEach(num -> allCards.add(new WettCard(c)));

    }
    return allCards;
  }



  public double expectedValueOfUnstartedExpedition(Color c) {

    double sum = 0;
    for (AbstractCard card : this.p.getHandKarten()) {
      if (card.getColor().equals(c) && card.isNumber()) {
        sum += ((NumberCard) card).getValue();
      }
    }

    for (StatisticalCard card : this.probCards.values()) {
      if (card.status.equals(Status.Unseen) && card.a.getColor().equals(c) && card.a.isNumber()) {
        sum += (double) (this.p.getRemainingCards()) / ((this.p.getRemainingCards() + 8) * 2)
            * ((NumberCard) card.a).getValue();
      }
    }

    return sum;

  }

  public int playableCards(Color c) {
    int sum = 0;
    for (AbstractCard card : this.p.getHandKarten()) {
      if (card.isNumber() && card.getColor().equals(c)) {
        sum++;
      }
    }

    return sum;
  }


  public int currentScoreOfExp(Color c) {

    Stack<AbstractCard> ls = this.p.getExpeditionen().get(c);

    if (ls.isEmpty()) {
      return 0;
    }

    else {

      List<AbstractCard> allToMove = new LinkedList<AbstractCard>(ls);
      this.p.getHandKarten().forEach(pa -> {

        if (pa.compareTo(ls.peek()) >= 0 && pa.getColor().equals(c)) {
          allToMove.add(pa);
        }

      });

      int sum = 0;
      for (AbstractCard abs : allToMove) {
        if (abs.isNumber()) {
          sum += ((NumberCard) abs).getValue();
        }
      }
      return sum;

    }


  }



}
