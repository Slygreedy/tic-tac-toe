package TicTacToe;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class TicTacToeBoard extends Application {

    // launch is a part of javafx - same as start below
    public static void main (String[] args) {
        launch();
    }

    //instantiating logic below PLUS calling the methods at the right time (when mouse
    //button is clicked) - NB I also need to turn string into 2D array
    private TicTacToeLogic logic = new TicTacToeLogic();

    //flag to allow playable and X to start
    private boolean playable = true;
    private boolean turnX = true;
    private Tile[][] board = new Tile[3][3];
    private List<Combo> combos = new ArrayList<>();
    private Pane root = new Pane ();

    //this creates 3 x 3 tiles
    private Parent createContent() {
        root.setPrefSize(600, 600);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Tile tile = new Tile();
                tile.setTranslateX(j * 200);
                tile.setTranslateY(i * 200);

                root.getChildren().add(tile);

                board [j][i] = tile;
            }
        }

        for (int y = 0; y < 3; y++) {
            combos.add(new Combo(board[0][y], board[1][y], board[2][y]));
        }

        for(int x = 0; x < 3; x++) {
            combos.add(new Combo(board[x][0], board[x][1], board[x][2]));
        }

        combos.add(new Combo(board[0][0], board[1][1], board[2][2]));
        combos.add(new Combo(board[2][0], board[1][1], board[0][2]));

        return root;
    }

    private void checkState() {
        for (Combo combo : combos) {
            if (combo.isComplete()) {
                playable = false;
                playWinAnimation(combo);
                break;
            }
        }
    }

    private void playWinAnimation(Combo combo) {
        Line line = new Line();
        line.setStartX(combo.tiles[0].getCentreX());
        line.setStartY(combo.tiles[0].getCentreY());
        line.setEndX(combo.tiles[0].getCentreX());
        line.setEndY(combo.tiles[0].getCentreY());

        root.getChildren().add(line);

        line.setStrokeWidth(10);

        Timeline timeline = new Timeline();
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1),
                new KeyValue(line.endXProperty(), combo.tiles[2].getCentreX()),
                new KeyValue(line.endYProperty(), combo.tiles[2].getCentreY())));
        timeline.play();
    }

    //can and should I move these classes out and into their own?
    private class Combo {
        private Tile[] tiles;
        public Combo(Tile... tiles) {
            this.tiles = tiles;
        }

        public boolean isComplete() {
            if (tiles[0].getValue().isEmpty())
                return false;

            return tiles[0].getValue().equals(tiles[1].getValue())
                    && tiles[0].getValue().equals(tiles[2].getValue());
        }
    }

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
            text.setFill(Color.BLACK);

            setAlignment(Pos.CENTER);
            //it was adding text below that enabled an X or a ) to be displayed on board
            getChildren().addAll(border, text);

            setOnMouseClicked(event -> {
                if (!playable)
                    return;

                if (event.getButton() == MouseButton.PRIMARY) {
                    if (!turnX)
                        return;

                    drawX();
                    turnX = false;
                    checkState();
                }
                else if (event.getButton() == MouseButton.SECONDARY) {
                    if (turnX)
                        return;

                    drawO();
                    turnX = true;
                    checkState();
                }
            });
        }

        public double getCentreX() {
            return getTranslateX() + 100;
        }

        public double getCentreY() {
            return getTranslateY() + 100;
        }

        public String getValue() {
            return text.getText();
        }

        private void drawX() {
            text.setText("X");
        }

        private void drawO() {
            text.setText("O");
        }
    }

}
