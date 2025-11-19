package org.example.tp_leo_enzo;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

public class Camera  {
    private Point2D positionCamera;
    private Point2D velocite;


    public void setVelocite(Point2D velocite) {
        this.velocite = velocite;
    }

    public Point2D getPositionCamera() {
        return positionCamera;
    }

    public void setPositionCamera(Point2D positionCamera) {
        this.positionCamera = positionCamera;
    }

    public Camera() {
        positionCamera = new Point2D(0, 0);
        velocite = new Point2D(0, 0);
    }

    public Point2D coordoEcran(Point2D positionMonde) {
        return positionMonde.subtract(positionCamera);
    }



    public void update(double deltaTemps) {
        positionCamera = positionCamera.add(velocite.multiply(deltaTemps));
    }




}
