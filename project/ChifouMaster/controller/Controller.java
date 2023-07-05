package controller;

import model.Model;
import model.BossModel;
import view.*;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.BossMapModel;
import javafx.scene.control.TextInputDialog;
import view.BossMapView;
import java.util.*;


public class Controller {
    private static final int CHARACTER_SPEED = 8;
    public static final int CHARACTER_SIZE = 40;
   
    private Model characterModel;
    private View characterView;
    private Scene scene;
    private BossModel bossModel;
    private BossView bossView;
    private BossController bossController;
    private BossMapController bossMapController;
    private BossMapModel bossMapModel;
    private BossMapView bossMapView;
    private Stage primaryStage;
    private List<Integer> numbers = new ArrayList<Integer>();
    private int compte = 0;
    private static final String CONTENT_TEXT = "Derrière cette porte se cache un monde fascinant. "
    		+ "\nMalheureusement, elle est verrouillée, gardant jalousement ses secrets.";
    private static final String CONTENT_TEXT_BOSS_MAP = "Enchantez-vous d'une aventure descendante captivante en surmontant "
    		+ "les redoutables \ndéfis posés par les quatre adversaires qui se dressent sur votre chemin. "
    		+ "\nSeul en les vainquant tous, pourrez-vous descendre vers les profondeurs inexplorées "
    		+ "\nqui révèlent des trésors cachés."
    		+ "\nIl vous reste : ";
    
    
    //Permet de savoir le nombre d'adversaire à battre avant d'atteindre le boss
    public int get() {
    	return 4 - characterModel.getVictory();
    }
    //Setteurs
    public void setSkinNumber(List<Integer> numbers) {
    	this.numbers = numbers;
    	characterView.changeSkin(numbers.get(0),numbers.get(1), numbers.get(2), numbers.get(3));
    }
    
    public Controller(Model characterModel, View characterView, Scene scene, Stage primaryStage) {
        this.setCharacterModel(characterModel);
        this.setCharacterView(characterView);
        this.scene = scene;
        this.primaryStage = primaryStage;
        numbers.add(0);
        numbers.add(0);
        numbers.add(0);
        numbers.add(0);

        // Enregistrer les coordonnées initiales du personnage
        characterView.updatePosition(characterModel.getX(), characterModel.getY());

        // Gérer les événements de touche
        //Pour se déplacer ou pour interagir avec l'environnement
        scene.setOnKeyPressed(event -> {
            KeyCode keyCode = event.getCode();
            switch (keyCode) {
                case UP:
                case Z:
                    characterModel.setY1(characterModel.getY() - CHARACTER_SPEED);
                    characterView.setPlayerTurn("playerUp.png");
                    break;
                case DOWN:
                case S:
                    characterModel.setY1(characterModel.getY() + CHARACTER_SPEED);
                    characterView.setPlayerTurn("player.png");
                    break;
                case LEFT:
                case Q:
                    characterModel.setX1(characterModel.getX() - CHARACTER_SPEED);
                    characterView.setPlayerTurn("playerLeft.png");
                    break;
                case RIGHT:
                case D:
                    characterModel.setX1(characterModel.getX() + CHARACTER_SPEED);
                    characterView.setPlayerTurn("playerRight.png");
                    break;
                case E:
                case I:
                	//Permet de vérifier si le personnage est proche d'un PNJ et lequel
                    if (characterModel.isCharacterNearPNJ()) {
                        if (characterModel.isCharacterNearPNJ1()) {
                        	createBossModelAndView(1);
                        }
                        if (characterModel.isCharacterNearPNJ2() && characterModel.getVictory() >= 1) {
                        	createBossModelAndView(2);
                        }
                        if (characterModel.isCharacterNearPNJ3() && characterModel.getVictory() >= 2) {
                        	createBossModelAndView(3);
                        }
                        if (characterModel.isCharacterNearPNJ4() && characterModel.getVictory() >= 3) {
                        	createBossModelAndView(4);
                        }
                    }
                    //Permet de vérifier si le personnage est proche du point d'information
                    else if (characterModel.isInformationNear()) {
                    	characterModel.showRules();
                    }
                    //Permet de vérifier si le personnage est proche de la map du boss
                    else if (characterModel.isCharacterNearBoss()) {
                    	createBossMap();
                    }//Si le personnage n'ap pas assez de victoires
                    else if (characterModel.isCharacterNearBossWithoutVictory()) {
                    	characterModel.showAlertHouse(CONTENT_TEXT_BOSS_MAP + get() + " adversaires.");
                    }
                    //Permet de vérifier si le personnage est proche d'une maison
                    else if (characterModel.isNearHouse()) {
                    	//Permet de changer de skin
                    	if (characterModel.isNearHouse1()) {
                    		changerSkin();
                    	}
                    	else {
                    		characterModel.showAlertHouse(CONTENT_TEXT);
                    	}
                    }
                    break;
                //Permet de quitter le jeu
                case ESCAPE:
                	Platform.exit();
                default:
                    break;
            }
            //Permet de se déplacer
            characterView.updatePosition(characterModel.getX(), characterModel.getY());
        });
    }

    //Permet de changer le skin
    private void changerSkin() {
    	 for (int i = 0; i <= 3; i++) {
             TextInputDialog dialog = new TextInputDialog();
             dialog.setTitle("Changement de skin");
             if (i == 0) {
            	 dialog.setHeaderText("Entrez le hue :");
             }
             else if (i == 1) {
            	 dialog.setHeaderText("Entrez la saturation :");
             }
             else if (i == 2) {
            	 dialog.setHeaderText("Entrez le brightness :");
             }
             else {
            	 dialog.setHeaderText("Entrez le contrast :");
             }
             dialog.setContentText("Nombre :");

             Optional<String> result = dialog.showAndWait();

             result.ifPresent(number -> {
                 try {
                     int parsedNumber = Integer.parseInt(number);
                     numbers.set(compte, parsedNumber);
                     compte++;
                 } catch (NumberFormatException e) {
                     System.out.println("Entrée invalide : " + number);
                 }
             });
         }

         // Utilisez les nombres entrés (stockés dans la liste "numbers") pour le changement de skin
         characterView.changeSkin(numbers.get(0),numbers.get(1), numbers.get(2), numbers.get(3));
    }
    
    //Pour rentrer dans le MVC du chifoumi (combat) parametre : int i = le numero du PNJ
    private void createBossModelAndView(int i) {
    	Scene previousScene = scene;
        BossModel bossModel = new BossModel();
        BossView bossView = new BossView(i);
        bossView.setBackground(null);
        BossController bossController = new BossController(bossModel, bossView, scene, primaryStage, characterModel.getX(), characterModel.getY(), numbers);
        scene.setRoot(bossView);
        this.bossModel = bossModel;
        this.bossView = bossView;
        this.bossController = bossController;
    }
    
    //Pour rentrer dans le MVC du labirynthe
    private void createBossMap() {
    	Scene previousScene = scene;
        BossMapModel bossMapModel = new BossMapModel();
        BossMapView bossMapView = new BossMapView();
        BossMapController bossMapController = new BossMapController(bossMapModel, bossMapView, scene, primaryStage, numbers);
        scene.setRoot(bossMapView);
        this.bossMapModel = bossMapModel;
        this.bossMapView = bossMapView;
        this.bossMapController = bossMapController;
    }
    
    
    //Des guetteurs et Setteurs
    public BossController getBossController() {
        return bossController;
    }
    
    public Model getCharacterModel() {
        return characterModel;
    }

    public void setCharacterModel(Model characterModel) {
        this.characterModel = characterModel;
    }

    public View getCharacterView() {
        return characterView;
    }

    public void setCharacterView(View characterView) {
        this.characterView = characterView;
    }
}
