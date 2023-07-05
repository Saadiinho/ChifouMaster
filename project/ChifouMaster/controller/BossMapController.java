package controller;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import model.BossMapModel;
import model.Model;
import view.BossMapView;
import view.View;
import view.MapView;
import model.MapModel;
import java.util.*;

public class BossMapController {

    private static final int CHARACTER_SPEED = 8;
    public static final int CHARACTER_SIZE = 40;
    private BossMapModel bossMapModel;
    private BossMapView bossMapView;
    private Scene scene;
    private Model characterModel;
    private View characterView;
    private Controller characterController;
    private Stage primaryStage;
    private List<Integer> numbers;

    public BossMapController(BossMapModel bossMapModel, BossMapView bossMapView, Scene scene, Stage primaryStage, List<Integer> numbers) {
        this.bossMapModel = bossMapModel;
        this.bossMapView = bossMapView;
        this.scene = scene;
        this.numbers = numbers;
        bossMapView.changeSkin(numbers.get(0), numbers.get(1), numbers.get(2), numbers.get(3));
        
       bossMapView.updatePosition(bossMapModel.getX(), bossMapModel.getY());
       //Permet de bouger dans la map et interagir avec l'environnement
        scene.setOnKeyPressed(event -> {
            KeyCode keyCode = event.getCode();
            switch (keyCode) {
                case UP:
                case Z:
                	bossMapModel.setY(bossMapModel.getY() - CHARACTER_SPEED);
                	bossMapView.setPlayerTurn("playerUp.png");
                	break;
                case DOWN:
                case S:
                	bossMapModel.setY(bossMapModel.getY() + CHARACTER_SPEED);
                	bossMapView.setPlayerTurn("player.png");
                	break;
                case LEFT:
                case Q:
                	bossMapModel.setX(bossMapModel.getX() - CHARACTER_SPEED);
                	bossMapView.setPlayerTurn("playerLeft.png");
                	break;
                case RIGHT:
                case D:
                	bossMapModel.setX(bossMapModel.getX() + CHARACTER_SPEED);
                	bossMapView.setPlayerTurn("playerRight.png");
                	break;
                case E:
                case I:
                	//Retourner à la map précédente
                	if (bossMapModel.isCharacterNearPreviousMap()) {
                		createBossModelAndView();
                	}
                	//Parler avec le boss
                	else if (bossMapModel.isCharacterNearBoss()) {
                		Platform.exit();
                	}
                	break;
                case ESCAPE:
                	Platform.exit();
                default : 
                	break;
            }
            bossMapView.updatePosition(bossMapModel.getX(), bossMapModel.getY());
        });
        
    }
    //Permet de revenir à la map principale
    private void createBossModelAndView() {
    	Scene previousScene = scene;
    	this.characterModel = new Model();
    	characterModel.setVictory(4);
        this.characterView = new View();
        characterController = new Controller(characterModel, characterView, scene, primaryStage);
        characterController.setSkinNumber(numbers);
        scene.setRoot(characterView);
        this.characterModel = characterModel;
        this.characterView = characterView;
        this.characterController = characterController;
    }
    
    //Quelques Setteurs
    public void setCharacterModel(Model characterModel) {
        this.characterModel = characterModel;
    }

    public void setCharacterView(View characterView) {
        this.characterView = characterView;
    }
}
