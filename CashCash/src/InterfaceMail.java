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
        menuPanel.setLayout(new GridLayout(1, 3));

        JButton button30Days = new JButton("30 jours");
        JButton button15Days = new JButton("15 jours");
        JButton button3Days = new JButton("3 jours");

        menuPanel.add(button30Days);
        menuPanel.add(button15Days);
        menuPanel.add(button3Days);

        add(menuPanel, BorderLayout.NORTH);

        contenuArea = new JTextArea();
        contenuArea.setLineWrap(true);
        contenuArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(contenuArea);
        add(scrollPane, BorderLayout.CENTER);

        button30Days.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int nbjour = 30;
                ArrayList<Client> TousClient = persistanceSQL.recupererClientsPourRelance(nbjour);
                for(Client unClient : TousClient){
                    
                }
            }
        });

        button15Days.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int nbjour = 15;
                ArrayList<Client> TousClient = persistanceSQL.recupererClientsPourRelance(nbjour);
                for(Client unClient : TousClient){
                    
                }
            }
        });

        button3Days.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int nbjour = 3;
                ArrayList<Client> TousClient = persistanceSQL.recupererClientsPourRelance(nbjour);
                for(Client unClient : TousClient){
                    
                }
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
