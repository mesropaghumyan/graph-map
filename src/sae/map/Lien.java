/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sae.map;

import sae.exception.TypeNotSupportedException;

/**
 * Classe qui représente un lien/ route
 * @author Loan
 */
public class Lien {
    
    // <editor-fold defaultstate="collapsed" desc="Déclaration">  
    
    String typeLiens; // Type du lien
    
    Noeud noeuds1; // un noeud que relie le lien
    
    Noeud noeuds2; // un autre neoud que relie le lien
    
    float poidsLiens; // le poid du lien, dans notre cas les kilomètres
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructeur">  
    public Lien(String newtypeLiens,Noeud newnoeuds1,Noeud newnoeuds2,float newpoidsLiens) throws TypeNotSupportedException{
        newtypeLiens = newtypeLiens.toUpperCase();
        if (newtypeLiens.equals("A")  || newtypeLiens.equals("N") || newtypeLiens.equals("D") )
        {
            typeLiens = newtypeLiens;
            noeuds1 = newnoeuds1;
            noeuds2 = newnoeuds2;
            poidsLiens = newpoidsLiens;
        }else{
            throw new TypeNotSupportedException();
        }
                
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methode">  
    
    /**
     * Getter le type du lien
     * @return type String
     */
    public String getTypeLiens(){
        return typeLiens;
    }
    
    /**
     * Getter le noeud 1 du lien
     * @return type Noeud
     */
    public Noeud getNoeuds1(){
        return noeuds1;
    }
    
    /**
     * Getter le noeud 2 du lien
     * @return type Noeud
     */
    public Noeud getNoeuds2(){
        return noeuds2;
    }
    
    /**
     * Getter le poid du lien
     * @return type float
     */
    public float getPoidsLiens(){
        return poidsLiens;
    }
    
    @Override
    public String toString(){
        //return "[lien]->["+typeLiens+"; "+noeuds1+"; "+noeuds2+"; "+poidsLiens+";] ";
        return typeLiens+" : "+poidsLiens;
    }
    
    /**
     * toString mais avec plus d'info
     * @return type String
     */
    public String toStringMoreInfo(){
        return "[lien]->["+typeLiens+"; "+noeuds1+"; "+noeuds2+"; "+poidsLiens+";] ";
        //return typeLiens+" : "+poidsLiens;
        
    }
    
  
    public boolean equals(Object obj){
        if(this == obj){
            return true;
        }
        if(obj == null){
            return false;
            
        }
        if ( obj.getClass() != getClass() ){
            return false;
        }
        final Lien other  = (Lien)obj;
        return (other.getPoidsLiens() == this.getPoidsLiens() && 
                this.getTypeLiens().equals(other.getTypeLiens()) &&
                (
                (this.getNoeuds1().equals(other.getNoeuds1()) && 
                this.getNoeuds2().equals(other.getNoeuds2()))
                )
                ||
                (
                (this.getNoeuds1().equals(other.getNoeuds2()) && 
                this.getNoeuds2().equals(other.getNoeuds1()))
                )
                
                );
    }
    
    /**
     * Retourne vrai si le nœud passer en paramètre est l’une des extrémités du lien
     * @param depart type Noeud
     * @return type boolean
     */
    public boolean estExtremite(Noeud depart){
        return this.getNoeuds1().equals(depart) || this.getNoeuds2().equals(depart);
    }
    
    /**
     * Retourne le nœud situé à l'autre extrémité du lie
     * @param depart type Noeud
     * @return type Noeud
     */
    public Noeud getOppose(Noeud depart){
        if (this.getNoeuds1().equals(depart)){
            return this.getNoeuds2();
        }else{
            return this.getNoeuds1();
        }
    }
    
    // </editor-fold>
}
