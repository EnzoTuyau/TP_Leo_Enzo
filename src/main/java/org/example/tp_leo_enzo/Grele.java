package org.example.tp_leo_enzo;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;

public class Grele extends Flocon {

    private boolean dejaRebondi;

    public Grele() {
        super(3, 4, Color.WHITE);
        dejaRebondi = false;
    }

    @Override
    protected void collisionAvecSol() {
        if (dejaRebondi) {
            recommencer();
            dejaRebondi = false;
        } else {
            dejaRebondi = true;
            teleporterHaut();
        }
    }

    private void teleporterHaut() {
        // On ramène le flocon dans l'écran pour éviter le
        // glitch décrit dans les notes de cours!
        position = new Point2D(
                position.getX(),
                Math.clamp(position.getY(), 0, MainJavaFX.HEIGHT - taille.getY())
        );

        velocite = new Point2D(velocite.getX(), velocite.getY() * -0.3);
    }
}
