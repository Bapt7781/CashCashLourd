import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

// Cette classe affiche le menu principal après une authentification réussie
public class MenuPrincipal extends JPanel {
    // Constructeur
    public MenuPrincipal() {
        // Utilisation d'un BorderLayout
        setLayout(new BorderLayout());

        // Panneau pour les boutons du menu
        JPanel boutonsPanel = new JPanel(new GridLayout(4, 1, 10, 10));
        boutonsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Création des boutons avec leurs icônes
        JButton deconnexionBtn = new JButton("Se déconnecter");
        JButton fichierXMLBtn = new JButton("Générer fichier XML client");
        JButton contratMaintenanceBtn = new JButton("Couvrir par un contrat de maintenance");
        JButton courriersBtn = new JButton("Générer des courriers automatiques de relance");

        // Ajout des boutons au panneau
        boutonsPanel.add(deconnexionBtn);
        boutonsPanel.add(fichierXMLBtn);
        boutonsPanel.add(contratMaintenanceBtn);
        boutonsPanel.add(courriersBtn);

        // Ajout du panneau des boutons au centre du layout
        add(boutonsPanel, BorderLayout.CENTER);

        // Gestion des actions des boutons
        deconnexionBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Déconnexion réussie.", "Déconnexion",
                        JOptionPane.INFORMATION_MESSAGE);
                System.exit(0); // Quitter l'application après la déconnexion
            }
        });

        fichierXMLBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    new Materiel();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });

        contratMaintenanceBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                PersistanceSQL persistanceSQL = new PersistanceSQL("localhost", 3306, "cashcash");
                JFrame frame = new JFrame("Liste des Clients");
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.setSize(400, 300);
                frame.setContentPane(new InterfaceListeClient(persistanceSQL));
                frame.setVisible(true);
            }
        });

        
        contratMaintenanceBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                PersistanceSQL persistanceSQL = new PersistanceSQL("localhost", 3306, "cashcash");
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        new InterfaceMail(persistanceSQL);
                    }
                });
            }
        });
    }
    
    

    /**
     * Affiche le menu principal dans une fenêtre séparée.
     */
    public static void afficherMenu() {
        // Création de la fenêtre principale
        JFrame frame = new JFrame("Menu Principal");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setContentPane(new MenuPrincipal());
        frame.setVisible(true);
    }
}
