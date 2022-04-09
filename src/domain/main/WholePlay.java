package domain.main;

import domain.cards.Stapel;

public class WholePlay {
  PlayOption playOption;
  Stapel s;

  public WholePlay(PlayOption p, Stapel drawStapel) {
    this.playOption = p;
    this.s = drawStapel;
  }

  public PlayOption getOption() {
    return this.playOption;
  }

  public Stapel getStapel() {
    return this.s;
  }

  @Override
  public String toString() {
    return playOption.getCard() + "auf" + playOption.getStapel() + " ziehen von " + this.s;
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

    return this.playOption.equals(cast.getOption()) && this.s == cast.s;
  }



}
