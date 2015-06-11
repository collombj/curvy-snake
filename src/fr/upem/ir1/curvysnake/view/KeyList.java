package fr.upem.ir1.curvysnake.view;
/* ############################################################################
 * 
 * TestKeyListener.java : d�monstration de la capture d'�v�nements issus du
 *                        clavier.
 * 
 * Auteur : Christophe Jacquet, Sup�lec
 * 
 * Historique
 * 2006-12-19  Cr�ation
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
 * Programme de test des KeyList : affiche une fen�tre o� un JLabel
 * affiche les codes de touches appuy�es et rel�ch�es.
 */

class KeyList implements KeyListener {
    private final JLabel label;
     
    public KeyList(JLabel label_) {
        label = label_;
    }
 
    public void keyPressed(KeyEvent e) {
        label.setText("Touche press�e : " + e.getKeyCode() + 
                " (" + e.getKeyChar() + ")");
    }
 
    public void keyReleased(KeyEvent e) {
        label.setText("Touche rel�ch�e : " + e.getKeyCode() +
                " (" + e.getKeyChar() + ")");
    }
 
    public void keyTyped(KeyEvent e) {
        // on ne fait rien
    }
}