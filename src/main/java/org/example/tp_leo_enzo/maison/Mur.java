package org.example.tp_leo_enzo.maison;

import javafx.geometry.Point2D;

import java.util.ArrayList;

public class Mur {

    ArrayList<ArrayList<Point2D>> coordBriques;
    final int w = 192;
    final int h = 96;

    public Mur() {
        // Initialise le mur complet au démarrage
        creerMur();
    }

    public void updatePhysics(Point2D coordsCam) {
        // Vérifie si la colonne la plus à gauche est sortie de l'écran (caméra)
        if (coordBriques.getFirst().getFirst().getX() + w < coordsCam.getX()) {

            // On enlève la colonne trop à gauche
            enleverColonne();

            // On ajoute une nouvelle colonne à droite pour continuer le mur
            ajouterColonne();
        }
    }

    private void enleverColonne() {
        // Retire la première colonne du mur
        coordBriques.removeFirst();
    }

    private void ajouterColonne() {
        // Ajoute une nouvelle colonne vide
        coordBriques.add(new ArrayList<>());

        // Ajoute 7 briques empilées verticalement dans cette colonne
        for (int i = 0; i < 7; i++) {
            coordBriques.getLast().add(
                    new Point2D(
                            coordBriques.get(coordBriques.size() - 2).getFirst().getX() + w, // position X = colonne précédente + largeur brique
                            i * h                                                       // position Y en fonction de la hauteur de la brique
                    )
            );
        }
    }

    public ArrayList<ArrayList<Point2D>> getCoordBriques() {
        return coordBriques;
    }

    public void resetMur() {
        // Remet le mur dans son état initial
        creerMur();
    }

    public void creerMur() {
        // Génère 6 colonnes, 7 briques chacune
        coordBriques = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            coordBriques.add(new ArrayList<>());
            for (int j = 0; j < 7; j++) {
                // Chaque brique est positionnée selon sa colonne et rangée
                coordBriques.get(i).add(new Point2D(i * w, j * h));
            }
        }
    }
}
