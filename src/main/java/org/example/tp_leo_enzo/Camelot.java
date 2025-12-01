package org.example.tp_leo_enzo;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;



public class Camelot extends ObjetDuJeu {
    private int journaux;
    private int argent;
    private int imgCamelot;
    private double tempsDepuisDernierChangement = 0;

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
        return this.journaux;
    }

    public int getArgent() {
        return this.argent;
    }

    public void ajouter12Journaux() {
        this.journaux = this.journaux + 12;
    }

    public void ajouter24Journaux() {
        this.journaux = this.journaux + 24;
    }

    public void ajouter10Journaux() {
        this.journaux = this.journaux + 10;
    }

    public void viderJournaux() {
        this.journaux = 0;
    }

    public void ajouterArgent(int argent) {
        this.argent = this.argent + argent;
    }

    public void lancerJournal() {
        this.journaux = this.journaux - 1;
    }

    //constructeur
    public Camelot() {
        taille = new Point2D(172, 144);
        this.journaux = 24;
        this.argent = 0;
        this.imgCamelot = 1;
        pos = new Point2D(180, 580 - taille.getY());
        velocite = new Point2D(400, 0);
        acceleration = new Point2D(0, 0);
    }

    public void changerImg(double deltaTemps) {
        // Durée minimale entre deux changements d'image (en secondes)
        double delai = 0.15;

        // On ajoute le temps écoulé depuis la dernière frame
        tempsDepuisDernierChangement += deltaTemps;

        // Si suffisamment de temps est passé, on change d'image
        if (tempsDepuisDernierChangement >= delai) {

            // Alterne entre l'image 1 et l'image 2
            if (imgCamelot == 1)
                imgCamelot = 2;
            else
                imgCamelot = 1;

            // Réinitialise le compteur pour recommencer le cycle
            tempsDepuisDernierChangement = 0;
        }
    }

    public Image getImgCamelot() {
        if (this.imgCamelot == 1) {
            return new Image("camelot1.png");
        }
        return new Image("camelot2.png");
    }

    public void updatePhysique(boolean gauche, boolean droite, boolean sauter, double deltaTemps) {
        // Empêche d'aller plus vite que +600 pixels/s ou -600 pixels/s
        velocite = new Point2D(
                Math.clamp(velocite.getX(), -600, +600),
                velocite.getY()
        );
        // boucle else if pour vérifier quelles touches sont appuyées
        if (gauche) { //boucle else if ralentir le camelot sans qu'il recule
            if (velocite.getX() > 200) {
                acceleration = new Point2D(-300, acceleration.getY());
            } else if (velocite.getX() <= 200) {
                setVelocite(new Point2D(200, 0));
            }
        } else if (velocite.getX() < 400) { //boucle else if pour réaccélérer
            acceleration = new Point2D(300, acceleration.getY());
        } else if (droite) { //boucle else if pour accélérer vers la droite
            acceleration = new Point2D(+300, acceleration.getY());
        } else if (sauter) { //boucle else if pour sauter
            velocite = new Point2D(velocite.getX(), -500);
            acceleration = new Point2D(acceleration.getX(), 1500);
        } else if (pos.getY() > 580 - taille.getY()) {
            setVelocite(new Point2D(velocite.getX(), 0));
            acceleration = new Point2D(velocite.getX(), 0);
            setPos(new Point2D(pos.getX(), 580 - taille.getY()));
        } else { //boucle else pour ralentir une fois qu'on ne touche plus rien
            double accelerationFrein = -300;
            acceleration = new Point2D(accelerationFrein, acceleration.getY());
        }
        pos = pos.add(velocite.multiply(deltaTemps)); //X=X0+VxΔt
        velocite = velocite.add(acceleration.multiply(deltaTemps)); //V=V0+AxΔt

    }

    protected void draw(GraphicsContext context, Camera camera) {

        var coordoEcran = camera.coordoEcran(pos);
        context.drawImage(getImgCamelot(), coordoEcran.getX(), coordoEcran.getY());

    }


}
