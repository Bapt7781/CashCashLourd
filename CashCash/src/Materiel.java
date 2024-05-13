import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

public class Materiel {
    private int numSerie, numClient;
    private Date dateVente, dateInstallation, dateEcheanceContrat;
    private double prixVente;
    private String emplacement;
    private TypeMateriel leType;
    
    private static final String URL = "jdbc:mysql://localhost/cashcash?useUnicode=true&characterEncoding=UTF-8";
    private static final String UTILISATEUR = "root";
    private static final String MOT_DE_PASSE = "";

    public Materiel(int numSerie, Date dateVente, Date dateInstallation, Date dateEcheanceContrat, double prixVente, String emplacement, TypeMateriel leType) {
        this.numSerie = numSerie;
        this.dateVente = dateVente;
        this.dateInstallation = dateInstallation;
        this.dateEcheanceContrat = dateEcheanceContrat;
        this.prixVente = prixVente;
        this.emplacement = emplacement;
        this.leType = leType;
    }

    public Materiel() throws IOException {
        xmlMateriel();
    }

    // Getters
    public int getNumSerie() {
        return numSerie;
    }

    public Date getDateVente() {
        return dateVente;
    }

    public Date getDateInstallation() {
        return dateInstallation;
    }

    public double getPrixVente() {
        return prixVente;
    }

    public String getEmplacement() {
        return emplacement;
    }

    public TypeMateriel getLeType() {
        return leType;
    }

    public int getNumClient() {
        return numClient;
    }

    // Setters
    public void setNumSerie(int numSerie) {
        this.numSerie = numSerie;
    }

    public void setDateVente(Date dateVente) {
        this.dateVente = dateVente;
    }

    public void setDateInstallation(Date dateInstallation) {
        this.dateInstallation = dateInstallation;
    }

    public void setPrixVente(double prixVente) {
        this.prixVente = prixVente;
    }

    public void setEmplacement(String emplacement) {
        this.emplacement = emplacement;
    }

    public void setLeType(TypeMateriel leType) {
        this.leType = leType;
    }

    public void setNumClient(int numClient) {
        this.numClient = numClient;
    }

    public ArrayList<ArrayList<Materiel>> recuperationInformationXML() {
        String numClient = JOptionPane.showInputDialog(null, "Numéro du Client :");
        setNumClient(Integer.parseInt(numClient));
        String requeteSQL = "SELECT * FROM materiel WHERE NumeroClient = ?";
        ArrayList<Materiel> listeMaterielsSousContrat = new ArrayList<>();
        ArrayList<Materiel> listeMaterielsHorsContrat = new ArrayList<>();

        // Création de la connexion à la base de données
        try (Connection connection = DriverManager.getConnection(URL, UTILISATEUR, MOT_DE_PASSE);
                PreparedStatement preparedStatement = connection.prepareStatement(requeteSQL)) {
            
            preparedStatement.setString(1, numClient);
            
            TypeMateriel typeMateriel = null; // Déclaration de typeMateriel en dehors de la portée des blocs try/catch
            Date dateEcheance = null;
            // Exécution de la requête et récupération des résultats
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                // Création du document XML
                while (resultSet.next()) {
                    String BdNumSerie = resultSet.getString("NumeroDeSerie");
                    String bdDateVente = resultSet.getString("DateDeVente");
                    String bdDateInstallation = resultSet.getString("DateInstallation");
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    String bdPrixVente = resultSet.getString("PrixDeVente");
                    String bdEmplacement = resultSet.getString("Emplacement");
                    String bdNumContrat = resultSet.getString("NumeroDeContrat");
                    String requeteSQL2 = "SELECT * FROM contratdemaintenance WHERE NumeroDeContrat = ?";
                    try (PreparedStatement preparedStatement2 = connection.prepareStatement(requeteSQL2)) {
                        preparedStatement2.setString(1, bdNumContrat);
                        try (ResultSet resultSet2 = preparedStatement2.executeQuery()) {
                            // Vérifiez s'il y a un résultat de la requête
                            if (resultSet2.next()) {
                                // Créez un objet contrat en fonction de la valeur de numcontrat
                                String DateEcheance = resultSet2.getString("DateEcheance");
                                dateEcheance = dateFormat.parse(DateEcheance);
                            }
                        }
                    } 
                    String bdRefInterne = resultSet.getString("ReferenceInterne");
                    String requeteSQL3 = "SELECT * FROM typeMateriel WHERE ReferenceInterne = ?";
                    try (PreparedStatement preparedStatement3 = connection.prepareStatement(requeteSQL3)) {
                        preparedStatement3.setString(1, bdRefInterne);
                        try (ResultSet resultSet3 = preparedStatement3.executeQuery()) {
                            // Vérifiez s'il y a un résultat de la requête
                            if (resultSet3.next()) {
                                // Créez un objet TypeMateriel en fonction de la valeur de bdRefInterne
                                String libelleTypeMateriel = resultSet3.getString("LibelleTypeMateriel");
                                Famille laFamille = null;

                                if (bdRefInterne.equals("1")) {
                                    laFamille = new Famille("A", "Souris");
                                } else if (bdRefInterne.equals("2")) {
                                    laFamille = new Famille("B", "Ecran");
                                } else if (bdRefInterne.equals("3")) {
                                    laFamille = new Famille("C", "Imprimante");
                                } else if (bdRefInterne.equals("4")) {
                                    laFamille = new Famille("D", "Terminal");
                                }

                                typeMateriel = new TypeMateriel(bdRefInterne, libelleTypeMateriel, laFamille);
                            }
                        }
                    }
                    Materiel materiel = new Materiel(Integer.parseInt(BdNumSerie), dateFormat.parse(bdDateVente), dateFormat.parse(bdDateInstallation), dateEcheance, Double.parseDouble(bdPrixVente), bdEmplacement, typeMateriel);                 
                    String bdContrat = resultSet.getString("NumeroDeContrat");
                    if (bdContrat != null){
                        listeMaterielsSousContrat.add(materiel);
                    }
                    else {
                        listeMaterielsHorsContrat.add(materiel);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                // Gestion des erreurs de connexion ou d'exécution de la requête
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Gestion des erreurs de connexion
        }
        ArrayList<ArrayList<Materiel>> listeMateriels = new ArrayList<>();
        listeMateriels.add(listeMaterielsSousContrat);
        listeMateriels.add(listeMaterielsHorsContrat);
        return listeMateriels;
    }

    public void xmlMateriel() throws IOException {
        ArrayList<ArrayList<Materiel>> listeMateriels = recuperationInformationXML();
        ArrayList<Materiel> listeMaterielsSousContrat = listeMateriels.get(0);
        ArrayList<Materiel> listeMaterielsHorsContrat = listeMateriels.get(1);
        int numClient = getNumClient();

        Fichier f = new Fichier();
        String nomFichier = "materielClient" + numClient + ".xml";
        f.ouvrir(nomFichier, "W");
        f.ecrire("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        f.ecrire("<!DOCTYPE listeMateriel SYSTEM \"./materielClient.dtd\">");
        f.ecrire("<listeMateriel>");
        f.ecrire("<materiels idClient=\""+ numClient + "\">");
        f.ecrire("<sousContrat>");
        for (Materiel materiel : listeMaterielsSousContrat) {
            f.ecrire("<materiel numSerie=\""+ materiel.numSerie + "\">");
            f.ecrire("<type refInterne=\""+ materiel.leType.getReferenceInterne() + "\" libelle=\"" + materiel.leType.getLibelleTypeMateriel() + "\" />");
            f.ecrire("<famille codeFamille=\"" + materiel.leType.getLaFamille().getCodeFamille() + "\" libelle=\""+ materiel.leType.getLaFamille().getLibelleFamille() + "\" />");
            f.ecrire("<date_vente>"+ materiel.dateVente + "</date_vente>");
            f.ecrire("<date_installation>" + materiel.dateInstallation + "</date_installation>");
            f.ecrire("<prix_vente>"+ materiel.prixVente + "</prix_vente>");
            f.ecrire("<emplacement>"+ materiel.emplacement + "</emplacement>");
            f.ecrire("<date_echeance_contrat>" + materiel.dateEcheanceContrat + "</date_echeance_contrat>");
            f.ecrire("</materiel>");
        }
        f.ecrire("</sousContrat>");
        f.ecrire("<horsContrat>");
        for (Materiel materiel : listeMaterielsHorsContrat) {
            f.ecrire("<materiel numSerie=\""+ materiel.numSerie + "\">");
            f.ecrire("<type refInterne=\""+ materiel.leType.getReferenceInterne() + "\" libelle=\"" + materiel.leType.getLibelleTypeMateriel() + "\" />");
            f.ecrire("<famille codeFamille=\"" + materiel.leType.getLaFamille().getCodeFamille() + "\" libelle=\""+ materiel.leType.getLaFamille().getLibelleFamille() + "\" />");
            f.ecrire("<date_vente>"+ materiel.dateVente + "</date_vente>");
            f.ecrire("<date_installation>" + materiel.dateInstallation + "</date_installation>");
            f.ecrire("<prix_vente>"+ materiel.prixVente + "</prix_vente>");
            f.ecrire("<emplacement>"+ materiel.emplacement + "</emplacement>");
            f.ecrire("</materiel>");
        }
        f.ecrire("</horsContrat>");
        f.ecrire("</materiels>");
        f.ecrire("</listeMateriel>");
        f.fermer();

        boolean valide = validerXmlAvecDtd(nomFichier, "materielClient.dtd");
        if(valide){
            JOptionPane.showMessageDialog(null, "Fichier XML client généré et respecte la dtd", "Succès",
                        JOptionPane.INFORMATION_MESSAGE);
        }
        else {
            JOptionPane.showMessageDialog(null, "Fichier XML client généré mais ne respecte pas la dtd", "Echec",
                        JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public static boolean validerXmlAvecDtd(String documentXML, String dtdPath) {
        try {
            // Instanciation d'une factory d'analyseurs SAX
            SAXParserFactory factory = SAXParserFactory.newInstance();
            // Instanciation d'un analyseur SAX validant les documents lus
            factory.setValidating(true);
            SAXParser parser = factory.newSAXParser();
            // Ensemble stockant les erreurs trouvees dans le fichier XML
            final ArrayList<SAXParseException> erreurs = new ArrayList<>();
            // Creation d'un gestionnaire SAX
            DefaultHandler gestionnaireSAX = new DefaultHandler() {
                // Redefinition des methodes de gestion d'erreur
                public void warning(SAXParseException ex) {
                    erreurs.add(ex);
                }

                public void error(SAXParseException ex) {
                    erreurs.add(ex);
                }

                public void fatalError(SAXParseException ex) {
                    erreurs.add(ex);
                }
            };
            // Analyse du fichier avec le gestionnaire precedent
            parser.parse(documentXML, gestionnaireSAX);
            // Si aucune erreur n'a été enregistrée, le document est valide
            return true;
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            ex.printStackTrace();
            return false;
        }
    }
}
