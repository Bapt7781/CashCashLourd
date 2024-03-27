import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collection;

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
    
        // Vérifie si la date actuelle est après la date de signature et avant la date d'échéance
        return now.isAfter(signature) && now.isBefore(echeance);
    }
    
    public void ajouteMateriel(Materiel unMateriel) {
        lesMaterielsAssures.add(unMateriel);
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
    public Collection<Materiel> getLesMaterielsAssures() {
        return lesMaterielsAssures;
    }
}