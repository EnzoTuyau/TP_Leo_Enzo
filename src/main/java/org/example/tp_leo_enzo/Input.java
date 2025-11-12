package org.example.tp_leo_enzo;

import javafx.scene.input.KeyCode;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Input {
    // Ensemble des touches sur lesquelles on appuie en ce moment
    private static Set<KeyCode> touches = new HashSet<>();

    // personnage.update() va demander si certaines touches sont
    // appuyées ou non
    public static boolean isKeyPressed(KeyCode code) {
        return touches.contains(code);
    }

    // Dans le Main, on va écouter les événements sur la scène
    // et modifier l'état quand les touches changent
    public static void setKeyPressed(KeyCode code, boolean appuie) {
        if(appuie)
            touches.add(code);
        else
            touches.remove(code);
    }
}