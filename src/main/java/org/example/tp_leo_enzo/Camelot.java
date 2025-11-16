package org.example.tp_leo_enzo;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.ArrayList;

public class Camelot extends ObjetDuJeu {
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

    public Point2D getVelocite() {
        return velocite;
    }

    public void setVelocite(Point2D velocite) {
        this.velocite = velocite;
    }

    public Camelot(ArrayList<Integer> adresses) {
        this.journaux = 24;
        this.argent = 0;
        this.adresses = adresses;
        this.imgCamelot = 1;
        this.pos = new Point2D(100, 300);
        this.velocite = new Point2D(0, 0);
        this.acceleration = new Point2D(0, 0);
    }

    public void ajouter12Journaux() {
        this.journaux = this.journaux + 12;
    }

    public int getJournaux() {
        return this.argent;
    }

    public void ajouterArgent(int argent) {
        this.argent = this.argent + argent;
    }

    public int getArgent() {
        return this.argent;
    }

    public void changerImg() {
        if (this.imgCamelot == 1) {
            this.imgCamelot = 2;
        } else {
            this.imgCamelot = 1;
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
        // Empêche d'aller plus vite que +300 ou -300
        velocite = new Point2D(
                Math.clamp(velocite.getX(), -600, +600),
                velocite.getY()
        );

        if (gauche) {
            if (velocite.getX()>200){
                acceleration = new Point2D(-300, acceleration.getY());
            }else if (velocite.getX()<=200){
                setVelocite(new Point2D(200, velocite.getY()));
            }
        } else if (droite) {
            acceleration = new Point2D(+300, acceleration.getY());
        } else if (Math.abs(velocite.getX()) > 400) {
            int signe = velocite.getX() > 0 ? -1 : +1; // force opposée
            acceleration = new Point2D(signe * 300, acceleration.getY());
        } else if (sauter){
            velocite = new Point2D(velocite.getX(), -500);
            acceleration = new Point2D(0, acceleration.getY());
        } else {
            setVelocite(new Point2D(400, velocite.getY()));
        }
        pos = pos.add(velocite.multiply(deltaTemps)); //X=X0+VxΔt
        velocite = velocite.add(acceleration.multiply(deltaTemps)); //V=V0+AxΔt

    }

    @Override
    protected void draw(GraphicsContext context, Camera camera) {

        var coordoEcran = camera.coordoEcran(pos);
        context.drawImage(getImgCamelot(), coordoEcran.getX(), coordoEcran.getY());
    }


}

