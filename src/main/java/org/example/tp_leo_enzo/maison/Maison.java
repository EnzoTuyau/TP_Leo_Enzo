package org.example.tp_leo_enzo.maison;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import org.example.tp_leo_enzo.Camera;
import org.example.tp_leo_enzo.Journaux;
import org.example.tp_leo_enzo.Camelot;

import java.util.ArrayList;
import java.util.Random;

public class Maison {

    private final Porte porte;
    private final BoiteAuxLettres boiteAuxLettres;
    private final ArrayList<Fenetre> fenetres = new ArrayList<>();
    private final int adresse;
    private final double posMaisonX;
    private final boolean abonne;

    public boolean isAbonne() {
        return abonne;
    }

    public ArrayList<Fenetre> getFenetres() {
        return fenetres;
    }

    public int getAdresse() {
        return adresse;
    }

    public Maison(int nbMaison, double HEIGHT, int adresse) {
        Random rnd = new Random();

        // Position horizontale de la maison dépend du numéro de maison
        posMaisonX = (nbMaison + 1) * 1300;

        // Création de la porte à une position fixe par rapport au sol
        porte = new Porte(new Point2D(posMaisonX, HEIGHT - 195), adresse);

        // Détermine si la maison est abonnée ou non
        this.abonne = rnd.nextBoolean();

        // Positionne la boite aux lettres à une hauteur aléatoire
        boiteAuxLettres = new BoiteAuxLettres(
                new Point2D(posMaisonX + 200, rnd.nextDouble(0.3, 0.8) * HEIGHT),
                abonne
        );

        // Nombre de fenêtres aléatoire (entre 0 et 2)
        int nbFenetres = rnd.nextInt(0, 3);

        // Génère les fenêtres de la maison
        for (int i = 0; i < nbFenetres; i++) {
            fenetres.add(new Fenetre(new Point2D(posMaisonX + 300 * (i + 1), 50), abonne));
        }

        this.adresse = adresse;
    }

    public BoiteAuxLettres getBoiteAuxLettres() {
        return boiteAuxLettres;
    }

    public void draw(GraphicsContext context, Camera camera) {
        // Dessine la porte
        porte.draw(context, camera);

        // Dessine la boîte aux lettres
        boiteAuxLettres.draw(context, camera);

        // Dessine chaque fenêtre
        for (Fenetre fenetre : fenetres) {
            fenetre.draw(context, camera);
        }
    }

    public boolean verifierCollisions(Journaux journal, Camelot camelot) {
        boolean collision = false;

        // Vérifie les collisions avec chaque fenêtre
        for (Fenetre fenetre : fenetres) {
            collision = fenetre.verifierCollision(journal, camelot);
            if (collision) {
                return collision;  // Collision trouvée → on arrête ici
            }
        }

        // Vérifie ensuite la collision avec la boîte aux lettres
        collision = boiteAuxLettres.verifierCollisions(journal, camelot);

        return collision;
    }

    public double getX() {
        return posMaisonX;
    }
}
