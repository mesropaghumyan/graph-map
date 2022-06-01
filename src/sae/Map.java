/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sae;
import java.awt.Color;
import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner;
import java.util.ArrayList;
import javax.swing.JPanel;

/**
 *
 * @author p2103642
 */
public class Map {
    ArrayList<Noeuds> listeVilles;
    ArrayList<Liens> listeRoutes;
    JPanel colorIndicator = null;
    Map(){
        listeVilles = new ArrayList<>();
        listeRoutes = new ArrayList<>();
        
    }
    
    public void addColorIndicator(JPanel indi){
        
        colorIndicator= indi;
        System.out.println("colorIndicator"+colorIndicator+ "added");
    }
    
    public void loadMap(String filename){
        if (colorIndicator != null){
            colorIndicator.setBackground(Color.yellow);
       
        }
     
        
        listeVilles = new ArrayList<>();
        listeRoutes = new ArrayList<>();
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
        if (colorIndicator != null){
            colorIndicator.setBackground(Color.green);}
        
        
    } catch (FileNotFoundException e){
        System.out.println("Fichier introuvable");
         if (colorIndicator != null){
            colorIndicator.setBackground(Color.red);}
        
    }catch (Exception e){
        System.out.println(e);
        if (colorIndicator != null){
            colorIndicator.setBackground(Color.red);}
        
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
            try{
                
                this.ajouterRoute(voisins[z], depart);
                
            }catch (StringIndexOutOfBoundsException e){
                System.out.println("\u001B[33m"+"[WARNING]"+"\u001B[0m"+" Une connection n'a pas pu etre ajouté, elle ne respect pas la structure");
            }
            catch(Exception e){
                System.out.println(e);
                
            }
            
            
            
  
        }
        if (!listeVilles.contains(depart)   ){
            this.listeVilles.add(depart);
        }
        
   
    }
    
   
    System.out.println("\u001B[32m"+"[INFO]"+"\u001B[0m"+" Importation terminé");

    
    
    
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
        
        if(!listeVilles.contains(newVille)){
            this.listeVilles.add(newVille);
            System.out.println("\u001B[32m"+"[INFO]"+"\u001B[0m"+" Noeud ajouté : "+newVille.getNom());
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
    public Liens stringToLiens(String data,Noeuds depart,Noeuds arriver) throws Exception{
        String routeType;
        float routeLongueur;
            
           
        routeType = data.substring(0,data.indexOf(",")).trim();
        routeLongueur = Float.parseFloat( data.substring(data.indexOf(",")+1,data.indexOf(":")).trim());
        return new Liens(routeType,depart,arriver,routeLongueur);
            
        
    }
    public Noeuds stringToNoeudDroite(String data) throws Exception {
        String voisinType;
        String voisinNom;
        voisinType = data.substring(data.indexOf("::")+2,data.indexOf(",", data.indexOf(",") + 1)).trim();
        voisinNom = data.substring(data.indexOf(",", data.indexOf(",") + 1)+1,data.length()).trim();
          
            
        Noeuds tmp = new Noeuds(voisinType,voisinNom);
        return tmp;
        
    }
    public Boolean ajouterRoute(String data, Noeuds depart) throws Exception{
        
            Noeuds tmp = this.stringToNoeudDroite(data);
            if (!this.addToListeVilles(tmp)){
                tmp = this.getNoeudsByNameAndType(tmp.getType(), tmp.getNom());
            }
     
            //Creation du lien
            Liens tmp_lien = this.stringToLiens(data, depart, tmp);
            
            //Ajout de la connection au deux extréminté
            if(depart.addConnection(tmp_lien) == true && tmp.addConnection(tmp_lien) == true){
                System.out.println("\u001B[32m"+"[INFO]"+"\u001B[0m"+" Lien ajouté : " + depart.getNom()+ " -> "+tmp.getNom()+" "+tmp_lien.poidsLiens+"km");
            }

            return this.addToListeRoutes(tmp_lien);
        
    }
    public Boolean estA2Distance(Noeuds depart, Noeuds Arriver){
        for (Liens voisin2Depart : depart.getConnection()){
            for (Liens tmp :voisin2Depart.getOppose(depart).getConnection()){
                if(tmp.getOppose(voisin2Depart.getOppose(depart)).equals(Arriver)){
                    return true;
                }
            }
        }
        
        return false;
    }
    public int ouvertureNoeuds(Noeuds depart){
        int c=0;
        ArrayList<Noeuds> skip = new ArrayList<Noeuds>();
        skip.add(depart);
    
        for (Liens voisin2Depart : depart.getConnection()){
            for (Liens tmp :voisin2Depart.getOppose(depart).getConnection()){
               
                if(!skip.contains(tmp.getOppose(voisin2Depart.getOppose(depart)))){
                    skip.add(tmp.getOppose(voisin2Depart.getOppose(depart)));
        
                    c++;
                 }
            }
        }
        
        return c;
    }
    
    public int gastronomoieNoeuds(Noeuds depart){
        int c=0;
        for (Liens voisin2Depart : depart.getConnection()){
            for (Liens tmp :voisin2Depart.getOppose(depart).getConnection()){
                if(tmp.getOppose(voisin2Depart.getOppose(depart)).getType().equalsIgnoreCase("R") && !tmp.getOppose(voisin2Depart.getOppose(depart)).equals(depart)){
                    c++;
                }
            }
        }
        
        return c;
    }
    
    public int cultureNoeuds(Noeuds depart){
        int c=0;
        for (Liens voisin2Depart : depart.getConnection()){
            for (Liens tmp :voisin2Depart.getOppose(depart).getConnection()){
                if(tmp.getOppose(voisin2Depart.getOppose(depart)).getType().equalsIgnoreCase("L") && !tmp.getOppose(voisin2Depart.getOppose(depart)).equals(depart)){
                    c++;
                }
            }
        }
        
        return c;
    }
    
    //WARNING pas chack >= 2
    public boolean estPlusOuvert(Noeuds sujet, Noeuds autre){
        return (this.ouvertureNoeuds(sujet)> this.ouvertureNoeuds(autre));
    }
    
    public boolean estPlusGastronomique(Noeuds sujet, Noeuds autre){
        return (this.gastronomoieNoeuds(sujet)> this.gastronomoieNoeuds(autre));
    }
    
    public boolean estPlusCulturel(Noeuds sujet, Noeuds autre){
        return (this.cultureNoeuds(sujet)> this.cultureNoeuds(autre));
    }
    

}
