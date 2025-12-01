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
    //getter setter

    public int getNiveau() {
        return niveau;
    }

    public void setNiveau(int niveau) {
        this.niveau = niveau;
    }


    //méthodes
    public void interfaceGameOver(GraphicsContext context, Camelot camelot, double WIDTH, double HEIGHT) {
        context.setFill(Color.BLACK);
        context.fillRect(0, 0, WIDTH, HEIGHT);

        context.setFont(Font.font(70));
        context.setFill(Color.RED);
        context.fillText("Rupture de stocks", WIDTH / 2 - 270, HEIGHT / 2 - 40);

        context.setFill(Color.GREEN);
        context.setFont(Font.font(50));
        context.fillText("Argent collecté : " + camelot.getArgent() + "$",
                WIDTH / 2 - 230, HEIGHT / 2 + 40);
    }


}

