import java.util.LinkedList;
import java.util.Random;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

public class SnakeGame extends Application {

    private static final int WIDTH = 900;
    private static final int HEIGHT = 600;
    private static final int HEAD_WIDTH = 30;
    private static final int HEAD_HEIGHT = 30;

    private int move = Direction.RIGHT.ordinal();
    private static int speed = 30;

    private int posX = WIDTH / 2 - HEAD_WIDTH / 2;
    private int posY = HEIGHT / 2 - HEAD_HEIGHT / 2;

    private static boolean gameOver = false;

    private static int foodX;
    private static int foodY;

    private int score = 0;
    public Color foodColor;

    public enum Direction {
        UP, RIGHT, DOWN, LEFT
    }

    @Override
    public void start(Stage stage) throws Exception {
        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.grayRgb(20));
        gc.fillRect(0, 0, WIDTH, HEIGHT);
        stage.setScene(new Scene(new StackPane(canvas)));
        stage.getScene().setFill(Color.DARKSEAGREEN);
        stage.setTitle("S N A K E");
        canvas.setEffect(new DropShadow());
        gc.setFill(Color.GREENYELLOW);
        gc.fillRect(posX, posY, HEAD_WIDTH, HEAD_HEIGHT);
        Snake snake = new Snake();
        stage.show();

        snake.newFood();

        Timeline tl = new Timeline(new KeyFrame(Duration.millis(250), e -> snake.update(gc)));
        tl.setCycleCount(Timeline.INDEFINITE);

        stage.getScene().setOnKeyPressed(e -> {
            KeyCode k = e.getCode();
            if (k == KeyCode.W || k == KeyCode.UP) {
                move = Direction.UP.ordinal();
            } else if (k == KeyCode.D || k == KeyCode.RIGHT) {
                move = Direction.RIGHT.ordinal();
            } else if (k == KeyCode.S || k == KeyCode.DOWN) {
                move = Direction.DOWN.ordinal();
            } else if (k == KeyCode.A || k == KeyCode.LEFT) {
                move = Direction.LEFT.ordinal();
            }
            // System.out.println("ACTION");
        });

        stage.getScene().setOnMouseClicked(e -> {
            if (gameOver) {
                reset(snake);
                snake.newFood();
                gameOver = false;
            }
        });

        tl.play();
    }

    private void reset(Snake snake) {
        snake = new Snake();
    }

    public class Snake {
        private LinkedList<Point> list = new LinkedList<>();
        private int length = 3;

        public Snake() {
            length = 3;
            list = new LinkedList<>();
            int initX = WIDTH / 2 - HEAD_WIDTH / 2 - 60;
            int initY = HEIGHT / 2 - HEAD_HEIGHT / 2 - 60;
            list.add(new Point(initX, initY, Direction.RIGHT));
            list.addLast(new Point(initX + 30, initY, Direction.RIGHT));
            list.addLast(new Point(initX + 60, initY, Direction.RIGHT));
        }

        public void newFood() {
            foodX = new Random().nextInt(29) * 30 + 15;
            foodY = new Random().nextInt(19) * 30 + 15;
            foodColor = Color.rgb(new Random().nextInt(255), new Random().nextInt(255), new Random().nextInt(255));
        }

        public void drawFood(GraphicsContext gc) {
            gc.setFill(foodColor);
            gc.fillOval(foodX, foodY, 30, 30);
            // System.out.println(foodX + " " + foodY);
        }

        public void update(GraphicsContext gc) {

            if (Math.abs(foodX - list.getLast().x) <= 15 && Math.abs(foodY - list.getLast().y) <= 15) {
                // System.out.println("G U L P !!!!!");
                newFood();
                list.addFirst(new Point(-1, -1, Direction.RIGHT));
                length++;
                score++;
            }

            if (gameOver) {
                gc.setFill(Color.RED);
                gc.setFont(new Font("", 50));
                gc.fillText("GAME OVER", 100, 250);
                return;
            }
            gc.setFill(Color.grayRgb(20));
            gc.fillRect(0, 0, WIDTH, HEIGHT);
            Direction d = Direction.RIGHT;

            for (int i = 0; i < length - 1; i++) {
                Point front = list.get(i + 1);
                list.set(i, new Point(front.x, front.y, front.direction));
            }

            Point head = list.get(length - 1);
            if (move == Direction.UP.ordinal()) {
                head.direction = Direction.UP;
                head.y -= speed;
            } else if (move == Direction.DOWN.ordinal()) {
                head.direction = Direction.DOWN;
                head.y += speed;
            } else if (move == Direction.RIGHT.ordinal()) {
                head.direction = Direction.RIGHT;
                head.x += speed;
            } else if (move == Direction.LEFT.ordinal()) {
                head.direction = Direction.LEFT;
                head.x -= speed;
            }
            list.set(length - 1, head);

            for (int i = 0; i < length - 1; i++) {
                if (list.get(length - 1).x == list.get(i).x && list.get(length - 1).y == list.get(i).y) {
                    gameOver = true;
                }
            }

            render(gc);
        }

        private void render(GraphicsContext gc) {
            drawFood(gc);
            gc.setFill(Color.GREENYELLOW);
            // System.out.println(list.getLast());
            for (int i = 0; i < length; i++) {
                Point p = list.get(i);
                if ((p.x < 0) || (p.x > WIDTH) || (p.y < 0) || (p.y > HEIGHT)) {
                    gameOver = true;
                    return;
                }
                gc.fillRect(p.x, p.y, HEAD_WIDTH, HEAD_HEIGHT);
            }
            gc.setFont(Font.font(26));
            gc.setStroke(Color.CORAL);
            gc.strokeText("Score: " + score, 30, 30);
        }
    }

    public class Point {
        int x;
        int y;
        Direction direction;

        public Point(int x, int y, Direction direction) {
            this.x = x;
            this.y = y;
            this.direction = direction;
        }

        public String toString() {
            return "x: " + x + " y: " + y + " Direction: " + direction;
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}