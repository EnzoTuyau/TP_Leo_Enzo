package org.example.tp_leo_enzo;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class InterfaceDebutNiveau {
    //attributs
    private double tempsDepuisDernierChangement=0;
    private Camelot donneesCamelot;
    private int niveau=1;
    //constructeur



    public void interfaceDeNiveau(GraphicsContext context, double WIDTH, double HEIGHT, boolean prochainNiveau) {
        if (prochainNiveau){
            niveau++;
        }


        context.setFill(Color.BLACK);
        context.fillRect(0, 0, WIDTH, HEIGHT);

        context.setFill(Color.GREEN);
        context.setFont(Font.font(60));

        String txt = "Niveau "+niveau;
        double textWidth = context.getFont().getSize() * txt.length() * 0.45;

        context.fillText(txt, (WIDTH - textWidth) / 2, HEIGHT / 2);
    }

}

