package org.example.tp_leo_enzo;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.example.tp_leo_enzo.maison.Mur;
import org.example.tp_leo_enzo.maison.Maison;

import java.util.Random;
import java.util.ArrayList;

public class MainJavaFX extends Application {
    public static final double WIDTH = 900, HEIGHT = 580;
    private int debug = 0;
    private boolean niveau1 = false;
    private boolean prochainNiveau = false;
    private boolean gameOver=false;
    boolean changerMasse = true;
    private double conteurTemps;
    private ArrayList<Journaux> journauxLances = new ArrayList<>();
    private ArrayList<Maison> maisons = new ArrayList<>();
    private long dernierTir = 0;
    private ArrayList<Integer> adresseMaison = new ArrayList<>();

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
        primaryStage.setResizable(false);


        //création du mur
        Mur mur = new Mur();

        //création maisons
        creationMaisons();


        //création du camelot
        Camelot camelot = new Camelot(adresseMaison);

        //création d'une interface
        InterfaceDebutNiveau interface1 = new InterfaceDebutNiveau();


        var timer = new AnimationTimer() {
            long dernierTemps = System.nanoTime();

            @Override
            public void handle(long maintenant) {

                //deltaTemps est le temps écoulé entre 2 frames
                double deltaTemps = (maintenant - dernierTemps) * 1e-9; // en secondes
                dernierTemps = maintenant;
                //conteur de temps permet de savoir quand 3 secondes se sont écoulées dès le lancement de la première frame
                conteurTemps += deltaTemps;
                // Vérifier plus de journaux et aucun sur l'écran pour afficher la rupture de stocks
                if (camelot.getJournaux() == 0 && journauxLances.isEmpty()) {
                    if (!gameOver){
                        conteurTemps=0;
                        gameOver=true;
                    }
                    interface1.interfaceGameOver(context, camelot, WIDTH, HEIGHT);
                    interface1.setNiveau(1);
                    if (conteurTemps > 3) {
                        camelot.ajouter24Journaux();
                        conteurTemps = 0;
                        gameOver=false;
                    }

                } else if (conteurTemps < 3) {
                        adresseMaison.clear();
                        maisons.clear();
                        creationMaisons();
                        camelot.setPos(new Point2D(180, 580 - camelot.taille.getY()));
                        camera.setPositionCamera(new Point2D(0, 0));
                        mur.resetMur();

                    interface1.interfaceDeNiveau(context, WIDTH, HEIGHT, prochainNiveau);
                    prochainNiveau = false;
                    niveau1 = false;
                    System.out.println(conteurTemps);

                } else if (maisons.isEmpty() && !niveau1 && !prochainNiveau) {
                    niveau1 = true;
                    camelot.ajouter12Journaux();
                } else if (!niveau1 && (conteurTemps > 3 && (!journauxLances.isEmpty() || camelot.getJournaux() > 0))) { //boucle if qui permet d'afficher l'interface du niveau 1 jusqu'à temps qu'on le réussisse
                    if (!maisons.isEmpty()) {
                        context.setFill(Color.BLACK);
                        context.fillRect(0, 0, WIDTH, HEIGHT);
                        // Arrière-plan des niveaux (maison)
                        for (int i = 0; i < mur.getCoordBriques().size(); i++) {
                            for (int j = 0; j < mur.getCoordBriques().get(i).size(); j++) {
                                Point2D coordsBriqueCam = camera.coordoEcran(new Point2D(mur.getCoordBriques().get(i).get(j).getX(), mur.getCoordBriques().get(i).get(j).getY()));
                                context.drawImage(new Image("brique.png"), coordsBriqueCam.getX(), coordsBriqueCam.getY());
                            }
                        }
                        mur.updatePhysics(camera.getPositionCamera());


                        //camera
                        camera.setPositionCamera(new Point2D(camelot.getPos().getX() - 0.2 * WIDTH, 0));


                        //avancer camelot
                        //permet de savoir quand on appuie et quand on lâche une touche
                        scene.setOnKeyPressed(event -> {
                            Input.keyPressed(event.getCode());
                            choixDebogage(event, primaryStage, camelot, interface1, context);
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

                        if (!maisons.isEmpty() && maisons.get(0).getX() + 1300 < camera.getPositionCamera().getX()) {
                            maisons.remove(0);
                        }
                        for (int i = 0; i < maisons.size(); i++) {
                            maisons.get(i).draw(context, camera);
                        }

                        //boucle if pour passer le niveau une fois que le camelot a parcouru toutes les maisons


                        //lancer journaux
                        if (camelot.getJournaux() > 0) {
                            verifierLancerJournaux(camelot);
                        }

                        //ligne jaune derrière le camelot
                        double positionligneCamelot = camera.coordoEcran(camelot.getPos()).getX() - 4;
                        modeDebogage(positionligneCamelot, context);

                        //interface bande du haut
                        interfaceDuHaut(context, camelot);

                        //dessiner et faire avancer le camelot
                        camelot.draw(context, camera);
                        camelot.changerImg(deltaTemps);

                        //update tout ce qui peut avoir devant la caméra
                        updateTout(context, gauche, droite, sauter, deltaTemps, camera, camelot);

                    }
                } else if (niveau1) {
                    niveau1 = false;
                    prochainNiveau = true;
                    conteurTemps = 0;
                }
            }
        };
        timer.start();


        primaryStage.setScene(scene);
        primaryStage.setTitle("Camelot à vélo");
        primaryStage.show();
    }
    //Méthodes

    public void updateTout(GraphicsContext context, boolean gauche, boolean droite, boolean sauter, double deltaTemps, Camera camera, Camelot camelot) {
        camelot.updatePhysique(gauche, droite, sauter, deltaTemps);
        camera.update(deltaTemps);


        // met à jour tous les journaux qui sont lancés
        for (int i = 0; i < journauxLances.size(); i++) {

            Journaux journalLance = journauxLances.get(i);

            journalLance.updatePhysique(deltaTemps);
            journalLance.draw(context, camera);

            double positionXCamera = camera.getPositionCamera().getX();

            //boucle if qui vérifie si les journaux dépassent de la caméra puis les enlève
            if (journalLance.getPos().getY() > HEIGHT || //dépasse par le bas
                    journalLance.getPos().getX() + journalLance.taille.getX() < positionXCamera || // derrière la caméra
                    journalLance.getPos().getX() > positionXCamera + WIDTH // trop loin devant
            ) {
                journauxLances.remove(i--);
            }
        }


    }

    public void verifierLancerJournaux(Camelot camelot) {

        boolean shift = Input.isKeyPressed(KeyCode.SHIFT);
        boolean z = Input.isKeyPressed(KeyCode.Z);
        boolean x = Input.isKeyPressed(KeyCode.X);

        // si aucune touche n’est enfoncée, on quitte tout de suite la méthode
        if (!z && !x) {
            return;
        }

        // met un délai de 0.5 seconde
        long maintenant = System.nanoTime();
        double secondesDepuisDernierTir = (maintenant - dernierTir) * 1e-9;

        // quitte directement la méthode dès que le délai est moins de 0.5 secondes
        if (secondesDepuisDernierTir < 0.5) {
            return;
        }

        // Masse du journal (aléatoire au début du niveau)
        double masseJournaux = masse();

        // quantité de mouvement
        Point2D quantiteMouvementZ = new Point2D(150, -1100);
        Point2D quantiteMouvementX = new Point2D(900, -900);

        if (shift) { //boucle if qui se déclanche si on appuie shift
            if (z)
                quantiteMouvementZ = quantiteMouvementZ.multiply(1.5); //boucle if qui se déclanche si on appuie shift puis z
            if (x)
                quantiteMouvementX = quantiteMouvementX.multiply(1.5); //boucle if qui se déclanche si on appuie shift puis x
        }

        // Centre du camelot
        Point2D posCentreCamelot = camelot.getCentre();

        // création du journal
        Journaux journal = new Journaux(posCentreCamelot, camelot.getVelocite(), masseJournaux);


        //double boucle if pour lancer le journal dans la bonne direction si on pèse z ou x
        if (z) {
            journal.setVelocite(camelot.getVelocite().add(quantiteMouvementZ.multiply(1 / masseJournaux)));
        }
        if (x) {
            journal.setVelocite(camelot.getVelocite().add(quantiteMouvementX.multiply(1 / masseJournaux)));
        }


        //ajoute le journal lancé dans la liste de journaux
        journauxLances.add(journal);
        camelot.lancerJournal();
        dernierTir = maintenant; // on enregistre le tir
        collision();
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

    public void modeDebogage(double posX, GraphicsContext context) {
        if (debug == 1) {
            context.setFill(Color.YELLOW);
            context.fillRect(posX, 0, 4, HEIGHT);
        } else if (debug==2) {

        }
    }

    public void choixDebogage(KeyEvent KeyEvent, Stage stage, Camelot camelot, InterfaceDebutNiveau interface1, GraphicsContext context) {
        switch (KeyEvent.getCode()) {
            case D:
                if (debug == 1) {
                    debug = 0;
                } else {
                    debug = 1;
                }
                break;
            case Q:
                camelot.ajouter10Journaux();
                break;
            case K:
                camelot.viderJournaux();
                break;
            case L:
                boolean passerNiveau= true;
                camelot.ajouter12Journaux();
                interface1.interfaceDeNiveau(context, WIDTH, HEIGHT, passerNiveau);
                conteurTemps=0;
                break;
            case ESCAPE:
                stage.close();
            default:
                Input.keyPressed(KeyEvent.getCode());
                break;
        }
    }

    public void interfaceDuHaut(GraphicsContext context, Camelot camelot) {

        context.setFill(new Color(0, 0, 0, 0.5));
        context.fillRect(0, 0, WIDTH, 50);
        context.setFill(Color.WHITE);
        context.drawImage(new Image("icone-journal.png"), 5, 9);
        context.setFont(Font.font(28));
        context.fillText(camelot.getJournaux() + "", 50, 35);
        context.drawImage(new Image("icone-dollar.png"), 100, 9);
        context.fillText(camelot.getArgent() + "", 160, 35);
        context.drawImage(new Image("icone-maison.png"), 200, 9);
        int positionXNumeroMaisonAbonne = 250;

        for (int i = 0; i < adresseMaison.size(); i++) {
            context.fillText(adresseMaison.get(i) + "", positionXNumeroMaisonAbonne + 53 * i, 35);

        }


    }

    public void collision() {
        boolean collison = true;
        for (int i = 0; i < journauxLances.size(); i++) {
            for (int j = 0; j < maisons.size(); j++) {
                //boucle if pour verifier si le journal est à gauche de la boite aux lettres
                if (journauxLances.get(i).getPos().getX() + journauxLances.get(i).taille.getX() < maisons.get(j).getBoiteAuxLettres().getGauche()) {
                    collison = false;
                } else if (journauxLances.get(i).getPos().getX() > maisons.get(j).getBoiteAuxLettres().getDroite()) { //boucle else if pour vérifier si le journal est à droite de la boite aux lettres
                    collison = false;
                } else if (journauxLances.get(i).getPos().getY() + journauxLances.get(i).taille.getY() > maisons.get(j).getBoiteAuxLettres().getHaut()) { //boucle else if pour vérifier si le journal est en haut de la boite aux lettres
                    collison = false;
                } else if (journauxLances.get(i).getPos().getY() < maisons.get(j).getBoiteAuxLettres().getBas()) { //boucle else if pour vérifier si le journal est en dessous de la boite aux lettres
                    collison = false;
                }
            }


        }
    }

    public void creationMaisons() {
        Random rnd = new Random();
        //boucle for pour générer les adresse des maisons
        int addMaison1 = rnd.nextInt(100, 951);
        for (int i = 0; i < 12; i++) {
            maisons.add(new Maison(i, HEIGHT, WIDTH, addMaison1 + i * 2));
            //boucle if pour mettre dans le ArrayList d'adresse de maisons abonnées les maisons abonnées
            if (maisons.get(i).isAbonne()) {
                adresseMaison.add(maisons.get(i).getAdresse());
            }
        }
    }

}


