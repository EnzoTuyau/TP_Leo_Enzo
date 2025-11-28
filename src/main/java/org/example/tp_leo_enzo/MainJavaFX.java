package org.example.tp_leo_enzo;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.example.tp_leo_enzo.maison.Mur;
import org.example.tp_leo_enzo.maison.Maison;
import java.util.Random;
import java.util.ArrayList;

public class MainJavaFX extends Application {
    public static final double WIDTH = 900, HEIGHT = 580;
    private int debug = 0;
    private boolean niveau1 = false;
    private boolean niveau2 = false;
    boolean changerMasse = true;
    private long conteurTemps;
    private ArrayList<Maison> maisons = new ArrayList<>();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Random rnd = new Random();
        var camera = new Camera();
        var root = new Pane();
        var scene = new Scene(root, WIDTH, HEIGHT);
        var canvas = new Canvas(WIDTH, HEIGHT);
        root.getChildren().add(canvas);
        var context = canvas.getGraphicsContext2D();

        Mur mur = new Mur();
        int addMaison1 = rnd.nextInt(100,951);
        for (int i = 0; i < 12; i++) {
            maisons.add(new Maison(i,HEIGHT,WIDTH,addMaison1+i*2));
        }

        //création des adresses
        ArrayList<Integer> adresses = new ArrayList<>();
        adresses.add(132);
        adresses.add(176);
        adresses.add(67);
        //création du camelot
        Camelot camelot = new Camelot(adresses);
        //création d'une interface
        InterfaceDebutNiveau interface1 = new InterfaceDebutNiveau(camelot);


        var timer = new AnimationTimer() {
            long dernierTemps = System.nanoTime();

            @Override
            public void handle(long maintenant) {
                //deltaTemps est le temps écoulé entre 2 frames
                double deltaTemps = (maintenant - dernierTemps) * 1e-9;

                //conteur de temps permet de savoir quand 3 secondes se sont écoulées dès le lancement de la première frame
                conteurTemps += dernierTemps;
                //boucle if qui permet d'afficher l'interface du niveau 1 jusqu'à temps qu'on le réussisse
//                if (conteurTemps <= 3 || (conteurTemps<3 && !niveau1)) {
                //interface du jeu
                context.setFill(Color.BLACK);
                context.fillRect(0, 0, WIDTH, HEIGHT);
                interface1.interfaceNiveau(deltaTemps);
//                } else if ((!niveau1 || !niveau2) && conteurTemps > 3) {


                // Arrière-plan des niveaux (maison)
                for (int i = 0; i < mur.getCoordBriques().size(); i++) {
                    for (int j = 0; j < mur.getCoordBriques().get(i).size(); j++) {
                        Point2D coordsBriqueCam = camera.coordoEcran(new Point2D(mur.getCoordBriques().get(i).get(j).getX(),mur.getCoordBriques().get(i).get(j).getY()));
                        context.drawImage(new Image("brique.png"),coordsBriqueCam.getX(),coordsBriqueCam.getY());
                    }
                }
                mur.updatePhysics(camera.getPositionCamera());


                //camera
//                camera.setVelocite(camelot.getVelocite());
                camera.setPositionCamera(new Point2D(camelot.getPos().getX() - 0.2 * WIDTH, 0));


                //avancer camelot
                //permet de savoir quand on appuie et quand on lâche une touche
                scene.setOnKeyPressed(event -> {
                    Input.keyPressed(event.getCode());
                    choixDebogage(event);
                });
                scene.setOnKeyReleased(event -> Input.keyReleased(event.getCode()));
                boolean gauche = false;
                boolean droite = false;

                //boucle if pour accélérer uniquement si on est au sol
                if (camelot.getPos().getY() == 580 - 144) { //144 est la hauteur du camelot
                    gauche = Input.isKeyPressed(KeyCode.LEFT);
                    droite = Input.isKeyPressed(KeyCode.RIGHT);
                }
                boolean sauter = false;
                //boucle if pour ne pas sauter dans les aires
                if (camelot.getPos().getY() == 580 - 144) { //144 est la hauteur du camelot
                    sauter = Input.isKeyPressed(KeyCode.UP);
                }

                if(!maisons.isEmpty()&&maisons.get(0).getX()+1300<camera.getPositionCamera().getX()){
                    maisons.remove(0);
                }
                for (int i = 0; i < maisons.size(); i++) {
                    maisons.get(i).draw(context,camera);
                }

                //lancer journaux
                Journaux journaux = lancerJournaux(camelot, context, camera);

                camelot.draw(context, camera);
                camelot.changerImg(deltaTemps);
                updateTout(context, gauche, droite, sauter, deltaTemps, camera, camelot, journaux);



//                    if (passerniveau1==true){
//                        niveau1=true;
//                    }


//                } else if (niveau1==true && maintenant <= 3) {
//
//                } else


                //modes de débogage différent


                dernierTemps = maintenant;
            }
        };
        timer.start();


        primaryStage.setScene(scene);
        primaryStage.setTitle("Animations!");
        primaryStage.show();
    }
    //Méthodes

    public void updateTout(GraphicsContext context, boolean gauche, boolean droite, boolean sauter, double deltaTemps, Camera camera, Camelot camelot, Journaux journaux) {
        camelot.updatePhysique(gauche, droite, sauter, deltaTemps);
        camera.update(deltaTemps);
        double positionligneCamelot = camera.coordoEcran(camelot.getPos()).getX() - 4;
        modeDebogage(positionligneCamelot, context);
        journaux.updatePhysique(deltaTemps);


    }

    public Journaux lancerJournaux(Camelot camelot, GraphicsContext context, Camera camera) {
        boolean shift = Input.isKeyPressed(KeyCode.SHIFT);
        double masseJournauxNiveau = 0;
        masseJournauxNiveau = masse();
        Point2D quantiteMouvementZ = new Point2D(900, -900);
        Point2D quantiteMouvementX = new Point2D(150, -1100);

        Journaux journaux = new Journaux(camelot.getCentre(), camelot.getVelocite(), masseJournauxNiveau);

        if (shift) {
            if (Input.isKeyPressed(KeyCode.Z)) {
                quantiteMouvementZ.multiply(1.5);
            } else if (Input.isKeyPressed(KeyCode.X)) {
                quantiteMouvementX.multiply(1.5);
            }
        } else if (Input.isKeyPressed(KeyCode.Z)) {
//            Point2D quantiteMouvement = new Point2D(masseJournauxNiveau * journaux.getVelocite().getX(), masseJournauxNiveau * journaux.getVelocite().getY());
            journaux.setVelocite(new Point2D(camelot.getVelocite().getX() + quantiteMouvementZ.getX() / masseJournauxNiveau, camelot.getVelocite().getY() + quantiteMouvementZ.getY() / masseJournauxNiveau));

        } else if (Input.isKeyPressed(KeyCode.X)) {
            journaux.setVelocite(new Point2D(camelot.getVelocite().getX() + quantiteMouvementX.getX() / masseJournauxNiveau, camelot.getVelocite().getY() + quantiteMouvementX.getY() / masseJournauxNiveau));

        }
        context.drawImage(new Image("journal.png"), camelot.getCentre().getX(), camelot.getCentre().getY());




        return journaux;
    }

    public double masse() {
        Random random = new Random();
        double masseJournauxNiveau = 1;
        if (changerMasse) {
            masseJournauxNiveau = random.nextDouble(1, 2);
            changerMasse = false;
        }
        return masseJournauxNiveau;
    }


    public void modeDebogage(double positionligneCamelot, GraphicsContext context) {
        if (debug == 1) {
            context.setFill(Color.YELLOW);
            context.fillRect(positionligneCamelot, 0, 4, 600);
        }
    }


    public void choixDebogage(KeyEvent KeyEvent) {
        switch (KeyEvent.getCode()) {
            case D:
                if (debug == 1) {
                    debug = 0;
                } else {
                    debug = 1;
                }
                break;
            case Q:
                if (debug == 2) {
                    debug = 0;
                } else {
                    debug = 2;
                }
                break;
            case K:

                if (debug == 3) {
                    debug = 0;
                } else {
                    debug = 3;
                }
                break;
            case L:
                if (debug == 4) {
                    debug = 0;
                } else {
                    debug = 4;
                    ;
                }
                break;
            default:
                Input.keyPressed(KeyEvent.getCode());
                break;
        }
    }


}