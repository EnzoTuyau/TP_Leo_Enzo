package org.example.tp_leo_enzo;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

public class Journaux extends ObjetDuJeu {
    protected Point2D pos;
    protected Point2D velocite;
    protected Point2D acc = new Point2D(0, 1500);
    protected Point2D taille;

    public Journaux(Point2D pos, Point2D velocite, Point2D taille) {
        this.pos = pos;
        this.velocite = velocite;
        this.taille = taille;
    }



    @Override
    protected void draw(GraphicsContext context, Camera camera) {

    }
}
