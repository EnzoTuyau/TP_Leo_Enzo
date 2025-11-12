package org.example.tp_leo_enzo;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.example.tp_leo_enzo.Flocon;
import org.example.tp_leo_enzo.FloconOscillant;
import org.example.tp_leo_enzo.Grele;

import java.util.ArrayList;
import java.util.Random;

public class MainJavaFX extends Application {
    public static final double WIDTH = 640, HEIGHT = 480;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        var root = new Pane();
        var scene = new Scene(root, WIDTH, HEIGHT);
        var canvas = new Canvas(WIDTH, HEIGHT);
        root.getChildren().add(canvas);
        var context = canvas.getGraphicsContext2D();

        ArrayList<Integer> adresses = new ArrayList<>();
        adresses.add(132);
        adresses.add(176);
        adresses.add(67);
        Camelot camelot = new Camelot(adresses);


        Flocon[] flocons = new Flocon[1000];

        for (int i = 0; i < flocons.length; i++) {
            Random rnd = new Random();
            int choix = rnd.nextInt(3);

            switch (choix) {
                case 0 -> flocons[i] = new Flocon();
                case 1 -> flocons[i] = new Grele();
                case 2 -> flocons[i] = new FloconOscillant();
            }
        }

        var timer = new AnimationTimer() {
            long lastTime = System.nanoTime();

            @Override
            public void handle(long now) {

                double deltaTemps = (now - lastTime) * 1e-9;

                // -- Update --
                for (var flocon : flocons)
                    flocon.update(deltaTemps);

                // -- Dessin --
                // Arrière-plan
                context.setFill(Color.gray(0.2));
                context.fillRect(0, 0, WIDTH, HEIGHT);

                //Ajouté
                //boolean gauche = Input.isKeyPressed(KeyCode.LEFT);
                //boolean droite = Input.isKeyPressed(KeyCode.RIGHT);
                //camelot.updatePhysique(gauche,droite);
                camelot.draw(context);
                camelot.changerImg();

                for (var flocon : flocons)
                    flocon.draw(context);

                lastTime = now;
            }
        };
        timer.start();

        primaryStage.setScene(scene);
        primaryStage.setTitle("Animations!");
        primaryStage.show();
    }



}