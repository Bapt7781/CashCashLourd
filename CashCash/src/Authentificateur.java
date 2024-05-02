import javax.swing.*;
import java.sql.*;

/**
 * Cette classe gère l'authentification de l'utilisateur.
 */
public class Authentificateur {

    private static final String URL = "jdbc:mysql://localhost/cashcash";
    private static final String UTILISATEUR = "root";
    private static final String MOT_DE_PASSE = "";

    /**
     * Authentifie l'utilisateur en vérifiant les identifiants dans la base de
     * données.
     *
     * @return true si l'authentification est réussie, sinon false
     */
    public boolean authentifier() {
        boolean authentificationReussie = false;
        do {
            String nomUtilisateur = JOptionPane.showInputDialog(null, "Nom d'utilisateur :");
            String motDePasse = JOptionPane.showInputDialog(null, "Mot de passe :", "Connexion",
                    JOptionPane.PLAIN_MESSAGE);
            try {
                authentificationReussie = validerConnexion(nomUtilisateur, motDePasse);
                if (!authentificationReussie) {
                    JOptionPane.showMessageDialog(null, "Identifiants incorrects. Veuillez réessayer.", "Erreur",
                            JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Erreur lors de la connexion à la base de données.", "Erreur",
                        JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
                System.exit(1);
            }
        } while (!authentificationReussie);

        // Une fois l'authentification réussie, afficher le menu principal
        MenuPrincipal menuPrincipal = new MenuPrincipal();
        menuPrincipal.afficherMenu();
        return true;
    }

    /**
     * Vérifie l'authentification dans la base de données.
     *
     * @param nomUtilisateur Le nom d'utilisateur saisi
     * @param motDePasse     Le mot de passe saisi
     * @return true si l'authentification est réussie, sinon false
     * @throws SQLException en cas d'erreur de base de données
     */
    private boolean validerConnexion(String nomUtilisateur, String motDePasse) throws SQLException {
        String requeteSQL = "SELECT * FROM employe WHERE NomEmploye = ? AND MotDePasse = SHA2(?, 256)";
        try (Connection connexion = DriverManager.getConnection(URL, UTILISATEUR, MOT_DE_PASSE);
                PreparedStatement statement = connexion.prepareStatement(requeteSQL)) {
            statement.setString(1, nomUtilisateur);
            statement.setString(2, motDePasse);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        }
    }
}