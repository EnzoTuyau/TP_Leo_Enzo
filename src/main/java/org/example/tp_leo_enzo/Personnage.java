package org.example.tp_leo_enzo;


import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

public class Personnage {
    // position = coin en haut à gauche du flocon
    protected Point2D position = Point2D.ZERO;
    protected Point2D taille = new Point2D(120, 60);

    // Physique
    protected Point2D velocite = Point2D.ZERO;
    protected Point2D acceleration = new Point2D(0, 600);

    protected boolean toucheLeSol;

    public Personnage() {
        position = new Point2D(
                MainJavaFX.WIDTH / 2.0 - taille.getX() / 2.0,
                MainJavaFX.HEIGHT - taille.getY());
        toucheLeSol = true;
    }

    public void update(double deltaTemps) {
        boolean gauche = Input.isKeyPressed(KeyCode.LEFT);
        boolean droite = Input.isKeyPressed(KeyCode.RIGHT);

        // Mouvement horizontal
        if (gauche)
            velocite = new Point2D(-300, velocite.getY());
        else if (droite)
            velocite = new Point2D(+300, velocite.getY());
        else // Pas de flèche appuyée
            velocite = new Point2D(0, velocite.getY());

        // Sauter avec Espace ou Flèche vers le haut
        boolean jump = Input.isKeyPressed(KeyCode.SPACE)
                || Input.isKeyPressed(KeyCode.UP);

        // Sauter = donner une vitesse vers le haut
        if (toucheLeSol && jump) {
            velocite = new Point2D(velocite.getX(), -300);
            toucheLeSol = false;
        }

        velocite = velocite.add(acceleration.multiply(deltaTemps));
        position = position.add(velocite.multiply(deltaTemps));

        if (position.getY() + taille.getY() >= MainJavaFX.HEIGHT) {
            toucheLeSol = true;
            velocite = new Point2D(velocite.getX(), 0);
        }

        position = new Point2D(
                Math.clamp(position.getX(), 0, MainJavaFX.WIDTH - taille.getX()),
                Math.clamp(position.getY(), 0, MainJavaFX.HEIGHT - taille.getY())
        );
    }


    public void draw(GraphicsContext context) {
        // Corps
        context.setFill(Color.web("#3D77E5"));
        context.fillRect(position.getX(), position.getY(), taille.getX(), taille.getY());

        // Yeux
        double hauteurYeux = taille.getY() / 6.0;
        context.setFill(Color.BLACK);
        context.fillOval(
                position.getX() + taille.getX() * 0.25 - hauteurYeux / 2.0,
                position.getY() + taille.getY() * 0.2,
                hauteurYeux, hauteurYeux);
        context.fillOval(
                position.getX() + taille.getX() * 0.75 - hauteurYeux / 2.0,
                position.getY() + taille.getY() * 0.2,
                hauteurYeux, hauteurYeux);

        // Bouche
        context.strokeLine(
                position.getX() + taille.getX() * 0.2,
                position.getY() + taille.getY() * 0.7,
                position.getX() + taille.getX() * 0.8,
                position.getY() + taille.getY() * 0.8);
    }
}