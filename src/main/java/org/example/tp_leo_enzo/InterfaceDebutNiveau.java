package org.example.tp_leo_enzo;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class InterfaceDebutNiveau {
    // attributs
    private int niveau = 1;

    // constructeur / méthode d'affichage du début de niveau
    public void interfaceDeNiveau(GraphicsContext context, double WIDTH, double HEIGHT, boolean prochainNiveau) {

        // Si on doit passer au niveau suivant, on incrémente
        if (prochainNiveau) {
            niveau++;
        }

        // Fond noir pour couvrir l'écran
        context.setFill(Color.BLACK);
        context.fillRect(0, 0, WIDTH, HEIGHT);

        // Style du texte (couleur + taille)
        context.setFill(Color.GREEN);
        context.setFont(Font.font(60));

        // Texte du niveau affiché
        String txt = "Niveau " + niveau;

        // Calcul approximatif de la largeur du texte pour le centrer
        double textWidth = context.getFont().getSize() * txt.length() * 0.45;

        // Affiche le texte centré horizontalement, et au milieu verticalement
        context.fillText(txt, (WIDTH - textWidth) / 2, HEIGHT / 2);
    }

    // getter / setter
    public int getNiveau() {
        return niveau;
    }

    public void setNiveau(int niveau) {
        this.niveau = niveau;
    }

    // méthodes
    public void interfaceGameOver(GraphicsContext context, Camelot camelot, double WIDTH, double HEIGHT) {

        // Fond noir pour l'écran de game over
        context.setFill(Color.BLACK);
        context.fillRect(0, 0, WIDTH, HEIGHT);

        // Gros texte rouge "Game Over"
        context.setFont(Font.font(70));
        context.setFill(Color.RED);
        context.fillText("Rupture de stocks", WIDTH / 2 - 270, HEIGHT / 2 - 40);

        // Affiche le score du joueur (argent récolté)
        context.setFill(Color.GREEN);
        context.setFont(Font.font(50));
        context.fillText("Argent collecté : " + camelot.getArgent() + "$",
                WIDTH / 2 - 230, HEIGHT / 2 + 40);
    }

}

