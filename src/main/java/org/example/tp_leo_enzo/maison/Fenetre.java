package org.example.tp_leo_enzo.maison;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import org.example.tp_leo_enzo.Camera;
import org.example.tp_leo_enzo.Journaux;
import org.example.tp_leo_enzo.ObjetDuJeu;
import org.example.tp_leo_enzo.Camelot;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Fenetre extends ObjetDuJeu {

    private int adresse;
    private String etatFenetre = "defaut";
    private boolean abonne;
    private boolean utilise;

    public Fenetre(Point2D position,boolean abonne) {
        pos = position;
        taille = new Point2D(159, 130);
        this.abonne = abonne;
        this.utilise = false;
    }

    @Override
    protected void draw(GraphicsContext context, Camera camera) {
        Point2D posCam = camera.coordoEcran(pos);
        switch (etatFenetre) {
            case "defaut":
                context.drawImage(new Image("fenetre.png"), posCam.getX(), posCam.getY());
                break;
            case "brise vert":
                context.drawImage(new Image("fenetre-brisee-vert.png"), posCam.getX(), posCam.getY());
                break;
            case "brise rouge":
                context.drawImage(new Image("fenetre-brisee-rouge.png"), posCam.getX(), posCam.getY());
                break;
        }
    }

    @Override
    protected void update(double deltaTemps) {
        updatePhysique(deltaTemps);
    }

    @Override
    protected void updatePhysique(double deltaTemps) {
        if (getDroite() < 0) { //boucle if pour vérifier si les objets sortent de la caméra

        }
        velocite = velocite.add(acceleration.multiply(deltaTemps));
        pos = pos.add(velocite.multiply(deltaTemps));
    }

    public void verifierCollision(Journaux journal,Camelot camelot){
        if(!(this.getDroite()<journal.getGauche()||
        journal.getDroite()<this.getGauche()||
        this.getBas()<journal.getHaut()||
        journal.getBas()<this.getHaut())){
            this.briserFenetre(camelot);
        }
    }

    private void briserFenetre(Camelot camelot) {
        if(!utilise) {
            if (abonne) {
                camelot.ajouterArgent(-2);
                etatFenetre = "brise rouge";
            } else {
                camelot.ajouterArgent(2);
                etatFenetre = "brise vert";
            }
            utilise=true;
        }
    }

}
