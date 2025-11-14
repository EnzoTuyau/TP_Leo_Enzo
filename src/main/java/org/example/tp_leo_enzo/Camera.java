package org.example.tp_leo_enzo;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

public class Camera extends ObjetDuJeu {
    private Point2D positionCamera;
    private Point2D velocite;

    public Point2D getVelocite() {
        return velocite;
    }

    public void setVelocite(Point2D velocite) {
        this.velocite = velocite;
    }

    public Camera() {
        positionCamera = new Point2D(0, 0);
        velocite = new Point2D(0, 0);
    }

    public Point2D coordoEcran(Point2D positionMonde) {
        return positionMonde.subtract(positionCamera);
    }

    @Override
    protected void draw(GraphicsContext context, Camera camera) {

    }

    public void update(double deltaTemps) {
        positionCamera = positionCamera.add(velocite.multiply(deltaTemps));
    }


    public Point2D getPositionCamera() {
        return positionCamera;
    }

    public void setPositionCamera(Point2D positionCamera) {
        this.positionCamera = positionCamera;
    }

}
