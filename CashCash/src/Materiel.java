import java.sql.Date;

public class Materiel {
    private int numSerie, nbJourAvantEcheance;
    private Date dateVente, dateInstallation;
    private double prixVente;
    private String emplacement;
    private TypeMateriel leType;
    private ContratMaintenance unContrat;

    public String xmlMateriel() {

        StringBuilder xmlBuilder = new StringBuilder();
        xmlBuilder.append("<materiel numSerie=\"").append(numSerie).append("\">\n");
        xmlBuilder.append("<type refInterne=\"").append(leType.getReferenceInterne()).append("\" libelle=\"").append(leType.getLibelleTypeMateriel()).append("\"/>\n");
        xmlBuilder.append("<famille codeFamille=\"").append(leType.getLaFamille().getCodeFamille()).append("\" libelle=\"").append(leType.getLaFamille().getLibelleFamille()).append("\"/>\n");
        xmlBuilder.append("<date_vente>").append(dateVente).append("</date_vente>\n");
        xmlBuilder.append("<date_installation>").append(dateInstallation).append("</date_installation>\n");
        xmlBuilder.append("<prix_vente>").append(prixVente).append("</prix_vente>\n");
        xmlBuilder.append("<emplacement>").append(emplacement).append("</emplacement>\n");
        xmlBuilder.append("nbJourAvantEcheance").append(nbJourAvantEcheance).append("</nbJourAvantEcheance>\n"); // La date de vente doit être formatée correctement
        xmlBuilder.append("</materiel>\n");
        return xmlBuilder.toString();
    }
}
