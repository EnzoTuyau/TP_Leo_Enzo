package org.example.tp_leo_enzo.maison;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.example.tp_leo_enzo.Camera;
import org.example.tp_leo_enzo.ObjetDuJeu;

public class Particules extends ObjetDuJeu {
    private Color couleur;

    public Particules(Point2D pos, Color couleur) {
        velocite = new Point2D(0, 0);
        taille = new Point2D(20, 20);
        this.pos=pos;
        this.couleur= couleur;
    }



    @Override
    public void draw(GraphicsContext context, Camera camera) {
        Point2D posCam = camera.coordoEcran(pos);
        context.setFill(couleur);
        context.fillOval(posCam.getX(), posCam.getY(), taille.getX(), taille.getY());
    }
}
