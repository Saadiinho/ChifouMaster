package application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.Random;
import java.util.Stack;

public class MazeGame extends Application {

    private static final int SIZE = 15; // Taille du labyrinthe (10x10 dans cet exemple)
    private static final int CELL_SIZE = 50; // Taille d'une case en pixels
    private static final Color WALL_COLOR = Color.BLACK;
    private static final Color PATH_COLOR = Color.WHITE;
    private static final Color VICTORY_COLOR = Color.GREEN;

    private char[][] maze;
    private Random random;
    private int playerX;
    private int playerY;
    private int victoryX;
    private int victoryY;

    public MazeGame() {
        maze = new char[SIZE][SIZE];
        random = new Random();

        // Initialise toutes les cases du labyrinthe avec des murs
        for (char[] row : maze) {
            for (int i = 0; i < row.length; i++) {
                row[i] = '#';
            }
        }
    }

    public void generateMaze() {
        int startX = 0; // Position de départ en X
        int startY = 0; // Position de départ en Y

        Stack<Integer> stack = new Stack<>();
        stack.push(startX);
        stack.push(startY);
        maze[startY][startX] = ' ';

        while (!stack.isEmpty()) {
            int y = stack.pop();
            int x = stack.pop();

            // Liste les voisins accessibles
            int[] neighbors = getNeighbors(x, y);
            if (neighbors.length > 0) {
                stack.push(x);
                stack.push(y);

                int randomIndex = random.nextInt(neighbors.length);
                int nx = neighbors[randomIndex] % SIZE;
                int ny = neighbors[randomIndex] / SIZE;

                maze[ny][nx] = ' ';
                maze[y + (ny - y) / 2][x + (nx - x) / 2] = ' ';

                stack.push(nx);
                stack.push(ny);
            }
        }

        // Position de la case de victoire
        victoryX = SIZE - 1;
        victoryY = SIZE - 1;
        maze[victoryY][victoryX] = 'V';
    }

    private int[] getNeighbors(int x, int y) {
        int[] dx = {0, 0, -1, 1}; // Décalage en X pour les voisins
        int[] dy = {-1, 1, 0, 0}; // Décalage en Y pour les voisins
        int[] neighbors = new int[4]; // Tableau pour stocker les voisins accessibles
        int count = 0; // Compteur de voisins accessibles

        // Parcourt les 4 directions autour de la position actuelle
        for (int i = 0; i < 4; i++) {
            int nx = x + dx[i] * 2; // Voisin en X
            int ny = y + dy[i] * 2; // Voisin en Y

            // Vérifie si le voisin est dans les limites du labyrinthe et s'il est un mur
            if (nx >= 0 && nx < SIZE && ny >= 0 && ny < SIZE && maze[ny][nx] == '#') {
                neighbors[count++] = ny * SIZE + nx; // Stocke l'indice du voisin
            }
        }

        // Redimensionne le tableau des voisins pour correspondre au nombre réel de voisins accessibles
        return java.util.Arrays.copyOf(neighbors, count);
    }

    public boolean isMoveAllowed(int x, int y) {
        // Vérifie si le mouvement est dans les limites du labyrinthe
        if (x >= 0 && x < SIZE && y >= 0 && y < SIZE) {
            // Vérifie si la case est un chemin
            return maze[y][x] == ' ';
        }
        return false;
    }

    public void drawMaze(GridPane gridPane) {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                Rectangle cell = new Rectangle(CELL_SIZE, CELL_SIZE);
                if (maze[row][col] == 'V') {
                    cell.setFill(VICTORY_COLOR);
                } else {
                    cell.setFill(maze[row][col] == '#' ? WALL_COLOR : PATH_COLOR);
                }
                gridPane.add(cell, col, row);
            }
        }
    }

    @Override
    public void start(Stage primaryStage) {
        GridPane gridPane = new GridPane();
        generateMaze();
        drawMaze(gridPane);

        // Position initiale du joueur
        playerX = 0;
        playerY = 0;

        // Déplacement du joueur avec les touches de direction
        gridPane.setOnKeyPressed(e -> {
            int dx = 0;
            int dy = 0;

            switch (e.getCode()) {
                case UP:
                    dy = -1;
                    break;
                case DOWN:
                    dy = 1;
                    break;
                case LEFT:
                    dx = -1;
                    break;
                case RIGHT:
                    dx = 1;
                    break;
                default:
                    return;
            }

            // Vérifie si le mouvement est autorisé
            if (isMoveAllowed(playerX + dx, playerY + dy)) {
                // Met à jour la position du joueur
                playerX += dx;
                playerY += dy;

                // Vérifie si le joueur a atteint la case de victoire
                if (playerX == victoryX && playerY == victoryY) {
                    primaryStage.close(); // Ferme la fenêtre si le joueur a gagné
                }

                // Met à jour la grille de jeu
                drawMaze(gridPane);
                drawPlayer(gridPane);
            }
        });

        Scene scene = new Scene(gridPane);
        gridPane.requestFocus(); // Pour que le GridPane puisse recevoir les événements de clavier
        primaryStage.setScene(scene);
        primaryStage.setTitle("Maze Game");
        primaryStage.show();
    }

    public void drawPlayer(GridPane gridPane) {
        Rectangle player = new Rectangle(CELL_SIZE, CELL_SIZE);
        player.setFill(Color.YELLOW);
        gridPane.add(player, playerX, playerY);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
