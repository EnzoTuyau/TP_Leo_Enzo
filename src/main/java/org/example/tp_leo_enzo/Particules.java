package org.example.tp_leo_enzo;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Particules extends ObjetDuJeu {
    private final Color couleur;

    public Particules(Point2D pos, Color couleur) {
        velocite = new Point2D(0, 0);
        taille = new Point2D(20, 20);
        this.pos = pos;
        this.couleur = couleur;
    }


    @Override
    public void draw(GraphicsContext context, Camera camera) {
        Point2D posCam = camera.coordoEcran(pos);
        context.setFill(couleur);
        context.fillOval(posCam.getX(), posCam.getY(), taille.getX(), taille.getY());
    }


    public Point2D calculerChampAuPoint(Point2D point) {
        //Distance entre le milieu du journal et la particule chargée
        Point2D vecteur = point.subtract(pos);
        double distance = vecteur.magnitude();
        //boucle if pour si la distance est plus petite que 1, on la considère de 1
        if (distance < 1) {
            distance = 1;
        }
        // Vecteur unitaire
        Point2D direction = vecteur.normalize();
        int k = 90;
        int q = 900;
        double moduleChamp = k * q / (distance * distance);
        //Vecteur champ électrique retourné
        return direction.multiply(moduleChamp);
    }
}
