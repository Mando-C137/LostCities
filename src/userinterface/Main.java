package userinterface;

import domain.main.Game;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

  private static Stage stage;

  @Override
  public void start(Stage arg0) throws Exception {

    Game theGame;
    theGame = Game.CHEATvsME();
    // (theGame.getPlayers().get(0))
    // .setStrategy(new CheatMctsStrategy(theGame.getPlayers().get(0), 5000));


    // (theGame.getPlayers().get(0)).setStrategy(new GitStrategy(theGame));

    GameScene scene = new GameScene(theGame);

    stage = arg0;
    stage.setTitle("LOST CITIES");
    stage.setScene(new Scene(scene));
    stage.show();

  }


  public static void main(String[] args) {
    launch(args);
  }

}
