import java.sql.Date;
import java.util.ArrayList;

public class Client {
    // Attributs privés
    private String numClient;
    private String raisonSociale;
    private String siren;
    private String codeApe;
    private String adresse;
    private String telClient;
    private String email;
    private int dureeDeplacement;
    private int distanceKm;
    private ArrayList<Materiel> lesMateriels;
    private ContratMaintenance leContrat;
    private String dateEcheance;

    // Constructeur
    public Client(String numClient, String raisonSociale, String siren, String codeApe, String adresse,
            String telClient, String email, int dureeDeplacement, int distanceKm, ArrayList<Materiel> lesMateriels,
            ContratMaintenance leContrat) {
        this.numClient = numClient;
        this.raisonSociale = raisonSociale;
        this.siren = siren;
        this.codeApe = codeApe;
        this.adresse = adresse;
        this.telClient = telClient;
        this.email = email;
        this.dureeDeplacement = dureeDeplacement;
        this.distanceKm = distanceKm;
        this.lesMateriels = lesMateriels;
        this.leContrat = leContrat;
    }

    // surcharge
    public Client() {
    }

    // Getters pour les attributs
    public String getNumClient() {
        return numClient;
    }

    public String getRaisonSociale() {
        return raisonSociale;
    }

    public String getSiren() {
        return siren;
    }

    public String getCodeApe() {
        return codeApe;
    }

    public String getAdresse() {
        return adresse;
    }

    public String getTelClient() {
        return telClient;
    }

    public String getEmail() {
        return email;
    }

    public int getDureeDeplacement() {
        return dureeDeplacement;
    }

    public int getDistanceKm() {
        return distanceKm;
    }

    public ArrayList<Materiel> getMateriels() {
        return lesMateriels;
    }

    public ContratMaintenance getLeContrat() {
        return leContrat;
    }

    public void setNumClient(String numClient) {
        this.numClient = numClient;
    }

    public void setRaisonSociale(String raisonSociale) {
        this.raisonSociale = raisonSociale;
    }

    public void setSiren(String siren) {
        this.siren = siren;
    }

    public void setCodeApe(String codeApe) {
        this.codeApe = codeApe;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public void setTelClient(String telClient) {
        this.telClient = telClient;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setDureeDeplacement(int dureeDeplacement) {
        this.dureeDeplacement = dureeDeplacement;
    }

    public void setDistanceKm(int distanceKm) {
        this.distanceKm = distanceKm;
    }

    public void setLesMateriels(ArrayList<Materiel> lesMateriels) {
        this.lesMateriels = lesMateriels;
    }

    public void setLeContrat(ContratMaintenance leContrat) {
        this.leContrat = leContrat;
    }

    public String getDateEcheance() {
        return dateEcheance;
    }

    // Méthode pour obtenir les matériels sous contrat
    public ArrayList<Materiel> getMaterielsSousContrat() {
        ArrayList<Materiel> materielsSousContrat = new ArrayList<>();
        if (leContrat != null && leContrat.estValide()) {
            for (Materiel materiel : lesMateriels) {
                if (leContrat.contientMateriel(materiel)) {
                    materielsSousContrat.add(materiel);
                }
            }
        }
        return materielsSousContrat;
    }

    // Méthode pour vérifier si le client est assuré
    public boolean estAssure() {
        return leContrat != null && leContrat.estValide();
    }

    public void setDateEcheance(Date date) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setDateEcheance'");
    }
}
