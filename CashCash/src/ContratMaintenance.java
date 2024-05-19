import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class ContratMaintenance {
    private String numContrat;
    private Date dateSignature, dateEcheance;
    private ArrayList<Materiel> lesMaterielsAssures = new ArrayList<>();

    public ContratMaintenance(String numContrat, Date dateSignature, Date dateEcheance) {
        this.numContrat = numContrat;
        this.dateSignature = dateSignature;
        this.dateEcheance = dateEcheance;
        this.lesMaterielsAssures = new ArrayList<>();
    }

    public ContratMaintenance() {
    }

    public boolean contientMateriel(Materiel materiel) {
        return lesMaterielsAssures.contains(materiel);
    }

    public int getJoursRestants() {
        // Conversion des dates SQL en LocalDate
        LocalDate now = LocalDate.now();
        LocalDate echeance = dateEcheance.toLocalDate();

        // Calcul des jours restants jusqu'à l'échéance
        long joursRestants = ChronoUnit.DAYS.between(now, echeance);

        // Conversion en int et retour
        return (int) joursRestants;
    }

    public boolean estValide() {
        // Conversion des dates SQL en LocalDate
        LocalDate now = LocalDate.now();
        LocalDate signature = dateSignature.toLocalDate();
        LocalDate echeance = dateEcheance.toLocalDate();

        // Vérifie si la date actuelle est après la date de signature et avant la date
        // d'échéance
        return now.isAfter(signature) && now.isBefore(echeance);
    }

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

    // Méthode pour obtenir le numéro de contrat
    public String getNumContrat() {
        return numContrat;
    }

    // Méthode pour obtenir la date de signature
    public Date getDateSignature() {
        return dateSignature;
    }

    // Méthode pour obtenir la date d'échéance
    public Date getDateEcheance() {
        return dateEcheance;
    }

    // Méthode pour obtenir les matériels assurés
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