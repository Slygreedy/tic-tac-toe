package TicTacToe;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class TicTacToeBoard extends Application {

    // 1. how do I join this up with the logic part of the program?

    // 2. what is this launching?
    public static void main (String[] args) {
        launch();
    }

    private boolean turnX = true;

    //this creates 3 x 3 tiles
    private Parent createContent() {
        Pane root = new Pane ();
        root.setPrefSize(600, 600);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Tile tile = new Tile();
                tile.setTranslateX(j * 200);
                tile.setTranslateY(i * 200);

                root.getChildren().add(tile);
            }
        }

        return root;
    }

    // 4. not sure what this is required for?
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setScene(new Scene(createContent()));
        primaryStage.show();
    }

    //creates an individual tile
    private class Tile extends StackPane {
        private Text text = new Text();

        public Tile() {
            Rectangle border = new Rectangle(200, 200);
            border.setFill(null);
            border.setStroke(Color.BLACK);

            text.setFont(Font.font(72));

            setAlignment(Pos.CENTER);
            getChildren().addAll(border);

            // 3. why are mouse clicks not working?
            setOnMouseClicked(event -> {
                if (event.getButton() == MouseButton.PRIMARY) {
                    if (!turnX)
                        return;

                    drawX();
                    turnX = false;
                }
                else if (event.getButton() == MouseButton.SECONDARY) {
                    if (turnX)
                        return;

                    drawO();
                    turnX = true;
                }
            });
        }

        private void drawX() {
            text.setText("X");
        }

        private void drawO() {
            text.setText("O");
        }
    }

}
