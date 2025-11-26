package org.example.tp_leo_enzo.maison;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import org.example.tp_leo_enzo.Camera;
import org.example.tp_leo_enzo.ObjetDuJeu;

public class Particules extends ObjetDuJeu {

    @Override
    protected void draw(GraphicsContext context, Camera camera) {
        Point2D posCam = camera.coordoEcran(pos);
        context.fillOval(posCam.getX(),posCam.getY(),10,10);
    }
}
