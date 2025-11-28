package org.example.tp_leo_enzo.maison;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import org.example.tp_leo_enzo.Camera;
import org.example.tp_leo_enzo.Journaux;

import java.util.ArrayList;
import java.util.Random;


public class Maison {
    private Porte porte;
    private BoiteAuxLettres boiteAuxLettres;
    private ArrayList<Fenetre> fenetres = new ArrayList<>();
    private int nbFenetres;
    private int adresse;
    private final double posMaisonX;
    private boolean abonne;

    public boolean isAbonne() {
        return abonne;
    }

    public int getAdresse() {
        return adresse;
    }

    public Maison(int nbMaison, double HEIGHT, double WIDTH, int adresse){
        Random rnd = new Random();
        posMaisonX = (nbMaison+1)*1300;
        porte = new Porte(new Point2D(posMaisonX,HEIGHT-195),adresse);
        boiteAuxLettres = new BoiteAuxLettres(new Point2D(posMaisonX+200,rnd.nextDouble(0.3,0.8)*HEIGHT));
        nbFenetres = rnd.nextInt(0,3);
        for (int i = 0; i < nbFenetres; i++) {
            fenetres.add(new Fenetre(new Point2D(posMaisonX+300*(i+1),50)));
        }
        this.adresse=adresse;
        this.abonne= rnd.nextBoolean();
    }

    public void draw(GraphicsContext context, Camera camera){
        porte.draw(context,camera);
        boiteAuxLettres.draw(context,camera);
        for (int i = 0; i < fenetres.size(); i++) {
            fenetres.get(i).draw(context,camera);
        }
    }

    public void verifierCollisionsFenetres(ArrayList<Journaux> journaux){
        for (int i = 0; i < fenetres.size(); i++) {
            fenetres.get(i).verifierCollision(journaux);
        }
    }

    public double getX(){
        return posMaisonX;
    }
}
