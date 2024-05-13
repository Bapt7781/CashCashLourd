import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

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

                // Envoi du courrier par e-mail
                EmailSender.sendEmail(email, "Rappel de contrat de maintenance", contenu);

                // Ajouter une pause pour éviter de surcharger le serveur de messagerie
                Thread.sleep(1000);
            }

            // Fermer la connexion à la base de données
            connection.close();

        } catch (SQLException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws SchedulerException {
        // Créer une instance du planificateur de tâches
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

        // Démarer le planificateur
        scheduler.start();

        // Définir la tâche planifiée pour s'exécuter tous les jours à minuit
        JobDetail job = JobBuilder.newJob()
                .ofType(GenerateurPDF.class)
                .withIdentity("generateurPDFJob", "group1")
                .build();

        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("trigger1", "group1")
                .startNow()
                .withSchedule(CronScheduleBuilder.dailyAtHourAndMinute(0, 0))
                .build();

        // Ajouter la tâche planifiée au planificateur
        scheduler.scheduleJob(job, trigger);
    }
}
