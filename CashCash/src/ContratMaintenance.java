import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class ContratMaintenance {
    // Attributs
    private String numContrat;
    private Date dateSignature, dateEcheance;
    private ArrayList<Materiel> lesMaterielsAssures = new ArrayList<>();

    /**
     * Constructeur avec paramètres de la classe ContratMaintenance.
     *
     * @param numContrat    Le numéro du contrat
     * @param dateSignature La date de signature du contrat
     * @param dateEcheance  La date d'échéance du contrat
     */
    public ContratMaintenance(String numContrat, Date dateSignature, Date dateEcheance) {
        this.numContrat = numContrat;
        this.dateSignature = dateSignature;
        this.dateEcheance = dateEcheance;
        this.lesMaterielsAssures = new ArrayList<>();
    }

    // Constructeur sans paramètre
    public ContratMaintenance() {
    }

    /**
     * Vérifie si le contrat contient un matériel spécifié.
     *
     * @param materiel Le matériel à vérifier
     * @return true si le matériel est contenu dans le contrat, sinon false
     */
    public boolean contientMateriel(Materiel materiel) {
        return lesMaterielsAssures.contains(materiel);
    }

    /**
     * Calcule le nombre de jours restants jusqu'à l'échéance du contrat.
     *
     * @return Le nombre de jours restants
     */
    public int getJoursRestants() {
        // Conversion des dates SQL en LocalDate
        LocalDate now = LocalDate.now();
        LocalDate echeance = dateEcheance.toLocalDate();

        // Calcul des jours restants jusqu'à l'échéance
        long joursRestants = ChronoUnit.DAYS.between(now, echeance);

        // Conversion en int et retour
        return (int) joursRestants;
    }

    /**
     * Vérifie si le contrat est valide à la date actuelle.
     *
     * @return true si le contrat est valide, sinon false
     */
    public boolean estValide() {
        // Conversion des dates SQL en LocalDate
        LocalDate now = LocalDate.now();
        LocalDate signature = dateSignature.toLocalDate();
        LocalDate echeance = dateEcheance.toLocalDate();

        // Vérifie si la date actuelle est après la date de signature et avant la date
        // d'échéance
        return now.isAfter(signature) && now.isBefore(echeance);
    }

    /**
     * Ajoute un matériel au contrat de maintenance.
     *
     * @param unMateriel Le matériel à ajouter
     */
    public void ajouteMateriel(Materiel unMateriel) {
        // Vérifier si la date de signature du contrat est antérieure ou égale à la date
        // d'installation du matériel
        if (dateSignature.compareTo(unMateriel.getDateInstallation()) <= 0) {
            // Ajouter unMatériel à la collection lesMaterielsAssures
            lesMaterielsAssures.add(unMateriel);

            // Mettre à jour la ligne correspondante dans la table materiel de la base de
            // données
            PersistanceSQL persistanceSQL = new PersistanceSQL("localhost", 3306, "cashcash");
            persistanceSQL.ajouterNumeroContratAuMateriel(this.numContrat, unMateriel.getNumSerie());
        } else {
            System.out.println("La date de signature du contrat est postérieure à la date d'installation du matériel.");
        }
    }

    // Méthodes getters et setters
    public String getNumContrat() {
        return numContrat;
    }

    public Date getDateSignature() {
        return dateSignature;
    }

    public Date getDateEcheance() {
        return dateEcheance;
    }

    public ArrayList<Materiel> getLesMaterielsAssures() {
        return lesMaterielsAssures;
    }

    public void setNumContrat(String numContrat) {
        this.numContrat = numContrat;
    }

    public void setDateSignature(Date dateSignature) {
        this.dateSignature = dateSignature;
    }

    public void setDateEcheance(Date dateEcheance) {
        this.dateEcheance = dateEcheance;
    }

    public void setLesMaterielsAssures(ArrayList<Materiel> lesMaterielsAssures) {
        this.lesMaterielsAssures = lesMaterielsAssures;
    }
}
