package domain.strategies;

import domain.cards.AbstractCard;
import domain.cards.Stapel;
import domain.main.PlayOption;
import domain.players.AiPlayer;

public class SecondRandomStrategy implements PlayStrategy {

  private AiPlayer ai;

  private Stapel lastPlay;

  public static int count = 0;

  public SecondRandomStrategy(AiPlayer ai) {
    this.ai = ai;
  }

  @Override
  public PlayOption choosePlay() {

    PlayOption result = null;

    int randomIndex = (int) (Math.random() * this.ai.getHandKarten().size());

    AbstractCard card = this.ai.getHandKarten().get(randomIndex);

    if (this.ai.getExpeditionen().get(card.getColor()).isEmpty()) {

      result = new PlayOption(Stapel.toExpedition(card.getColor()), card);
      count++;
    } else if (card.compareTo(this.ai.getExpeditionen().get(card.getColor()).peek()) >= 0) {
      count++;
      result = new PlayOption(Stapel.toExpedition(card.getColor()), card);
    } else {
      result = new PlayOption(Stapel.toMiddle(card.getColor()), card);
    }

    lastPlay = result.getStapel();
    return result;
  }

  @Override
  public Stapel chooseStapel() {
    if (Math.random() > 0.5) {
      return Stapel.NACHZIEHSTAPEL;
    }

    int randomIndex = (int) (Math.random() * Stapel.alleZiehStapel.length);

    Stapel answer = Stapel.alleZiehStapel[randomIndex];

    /*
     * Stapel ist nachziehstapel
     */
    if (answer.getColor() == null) {
      return Stapel.NACHZIEHSTAPEL;
    }
    /*
     * Stapel ist leer oder war play
     */

    else if (this.ai.getGame().getAblageStapel(answer.getColor()).isEmpty()
        || this.lastPlay == answer) {
      return Stapel.NACHZIEHSTAPEL;
    }
    /*
     * stapel ist ok
     */
    else {
      return answer;
    }



  }

  @Override
  public StrategyName getStrategyName() {

    return StrategyName.RANDOM;
  }



}
