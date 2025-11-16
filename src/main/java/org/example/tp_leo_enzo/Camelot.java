package org.example.tp_leo_enzo;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Camelot extends ObjetDuJeu {
    private int journaux;
    private int argent;
    private ArrayList<Integer> adresses;
    private int imgCamelot;
    private Point2D pos;
    private Point2D velocite;
    private Point2D acceleration;
    private double tempsDepuisDernierChangement=0;

    public Point2D getPos() {
        return pos;
    }

    public void setPos(Point2D pos) {
        this.pos = pos;
    }

    public Point2D getVelocite() {
        return velocite;
    }

    public void setVelocite(Point2D velocite) {
        this.velocite = velocite;
    }

    public int getJournaux() {
        return this.argent;
    }

    public int getArgent() {
        return this.argent;
    }

    public void ajouter12Journaux() {
        this.journaux = this.journaux + 12;
    }

    public void ajouterArgent(int argent) {
        this.argent = this.argent + argent;
    }

    public Camelot(ArrayList<Integer> adresses) {
        this.journaux = 24;
        this.argent = 0;
        this.adresses = adresses;
        this.imgCamelot = 1;
        this.pos = new Point2D(100, 580-144); //144 est la hauteur du camelot
        this.velocite = new Point2D(0, 0);
        this.acceleration = new Point2D(0, 0);
    }



    public void changerImg(double deltaTemps) {
        // Durée entre deux changements d'image (en secondes)
        double delai = 0.15;

        tempsDepuisDernierChangement += deltaTemps;

        if (tempsDepuisDernierChangement >= delai) {
            // alterner l'image
            if (imgCamelot == 1)
                imgCamelot = 2;
            else
                imgCamelot = 1;

            tempsDepuisDernierChangement = 0; // reset du compteur
        }
    }

    public Image getImgCamelot() {
        if (this.imgCamelot == 1) {
            return new Image("camelot1.png");
        }
        return new Image("camelot2.png");
    }

    public void update(boolean gauche, boolean droite, boolean sauter, double deltatemps) {
        updatePhysique(gauche, droite, sauter, deltatemps);
    }

    public void updatePhysique(boolean gauche, boolean droite, boolean sauter, double deltaTemps) {
        // Empêche d'aller plus vite que +600 pixels/s ou -600 pixels/s
        velocite = new Point2D(
                Math.clamp(velocite.getX(), -600, +600),
                velocite.getY()
        );
        // boucle else if pour vérifier quelles touches sont appuyées
        if (gauche) { //boucle else if ralentir le camelot sans qu'il recule
            if (velocite.getX()>200){
                acceleration = new Point2D(-300, acceleration.getY());
            }else if (velocite.getX()<=200){
                setVelocite(new Point2D(200, 0));
            }
        } else if (droite) { //boucle else if pour accélérer vers la droite
            acceleration = new Point2D(+300, acceleration.getY());
//        } else if (Math.abs(velocite.getX()) > 400) {
//            int signe = velocite.getX() > 0 ? -1 : +1; // force opposée
//            acceleration = new Point2D(signe * 300, acceleration.getY());
        } else if (sauter){
            velocite = new Point2D(velocite.getX(), -500);
            acceleration = new Point2D(velocite.getX(), 1500);
        } else if (pos.getY()>580-144){ //144 est la hauteur du camelot
            setVelocite(new Point2D(velocite.getX(), 0));
            acceleration= new Point2D(velocite.getX(), 0);
            setPos(new Point2D(pos.getX(), 580-144)); //144 est la hauteur du camelot
        }else {
            setVelocite(new Point2D(400, velocite.getY()));
        }

        pos = pos.add(velocite.multiply(deltaTemps)); //X=X0+VxΔt
        velocite = velocite.add(acceleration.multiply(deltaTemps)); //V=V0+AxΔt

    }


    protected void draw(GraphicsContext context, Camera camera) {

        var coordoEcran = camera.coordoEcran(pos);
        context.drawImage(getImgCamelot(), coordoEcran.getX(), coordoEcran.getY());

    }



}

