package domain.exception;

import domain.cards.AbstractCard;
import domain.cards.Stapel;
import domain.players.AbstractPlayer;

public class GameException extends RuntimeException {


  /**
   * 
   */
  private static final long serialVersionUID = 3445668052383879602L;

  public GameException(String s) {
    super(s);
  }

  /**
   * Exeption für verbotene Ablagelays
   * 
   * @author paulh
   *
   */
  public static class IllegalPlayException extends GameException {
    /**
     * 
     */
    private static final long serialVersionUID = 3072777512932009861L;

    public IllegalPlayException(AbstractCard a, Stapel s) {
      super(a + " darf nicht auf " + s + "gelegt werden");
    }

  }

  /**
   * Exception für das Ablegen einer Karte, die man gar nicht besitzt
   * 
   * @author paulh
   *
   */
  public static class DoNotOwnException extends GameException {
    /**
     * 
     */
    private static final long serialVersionUID = 5558498355538770728L;

    public DoNotOwnException(AbstractPlayer s, AbstractCard a) {
      super("Spieler " + s + " hat die Karte " + a + "gar nicht");
    }
  }

  /**
   * Exxeption für das Ziehen von einem bereits leeren Stapel
   * 
   * @author paulh
   *
   */
  public static class EmptyStapelException extends GameException {
    /**
     * 
     */
    private static final long serialVersionUID = 4480112367023115554L;

    public EmptyStapelException(Stapel s) {
      super("Stapel" + s + "ist bereits leer");
    }
  }

  /**
   * Exception für das Ziehen der selben Karte, die gerade schon abgelegt worden war.
   * 
   * @author paulh
   *
   */
  public static class SameCardException extends GameException {
    /**
     * 
     */
    private static final long serialVersionUID = -6358406482240299358L;

    public SameCardException() {
      super("Man darf nicht die selbe karten ziehen, die man gerade ablegte");
    }
  }


}


