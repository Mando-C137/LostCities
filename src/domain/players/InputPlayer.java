package domain.players;

import java.util.List;
import java.util.Scanner;
import domain.Game;
import domain.cards.Stapel;

public class InputPlayer extends AbstractPlayer {

  public static Scanner scanner = new Scanner(System.in);

  public InputPlayer(Game game, String name) {
    super(game, name);
  }

  @Override
  public PlayOption play() {
    List<PlayOption> ls = this.getPlaySet();
    System.out.println("Playoptions for you are:  ");
    int i = 0;
    for (PlayOption p : ls) {
      System.out.println((i++) + "\t" + p);
    }
    int index = scanner.nextInt();
    return ls.get(index);
  }

  @Override
  public Stapel chooseStapel() {
    List<Stapel> ls = this.getDrawSet();
    System.out.println("DrawStapels for you are:  ");
    int i = 0;
    for (Stapel s : ls) {
      System.out.println((i++) + "\t" + s);
    }

    int index = scanner.nextInt();
    return ls.get(index);

  }

  @Override
  public boolean isAI() {
    // TODO Auto-generated method stub
    return false;
  }

}
