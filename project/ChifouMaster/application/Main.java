package application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import view.*;
import model.Model;
import controller.Controller;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DialogPane;


public class Main extends Application {

    private static final int WINDOW_WIDTH = 789; //Largeur de la fenêtre en pixels
    private static final int WINDOW_HEIGHT = 789; //Hauteur de la fenêtre en pixels

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Pane root = new Pane();
        Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
        primaryStage.setScene(scene);


        // Créer le modèle, la vue et le contrôleur du personnage
        Model characterModel = new Model();
        View characterView = new View();
        @SuppressWarnings("unused")
		Controller characterController = new Controller(characterModel, characterView, scene, primaryStage);

        // Ajouter la vue du personnage au Pane
        root.getChildren().add(characterView);

        primaryStage.setTitle("ChifouMaster");
        // Bloquer la taille de la fenêtre
        primaryStage.setResizable(false);
        showAlert();
        primaryStage.show();
    }
    private void showAlert() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Bienvenue dans le jeu !");
        alert.setHeaderText("Bienvenue à Échosombre, la cité ensorcelante aux mystères insondables.");
        
        // Texte attractif
        String contentText = "Plongez dans l'énigmatique dédale de ce village et découvrez ses secrets bien gardés. Vous vous retrouverez face à des villageois égarés, mais ne vous méprenez pas, car votre mission est de restaurer leur sérénité à travers un défi captivant : le jeu du Chifoumi."
        		+ "\n\nNaviguez avec aisance en utilisant les flèches de votre clavier ou les touches ZQSD pour vous déplacer dans cet univers envoûtant. "
        		+ "\nLorsque vous souhaitez interagir avec votre environnement, appuyez simplement sur la touche E ou I pour dévoiler de nouvelles possibilités passionnantes.";
        
        // Ajout de style au texte
        String styledContentText = "-fx-font-size: 16px;" + // Taille de police
                                  "-fx-font-weight: bold;" + // Gras
                                  "-fx-text-fill: #0066FF;"; // Couleur du texte (bleu)
        
        alert.setContentText(contentText);
        
        // Appliquer le style au contenu du DialogPane
        DialogPane dialogPane = alert.getDialogPane();
        
        
        
        // Modifier la taille du DialogPane
        dialogPane.setPrefWidth(620); // Largeur préférée
        dialogPane.setPrefHeight(298); // Hauteur préférée

        alert.showAndWait();
    }


}


