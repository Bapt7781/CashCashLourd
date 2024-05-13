import java.io.*;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author baptiste.delisle
 */
public class Fichier {
    //Ecriture
    private BufferedWriter fW;
    
    //Lecture
    private BufferedReader fR;
    
    private char mode;
    
    public void ouvrir(String nomDuFichier, String s) throws IOException{
        mode = (s.toUpperCase()).charAt(0);
        File f = new File(nomDuFichier);
        
        if (mode == 'R' || mode == 'L')
            fR = new BufferedReader(new FileReader(f));
        else if (mode == 'W' || mode == 'E')
            fW = new BufferedWriter(new FileWriter(f, true)); //True pour ne pas écraser les données existantes
    }
    
    
    //Lecture
    public String lire() throws IOException{
        String ligne = fR.readLine();
        return ligne;
    }
    
    //Ecriture
    public void ecrire(String ligne) throws IOException{
        if(ligne != null){
            fW.write(ligne, 0, ligne.length());
            fW.newLine();
        }
    }
    
    //fermer le flux 
    public void fermer() throws IOException{
        if (mode == 'R' || mode == 'L')
            fR.close();
        else if (mode == 'W' || mode == 'E')
            fW.close();
    }
    
    //Surcharge Ecriture (Si int)
    public void ecrire(int nb) throws IOException{
        String ligne = "";
        ligne = String.valueOf(nb);
        if(ligne != null){
            fW.write(ligne, 0, ligne.length());
            fW.newLine();
        }
    }
}
