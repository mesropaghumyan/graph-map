/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sae;

import sae.exception.TypeNotSupportedException;

/**
 *
 * @author Loan
 */
public class Lien {
    String typeLiens;
    Noeud noeuds1;
    Noeud noeuds2;
    float poidsLiens;
    Lien(String newtypeLiens,Noeud newnoeuds1,Noeud newnoeuds2,float newpoidsLiens) throws Exception{
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
    public String getTypeLiens(){
        return typeLiens;
    }
    public Noeud getNoeuds1(){
        return noeuds1;
    }
    public Noeud getNoeuds2(){
        return noeuds2;
    }
    public float getPoidsLiens(){
        return poidsLiens;
    }
    @Override
    public String toString(){
        //return "[lien]->["+typeLiens+"; "+noeuds1+"; "+noeuds2+"; "+poidsLiens+";] ";
        return typeLiens+" : "+poidsLiens;
        
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
    public boolean estExtremite(Noeud depart){
        return this.getNoeuds1().equals(depart) || this.getNoeuds2().equals(depart);
    }
    
    public Noeud getOppose(Noeud depart){
        if (this.getNoeuds1().equals(depart)){
            return this.getNoeuds2();
        }else{
            return this.getNoeuds1();
        }
    }
}
