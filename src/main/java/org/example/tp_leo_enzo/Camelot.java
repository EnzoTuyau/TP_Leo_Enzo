package org.example.tp_leo_enzo;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.ArrayList;

public class Camelot {
    private int journaux;
    private int argent;
    private ArrayList<Integer> adresses;
    private int imgCamelot;
    private Point2D pos;
    private Point2D velocite;
    private Point2D acceleration;

    public Point2D getPos() {
        return pos;
    }

    public void setPos(Point2D pos) {
        this.pos = pos;
    }

    public Camelot(ArrayList<Integer> adresses){
        this.journaux = 24;
        this.argent = 0;
        this.adresses = adresses;
        this.imgCamelot = 1;
        this.pos = new Point2D(100,300);
        this.velocite = new Point2D(0,0);
        this.acceleration = new Point2D(0,0);
    }

    public void ajouter12Journaux(){
        this.journaux = this.journaux + 12;
    }

    public int getJournaux(){
        return this.argent;
    }

    public void ajouterArgent(int argent){
        this.argent = this.argent + argent;
    }

    public int getArgent(){
        return this.argent;
    }

    public void changerImg(){
        if(this.imgCamelot==1){
            this.imgCamelot=2;
        }else{
            this.imgCamelot=1;
        }
    }

    public Image getImgCamelot(){
        if(this.imgCamelot==1){
            return new Image("camelot1.png");
        }
        return new Image("camelot2.png");
    }

    public void updatePhysique(boolean gauche, boolean droite){
        pos = pos.add(velocite);
        pos = pos.add(acceleration);

        if (gauche) {
            acceleration = new Point2D(-1000, acceleration.getY());
        } else if (droite) {
            acceleration = new Point2D(+1000, acceleration.getY());
        } else if (Math.abs(velocite.getX()) > 0.5) {
            int signe = velocite.getX() > 0 ? -1 : +1;
            acceleration = new Point2D(signe * 500, acceleration.getY());
        } else {
            velocite = new Point2D(0, velocite.getY());
            acceleration = new Point2D(0, acceleration.getY());
        }

    }

    public void draw(GraphicsContext context){
        context.drawImage(getImgCamelot(),pos.getX(),pos.getY());
    }
}

