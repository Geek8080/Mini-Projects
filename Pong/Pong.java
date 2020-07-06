import java.io.File;
import java.util.Random;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Pong extends Application {

    // variables
    private static final int width = 800;
    private static final int height = 600;
    private static final int PLAYER_HEIGHT = 100;
    private static final int PLAYER_WIDTH = 15;
    private static final double BALL_RADIUS = 15.0d;
    private double ballXPosition = width / 2;
    private int ballXSpeed = 1;
    private double ballYPosition = height / 2;
    private int ballYSpeed = 1;
    private double playerOneXPosition = 0;
    private double playerOneYPosition = height / 2;
    private double playerTwoXPosition = width - PLAYER_WIDTH;
    private double playerTwoYPosition = height / 2;
    private int scoreP1 = 0;
    private int scoreP2 = 0;
    private boolean gameStarted;

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("P O N G");
        Canvas canvas = new Canvas(width, height);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        Timeline tl = new Timeline(new KeyFrame(Duration.millis(10), e -> run(gc)));
        tl.setCycleCount(Timeline.INDEFINITE);

        // mouse Control
        canvas.setOnMouseMoved(e -> playerOneYPosition = e.getY());
        canvas.setOnMouseClicked(e -> gameStarted = true);

        primaryStage.setScene(new Scene(new StackPane(canvas)));
        primaryStage.show();

        tl.play();

        main.playBackground("music.mid", 128);

        primaryStage.setOnCloseRequest(event -> System.exit(0));
    }

    private void run(GraphicsContext gc) {
        // set background color
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, width, height);

        // set font color
        gc.setFill(Color.WHITE);
        gc.setFont(Font.font(25));

        if (gameStarted) {
            // set ball movement
            ballXPosition += ballXSpeed;
            ballYPosition += ballYSpeed;

            // computer
            if (ballXPosition < width - width / 4) {
                playerTwoYPosition = ballYPosition - PLAYER_HEIGHT / 2;
            } else {
                playerTwoYPosition = (ballYPosition > playerTwoYPosition + PLAYER_HEIGHT / 2) ? playerTwoYPosition += 1
                        : playerTwoYPosition - 1;
            }

            // draw ball
            gc.fillOval(ballXPosition, ballYPosition, BALL_RADIUS, BALL_RADIUS);
        } else {
            // set the start text
            gc.setStroke(Color.WHITE);
            gc.setTextAlign(TextAlignment.CENTER);
            gc.strokeText("Click to Start", width / 2, height / 2);

            // reset the ball start position
            ballXPosition = width / 2;
            ballYPosition = height / 2;

            // reset speed and direction
            ballXSpeed = (new Random().nextInt(2) == 0) ? 1 : -1;
            ballYSpeed = (new Random().nextInt(2) == 0) ? 1 : -1;
        }

        // ball stays in canvas
        if (ballYPosition > height || ballYPosition < 0)
            ballYSpeed *= -1;

        // computer gets a point
        if (ballXPosition < playerOneXPosition - PLAYER_WIDTH) {
            scoreP2++;
            gameStarted = false;
        }

        // human gets a point
        if (ballXPosition > playerTwoXPosition + PLAYER_WIDTH) {
            scoreP1++;
            gameStarted = false;
        }

        // draw score
        gc.setFont(Font.font(26));
        gc.setStroke(Color.CORAL);
        gc.fillText(scoreP1 + "\t\t\t\t\t\t\t\t\t" + scoreP2, width / 2, 100);

        // keep balls in bounds
        if (((ballXPosition + BALL_RADIUS > playerTwoXPosition) && ballYPosition >= playerTwoYPosition
                && ballYPosition <= playerTwoYPosition + PLAYER_HEIGHT)
                || ((ballXPosition < playerOneXPosition + PLAYER_WIDTH) && ballYPosition >= playerOneYPosition
                        && ballYPosition <= playerOneYPosition + PLAYER_HEIGHT)) {
            ballYSpeed += 1 * Math.signum(ballYSpeed);
            ballXSpeed += 1 * Math.signum(ballXSpeed);
            ballXSpeed *= -1;
            ballYSpeed *= -1;
        }

        // draw players
        gc.fillRect(playerOneXPosition, playerOneYPosition, PLAYER_WIDTH, PLAYER_HEIGHT);
        gc.fillRect(playerTwoXPosition, playerTwoYPosition, PLAYER_WIDTH, PLAYER_HEIGHT);
    }

    public static void main(String[] args) {
        launch(args);
    }

}