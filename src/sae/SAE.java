/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sae;

/**
 *
 * @author p2103642
 */
public class SAE {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        MapManager m = new MapManager();
        m.loadMap("map2.csv");
        
     
        //System.out.println(m.bestRouteDetour(m.getListeNoeuds().get(0), m.getListeNoeuds().get(7), 1, 0, 0));
        /*
        System.out.println(m.gastronomoieNoeuds(m.listeVilles.get(0)));
        System.out.println(m.gastronomoieNoeuds(m.listeVilles.get(1)));
        
        System.out.println(m.villeNoeuds(m.listeVilles.get(0)));
        System.out.println(m.villeNoeuds(m.listeVilles.get(1)));
        
        
        
        
        System.out.println(m.estPlusCulturel(m.listeVilles.get(0), m.listeVilles.get(1)));
        
 
        //new IHM(m.listeVilles,m.listeRoutes);
        */
        //new Fenetre();
        
     
    }
    
}
