package org.example.tp_leo_enzo;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Journaux extends ObjetDuJeu {

    private final double masse;


    public Point2D getAcceleration() {
        return acceleration;
    }


    public void setVelocite(Point2D velocite) {
        this.velocite = velocite;
    }

    public Point2D getPos() {
        return pos;
    }

    public void setAcceleration(Point2D acceleration) {
        this.acceleration = acceleration;
    }

    public double getMasse() {
        return masse;
    }

    public Journaux(Point2D pos, Point2D velocite, double masse) {
        this.pos = pos;
        this.velocite = velocite;
        this.masse = masse;
        this.acceleration = new Point2D(0, 1500);
        taille = new Point2D(52, 31);
    }

    public void updatePhysique(double deltaTemps) {
        double max = 1500;
        pos = pos.add(velocite.multiply(deltaTemps)); //X=X0+VxΔt
        velocite = velocite.add(acceleration.multiply(deltaTemps)); //V=V0+AxΔt
        if (velocite.magnitude() > max) {
            velocite = velocite.multiply(max / velocite.magnitude());
        }
    }


    @Override
    protected void draw(GraphicsContext context, Camera camera) {
        Image journal = new Image("journal.png");
        Point2D posCam = camera.coordoEcran(pos);
        context.drawImage(journal, posCam.getX(), posCam.getY(), taille.getX(), taille.getY());
    }
}
