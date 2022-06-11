package test;

import java.io.File;
import static junit.framework.Assert.assertEquals;
import org.junit.Test;
import sae.exception.TypeNotSupportedException;
import sae.map.Lien;
import sae.map.MapManager;
import sae.map.Noeud;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */



/**
 *
 * @author Loan
 */
public class MapManagerTest {
    
    
    
    /**
     * Méthode de test des fonctionnalités de la classe nœud
     */
    @Test
    public void noeudTest(){
       
        assertEquals("V",new Noeud("V","Lyon").getType());
        
        assertEquals("L",new Noeud("L","Cinema Lyon").getType());
        
        assertEquals("R",new Noeud("R","Resto Lyon").getType());
        
        assertEquals("V",new Noeud("v","Lyon").getType());
        
        assertEquals("L",new Noeud("l","Cinema Lyon").getType());
        
        assertEquals("R",new Noeud("r","Resto Lyon").getType());
        
        assertEquals("Lyon",new Noeud("V","Lyon").getNom());
        
        assertEquals("Cinema Lyon",new Noeud("L","Cinema Lyon").getNom());
        
        assertEquals("Resto Lyon",new Noeud("R","Resto Lyon").getNom());
        

    }
    
    /**
     * Méthode de test des excpetions de la classe nœud
     */
    @Test(expected = TypeNotSupportedException.class)
    public void noeudTestException(){
        new Noeud("VV","Lyon");
        
        new Noeud(" ","Lyon");
        
        new Noeud("&","Lyon");
        
        new Noeud("M","Lyon");
        
        new Noeud("2","Lyon");
        
        
    }
    
    /**
     * Méthode de test des fonctionnalités de la classe lien
     */
    @Test
    public void lienTest(){
        
        assertEquals("A", new Lien("A",null,null,20).getTypeLiens());
        
        assertEquals("N", new Lien("N",null,null,20).getTypeLiens());
        
        assertEquals("D", new Lien("D",null,null,20).getTypeLiens());
        
        assertEquals("A", new Lien("a",null,null,20).getTypeLiens());
        
        assertEquals("N", new Lien("n",null,null,20).getTypeLiens());
        
        assertEquals("D", new Lien("d",null,null,20).getTypeLiens());
        
        assertEquals(new Noeud("V","Lyon"), new Lien("A",new Noeud("V","Lyon"),null,20).getNoeuds1());
        
        assertEquals(null, new Lien("N",null,null,20).getNoeuds1());
        
        assertEquals(new Noeud("V","Lyon"), new Lien("D",null,new Noeud("V","Lyon"),20).getNoeuds2());
        
        assertEquals(new Noeud("V","Lyon"), new Lien("D",new Noeud("L","Loisir Lyon"),new Noeud("V","Lyon"),20).getNoeuds2());
        
        assertEquals(new Noeud("L","Loisir Lyon"), new Lien("D",new Noeud("L","Loisir Lyon"),new Noeud("V","Lyon"),20).getNoeuds1());
        
        assertEquals(20.f, new Lien("A",null,null,20).getPoidsLiens());
        
        assertEquals(0.f, new Lien("A",null,null,0).getPoidsLiens());
        
        assertEquals(9999.f, new Lien("A",null,null,9999).getPoidsLiens());
        
    }
    
    /**
     * Méthode de test des excpetions de la classe lien
     */
    @Test(expected = TypeNotSupportedException.class)
    public void lienTestException(){
        new Lien("AA",null,null,20).getTypeLiens();
        
        new Lien(" ",null,null,20).getTypeLiens();
        
        new Lien("",null,null,20).getTypeLiens();
        
        new Lien("&",null,null,20).getTypeLiens();
        
        new Lien("2",null,null,20).getTypeLiens();
        
    }
    
    /**
     * Méthode de test des excpetions de la classe MapManger
     */
    @Test
    public void mapManagerTest(){
        
        MapManager m = new MapManager();
        
        m.loadMap("map2.csv");
        
        assertEquals(3, m.getNbVilles());
        
        assertEquals(3, m.getNbLoisirs());
        
        assertEquals(1, m.getNbRestaurants());
        
        assertEquals(1, m.getNbAutoroutes());
        
        assertEquals(1, m.getNbDepartementales());
        
        assertEquals(4, m.getNbNationales());
        
        assertEquals(true, m.estPlusCulturel(new Noeud("V","Montpellier"), null));
        
        assertEquals(false, m.estPlusCulturel(null, new Noeud("V","Montpellier")));

        assertEquals(false, m.estPlusCulturel(null, null));
        
        assertEquals(true, m.estPlusGastronomique(new Noeud("V","Montpellier"), null));
        
        assertEquals(false, m.estPlusGastronomique(null, new Noeud("V","Montpellier")));

        assertEquals(false, m.estPlusGastronomique(null, null));
        
        assertEquals(true, m.estPlusOuvert(new Noeud("V","Montpellier"), null));
        
        assertEquals(false, m.estPlusOuvert(null, new Noeud("V","Montpellier")));

        assertEquals(false, m.estPlusOuvert(null, null));
        
        assertEquals(false, m.estA2Distance(null, new Noeud("V","Montpellier")));
        
        assertEquals(false, m.estA2Distance(new Noeud("V","Montpellier"), null));
        
        assertEquals(true, m.estA2Distance(
                m.getNoeudsByNameAndType(new Noeud("V","Montpellier"))
                , m.getNoeudsByNameAndType(new Noeud("R","Nanterre"))));
        
        assertEquals(false, m.estA2Distance(
                m.getNoeudsByNameAndType(new Noeud("V","Montpellier"))
                , m.getNoeudsByNameAndType(new Noeud("V","Montpellier"))));
        
        assertEquals(true, m.estPlusCulturel(
                m.getNoeudsByNameAndType(new Noeud("V","Montpellier"))
                , m.getNoeudsByNameAndType(new Noeud("R","Nanterre"))));
        
        assertEquals(true, m.estPlusGastronomique(
                m.getNoeudsByNameAndType(new Noeud("V","Montpellier"))
                , m.getNoeudsByNameAndType(new Noeud("R","Nanterre"))));
        
        assertEquals(false, m.estPlusOuvert(
                m.getNoeudsByNameAndType(new Noeud("V","Montpellier"))
                , m.getNoeudsByNameAndType(new Noeud("R","Nanterre"))));
        
        assertEquals(false, m.estPlusOuvert(null,null));
        
        
        
        

        
    }
}
