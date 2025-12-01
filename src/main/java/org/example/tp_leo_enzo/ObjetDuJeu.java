package org.example.tp_leo_enzo;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

public abstract class ObjetDuJeu {
    protected Point2D pos = new Point2D(0, 0);
    protected Point2D velocite;
    protected Point2D acceleration;
    protected Point2D taille = new Point2D(0, 0);


    protected abstract void draw(GraphicsContext context, Camera camera);

    protected void update(double deltaTemps) {
        updatePhysique(deltaTemps);
    }

    protected void updatePhysique(double deltaTemps) {
        velocite = velocite.add(acceleration.multiply(deltaTemps));
        pos = pos.add(velocite.multiply(deltaTemps));


    }

    public double getHaut() {
        return pos.getY();
    }

    public double getBas() {
        return pos.getY() + taille.getY();
    }

    public double getGauche() {
        return pos.getX();
    }

    public double getDroite() {
        return pos.getX() + taille.getX();
    }

    public Point2D getCentre() {
        return pos.add(taille.multiply(1 / 2.0));
    }

}
