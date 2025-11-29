package org.example.tp_leo_enzo.maison;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import org.example.tp_leo_enzo.Camera;
import org.example.tp_leo_enzo.Journaux;
import org.example.tp_leo_enzo.ObjetDuJeu;

import java.util.ArrayList;

public class BoiteAuxLettres extends ObjetDuJeu {

    private int adresse;
    private String couleurBoite = "defaut";

    public BoiteAuxLettres(Point2D position){
        pos = position;
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
    public void verifierCollision(ArrayList<Journaux> journaux){
        for (int i = 0; i < journaux.size(); i++) {

        }
    }
    public void changerBoite (boolean abonne){
        if(abonne){
            couleurBoite = "vert";
        }else{
            couleurBoite = "rouge";
        }
    }
}
