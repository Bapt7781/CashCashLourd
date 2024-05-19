////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class InterfaceMail extends JPanel {
    // Constructeur
    public InterfaceMail() {
        // Utilisation d'un BorderLayout
        setLayout(new BorderLayout());

        // Panneau pour les boutons
        JPanel boutonsPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        boutonsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Création des boutons avec leurs textes
        JButton bouton1 = new JButton("30 jours");
        JButton bouton2 = new JButton("15 jours");
        JButton bouton3 = new JButton("3 jours");

        // Ajout des boutons au panneau
        boutonsPanel.add(bouton1);
        boutonsPanel.add(bouton2);
        boutonsPanel.add(bouton3);

        // Ajout du panneau des boutons au centre du layout
        add(boutonsPanel, BorderLayout.CENTER);

        // Gestion des actions des boutons
        bouton1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Action du Bouton 1.", "Message",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });

        bouton2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Action du Bouton 2.", "Message",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });

        bouton3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Action du Bouton 3.", "Message",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }

    /**
     * Méthode statique pour afficher l'interface graphique.
     */
    public static void afficherInterface() {
        // Création de la fenêtre
        JFrame frame = new JFrame("Interface ");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setContentPane(new InterfaceMail());
        frame.setVisible(true);
    }
}
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////