package org.example.tp_leo_enzo.maison;

import javafx.geometry.Point2D;

import java.util.ArrayList;

public class Mur {
    ArrayList<ArrayList<Point2D>> coordBriques;
    final int w = 192;
    final int h = 96;
    public Mur(){
        coordBriques = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            coordBriques.add(new ArrayList<>());
            for (int j = 0; j < 7; j++) {
                coordBriques.get(i).add(new Point2D(i*w,j*h));
            }
        }
    }
    public void updatePhysics(Point2D coordsCam){
        if(coordBriques.getFirst().getFirst().getX()+w<coordsCam.getX()){

            enleverColonne();
            ajouterColonne();
        }

    }
    private void enleverColonne(){
        coordBriques.removeFirst();
    }
    private void ajouterColonne(){
        coordBriques.add(new ArrayList<>());
        for (int i = 0; i < 7; i++) {
            coordBriques.getLast().add(new Point2D(coordBriques.get(coordBriques.size()-2).get(0).getX()+w,i*h));
        }

    }

    public ArrayList<ArrayList<Point2D>> getCoordBriques() {
        return coordBriques;
    }
}
