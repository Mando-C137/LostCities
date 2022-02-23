package domain.cards;

/**
 * Das Enum color beschreibt die Farben der Karten und Expeditionen sowie die Ablgestaepel
 * 
 * @author paulh
 *
 */
public enum Color {

  YELLOW("YELLOW"), WHITE("WHITE"), BLUE("BLUE"), GREEN("GREEN"), RED("RED");

  Color(String s) {

  }

  /**
   * ein Array, das eine feste Ordnung der Colors festlegt : (YELLOW, WHITE, BLUE, GREEN, RED)
   */
  public static final Color[] orderedColors = {YELLOW, WHITE, BLUE, GREEN, RED};



}
