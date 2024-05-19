import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.Statement;

public class PersistanceSQL {
    private final String URL;

    public PersistanceSQL(String ipBase, int port, String nomBaseDonnee) {
        this.URL = "jdbc:mysql://" + ipBase + ":" + port + "/" + nomBaseDonnee;
    }

    public void rangerDansBase(Object unObjet) {
        // Code pour stocker les données de l'objet dans la base de données
        // (à implémenter selon vos besoins)
    }

    public ArrayList<Client> chargerTousClients() {
        ArrayList<Client> clients = new ArrayList<>();
        String requeteSQL = "SELECT * FROM client";
        try (Connection connexion = DriverManager.getConnection(URL, "root", "");
                PreparedStatement statement = connexion.prepareStatement(requeteSQL);
                ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Client client = new Client();
                client.setNumClient(resultSet.getString("NumeroClient"));
                client.setRaisonSociale(resultSet.getString("RaisonSociale"));
                client.setSiren(resultSet.getString("Siren"));
                client.setCodeApe(resultSet.getString("CodeApe"));
                client.setAdresse(resultSet.getString("Adresse"));
                client.setTelClient(resultSet.getString("TelephoneCLient"));
                client.setEmail(resultSet.getString("Email"));
                client.setDureeDeplacement(resultSet.getInt("DureeDeplacement"));
                client.setDistanceKm(resultSet.getInt("DistanceKm"));
                PersistanceSQL persistanceSQL = new PersistanceSQL("localhost", 3306, "cashcash");
                ContratMaintenance leContrat = (ContratMaintenance) persistanceSQL.chargerDepuisBase(
                        resultSet.getInt("NumeroClient"),
                        "contratdemaintenance");
                client.setLeContrat(leContrat);
                // Ajouter le client à la liste
                clients.add(client);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clients;
    }

    public ArrayList<Materiel> recupererMaterielsClient(String numeroClient) {
        ArrayList<Materiel> materiels = new ArrayList<>();
        String requeteSQL = "SELECT * FROM materiel WHERE NumeroClient = ?";
        try (Connection connexion = DriverManager.getConnection(URL, "root", "");
                PreparedStatement statement = connexion.prepareStatement(requeteSQL)) {
            statement.setString(1, numeroClient);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int numSerie = resultSet.getInt("NumeroDeSerie");
                Materiel materiel = new Materiel(numSerie);
                materiel.setNumClient(resultSet.getInt("NumeroClient"));
                materiel.setDateVente(resultSet.getDate("DateDeVente"));
                materiel.setDateInstallation(resultSet.getDate("DateInstallation"));
                materiel.setPrixVente(resultSet.getDouble("PrixDeVente"));
                materiel.setEmplacement(resultSet.getString("Emplacement"));
                PersistanceSQL persistanceSQL = new PersistanceSQL("localhost", 3306, "cashcash");
                ArrayList<TypeMateriel> typesMateriel = persistanceSQL.chargerTousTypesMateriel();
                String referenceInterne = resultSet.getString("ReferenceInterne");
                for (TypeMateriel type : typesMateriel) {
                    if (type.getReferenceInterne().equals(referenceInterne)) {
                        materiel.setLeType(type);
                        break;
                    }
                }
                materiels.add(materiel);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return materiels;
    }

    public ArrayList<TypeMateriel> chargerTousTypesMateriel() {
        ArrayList<TypeMateriel> typesMateriel = new ArrayList<>();
        String requeteSQL = "SELECT * FROM typemateriel";
        try (Connection connexion = DriverManager.getConnection(URL, "root", "");
                Statement statement = (Statement) connexion.createStatement();
                ResultSet resultSet = statement.executeQuery(requeteSQL)) {
            while (resultSet.next()) {
                TypeMateriel typeMateriel = new TypeMateriel();
                typeMateriel.setReferenceInterne(resultSet.getString("ReferenceInterne"));
                typeMateriel.setLibelleTypeMateriel(resultSet.getString("LibelleTypeMateriel"));
                // Autres attributs à définir selon votre modèle de données
                typesMateriel.add(typeMateriel);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return typesMateriel;
    }

    public ArrayList<Materiel> chargerMatérielsAssures(String numeroContrat) {
        ArrayList<Materiel> materielsAssures = new ArrayList<>();
        String requeteSQL = "SELECT * FROM Materiel WHERE NumeroDeContrat = ?";
        try (Connection connexion = DriverManager.getConnection(URL, "root", "");
                PreparedStatement statement = connexion.prepareStatement(requeteSQL)) {
            statement.setString(1, numeroContrat);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int numSerie = resultSet.getInt("NumeroDeSerie");
                Materiel materiel = new Materiel(numSerie);
                materiel.setNumClient(resultSet.getInt("NumeroClient"));
                materiel.setDateVente(resultSet.getDate("DateDeVente"));
                materiel.setDateInstallation(resultSet.getDate("DateInstallation"));
                materiel.setPrixVente(resultSet.getDouble("PrixDeVente"));
                materiel.setEmplacement(resultSet.getString("Emplacement"));
                PersistanceSQL persistanceSQL = new PersistanceSQL("localhost", 3306, "cashcash");
                ArrayList<TypeMateriel> typesMateriel = persistanceSQL.chargerTousTypesMateriel();
                String referenceInterne = resultSet.getString("ReferenceInterne");
                for (TypeMateriel type : typesMateriel) {
                    if (type.getReferenceInterne().equals(referenceInterne)) {
                        materiel.setLeType(type);
                        break;
                    }
                }
                // Autres attributs à définir selon votre modèle de données
                materielsAssures.add(materiel);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return materielsAssures;
    }

    public Object chargerDepuisBase(int id, String nomClasse) {
        String requeteSQL = "";
        if (nomClasse == "Materiel") {
            requeteSQL = "SELECT * FROM " + nomClasse + " WHERE NumeroDeSerie = ?";
        } else if (nomClasse == "TypeMateriel") {
            requeteSQL = "SELECT * FROM " + nomClasse + " WHERE ReferenceInterne = ?";
        } else if (nomClasse == "contratdemaintenance") {
            requeteSQL = "SELECT * FROM " + nomClasse + " WHERE NumeroClient = ?";
        } else if (nomClasse == "Client") {
            requeteSQL = "SELECT * FROM " + nomClasse + " WHERE NumeroClient = ?";
        } else {
            return null;
        }
        try (Connection connexion = DriverManager.getConnection(URL, "root", "");
                PreparedStatement statement = connexion.prepareStatement(requeteSQL)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                switch (nomClasse) {
                    case "Materiel":
                        int numSerie = resultSet.getInt("numSerie");
                        Materiel materiel = new Materiel(numSerie);
                        materiel.setNumClient(resultSet.getInt("numClient"));
                        materiel.setDateVente(resultSet.getDate("dateVente"));
                        materiel.setDateInstallation(resultSet.getDate("dateInstallation"));
                        materiel.setPrixVente(resultSet.getDouble("prixVente"));
                        materiel.setEmplacement(resultSet.getString("emplacement"));
                        // Charger le type de matériel associé (si nécessaire)
                        // TypeMateriel leType =
                        // persistanceSQL.chargerDepuisBase(resultSet.getString("referenceInterne"),
                        // "TypeMateriel");
                        // materiel.setLeType(leType);
                        return materiel;
                    case "Client":
                        Client client = new Client();
                        client.setNumClient(resultSet.getString("numClient"));
                        client.setRaisonSociale(resultSet.getString("raisonSociale"));
                        client.setSiren(resultSet.getString("siren"));
                        client.setCodeApe(resultSet.getString("codeApe"));
                        client.setAdresse(resultSet.getString("adresse"));
                        client.setTelClient(resultSet.getString("telClient"));
                        client.setEmail(resultSet.getString("email"));
                        client.setDureeDeplacement(resultSet.getInt("dureeDeplacement"));
                        client.setDistanceKm(resultSet.getInt("distanceKm"));
                        // Charger les matériels du client (si nécessaire)
                        // client.setLesMateriels(persistanceSQL.chargerMatérielsClient(client.getNumClient()));
                        // Charger le contrat du client (si nécessaire)
                        // client.setLeContrat(persistanceSQL.chargerDepuisBase(client.getNumClient(),
                        // "ContratMaintenance"));
                        return client;
                    case "contratdemaintenance":
                        ContratMaintenance contratMaintenance = new ContratMaintenance();
                        contratMaintenance.setNumContrat(resultSet.getString("NumeroDeContrat"));
                        contratMaintenance.setDateSignature(resultSet.getDate("DateSignature"));
                        contratMaintenance.setDateEcheance(resultSet.getDate("DateEcheance"));
                        PersistanceSQL persistanceSQL = new PersistanceSQL("localhost", 3306, "cashcash");
                        contratMaintenance.setLesMaterielsAssures(
                                persistanceSQL.chargerMatérielsAssures(contratMaintenance.getNumContrat()));
                        return contratMaintenance;
                    case "TypeMateriel":
                        TypeMateriel typeMateriel = new TypeMateriel();
                        typeMateriel.setReferenceInterne(resultSet.getString("referenceInterne"));
                        typeMateriel.setLibelleTypeMateriel(resultSet.getString("libelleTypeMateriel"));
                        // Charger la famille du type de matériel (si nécessaire)
                        // typeMateriel.setLaFamille(persistanceSQL.chargerDepuisBase(resultSet.getString("referenceFamille"),
                        // "Famille"));
                        return typeMateriel;
                    default:
                        return null; // Si le nom de la classe n'est pas reconnu
                }
            } else {
                return null; // Aucun résultat trouvé
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void ajouterNumeroContratAuMateriel(String numContrat, int numSerie) {
        String requeteSQL = "UPDATE materiel SET NumeroDeContrat = ? WHERE NumeroDeSerie = ?";

        try (Connection connexion = DriverManager.getConnection(URL, "root", "");
                PreparedStatement statement = connexion.prepareStatement(requeteSQL)) {
            statement.setString(1, numContrat);
            statement.setInt(2, numSerie);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
