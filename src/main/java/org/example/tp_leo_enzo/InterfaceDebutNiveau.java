package org.example.tp_leo_enzo;

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
            Text txt = new Text("Mon texte en vert");
            txt.setFill(Color.GREEN);



            tempsDepuisDernierChangement = 0; // reset du compteur
        }
    }
}
