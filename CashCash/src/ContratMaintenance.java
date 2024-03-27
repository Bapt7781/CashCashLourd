import java.sql.Date;
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
        
    }
    public boolean estValide() {
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