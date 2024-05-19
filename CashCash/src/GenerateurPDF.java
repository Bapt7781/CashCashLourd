////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
import java.sql.*;
import java.util.Date;

public class GenerateurPDF {

    public void execute() {
        try {
            // Établir la connexion à la base de données
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/cashcash", "", "");

            // Récupérer la date actuelle
            Date currentDate = new Date();

            // Requête pour récupérer les contrats arrivant à échéance
            String query = "SELECT NumeroClient, RaisonSociale, Email FROM client " +
                    "JOIN contratdemaintenance ON client.NumeroClient = contratdemaintenance.NumeroClient " +
                    "WHERE DateEcheance <= ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setDate(1, new java.sql.Date(currentDate.getTime()));
            ResultSet resultSet = statement.executeQuery();

            // Créer le document PDF et envoyer le courrier pour chaque contrat
            while (resultSet.next()) {
                int numeroClient = resultSet.getInt("NumeroClient");
                String raisonSociale = resultSet.getString("RaisonSociale");
                String email = resultSet.getString("Email");

                // Générer le contenu du courrier de relance
                String contenu = "Cher " + raisonSociale + "Nous vous rappelons que votre contrat de maintenance arrive à échéance. " +
                        "Merci de prendre les mesures nécessaires pour son renouvellement Cordialement,Votre société de maintenance";

                InterfaceMail.afficherInterface();

                // Ajouter une pause pour éviter de surcharger le serveur de messagerie
                Thread.sleep(1000);
            }

            // Fermer la connexion à la base de données
            connection.close();

        } catch (SQLException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////