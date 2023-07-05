package view;

import javafx.scene.image.ImageView;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

public class View extends Pane {
    private static final int CHARACTER_SIZE = 40;
    private static final int WINDOW_WIDTH = 800; //Largeur de la fenêtre en pixels
    private static final int WINDOW_HEIGHT = 800; //Hauteur de la fenêtre en pixels
    private ImageView backgroundImageView;
    private String imagePath = "map.png";
    private String skinMain = "player.png";
    private String preImagePath = "file:/C:/Users/saadr/OneDrive/Documents/Ecole/Informatique/Langage/Java/Projet-java-ihm/src/";
    private ImageView player;
    private ImageView pnjLeftBottom;
    private ImageView pnjLeftTop;
    private ImageView pnjRightTop;
    private ImageView pnjRightBottom;
    
    //Constructeur
    public View() {
    	// Création de l'image de fond par défaut
        Image defaultBackgroundImage = new Image(preImagePath + imagePath);
        backgroundImageView = new ImageView(defaultBackgroundImage);
        backgroundImageView.setFitWidth(WINDOW_WIDTH);
        backgroundImageView.setFitHeight(WINDOW_HEIGHT);

        // Ajout de l'image de fond au pane
        getChildren().add(backgroundImageView);

        // Joueur
        Image mainCharacter = new Image(skinMain);
        player = new ImageView(mainCharacter);
        player.setFitWidth(CHARACTER_SIZE);
        player.setFitHeight(CHARACTER_SIZE);
        player.setLayoutX(400);
        player.setLayoutY(640);

        // Pnj Bas Gauche
        Image secondImage = new Image(preImagePath + "pnj1.png");
        pnjLeftBottom = new ImageView(secondImage);
        pnjLeftBottom.setFitWidth(CHARACTER_SIZE);
        pnjLeftBottom.setFitHeight(CHARACTER_SIZE);
        pnjLeftBottom.setLayoutX(0);
        pnjLeftBottom.setLayoutY(525);

        // Pnj Haut Gauche
        Image troisImage = new Image(preImagePath + "pnj2.png");
        pnjLeftTop = new ImageView(troisImage);
        pnjLeftTop.setFitWidth(CHARACTER_SIZE);
        pnjLeftTop.setFitHeight(CHARACTER_SIZE);
        pnjLeftTop.setLayoutX(0);
        pnjLeftTop.setLayoutY(100);

        // Pnj Haut Droite
        Image quatreImage = new Image(preImagePath + "pnj3.png");
        pnjRightTop = new ImageView(quatreImage);
        pnjRightTop.setFitWidth(CHARACTER_SIZE);
        pnjRightTop.setFitHeight(CHARACTER_SIZE);
        pnjRightTop.setLayoutX(760);
        pnjRightTop.setLayoutY(100);

        // Pnj Bas Droite
        Image cinqImage = new Image(preImagePath + "pnj4.png");
        pnjRightBottom = new ImageView(cinqImage);
        pnjRightBottom.setFitWidth(CHARACTER_SIZE);
        pnjRightBottom.setFitHeight(CHARACTER_SIZE);
        pnjRightBottom.setLayoutX(760);
        pnjRightBottom.setLayoutY(525);

        // Ajout des pnj et du joueur
        getChildren().addAll(player, pnjLeftBottom, pnjLeftTop, pnjRightTop, pnjRightBottom);
        
    }
    //Permet de changer de la couleur du skin parametre = w,x,y et z choisi dans la méthode du controller changerSkin
    public void changeSkin(int w, int x, int y, int z) {
    	ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setHue(w);       // Réglage de la teinte (0.0 pour le rouge pur)
        colorAdjust.setSaturation(x); // Réglage de la saturation (1.0 pour une saturation maximale)
        colorAdjust.setBrightness(y); // Réglage de la luminosité (0.0 pour une luminosité normale)
        colorAdjust.setContrast(z);   // Réglage du contraste (1.0 pour un contraste normal)
        player.setEffect(colorAdjust);
    }
    
    

    //Permet de se déplacer
    public void updatePosition(double x, double y) {
        player.setLayoutX(x);
        player.setLayoutY(y);
    }
    
    // Méthode pour changer le fond
    public void setBackground(String imagePath) {
        Image newBackgroundImage = new Image(preImagePath + imagePath);
        backgroundImageView.setImage(newBackgroundImage);
    }
    //Permet de voir le personnage se tourner lorsqu'il se déplace vers la droite ou la gauche
    public void setPlayerTurn(String imagePath) {
        Image newPlayerImage = new Image(preImagePath + imagePath);
        player.setImage(newPlayerImage);
    }
    
   

}
