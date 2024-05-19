public class TypeMateriel {
    private String referenceInterne;
    private String libelleTypeMateriel;
    private Famille laFamille;

    // Constructeur
    public TypeMateriel(String referenceInterne, String libelleTypeMateriel, Famille laFamille) {
        this.referenceInterne = referenceInterne;
        this.libelleTypeMateriel = libelleTypeMateriel;
        this.laFamille = laFamille;
    }

    public TypeMateriel() {
    }

    // Getters et setters
    public String getReferenceInterne() {
        return referenceInterne;
    }

    public void setReferenceInterne(String referenceInterne) {
        this.referenceInterne = referenceInterne;
    }

    public String getLibelleTypeMateriel() {
        return libelleTypeMateriel;
    }

    public void setLibelleTypeMateriel(String libelleTypeMateriel) {
        this.libelleTypeMateriel = libelleTypeMateriel;
    }

    public Famille getLaFamille() {
        return laFamille;
    }

    public void setLaFamille(Famille laFamille) {
        this.laFamille = laFamille;
    }
}
