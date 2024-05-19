import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class InterfaceMail extends JPanel {
    private PersistanceSQL persistanceSQL;
    private JTextArea contenuArea;

    public InterfaceMail(PersistanceSQL persistanceSQL) {
        this.persistanceSQL = persistanceSQL;

        setLayout(new BorderLayout());

        // Menu avec les boutons
        JPanel menuPanel = new JPanel();
        GridBagLayout gridBagLayout = new GridBagLayout();
        menuPanel.setLayout(gridBagLayout);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // padding

        JButton button30Days = new JButton("30 jours");
        JButton button15Days = new JButton("15 jours");
        JButton button3Days = new JButton("3 jours");

        // Set preferred size for buttons to make them bigger
        Dimension buttonSize = new Dimension(200, 50);
        button30Days.setPreferredSize(buttonSize);
        button15Days.setPreferredSize(buttonSize);
        button3Days.setPreferredSize(buttonSize);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        menuPanel.add(button30Days, gbc);

        gbc.gridy = 1;
        menuPanel.add(button15Days, gbc);

        gbc.gridy = 2;
        menuPanel.add(button3Days, gbc);

        add(menuPanel, BorderLayout.CENTER);

        contenuArea = new JTextArea();
        contenuArea.setLineWrap(true);
        contenuArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(contenuArea);
        add(scrollPane, BorderLayout.SOUTH);

        button30Days.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                afficherClients(30);
                persistanceSQL.generationPDF30jours();
                System.out.println("test");
            }
        });

        button15Days.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                afficherClients(15);
            }
        });

        button3Days.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                afficherClients(3);
            }
        });
    }

    private void afficherClients(int jours) {
        ArrayList<Client> clients = persistanceSQL.recupererClientsPourRelance(jours);
        StringBuilder contenu = new StringBuilder();
        for (Client client : clients) {
            contenu.append("Numéro Client: ").append(client.getNumClient())
                    .append("\nRaison Sociale: ").append(client.getRaisonSociale())
                    .append("\nEmail: ").append(client.getEmail())
                    .append("\nDate d'échéance: ").append(client.getDateEcheance())
                    .append("\n\n");
        }
        contenuArea.setText(contenu.toString());
    }

    /**
     * Méthode statique pour afficher l'interface graphique.
     */
    public static void afficherInterface(PersistanceSQL persistanceSQL) {
        // Création de la fenêtre
        JFrame frame = new JFrame("Interface d'affichage des clients");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setContentPane(new InterfaceMail(persistanceSQL));
        frame.setVisible(true);
    }
}
