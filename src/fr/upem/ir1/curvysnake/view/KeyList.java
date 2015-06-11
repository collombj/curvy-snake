package fr.upem.ir1.curvysnake.view;
/* ############################################################################
 * 
 * TestKeyListener.java : démonstration de la capture d'événements issus du
 *                        clavier.
 * 
 * Auteur : Christophe Jacquet, Supélec
 * 
 * Historique
 * 2006-12-19  Création
 * 
 * ############################################################################
 */
 
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
 
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
 
 
/**
 * Programme de test des KeyList : affiche une fenêtre où un JLabel
 * affiche les codes de touches appuyées et relâchées.
 */

class KeyList implements KeyListener {
    private final JLabel label;
     
    public KeyList(JLabel label_) {
        label = label_;
    }
 
    public void keyPressed(KeyEvent e) {
        label.setText("Touche pressée : " + e.getKeyCode() + 
                " (" + e.getKeyChar() + ")");
    }
 
    public void keyReleased(KeyEvent e) {
        label.setText("Touche relâchée : " + e.getKeyCode() +
                " (" + e.getKeyChar() + ")");
    }
 
    public void keyTyped(KeyEvent e) {
        // on ne fait rien
    }
}