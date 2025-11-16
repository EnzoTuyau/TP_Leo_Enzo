package org.example.tp_leo_enzo;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;

public class MainJavaFX extends Application {
    public static final double WIDTH = 640, HEIGHT = 480;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        var camera = new Camera();
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


//        Flocon[] flocons = new Flocon[1000];
//
//        for (int i = 0; i < flocons.length; i++) {
//            Random rnd = new Random();
//            int choix = rnd.nextInt(3);
//
//            switch (choix) {
//                case 0 -> flocons[i] = new Flocon();
//                case 1 -> flocons[i] = new Grele();
//                case 2 -> flocons[i] = new FloconOscillant();
//            }
//        }

        var timer = new AnimationTimer() {
        long dernierTemps = System.nanoTime();

            @Override
            public void handle(long maintenant) {
                double deltaTemps = (maintenant - dernierTemps) * 1e-9;
                dernierTemps = maintenant;
                context.setFill(Color.gray(0.2));
                context.fillRect(0, 0, WIDTH, HEIGHT);

                // -- Update --
//                for (var flocon : flocons)
//                    flocon.update(deltaTemps);


                // Arrière-plan


                //avancer camelot

                //permets de savoir quand on appuie et quand on lâche une touche
                scene.setOnKeyPressed(event -> Input.keyPressed(event.getCode()));
                scene.setOnKeyReleased(event -> Input.keyReleased(event.getCode()));
                boolean gauche= false;
                boolean droite= false;
                //boucle if pour accélérer uniquement si on est au sol
                if (camelot.getPos().getY()==300) {
                    gauche = Input.isKeyPressed(KeyCode.LEFT);
                    droite = Input.isKeyPressed(KeyCode.RIGHT);
                }
                    boolean sauter = false;
                //boucle if pour ne pas sauter dans les aires
                if (camelot.getPos().getY()==300) {
                    sauter = Input.isKeyPressed(KeyCode.UP);
                }



                //a supprimer test pour verifier si detecte une touche
                if(gauche||droite){
                    context.setFill(Color.RED);
                    context.fillRect(0,0,50,50);
                }

                camelot.update(gauche, droite, sauter, deltaTemps);
                camelot.draw(context, camera);
                camelot.changerImg();

//                for (var flocon : flocons)
//                    flocon.draw(context);


                camera.setVelocite(new Point2D(camelot.getVelocite().getX(), 0));
            }
        };
        timer.start();



        primaryStage.setScene(scene);
        primaryStage.setTitle("Animations!");
        primaryStage.show();
    }





}