package domain.main;

import domain.cards.Stapel;

/**
 * Ein WholePlay ist ein ganzer Spielzug: Er enthält den AblagePlay, also worauf ein Spieler eine
 * Karte ablegt und den Stapel von dem gezogen wird.
 * 
 * @author paulh
 *
 */
public class WholePlay {
  final PlayOption playOption;
  final Stapel toDraw;

  public WholePlay(PlayOption p, Stapel drawStapel) {
    this.playOption = p;
    this.toDraw = drawStapel;
  }

  /**
   * Getter für den AblagePlay
   * 
   * @return der AblagePlay
   */
  public PlayOption getOption() {
    return this.playOption;
  }

  /**
   * Getter für den Stapel, von dem gezogen wird.
   * 
   * @return der Stapel, von dem gezogen wird.
   */
  public Stapel getStapel() {
    return this.toDraw;
  }

  @Override
  public String toString() {
    return playOption.getCard() + "auf" + playOption.getStapel() + " ziehen von " + this.toDraw;
  }



  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }


    if (obj == null) {
      return false;
    }

    if (obj.getClass() != this.getClass()) {
      return false;
    }

    WholePlay cast = (WholePlay) obj;

    return this.playOption.equals(cast.getOption()) && this.toDraw == cast.toDraw;
  }



}
