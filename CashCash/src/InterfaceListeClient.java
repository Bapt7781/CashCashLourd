import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class InterfaceListeClient extends JPanel {
    // Attribut
    private PersistanceSQL persistanceSQL;

    /**
     * Constructeur de la classe InterfaceListeClient.
     *
     * @param persistanceSQL L'objet PersistanceSQL utilisé pour charger les clients
     *                       depuis la base de données
     */
    public InterfaceListeClient(PersistanceSQL persistanceSQL) {
        this.persistanceSQL = persistanceSQL;

        setLayout(new BorderLayout());

        DefaultListModel<String> model = new DefaultListModel<>();
        ArrayList<Client> clients = persistanceSQL.chargerTousClients();
        for (Client client : clients) {
            model.addElement(client.getNumClient() + " - " + client.getRaisonSociale());
        }

        JList<String> listeClients = new JList<>(model);
        JScrollPane scrollPane = new JScrollPane(listeClients);
        add(scrollPane, BorderLayout.CENTER);

        JButton choisirClientBtn = new JButton("Choisir ce client");
        choisirClientBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = listeClients.getSelectedIndex();
                if (selectedIndex != -1) {
                    Client clientChoisi = clients.get(selectedIndex);
                    afficherMaterielsClient(clientChoisi);
                } else {
                    JOptionPane.showMessageDialog(null, "Veuillez sélectionner un client.", "Aucun client sélectionné",
                            JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        add(choisirClientBtn, BorderLayout.SOUTH);
    }

    private void afficherMaterielsClient(Client client) {
        ArrayList<Materiel> materiels = persistanceSQL.recupererMaterielsClient(client.getNumClient());
        ArrayList<Materiel> materielsSansContrat = new ArrayList<>();
        ArrayList<Materiel> materielsSousContrat = client.getLeContrat().getLesMaterielsAssures();

        // Séparer les matériels en fonction de leur statut d'assurance
        for (Materiel materiel : materiels) {
            boolean trouve = false;
            for (Materiel materielAvecContrat : materielsSousContrat) {
                if (materiel.getNumSerie() == materielAvecContrat.getNumSerie()) {
                    trouve = true;
                    break;
                }
            }
            if (!trouve) {
                materielsSansContrat.add(materiel);
            }
        }

        // Créer une nouvelle fenêtre pour afficher les matériels du client
        JFrame frame = new JFrame("Matériels du client : " + client.getRaisonSociale());
        frame.setLayout(new BorderLayout()); // Utiliser un layout de bordure

        // Créer le panneau pour la partie supérieure (liste déroulante + bouton)
        JPanel panelHaut = new JPanel();
        panelHaut.setLayout(new BorderLayout()); // Utiliser un layout en bordure

        // Créer la liste des matériels sans contrat
        DefaultListModel<String> materielsSansContratListModel = new DefaultListModel<>();
        for (Materiel materiel : materielsSansContrat) {
            TypeMateriel typeMateriel = materiel.getLeType();
            if (typeMateriel != null) {
                String libelleTypeMateriel = typeMateriel.getLibelleTypeMateriel();
                String emplacement = materiel.getEmplacement();
                int numSerie = materiel.getNumSerie();
                String Materiel = "Numéro série : " + numSerie + " - " + emplacement + " - " + libelleTypeMateriel;
                materielsSansContratListModel.addElement(Materiel);
            }
        }
        JList<String> materielsSansContratList = new JList<>(materielsSansContratListModel);
        materielsSansContratList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        panelHaut.add(new JScrollPane(materielsSansContratList), BorderLayout.CENTER);

        // Créer le panneau pour la partie inférieure (affichage des matériels sous
        // contrat)
        JPanel panelBas = new JPanel();
        panelBas.setLayout(new BoxLayout(panelBas, BoxLayout.Y_AXIS)); // Utiliser un layout en colonne
        JLabel labelSousContrat = new JLabel("Matériels sous contrat de maintenance :");
        panelBas.add(labelSousContrat);
        for (Materiel materiel : materielsSousContrat) {
            JLabel label = new JLabel(materiel.getNumSerie() + " - " +
                    (materiel.getLeType() != null ? materiel.getLeType().getLibelleTypeMateriel()
                            : "Type de matériel inconnu")
                    +
                    " - " + materiel.getEmplacement());
            panelBas.add(label);
        }

        // Créer le bouton pour ajouter le matériel au contrat de maintenance du client
        JButton ajouterAuContratBtn = new JButton("Ajouter au contrat de maintenance du client");
        ajouterAuContratBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String materielChoisi = materielsSansContratList.getSelectedValue();
                if (materielChoisi != null) {
                    int debut = materielChoisi.indexOf(":") + 1;
                    int fin = materielChoisi.indexOf("-");
                    String numSerieStr = materielChoisi.substring(debut, fin).trim();
                    int numSerie = Integer.parseInt(numSerieStr);
                    // Trouver le matériel correspondant dans la liste des matériels sans contrat
                    Materiel materielAffecte = null;
                    for (Materiel materiel : materielsSansContrat) {
                        if (materiel.getNumSerie() == numSerie) {
                            materielAffecte = materiel;
                            break;
                        }
                    }
                    // Ajouter le matériel au contrat de maintenance du client
                    if (materielAffecte != null) {
                        client.getLeContrat().ajouteMateriel(materielAffecte);
                        JOptionPane.showMessageDialog(frame,
                                "Matériel ajouté au contrat de maintenance du client : " + numSerie);

                        // Retirer le matériel de la liste supérieure
                        materielsSansContratListModel.removeElement(materielChoisi);

                        // Ajouter le matériel à la liste inférieure
                        String libelleTypeMateriel = materielAffecte.getLeType().getLibelleTypeMateriel();
                        String emplacement = materielAffecte.getEmplacement();
                        String nouveauMateriel = numSerie + " - " + libelleTypeMateriel + " - " + emplacement;
                        client.getLeContrat().ajouteMateriel(materielAffecte);

                        JLabel nouveauLabel = new JLabel(nouveauMateriel);
                        panelBas.add(nouveauLabel);

                        // Rafraîchir l'affichage
                        frame.revalidate();
                        frame.repaint();
                    } else {
                        JOptionPane.showMessageDialog(frame, "Le matériel sélectionné n'existe pas.",
                                "Matériel inexistant",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Veuillez sélectionner un matériel.",
                            "Aucun matériel sélectionné",
                            JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        panelHaut.add(ajouterAuContratBtn, BorderLayout.SOUTH);

        frame.add(panelHaut, BorderLayout.NORTH);

        JScrollPane scrollPane = new JScrollPane(panelBas);
        frame.add(scrollPane, BorderLayout.CENTER);

        frame.setSize(500, 500); // Taille ajustée pour contenir les deux parties
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

}
