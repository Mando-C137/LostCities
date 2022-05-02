package experiments;

import java.util.ArrayList;
import java.util.List;

public class ExperimentInfo {


  public int diff;
  public int wins;
  public int losses;
  public int draws;
  public double start;
  public int numberOfGames;

  public List<String> results;

  public ExperimentInfo() {
    this.start = System.currentTimeMillis();
    this.diff = 0;
    this.wins = 0;
    this.losses = 0;
    this.draws = 0;
    this.numberOfGames = 0;
    this.results = new ArrayList<String>();
  }

  public void printInfo() {
    System.out.println("------------");
    System.out.println("diff = " + this.diff);
    System.out.println("Wins = " + this.wins);
    System.out.println("losses = " + this.losses);
    System.out.println("Draws = " + this.draws);
    System.out.println("OverallGames  " + this.numberOfGames);

    for (String res : this.results) {
      System.out.println(res);
    }


    System.out
        .println("Overalltime " + (double) (System.currentTimeMillis() - this.start) / 1000 + "s");

    System.out.println("------------");
  }

  public void applyGame(int diff2) {
    this.numberOfGames++;
    this.diff += diff2;
    if (diff2 > 0) {
      this.wins++;
    } else if (diff2 == 0) {
      this.losses++;
    } else {
      this.draws++;
    }
  }

  public void addGame(String calculateScores) {
    this.results.add(calculateScores);

  }



}
