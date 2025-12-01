package org.example.tp_leo_enzo.maison;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import org.example.tp_leo_enzo.Camera;
import org.example.tp_leo_enzo.Journaux;
import org.example.tp_leo_enzo.ObjetDuJeu;
import org.example.tp_leo_enzo.Camelot;


public class Fenetre extends ObjetDuJeu {

    private String etatFenetre = "defaut";
    private final boolean abonne;
    private boolean utilise;

    public Fenetre(Point2D position, boolean abonne) {
        pos = position;
        taille = new Point2D(159, 130);
        this.abonne = abonne;
        this.utilise = false;
    }

    @Override
    protected void draw(GraphicsContext context, Camera camera) {
        // Conversion de la position dans le monde en position sur l'écran
        Point2D posCam = camera.coordoEcran(pos);

        // boucle switch pour sélectionner l’image selon l’état de la fenêtre
        switch (etatFenetre) {
            case "defaut":
                context.drawImage(new Image("fenetre.png"), posCam.getX(), posCam.getY());
                break;
            case "brise vert":
                context.drawImage(new Image("fenetre-brisee-vert.png"), posCam.getX(), posCam.getY());
                break;
            case "brise rouge":
                context.drawImage(new Image("fenetre-brisee-rouge.png"), posCam.getX(), posCam.getY());
                break;
        }
    }

    @Override
    protected void update(double deltaTemps) {
        // Met à jour les éléments physiques (position, vitesse)
        updatePhysique(deltaTemps);
    }

    @Override
    protected void updatePhysique(double deltaTemps) {
        // Mise à jour de la vitesse selon l'accélération
        velocite = velocite.add(acceleration.multiply(deltaTemps));

        // Mise à jour de la position selon la vitesse
        pos = pos.add(velocite.multiply(deltaTemps));
    }

    public boolean verifierCollision(Journaux journal, Camelot camelot) {
        boolean collision = false;

        // Vérifie s'il y a une collision rectangulaire entre la fenêtre et le journal
        if (!(this.getDroite() < journal.getGauche() ||
                journal.getDroite() < this.getGauche() ||
                this.getBas() < journal.getHaut() ||
                journal.getBas() < this.getHaut())) {

            // Si collision, brise potentiellement la fenêtre
            this.briserFenetre(camelot);
            collision = true;
        }

        return collision;
    }

    private void briserFenetre(Camelot camelot) {
        // Empêche de briser plusieurs fois la même fenêtre
        if (!utilise) {

            // Si la maison est abonnée, Camelot perd de l’argent
            if (abonne) {
                camelot.ajouterArgent(-2);
                etatFenetre = "brise rouge";
            } else {
                // Sinon Camelot gagne de l’argent
                camelot.ajouterArgent(2);
                etatFenetre = "brise vert";
            }

            utilise = true; // Marque la fenêtre comme ayant été utilisée
        }
    }

}
