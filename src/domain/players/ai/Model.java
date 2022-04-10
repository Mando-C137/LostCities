package domain.players.ai;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Stack;
import java.util.stream.Collectors;
import domain.cards.AbstractCard;
import domain.cards.Color;
import domain.cards.NumberCard;
import domain.players.AiPlayer;

public class Model {

  private AiPlayer myPlayer;
  private List<AbstractCard> enemyHand;


  public Model(AiPlayer aiPlayer) {
    this.myPlayer = aiPlayer;
    enemyHand = new ArrayList<AbstractCard>();
  }


  public double calculateScore() {

    if (this.myPlayer.getRemainingCards() <= 7) {
      return this.myPlayer.getGame().calculateScore(myPlayer);
    }

    double sum = 0;
    for (Entry<Color, Stack<AbstractCard>> en : this.myPlayer.getExpeditionen().entrySet()) {


      Stack<AbstractCard> ex = en.getValue();
      if (ex.isEmpty()) {
        continue;
      }

      List<AbstractCard> allCardsFromColor = new ArrayList<AbstractCard>();

      for (int i = 2; i <= 10; i++) {
        allCardsFromColor.add(new NumberCard(en.getKey(), i));
      }
      for (AbstractCard card : ex) {
        sum += card.getValue();
      }



      List<AbstractCard> ls = null;
      if (!ex.isEmpty()) {
        ls = myPlayer.getHandKarten().stream().filter(
            handk -> handk.compareTo(ex.peek()) >= 0 && handk.getColor() == ex.peek().getColor())
            .collect(Collectors.toList());
      } else {
        ls = myPlayer.getHandKarten().stream().filter(handk -> handk.getColor() == en.getKey())
            .collect(Collectors.toList());
      }

      for (AbstractCard abs : ls) {
        sum += (0.8) + abs.getValue();
      }

      allCardsFromColor.removeAll(ls);
      allCardsFromColor.removeAll(ex);
      allCardsFromColor.removeAll(this.myPlayer.getEnemyExp().get(en.getKey()));
      allCardsFromColor = allCardsFromColor.stream().filter(pre -> {
        if (ex.isEmpty()) {
          return true;
        } else {
          return pre.compareTo(ex.firstElement()) >= 0;
        }
      }).collect(Collectors.toList());

      if (!this.myPlayer.getEnemyExp().get(en.getKey()).isEmpty()) {
        allCardsFromColor.remove(this.myPlayer.getEnemyExp().get(en.getKey()).firstElement());
      }

      for (AbstractCard a : allCardsFromColor) {
        sum += 0.5 * (this.myPlayer.getRemainingCards() / (this.myPlayer.getRemainingCards() + 8))
            * a.getValue();
      }

    }
    return sum;



  }

  public List<AbstractCard> getEnemyModel() {
    return this.enemyHand;
  }



}
