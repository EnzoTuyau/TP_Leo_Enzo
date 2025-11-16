package org.example.tp_leo_enzo;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;

public class MainJavaFX extends Application {
    public static final double WIDTH = 900, HEIGHT = 580;
    private int debug = 0;

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



        var timer = new AnimationTimer() {
        long dernierTemps = System.nanoTime();

            @Override
            public void handle(long maintenant) {
                double deltaTemps = (maintenant - dernierTemps) * 1e-9;
                dernierTemps = maintenant;
                // Arrière-plan
                context.setFill(Color.BLACK);
                context.fillRect(0, 0, WIDTH, HEIGHT);
                Image fond = new Image("brique.png");
                ImageView fondbrique = new ImageView(fond);



                //modes de débogage différent
                if (debug==1) {
                    context.setFill(Color.YELLOW);
                    context.fillRect(camera.coordoEcran(camelot.getPos()).getX(), 0, 4,600);
                }

                //avancer camelot

                //permets de savoir quand on appuie et quand on lâche une touche
                scene.setOnKeyPressed(event -> choixDebogage(event));
                scene.setOnKeyReleased(event -> Input.keyReleased(event.getCode()));
                boolean gauche= false;
                boolean droite= false;
                //boucle if pour accélérer uniquement si on est au sol
                if (camelot.getPos().getY()==580-144) { //144 est la hauteur du camelot
                    gauche = Input.isKeyPressed(KeyCode.LEFT);
                    droite = Input.isKeyPressed(KeyCode.RIGHT);
                }
                    boolean sauter = false;
                //boucle if pour ne pas sauter dans les aires
                if (camelot.getPos().getY()==580-144) { //144 est la hauteur du camelot
                    sauter = Input.isKeyPressed(KeyCode.UP);
                }





                //a supprimer test pour verifier si detecte une touche
                if(gauche||droite){
                    context.setFill(Color.RED);
                    context.fillRect(0,0,50,50);
                }

                camelot.update(gauche, droite, sauter, deltaTemps);
                camelot.draw(context, camera);
                camelot.changerImg(deltaTemps);



                camera.setVelocite(new Point2D(camelot.getVelocite().getX(), 0));
                camera.update(deltaTemps);



            }
        };
        timer.start();



        primaryStage.setScene(scene);
        primaryStage.setTitle("Animations!");
        primaryStage.show();
    }


    public void choixDebogage(KeyEvent KeyEvent){
        switch (KeyEvent.getCode()) {
            case D:
                if (debug==1){
                    debug=0;
                }else {
                    debug=1;
                }
                break;
            case Q:
                if (debug==2){
                    debug=0;
                }else {
                    debug=2;
                }
                break;
            case K:

                if (debug==3){
                    debug=0;
                }else {
                    debug=3;
                }
                break;
            case L:
                if (debug==4){
                    debug=0;
                }else {
                    debug=4;;
                }
                break;
            default:
                Input.keyPressed(KeyEvent.getCode());
                break;
        }
    }





}