package org.example.tp_leo_enzo.maison;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import org.example.tp_leo_enzo.Camera;
import org.example.tp_leo_enzo.Journaux;
import org.example.tp_leo_enzo.ObjetDuJeu;
import org.example.tp_leo_enzo.Camelot;

import java.util.ArrayList;

public class BoiteAuxLettres extends ObjetDuJeu {

    private int adresse;
    private String couleurBoite = "defaut";
    private boolean abonne;
    private boolean utilise;

    public BoiteAuxLettres(Point2D position,boolean abonne){
        pos = position;
        this.abonne = abonne;
        taille = new Point2D(52, 31);
        utilise = false;
    }

    @Override
    protected void draw(GraphicsContext context, Camera camera) {
        Point2D posCam = camera.coordoEcran(pos);
        switch(couleurBoite){
            case "defaut":
                context.drawImage(new Image("boite-aux-lettres.png"),posCam.getX(),posCam.getY());
                break;
            case "vert":
                context.drawImage(new Image("boite-aux-lettres-vert.png"),posCam.getX(),posCam.getY());
                break;
            case "rouge":
                context.drawImage(new Image("boite-aux-lettres-rouge.png"),posCam.getX(),posCam.getY());
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
    public boolean verifierCollisions(Journaux journal, Camelot camelot){
        boolean collision=false;
        if(!(this.getDroite()<journal.getGauche()||
                journal.getDroite()<this.getGauche()||
                this.getBas()<journal.getHaut()||
                journal.getBas()<this.getHaut())){
            this.changerBoite(camelot);
            collision=true;
        }
        return collision;
    }
    public void changerBoite(Camelot camelot) {
        if (!utilise) {
            if (abonne) {
                couleurBoite = "vert";
                camelot.ajouterArgent(1);
            } else {
                couleurBoite = "rouge";
            }
            utilise=true;
        }
    }
}
