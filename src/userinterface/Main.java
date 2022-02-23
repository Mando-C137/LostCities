package userinterface;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

  private static Stage stage;

  @Override
  public void start(Stage arg0) throws Exception {
    stage = arg0;
    stage.setTitle("LOST CITIES");
    stage.setScene(new Scene(new GameScene()));
    stage.show();

  }



  public static void main(String[] args) {
    launch(args);
  }

}
