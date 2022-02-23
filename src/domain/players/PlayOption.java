package domain.players;

import domain.cards.AbstractCard;
import domain.cards.Stapel;

/**
 * Repräsentation eines Spielzuges
 *
 */
public class PlayOption {

  /**
   * Die zu spielende Karte
   */
  private final AbstractCard abstractCard;

  /**
   * Der Stapel, auf der die Karte gelegt werden soll.
   */
  private final Stapel stapel;

  /**
   * lediglich setzen der Felder
   * 
   * @param stapel
   * @param abstractCard
   */
  public PlayOption(Stapel stapel, AbstractCard abstractCard) {
    this.stapel = stapel;
    this.abstractCard = abstractCard;
  }


  @Override
  public String toString() {
    return "Play: " + this.abstractCard + " auf " + this.stapel;
  }

  /**
   * Getter
   * 
   * @return
   */
  public Stapel getStapel() {
    return this.stapel;
  }

  /**
   * Getter
   * 
   * @return
   */
  public AbstractCard getCard() {
    return this.abstractCard;
  }

}
