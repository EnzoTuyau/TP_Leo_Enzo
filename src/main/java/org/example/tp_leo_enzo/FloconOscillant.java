package org.example.tp_leo_enzo;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;

public class FloconOscillant extends Flocon {
    private double tempsTotalEcoule = 0;
    private double xCentre;

    public FloconOscillant() {
        super(2, 2, Color.GRAY);
        this.xCentre = position.getX();

        // Évite de synchroniser tous les flocons avec exactement la
        // même oscillation en même temps
        this.tempsTotalEcoule = Math.random() * 10;
    }

    @Override
    public void update(double deltaTemps) {
        // Fait tout ce qu'un Flocon normal fait...
        super.update(deltaTemps);

        // Ajoute ce qui est spécifique au FloconOscillant
        tempsTotalEcoule += deltaTemps;
        position = new Point2D(
                xCentre + 5 * Math.sin(10 * tempsTotalEcoule),
                position.getY()
        );
    }
}
