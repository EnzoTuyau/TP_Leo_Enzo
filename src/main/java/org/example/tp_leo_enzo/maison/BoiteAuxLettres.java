package org.example.tp_leo_enzo.maison;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import org.example.tp_leo_enzo.Camera;
import org.example.tp_leo_enzo.Journaux;
import org.example.tp_leo_enzo.ObjetDuJeu;
import org.example.tp_leo_enzo.Camelot;



public class BoiteAuxLettres extends ObjetDuJeu {

    //attributs
    private String couleurBoite = "defaut";
    private final boolean abonne;
    private boolean utilise;
    //constructeur
    public BoiteAuxLettres(Point2D position, boolean abonne) {
        pos = position;
        this.abonne = abonne;
        taille = new Point2D(52, 31);
        utilise = false;
    }
    //méthode pour dessiner les boites aux lettres
    @Override
    protected void draw(GraphicsContext context, Camera camera) {
        Point2D posCam = camera.coordoEcran(pos);
        //boucle switch pour changer l'image s'il y a collision
        switch (couleurBoite) {
            case "defaut":
                context.drawImage(new Image("boite-aux-lettres.png"), posCam.getX(), posCam.getY());
                break;
            case "vert":
                context.drawImage(new Image("boite-aux-lettres-vert.png"), posCam.getX(), posCam.getY());
                break;
            case "rouge":
                context.drawImage(new Image("boite-aux-lettres-rouge.png"), posCam.getX(), posCam.getY());
                break;
        }
    }

    @Override
    protected void update(double deltaTemps) {
        updatePhysique(deltaTemps);
    }

    @Override
    protected void updatePhysique(double deltaTemps) {
        super.updatePhysique(deltaTemps);
    }

    public boolean verifierCollisions(Journaux journal, Camelot camelot) {
        //boucle if pour vérifier s'il y a collision
        boolean collision = false;
        if (!(this.getDroite() < journal.getGauche() ||
                journal.getDroite() < this.getGauche() ||
                this.getBas() < journal.getHaut() ||
                journal.getBas() < this.getHaut())) {
            this.changerBoite(camelot);
            collision = true;
        }
        return collision;
    }

    public void changerBoite(Camelot camelot) {
        //boucle if pour donner le signal qu'il y a collision et il faut changer la couleur de la boite aux lettres
        if (!utilise) {
            if (abonne) {
                couleurBoite = "vert";
                camelot.ajouterArgent(1);
            } else {
                couleurBoite = "rouge";
            }
            utilise = true;
        }
    }
}
