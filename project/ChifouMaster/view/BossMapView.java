package view;

import javafx.scene.image.ImageView;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

public class BossMapView extends Pane {
    private static final int CHARACTER_SIZE = 40;
    private static final int WINDOW_WIDTH = 800; //Largeur de la fenêtre en pixels
    private static final int WINDOW_HEIGHT = 800; //Hauteur de la fenêtre en pixels
    private ImageView backgroundImageView;
    private String imagePath = "BossMap.jpg";
    private String skinMain = "player.png";
    private String skinBoss = "boss.png";
    private String preImagePath = "file:/C:/Users/saadr/OneDrive/Documents/Ecole/Informatique/Langage/Java/Projet-java-ihm/src/";
    private ImageView player;
    private ImageView boss;
    
    //Constructeur
    public BossMapView() {
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
        player.setLayoutX(380);
        player.setLayoutY(120);
        
        // Boss
        Image bossSkin = new Image(preImagePath + skinBoss);
        boss = new ImageView(bossSkin);
        boss.setFitWidth(CHARACTER_SIZE);
        boss.setFitHeight(CHARACTER_SIZE);
        boss.setLayoutX(380);
        boss.setLayoutY(550);

        // Ajout des pnj et du joueur
        getChildren().addAll(player, boss);
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
    
    //Permet de garder le changer de skin
    public void changeSkin(int w, int x, int y, int z) {
    	ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setHue(w);       // Réglage de la teinte (0.0 pour le rouge pur)
        colorAdjust.setSaturation(x); // Réglage de la saturation (1.0 pour une saturation maximale)
        colorAdjust.setBrightness(y); // Réglage de la luminosité (0.0 pour une luminosité normale)
        colorAdjust.setContrast(z);   // Réglage du contraste (1.0 pour un contraste normal)
        player.setEffect(colorAdjust);
    }

}
