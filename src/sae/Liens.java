/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sae;

/**
 *
 * @author Loan
 */
public class Liens {
    String typeLiens;
    Noeuds noeuds1;
    Noeuds noeuds2;
    float poidsLiens;
    Liens(String newtypeLiens,Noeuds newnoeuds1,Noeuds newnoeuds2,float newpoidsLiens) throws Exception{
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
    public Noeuds getNoeuds1(){
        return noeuds1;
    }
    public Noeuds getNoeuds2(){
        return noeuds2;
    }
    public float getPoidsLiens(){
        return poidsLiens;
    }
    @Override
    public String toString(){
        return "[lien]->["+typeLiens+"; "+noeuds1+"; "+noeuds2+"; "+poidsLiens+";] ";
        
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
        final Liens other  = (Liens)obj;
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
    public boolean estExtremite(Noeuds depart){
        return this.getNoeuds1().equals(depart) || this.getNoeuds2().equals(depart);
    }
    
    public Noeuds getOppose(Noeuds depart){
        if (this.getNoeuds1().equals(depart)){
            return this.getNoeuds2();
        }else{
            return this.getNoeuds1();
        }
    }
}
