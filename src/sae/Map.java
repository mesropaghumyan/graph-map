/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sae;
import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner;
import java.util.ArrayList;

/**
 *
 * @author p2103642
 */
public class Map {
    ArrayList<Noeuds> listeVilles;
    ArrayList<Liens> listeRoutes;
    Map(){
        listeVilles = new ArrayList<>();
        listeRoutes = new ArrayList<>();
        
    }
    public void loadMap(String filename){
    String res_file = "";
    try{
        File fileMap = new File(filename);
        Scanner fileReader = new Scanner(fileMap);
        while (fileReader.hasNextLine()){
            String data = fileReader.nextLine();
            res_file += data;
            //System.out.println(data);
        }
        fileReader.close();
        this.stringMap(res_file);
        
        
    } catch (FileNotFoundException e){
        System.out.println("Fichier introuvable");
        
    }catch (Exception e){
        System.out.println(e);
        
    }
    
}
    /**
     * Découpe le string multiligne avant de le passer a stringToListNoeuds
     * @param data String multiligne exemple venant d'un fichier csv
     * @throws Exception 
     */
    public void stringMap(String data) throws Exception{
    String[] villeListe;
   
    String[] voisins;
    
    //this.stringToListNoeuds(data); recupere to les noeuds de departs
    
    villeListe = data.split(";;");
    for(int i=0; i<villeListe.length;i++){
        voisins = villeListe[i].substring(villeListe[i].indexOf(":")+1,villeListe[i].length()).split(";");
       

        Noeuds depart = this.stringToNoeuds(villeListe[i]);
        if (!this.addToListeVilles(depart)){
                depart = this.getNoeudsByNameAndType(depart.getType(), depart.getNom());
            }
        for(int z=0; z<voisins.length ;z++){
            String routeType;
            float routeLongueur;
            String voisinType;
            String voisinNom;
           
            routeType = voisins[z].substring(0,voisins[z].indexOf(",")).trim();
            routeLongueur = Float.parseFloat( voisins[z].substring(voisins[z].indexOf(",")+1,voisins[z].indexOf(":")).trim());
            voisinType = voisins[z].substring(voisins[z].indexOf("::")+2,voisins[z].indexOf(",", voisins[z].indexOf(",") + 1)).trim();
            voisinNom = voisins[z].substring(voisins[z].indexOf(",", voisins[z].indexOf(",") + 1)+1,voisins[z].length()).trim();
          
            
            Noeuds tmp = new Noeuds(voisinType,voisinNom);
            if (!this.addToListeVilles(tmp)){
                tmp = this.getNoeudsByNameAndType(voisinType, voisinNom);
            }
            
            //Creation du lien
            Liens tmp_lien = new Liens(routeType,depart,tmp,routeLongueur);
            
            //Ajout de la connection au deux extréminté
            if(depart.addConnection(tmp_lien) == true && tmp.addConnection(tmp_lien) == true){
                System.out.println("[INFO] Lien ajouté : " + depart.getNom()+ " -> "+tmp.getNom());
            }

            this.addToListeRoutes(tmp_lien);
  
        }
        this.listeVilles.add(depart);
   
    }
    
   
    System.out.println("[INFO] Importation terminé");

    
    
    
}
 
    /**
     * Extrait le nœud départ à partir d’un String.
     * @param data
     * @return 
     */
    public Noeuds stringToNoeuds(String data){
      
            String villeInfo = data.substring(0,data.indexOf(":"));
            String villeType = villeInfo.substring(0,data.indexOf(",")).trim();
            String villeNom = villeInfo.substring(data.indexOf(",")+1,villeInfo.length()).trim();
    
            try{
                Noeuds newNoeuds = new Noeuds(villeType,villeNom);
                return newNoeuds;
            }catch(TypeNotSupportedException e){
                System.out.println("Exception : "+e);                
            } catch (Exception ex) {
                System.out.println("Exception : "+ex);
            }
            return null;
            
            
        
        
    } 
    /**
     * Retourne un objet noeud qui a le même type et nom.
     * @param name Nom du noeud
     * @param type Type du noeud
     * @return Retourne le noeud is trouver sinon null
     */
    public Noeuds getNoeudsByNameAndType( String type,String name){
        for (Noeuds ville: listeVilles){
            
            if(ville.getNom().equals(name) && ville.getType().equals(type)){
                return ville;
            }
        }
        return null;
    }
    /**
     * Vérifie si le nœud est déjà présent dans la liste.
     * @param newVille Le nœud à vérifier la présence dans la liste.
     * @return Retourne un true si le nœud est présent sinon false.
     */
    public boolean isInListeVilles(Noeuds newVille){
        for(Noeuds ville: this.listeVilles){
            if (ville.equals(newVille)){
                return true;
            }
        }
        return false;
    }
    /**
     * Ajoute un nœud à la liste tout en vérifiant qu’il n’est déjà pas présent.
     * @param newVille Le nœud à ajouter.
     * @return Retourne true si le nœud a été ajouté sinon false.
     */
    public boolean addToListeVilles(Noeuds newVille){
        if(!this.isInListeVilles(newVille)){
            this.listeVilles.add(newVille);
            System.out.println("[INFO] Noeud ajouté : "+newVille.getNom());
            return true;
        }
        return false;
    }
    
   
   
    
    public boolean addToListeRoutes(Liens newRoute){
        if(!this.listeRoutes.contains(newRoute)){
            this.listeRoutes.add(newRoute);
            return true;
        }
        return false;
    }
    public boolean removeFromListeRoutes(Liens newRoute){
        if(this.listeRoutes.contains(newRoute)){
            this.listeRoutes.remove(newRoute);
            return true;
        }
        return false;
        
    }

    public ArrayList<Liens> getListeRoutes() {
        return listeRoutes;
    }
    
    public ArrayList<Liens> getCoonections(Noeuds depart){
        ArrayList<Liens> routesTrouver = new ArrayList<>();
        for(Liens tmpVoisin: this.listeRoutes){
            if(tmpVoisin.estExtremite(depart)){
                routesTrouver.add(tmpVoisin);
            }
        }
        return routesTrouver;
        
    }
    public ArrayList<Noeuds> getVoisins(Noeuds depart){
        ArrayList<Noeuds> villesTrouver = new ArrayList<>();
        for(Liens tmpVoisin: this.listeRoutes){
            if (!villesTrouver.contains(tmpVoisin.getOppose(depart)))
                villesTrouver.add(tmpVoisin.getOppose(depart));
            }
        return villesTrouver;
        
    }
    

}
