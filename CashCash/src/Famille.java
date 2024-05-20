public class Famille {
    // Attributs
    private String codeFamille, libelleFamille;

    /**
     * Constructeur avec paramètres de la classe Famille.
     *
     * @param codeFamille    Le code de la famille
     * @param libelleFamille Le libellé de la famille
     */
    public Famille(String codeFamille, String libelleFamille) {
        this.codeFamille = codeFamille;
        this.libelleFamille = libelleFamille;
    }

    public String getCodeFamille() {
        return codeFamille;
    }

    public void setCodeFamille(String codeFamille) {
        this.codeFamille = codeFamille;
    }

    public String getLibelleFamille() {
        return libelleFamille;
    }

    public void setLibelleFamille(String libelleFamille) {
        this.libelleFamille = libelleFamille;
    }
}
