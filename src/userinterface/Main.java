package userinterface;

import domain.main.Game;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

  private static Stage stage;

  @Override
  public void start(Stage arg0) throws Exception {

    Game game;

    game = Game.SimpleVsMe();

    // game = Game.ISMCTSvsME();

    // game = Game.CHEATvsME();

    // game = game.RandomvsMe();

    // game = PimcStrategy.PIMCvsHuman();
    game = Game.demoGame();
    GameScene scene = new GameScene(game);

    stage = arg0;
    stage.setTitle("LOST CITIES");
    stage.setScene(new Scene(scene));
    stage.show();

  }


  public static void main(String[] args) {
    launch(args);
  }

}
