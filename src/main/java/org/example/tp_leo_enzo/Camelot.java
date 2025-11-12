package org.example.tp_leo_enzo;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.ArrayList;

public class Camelot extends ObjetDuJeu{
    private int journaux;
    private int argent;
    private ArrayList<Integer> adresses;
    private int imgCamelot;
    private Point2D pos;
    private Point2D velocite;
    private Point2D acc;

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
        this.acc = new Point2D(0,0);
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
        pos = pos.add(acc);

        if (gauche) {
            acc = new Point2D(-1000, acc.getY());
        } else if (droite) {
            acc = new Point2D(+1000, acc.getY());
        } else if (Math.abs(velocite.getX()) > 0.5) {
            int signe = velocite.getX() > 0 ? -1 : +1;
            acc = new Point2D(signe * 500, acc.getY());
        } else {
            velocite = new Point2D(0, velocite.getY());
            acc = new Point2D(0, acc.getY());
        }

    }

    public void draw(GraphicsContext context){
        context.drawImage(getImgCamelot(),pos.getX(),pos.getY());
    }
}

