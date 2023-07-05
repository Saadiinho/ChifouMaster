package controller;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import model.BossModel;
import model.Model;
import view.BossView;
import view.View;
import javafx.stage.Stage;
import java.util.*;

public class BossController {
    private BossModel bossModel;
    private BossView bossView;
    private Scene scene;
    private Model characterModel;
    private View characterView;
    private Controller characterController;
    private Stage primaryStage;
    private double X;
    private double Y;
    private List<Integer> numbers;
    
    public BossController(BossModel bossModel2, BossView bossView2, Scene scene, Stage primaryStage, double d, double e, List<Integer> numbers) {
        this.bossModel = bossModel2;
        this.bossView = bossView2;
        this.scene = scene;
        this.X = d;
        this.Y = e;
        this.numbers = numbers;
        bossView.changeSkin(numbers.get(0), numbers.get(1), numbers.get(2), numbers.get(3));

        //Permet de quitter le combat pour revenir à la map principale
        //Les évènements du combats se trouve dans la classe bossView
        scene.setOnKeyPressed(event -> {
            KeyCode keyCode = event.getCode();
            switch (keyCode) {
                case ESCAPE:
                	createBossModelAndView();
                default:
                    break;
            }
        });
    }

    //Quelques Guetteurs
    public List<Integer> getNumbers(){
    	return numbers;
    }
    public BossModel getBossModel() {
        return bossModel;
    }
    public BossView getBossView() {
        return bossView;
    }
    
    
    //Permet de revenir à la ap principale avec le résultat du combat
    private void createBossModelAndView() {
    	Scene previousScene = scene;
    	this.characterModel = new Model();
    	if (bossView.getUser()) {
    		characterModel.setVictory(bossView.getVictory());
    	}
    	else {
    		characterModel.setVictory(bossView.getVictory()-1); //A changer ici
    	}
    	characterModel.setX1(X);
    	characterModel.setY1(Y);
    	System.out.println(characterModel.getVictory());
        this.characterView = new View();
        bossView.setBackground(null);
        characterController = new Controller(characterModel, characterView, scene, primaryStage);
        characterController.setSkinNumber(numbers);
        scene.setRoot(characterView);

        // Utilisez bossModel, bossView et bossController selon vos besoins
        this.characterModel = characterModel;
        this.characterView = characterView;
        this.characterController = characterController;
    }
    
}
