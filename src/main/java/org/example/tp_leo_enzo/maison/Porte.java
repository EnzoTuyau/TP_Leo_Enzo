package org.example.tp_leo_enzo.maison;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import org.example.tp_leo_enzo.Camera;
import org.example.tp_leo_enzo.ObjetDuJeu;

import java.awt.*;
import javafx.scene.paint.Color;

public class Porte extends ObjetDuJeu {

    final private int adresse;

    public Porte(Point2D position, int adresse){
        pos = position;
        this.adresse = adresse;
    }
    @Override
    protected void draw(GraphicsContext context, Camera camera) {
        Point2D posCam = camera.coordoEcran(pos);
        context.drawImage(new Image("porte.png"),posCam.getX(),posCam.getY());
        context.setFont(Font.font(50));

        context.setFill(Color.YELLOW);
        context.fillText(adresse+"",posCam.getX()+32,posCam.getY()+50);

    }

    protected void update(double deltaTemps) {
        updatePhysique(deltaTemps);
    }
}
