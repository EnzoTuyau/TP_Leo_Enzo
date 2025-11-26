package org.example.tp_leo_enzo.maison;

import javafx.scene.canvas.GraphicsContext;
import org.example.tp_leo_enzo.Camera;
import org.example.tp_leo_enzo.ObjetDuJeu;

public class Porte extends ObjetDuJeu {

    private int adresse;


    @Override
    protected void draw(GraphicsContext context, Camera camera) {

    }

    protected void update(double deltaTemps) {
        updatePhysique(deltaTemps);

    }

    protected void updatePhysique(double deltaTemps) {
        if (getDroite() < 0) { //boucle if pour vérifier si les objets sortent de la caméra

        }
        velocite = velocite.add(acceleration.multiply(deltaTemps));
        pos = pos.add(velocite.multiply(deltaTemps));


    }
}
