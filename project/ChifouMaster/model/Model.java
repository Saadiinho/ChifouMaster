package model;

import controller.Controller;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class Model {
	private static final int WINDOW_WIDTH = 800; // Largeur de la fenêtre en pixels
    private static final int WINDOW_HEIGHT = 800; // Hauteur de la fenêtre en pixels
    private static final double THRESHOLD = 75; // Valeur seuil de distance pour considérer le personnage proche du PNJ
    private double x;
    private double y; 
    int victory;
    private static final String STYLED_CONTENT_TEXT = "-fx-font-size: 16px;" + // Taille de police
            "-fx-font-weight: bold;" + // Gras
            "-fx-text-fill: #0066FF;"; // Couleur du texte (bleu)
    private static final Duration ANIMATION_DURATION = Duration.millis(50); // Durée de l'animation (50 millisecondes)
    private Text text;

    public Model() {
        this.x = 380;
        this.y = 640;
    }

    //Guetteur
    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }
    public int getVictory() {
    	return victory;
    }
   
    //Setteur
    public void setVictory(int n) {
    	this.victory = n;
    }
    //Permet de se déplacer sur la map
    public void setX1(double x) {
    	//Bloque les bordures de la fenêtre
        if (x >= 0 && x <= WINDOW_WIDTH - Controller.CHARACTER_SIZE) {
        	//Bloque la verdure en haut à gauche de la fenêtre
        	if ((x >=0 && x<=300) || (x>=460 && x<=WINDOW_WIDTH)) {
            	if (y>=170 && y<=460) {
            		return;
            	}
            }
        	// Bloque le pnj en haut à gauche
            if (y>=100 - Controller.CHARACTER_SIZE && y<= 100 + Controller.CHARACTER_SIZE) {
            	if ((x>=0 && x <= Controller.CHARACTER_SIZE) || (x>=760 - Controller.CHARACTER_SIZE && x<= WINDOW_WIDTH)) {
            		return;
            	}
            }
        	//Bloque la verdure en bas à gauche de la fenêtre
            if ((x >=0 && x<=300) || (x >=460 && x <=WINDOW_WIDTH)) {
            	if (y>=580 && y <= WINDOW_HEIGHT - Controller.CHARACTER_SIZE) {
            		return;
            	}
            } 
            //Bloque le pnj au milieu à gauche
            if ((x>=0 && x <= Controller.CHARACTER_SIZE) || (x>=760 - Controller.CHARACTER_SIZE && x<= WINDOW_WIDTH)) {
            	if (y>=525 - Controller.CHARACTER_SIZE && y<= 525 + Controller.CHARACTER_SIZE) {
            		return;
            	}
            }
        	this.x = x;
        }
    }
    public void setY1(double y) {
    	//Bloque les bordures de la fenêtre
        if (y >= 20 && y <= WINDOW_HEIGHT - Controller.CHARACTER_SIZE) {
        	//Bloque la verdure en haut à gauche de la fenêtre
            if (y >= 170 && y <= 460) {
            	if ((x>=0 && x<=300) || (x>=460 && x<=WINDOW_WIDTH)) {
            		return;
            	}
            }
            // Bloque le pnj en haut à gauche
            if (y>=100 - Controller.CHARACTER_SIZE && y<= 100 + Controller.CHARACTER_SIZE) {
            	if ((x>=0 && x <= Controller.CHARACTER_SIZE) || (x>=760 - Controller.CHARACTER_SIZE && x<= WINDOW_WIDTH)) {
            		return;
            	}
            }
            //Bloque la verdure en haut à gauche de la fenêtre
            if (y>=580 && y<= WINDOW_HEIGHT - Controller.CHARACTER_SIZE) {
            	if ((x>=0 && x<=300) || (x >=460 && x <=WINDOW_WIDTH)) {
            		return;
            	}
            }
            // Bloque le pnj au milieu à gauche
            if (y>=525 - Controller.CHARACTER_SIZE && y<= 525 + Controller.CHARACTER_SIZE) {
            	if ((x>=0 && x <= Controller.CHARACTER_SIZE) || (x>=760 - Controller.CHARACTER_SIZE && x<= WINDOW_WIDTH)) {
            		return;
            	}
            }
            this.y = y;
        }
    }

	//Permet de savoir si le personnage est assez proche d'un élément pour le déclencher 
    //Pour les PNJ
	public boolean isCharacterNearPNJ() {
		return isCharacterNearPNJ1() || isCharacterNearPNJ2() || isCharacterNearPNJ3() || isCharacterNearPNJ4(); 
	}
	public boolean isCharacterNearPNJ1() {
	    double distance = Math.sqrt(Math.pow(x - 0, 2) + Math.pow(y - 525, 2));
	    return distance <= THRESHOLD;
	}
	public boolean isCharacterNearPNJ2() {
	    double distance = Math.sqrt(Math.pow(x - 0, 2) + Math.pow(y - 100, 2));
	    return distance <= THRESHOLD;
	}
	public boolean isCharacterNearPNJ3() {
	    double distance = Math.sqrt(Math.pow(x - 760, 2) + Math.pow(y - 100, 2));
	    return distance <= THRESHOLD;
	}
	public boolean isCharacterNearPNJ4() {
	    double distance = Math.sqrt(Math.pow(x - 760, 2) + Math.pow(y - 525, 2));
	    return distance <= THRESHOLD;
	}
	//Pour le point d'information
	public boolean isInformationNear() {
		double distance = Math.sqrt(Math.pow(x - 540, 2) + Math.pow(y - 625, 2));
	    return distance <= THRESHOLD;
	}
	//Pour les maisons
	public boolean isNearHouse() {
		return (isNearHouse1() || isNearHouse2() || isNearHouse3());
	}
	public boolean isNearHouse1() {
		double distance = Math.sqrt(Math.pow(x - 120, 2) + Math.pow(y - 625, 2));
	    return distance <= THRESHOLD;
	}
	public boolean isNearHouse2() {
		double distance = Math.sqrt(Math.pow(x - 110, 2) + Math.pow(y - 505, 2));
	    return distance <= THRESHOLD;
	}
	public boolean isNearHouse3() {
		double distance = Math.sqrt(Math.pow(x - 700, 2) + Math.pow(y - 505, 2));
	    return distance <= THRESHOLD;
	}
	//Pour la map du boss
	//Avec les victoires nécessaires
	public boolean isCharacterNearBoss() {
		if (x >= 300 && x <= 460) {
			if (y >= 760 && y <= WINDOW_HEIGHT) {
				if (victory >= 4) {
					return true;
				}
			}
		}
		return false;
	}//Sans les vicoires nécessaires
	public boolean isCharacterNearBossWithoutVictory() {
		if (x >= 300 && x <= 460) {
			if (y >= 760 && y <= WINDOW_HEIGHT) {
				if (victory != 4) {
					return true;
				}
			}
		}
		return false;
	}
	
	//Alerte déclencher lorsque le personnage s'approche du point I
	public void showRules() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Bienvenue dans le jeu !");
        alert.setHeaderText("Bienvenue à Échosombre, la cité ensorcelante aux mystères insondables.");
        // Texte attractif
        String contentText = "Plongez dans l'énigmatique dédale de ce village et découvrez ses secrets bien gardés. Vous vous retrouverez face à des villageois égarés, mais ne vous méprenez pas, car votre mission est de restaurer leur sérénité à travers un défi captivant : le jeu du Chifoumi."
        		+ "\n\nNaviguez avec aisance en utilisant les flèches de votre clavier ou les touches ZQSD pour vous déplacer dans cet univers envoûtant. "
        		+ "\nLorsque vous souhaitez interagir avec votre environnement, appuyez simplement sur la touche E ou I pour dévoiler de nouvelles possibilités passionnantes."
        		+ "\nEntrez dans les batailles envoûtantes du Chifoumi et laissez votre choix glisser avec grâce dans la mystérieuse case qui révélera votre destin.";
        // Ajout de style au texte
        alert.setContentText(contentText);
        // Appliquer le style au contenu du DialogPane
        DialogPane dialogPane = alert.getDialogPane();
        // Modifier la taille du DialogPane
        dialogPane.setPrefWidth(620); // Largeur préférée
        dialogPane.setPrefHeight(298); // Hauteur préférée

        alert.showAndWait();
    }
	
	//Alerte déclencher si le personnage veut rentrer dans une maison
	public void showAlertHouse(String information) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText("Bonjour !");
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
    }//Une animation utilisé lors de l'utilisation de la mméthode showAlertHouse()
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
}
