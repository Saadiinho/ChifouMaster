package view;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.layout.Pane;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.input.Dragboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.TransferMode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;
import java.util.Random;
import javafx.scene.image.WritableImage;
import javafx.scene.image.PixelWriter;


public class BossView extends Pane {
	private static final int CHARACTER_SIZE = 40;
	private static final int WINDOW_WIDTH = 800; // Largeur de la fenêtre en pixels
	private static final int WINDOW_HEIGHT = 800; // Hauteur de la fenêtre en pixels
	private static final Duration ANIMATION_DURATION = Duration.millis(50);
	private static final String STYLED_CONTENT_TEXT = "-fx-font-size: 16px;" + // Taille de police
	            "-fx-font-weight: bold;" + // Gras
	            "-fx-text-fill: #0066FF;"; // Couleur du texte (bleu)
	private ImageView backgroundImageView;
	private String imagePath = "file:/C:/Users/saadr/OneDrive/Documents/Ecole/Informatique/Langage/Java/Projet-java-ihm/src/Fight.png";
	private ImageView player;
	private ImageView pnjLeftBottom;
	private ImageView pnjLeftTop;	
	private ImageView pnjRightTop;
	private ImageView pnjRightBottom;
	private Random rand;
	private ImageView enemyChoice;
	private ImageView rock;
	private ImageView paper;
	private ImageView scissors;
	private Rectangle dropZone;
	private boolean dragAndDropEnable = false;
	private int choiceRandomEnemy;
	private int userChoice;
	private boolean user;
	private int result;
	private Text text;
	private int victory;
	
	//Guetteurs
	public int getVictory() {
		return victory;
	}

	public boolean getUser() {
		return user;
	}
	
	public BossView(int i) {
		victory = i;
		//Mise en place du background
		Image defaultBackgroundImage = new Image(imagePath);
		backgroundImageView = new ImageView(defaultBackgroundImage);
		backgroundImageView.setFitWidth(WINDOW_WIDTH);
		backgroundImageView.setFitHeight(WINDOW_HEIGHT);
		
		// Ajout de l'image de fond au pane
		getChildren().add(backgroundImageView);
		
		// Joueur
		Image mainCharacter = new Image("file:/C:/Users/saadr/OneDrive/Documents/Ecole/Informatique/Langage/Java/Projet-java-ihm/src/playerFight.png");
		player = new ImageView(mainCharacter);
		player.setFitWidth(CHARACTER_SIZE * 4);
		player.setFitHeight(CHARACTER_SIZE * 3);
		
		// Positionnement du joueur
		player.setLayoutX(0);
		player.setLayoutY(512);
		
		// Positionnement du pnj. le paramètre i permet de savoir quelle PNJ est combattu
		if (i == 1) {
			Image secondImage = new Image("file:/C:/Users/saadr/OneDrive/Documents/Ecole/Informatique/Langage/Java/Projet-java-ihm/src/pnjcombat1.png");
			pnjLeftBottom = new ImageView(secondImage);
			pnjLeftBottom.setFitWidth(CHARACTER_SIZE * 5);
			pnjLeftBottom.setFitHeight(CHARACTER_SIZE * 3);
			pnjLeftBottom.setLayoutX(600);
			pnjLeftBottom.setLayoutY(512);
		} else if (i == 2) {
			Image secondImage = new Image("file:/C:/Users/saadr/OneDrive/Documents/Ecole/Informatique/Langage/Java/Projet-java-ihm/src/pnjcombat2.png");
			pnjLeftBottom = new ImageView(secondImage);
			pnjLeftBottom.setFitWidth(CHARACTER_SIZE * 5);
			pnjLeftBottom.setFitHeight(CHARACTER_SIZE * 3);
			pnjLeftBottom.setLayoutX(620);
			pnjLeftBottom.setLayoutY(512);
		} else if (i == 3) {
			Image secondImage = new Image("file:/C:/Users/saadr/OneDrive/Documents/Ecole/Informatique/Langage/Java/Projet-java-ihm/src/pnjcombat3.png");
			pnjLeftBottom = new ImageView(secondImage);
			pnjLeftBottom.setFitWidth(CHARACTER_SIZE * 5);
			pnjLeftBottom.setFitHeight(CHARACTER_SIZE * 3);
			pnjLeftBottom.setLayoutX(540);
			pnjLeftBottom.setLayoutY(512);
		} else if (i == 4) {
			Image secondImage = new Image("file:/C:/Users/saadr/OneDrive/Documents/Ecole/Informatique/Langage/Java/Projet-java-ihm/src/pnjcombat4.png");
			pnjLeftBottom = new ImageView(secondImage);
			pnjLeftBottom.setFitWidth(CHARACTER_SIZE * 5);
			pnjLeftBottom.setFitHeight(CHARACTER_SIZE * 3);
			pnjLeftBottom.setLayoutX(560);
			pnjLeftBottom.setLayoutY(512);
		}
		
		// Suppression des PNJ du pane (si nécessaire)
		getChildren().removeAll(player, pnjLeftBottom, pnjLeftTop, pnjRightTop, pnjRightBottom);
		
		// Ajout du joueur au pane
		getChildren().addAll(player, pnjLeftBottom);
		
		// Ajouter trois ImageView pour les images que l'utilisateur peut glisser et déposer.
		rock = new ImageView(new Image("file:/C:/Users/saadr/OneDrive/Documents/Ecole/Informatique/Langage/Java/Projet-java-ihm/src/rock.png"));
		paper = new ImageView(new Image("file:/C:/Users/saadr/OneDrive/Documents/Ecole/Informatique/Langage/Java/Projet-java-ihm/src/paper.png"));
		scissors = new ImageView(new Image("file:/C:/Users/saadr/OneDrive/Documents/Ecole/Informatique/Langage/Java/Projet-java-ihm/src/scissors.png"));
		
		// Réglez la position et la taille des ImageView.
		rock.setX(10);
		rock.setY(300);
		paper.setX(100);
		paper.setY(370);
		scissors.setX(200);
		scissors.setY(300);
		
		// Créer la zone de dépôt.
		dropZone = new Rectangle(240, 100, Color.TRANSPARENT);
		dropZone.setX(22);
		dropZone.setY(140);
		
		// Ajoutez les ImageView et le Rectangle au Pane.
		getChildren().addAll(rock, paper, scissors, dropZone);
		
		// Créer une instance de Random
		rand = new Random();
		
		// Setup Drag and Drop
		setupDragAndDrop(rock, paper, scissors, dropZone);
		
		// Générer le choix aléatoire de l'adversaire
		generateEnemyChoice();
	}
	//Permet de générer aléatoirement un choix pour l'adversaire
	private void generateEnemyChoice() {
		//Autoriser le drag and drop
		dragAndDropEnable = false;
		
		// Générer un nombre aléatoire entre 0 et 2
		choiceRandomEnemy = rand.nextInt(3); 

		// Sélectionner l'image correspondante en fonction de l'index aléatoire
		if (choiceRandomEnemy == 0) {
			enemyChoice = new ImageView(new Image("file:/C:/Users/saadr/OneDrive/Documents/Ecole/Informatique/Langage/Java/Projet-java-ihm/src/rock.png"));
		} else if (choiceRandomEnemy == 1) {
			enemyChoice = new ImageView(new Image("file:/C:/Users/saadr/OneDrive/Documents/Ecole/Informatique/Langage/Java/Projet-java-ihm/src/paper.png"));
		} else if (choiceRandomEnemy == 2) {
			enemyChoice = new ImageView(new Image("file:/C:/Users/saadr/OneDrive/Documents/Ecole/Informatique/Langage/Java/Projet-java-ihm/src/scissors.png"));
		}
		
		// Positionner l'image de choix de l'adversaire
		enemyChoice.setX(570);
		enemyChoice.setY(140);
		
		// Ajouter l'image de choix de l'adversaire au Pane
		getChildren().add(enemyChoice);
		
		//Le mettre en arrière plan
		enemyChoice.toBack();
	}
	
	private void setupDragAndDrop(ImageView rock, ImageView paper, ImageView scissors, Rectangle dropZone) {
		// Ajoutez le code de glisser-déposer ici.
		ImageView[] images = {rock, paper, scissors};
		for (ImageView imgView : images) {
			imgView.setOnDragDetected(event -> {
				Dragboard db = imgView.startDragAndDrop(TransferMode.ANY);
				ClipboardContent content = new ClipboardContent();
				
				// Convertir l'image en format compatible RVB avant de la placer dans le presse-papiers
				Image image = imgView.getImage();
				WritableImage writableImage = new WritableImage((int) image.getWidth(), (int) image.getHeight());
				PixelWriter pixelWriter = writableImage.getPixelWriter();
				for (int x = 0; x < image.getWidth(); x++) {
					for (int y = 0; y < image.getHeight(); y++) {
						Color color = image.getPixelReader().getColor(x, y);
						pixelWriter.setColor(x, y, color);
					}
				}
				content.putImage(writableImage);
				db.setContent(content);
				event.consume();
			});
		}
		dropZone.setOnDragOver(event -> {
			if (event.getGestureSource() != dropZone && event.getDragboard().hasImage()) {
				event.acceptTransferModes(TransferMode.MOVE);
			}
			event.consume();
		});
		
		//Une fois que l'image est déposée
		dropZone.setOnDragDropped(event -> {
			if (!dragAndDropEnable) {//Bloquer la possibilité de drag and drop 
				Dragboard db = event.getDragboard();
				boolean success = false;
				if (db.hasImage()) {
					//Permet de traiter l'image déposer
					ImageView droppedImgView = new ImageView(db.getImage());
					droppedImgView.setX(dropZone.getX());
					droppedImgView.setY(dropZone.getY());
					getChildren().add(droppedImgView);
					//permet de savoir ce que l'utilisateur a mis dans la zone 
					if (compareImages(droppedImgView.getImage(), rock.getImage())) {
						userChoice = 0;//Pierre
					}
					else if (compareImages(droppedImgView.getImage(), paper.getImage())) {
						userChoice = 1;//Feuille
					}
					else {
						userChoice = 2;//Ciseaux
					}
					
					//Permet de montrer le choix de l'ennemi
					enemyChoice.toFront();
					dragAndDropEnable = true;
					success = true;
					//Permet de donner le résultat
					result = chifoumi(userChoice, choiceRandomEnemy);
					//Permet de montrer le résultat à l'utilisateur
					if (result == 1) {
						user = true;
						showAlertHouse("Victoire !\nPour quitter, appuyez sur la touche échap");
					}
					else if (result == 0) {
						user = false;
						showAlertHouse("Défaite !\nPour quitter, appuyez sur la touche échap");
					}
					else {
						user = false;
						showAlertHouse("Egalité !\nPour quitter, appuyez sur la touche échap");
					}
					
				}
				event.setDropCompleted(success);
				event.consume();
			}
		});
	}
	
	//Permet de comparer deux images passer en paramètre pixel par pixel
	public static boolean compareImages(Image image1, Image image2) {
        if (image1.getWidth() != image2.getWidth() || image1.getHeight() != image2.getHeight()) {
            return false; // Les dimensions des images sont différentes, elles ne peuvent pas être égales
        }
        PixelReader pixelReader1 = image1.getPixelReader();
        PixelReader pixelReader2 = image2.getPixelReader();
        int width = (int) image1.getWidth();
        int height = (int) image1.getHeight();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (pixelReader1.getArgb(x, y) != pixelReader2.getArgb(x, y)) {
                    return false; // Les pixels sont différents, les images ne sont pas égales
                }
            }
        }
        return true; // Tous les pixels sont identiques, les images sont égales
    }
	
	//Le résultat du chifoumi. Return : -1 => égalité / 0 => défaite / 1 => victoire 
	public int chifoumi(int user, int pnj) {
		if (user == 0) { //Pierre
			if (pnj == 0) { //Pierre
				return -1; 
			}
			else if (pnj == 1) { //Feuille
				return 0;
			}
			else { //Ciseaux
				return 1;
			}
		}
		else if (user == 1) {//Feuille
			if (pnj == 1) {//Feuille
				return -1;
			}
			else if (pnj == 2) {//Ciseaux
				return 0;
			}
			else {
				return 1;//Pierre
			}
		}
		else {//Ciseaux
			if (pnj == 2) {//Ciseaux
				return -1;
			}
			else if (pnj == 0) {//Pierre
				return 0;
			}
			else {//Feuille
				return 1;
			}
		}
	}
	
	//Permet de montrer une instructio, à l'utilisateur
	public void showAlertHouse(String information) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText("Résultat : ");
        text = new Text();
        text.setFont(Font.font("Arial", 16));
        text.setStyle(STYLED_CONTENT_TEXT);
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.setContent(text);
        // Modifier la taille du DialogPane
        dialogPane.setPrefWidth(700); // Largeur préférée
        dialogPane.setPrefHeight(298); // Hauteur préférée
        animateText(information);
        alert.showAndWait();
    }
    private void animateText(String textHouse) {
        Timeline timeline = new Timeline();
        for (int i = 0; i < textHouse.length(); i++) {
            final int index = i;
            KeyFrame keyFrame = new KeyFrame(ANIMATION_DURATION.multiply(i),
                    new KeyValue(text.textProperty(), textHouse.substring(0, index + 1)));
            timeline.getKeyFrames().add(keyFrame);
        }
        timeline.play();
    }
    public void changeSkin(int w, int x, int y, int z) {
    	ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setHue(w);       // Réglage de la teinte (0.0 pour le rouge pur)
        colorAdjust.setSaturation(x); // Réglage de la saturation (1.0 pour une saturation maximale)
        colorAdjust.setBrightness(y); // Réglage de la luminosité (0.0 pour une luminosité normale)
        colorAdjust.setContrast(z);   // Réglage du contraste (1.0 pour un contraste normal)
        player.setEffect(colorAdjust);
    }
}
