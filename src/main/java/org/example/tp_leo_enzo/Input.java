package org.example.tp_leo_enzo;

import javafx.scene.input.KeyCode;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Input {

        // Ensemble des touches actuellement appuyées
        private static final Set<KeyCode> touches = new HashSet<>();

        public static void keyPressed(KeyCode code) {
            touches.add(code);
        }

        public static void keyReleased(KeyCode code) {
            touches.remove(code);
        }

        public static boolean isKeyPressed(KeyCode code) {


            return touches.contains(code);
        }














}