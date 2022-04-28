package domain.strategies;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import domain.cards.AbstractCard;
import domain.cards.Color;
import domain.cards.Stapel;
import domain.main.PlayOption;
import domain.players.AiPlayer;

public class SimulationStrategy implements PlayStrategy {

  private AiPlayer player;

  private Stapel lastPlay;

  public SimulationStrategy(AiPlayer player) {
    this.player = player;
  }

  private Optional<AbstractCard> optionalDirectSuccessor() {

    List<AbstractCard> ls = new LinkedList<AbstractCard>();

    player.getExpeditionen().forEach((c, e) -> {

      if (!e.isEmpty()) {

        Optional<AbstractCard> temp = player.getHandKarten().stream()
            .filter(card -> card.getColor() == c && card.compareTo(e.peek()) == 1)
            .max(Comparator.comparing(com -> com.getValue()));

        temp.ifPresent(val -> ls.add(val));

      }

    });

    if (ls.isEmpty()) {
      return Optional.ofNullable(null);
    }

    return Optional.ofNullable(ls.stream().max(Comparator.naturalOrder()).get());

  }



  @Override
  public PlayOption choosePlay() {

    PlayOption res = null;

    // Optional<AbstractCard> opt = optionalDirectSuccessor();
    //
    // if (opt.isPresent()) {
    // res = new PlayOption(Stapel.toExpedition(opt.get().getColor()), opt.get());
    // this.lastPlay = res.getStapel();
    // return res;
    // }

    List<PlayOption> goodPlays = new LinkedList<PlayOption>();
    /*
     * machen dass er nix gutes für den gegner ablegt
     */

    this.player.getHandKarten().forEach(handkarte -> {

      if (this.player.getEnemyExp().get(handkarte.getColor()).isEmpty()) {
        goodPlays.add(new PlayOption(Stapel.toMiddle(handkarte.getColor()), handkarte));
      }
      /*
       * wenn meine karte nicht mehr vom gegner verwendet werden kann
       */
      else if (handkarte
          .compareTo(this.player.getEnemyExp().get(handkarte.getColor()).peek()) <= 0) {
        goodPlays.add(new PlayOption(Stapel.toMiddle(handkarte.getColor()), handkarte));
      }

      /*
       * wenn expedition leer, dann nur mit karten mit kleinerem wert als 9 oder 10 starten
       */
      if (this.player.getExpeditionen().get(handkarte.getColor()).isEmpty()
          && handkarte.getValue() <= 9) {
        goodPlays.add(new PlayOption(Stapel.toExpedition(handkarte.getColor()), handkarte));
      }

      /*
       * wenn expedition nicht leer, dann alles mögliche möglich
       */
      if (!this.player.getExpeditionen().get(handkarte.getColor()).isEmpty()) {
        if (handkarte
            .compareTo(this.player.getExpeditionen().get(handkarte.getColor()).peek()) >= 0) {
          goodPlays.add(new PlayOption(Stapel.toExpedition(handkarte.getColor()), handkarte));
        }
      }
    });

    if (!goodPlays.isEmpty()) {
      return goodPlays.get((int) (Math.random() * goodPlays.size()));
    }



    res = new RandomStrategy(player).choosePlay();
    this.lastPlay = res.getStapel();
    return res;
  }

  @Override
  public Stapel chooseStapel() {

    if (this.player.getRemainingCards() == 1) {
      if (this.player.getGame().calculateWinnerIndex(player.getIndex()) == player.getIndex()) {
        return Stapel.NACHZIEHSTAPEL;
      }
    }

    List<Stapel> resultSet = new LinkedList<Stapel>();

    for (Color col : Color.values()) {
      if (!this.player.getGame().getAblageStapel(col).isEmpty()
          && !this.player.getExpeditionen().get(col).isEmpty()
          && this.lastPlay != Stapel.toMiddle(col)) {

        AbstractCard currentExpedionPeek = this.player.getExpeditionen().get(col).peek();
        AbstractCard currentAblagePeek = this.player.getGame().getAblageStapel(col).get(0);

        if (currentExpedionPeek.compareTo(currentAblagePeek) > 0) {
          resultSet.add(Stapel.toMiddle(col));
        }

      }
    }

    if (!resultSet.isEmpty()) {
      return resultSet.get((int) (Math.random() * resultSet.size()));
    }



    return Stapel.NACHZIEHSTAPEL;
    // if ((double) Math.random() < 0.5) {
    // return Stapel.NACHZIEHSTAPEL;
    // }
    //
    // List<Stapel> result = new LinkedList<Stapel>();
    // for (Color c : Color.values()) {
    // if (!this.player.getGame().getAblageStapel(c).isEmpty()) {
    // result.add(Stapel.toMiddle(c));
    // }
    // }
    //
    // result.remove(this.lastPlay);
    //
    // if (result.isEmpty()) {
    // return Stapel.NACHZIEHSTAPEL;
    // }
    //
    // int randomIndex = (int) (Math.random() * result.size());
    // Stapel toDraw = result.get(randomIndex);
    // return toDraw;
  }

  @Override
  public StrategyName getStrategyName() {
    // TODO Auto-generated method stub
    return StrategyName.SIMULATE;
  }

}
