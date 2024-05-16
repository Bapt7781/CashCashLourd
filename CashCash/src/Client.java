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

    // Getter pour la collection de matériels
    public ArrayList<Materiel> getMateriels() {
        return lesMateriels;
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
}
