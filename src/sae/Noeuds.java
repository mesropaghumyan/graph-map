/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sae;
import java.util.ArrayList;
/**
 *
 * @author Loan
 */
public class Noeuds  {
    String typeNoeuds;
    String nomNoeuds;
    ArrayList<Liens> connection;
    Noeuds(String newtypeNoeuds, String newnomNoeuds) throws Exception{
        if (newtypeNoeuds.equals("V")  || newtypeNoeuds.equals("R") || newtypeNoeuds.equals("L") )
        {
            typeNoeuds = newtypeNoeuds;
            nomNoeuds = newnomNoeuds;
            connection = new ArrayList<>();
        }else{
            throw new TypeNotSupportedException();
        }
        
    }
    
    
    @Override
    public String toString(){
        return ("Noeuds : "+typeNoeuds+" "+nomNoeuds);
    }
    
    public String getNom(){
        return nomNoeuds;
    }
    public String getType(){
        return typeNoeuds;
    }
    
    public boolean isConnection(Liens tmp){
        return connection.contains(tmp);
    }
    public boolean addConnection(Liens tmp){
        if (!this.isConnection(tmp)){
            this.connection.add(tmp);
            return true;
        }
        return false;
    }
    @Override
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
        final Noeuds other  = (Noeuds)obj;
        return (this.getNom().equals(other.getNom())&&this.getType().equals(other.getType()));
         
    }
    
}
