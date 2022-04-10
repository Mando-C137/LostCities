package domain.players.ai.stats;

import java.util.HashMap;
import java.util.Map;
import domain.cards.AbstractCard;

public class StatisticalCard {

  AbstractCard a;

  protected double probToGet;

  protected Status status;

  public static Map<AbstractCard, StatisticalCard> initStats() {

    Map<AbstractCard, StatisticalCard> res = new HashMap<AbstractCard, StatisticalCard>();

    Statistics.allCards().forEach(c -> res.put(c, new StatisticalCard(c)));
    return res;

  }

  public StatisticalCard(AbstractCard a) {
    this.a = a;
    this.status = Status.Unseen;
    this.probToGet = 0.5;
  }

  public double PropabilityToGet() {
    return this.probToGet;
  }

  @Override
  public String toString() {

    return this.a.toString() + " | " + this.status;
  }

  public Status getStatus() {
    return this.status;
  }



}
