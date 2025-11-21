package org.example.tp_leo_enzo;

import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class InterfaceDebutNiveau {
    //attributs
    private double tempsDepuisDernierChangement=0;
    private Camelot donneesCamelot;
    //constructeur

    public InterfaceDebutNiveau(Camelot camelot) {
        this.donneesCamelot=camelot;
    }

    public void interfaceNiveau(double deltaTemps) {
        // Durée entre deux changements d'image (en secondes)
        double  tempsInterfaceNiveau = 3;

        tempsDepuisDernierChangement += deltaTemps;

        if (tempsDepuisDernierChangement <= tempsInterfaceNiveau) {
            // afficher écran de présentation du niveau
            Label lbl = new Label("Mon texte en vert");
            lbl.setStyle("-fx-text-fill: green;");



            tempsDepuisDernierChangement = 0; // reset du compteur
        }
    }
}
