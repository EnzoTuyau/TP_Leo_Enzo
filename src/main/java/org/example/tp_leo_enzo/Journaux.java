package org.example.tp_leo_enzo;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Journaux extends ObjetDuJeu {
    private Point2D pos;
    private Point2D velocite;
    private Point2D acc = new Point2D(0, 1500);
    private final Point2D taille = new Point2D(52, 31);
    private double masse;

    public Point2D getVelocite() {
        return velocite;
    }



    public void setPos(Point2D pos) {
        this.pos = pos;
    }

    public Journaux(Point2D pos, Point2D velocite, double masse) {
        this.pos = pos;
        this.velocite = velocite;
        this.masse = masse;
    }




    @Override
    protected void draw(GraphicsContext context, Camera camera) {
        Image journal = new Image("journal.png");
        context.drawImage(journal, 52, 31);
    }
}
