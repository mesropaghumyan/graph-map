package sae.map;

import sae.exception.TypeNotSupportedException;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JLabel;

/**
 * Cette classe représente les noeuds.
 * @author Loan & Mesrop
 * @version 1.0
 */

public class Noeud {

    // <editor-fold defaultstate="collapsed" desc="Déclaration">  
    String typeNoeuds; // le type du noeud V : Ville L: Loisir R: Resto*

    String nomNoeuds; // le nom du noeud

    ArrayList<Lien> connection; // la liste des liens auxquels il est relié

    int vPosX; // sa position en X

    int vPosY; // sa position en Y

    int width = 20; // sa taille

    JLabel labelNoeuds; // son label associé
    
    boolean selected; // si le noeud est selectionner

    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructeur">  
    public Noeud(String newtypeNoeuds, String newnomNoeuds) throws TypeNotSupportedException {
        newtypeNoeuds = newtypeNoeuds.toUpperCase();
        if (newtypeNoeuds.equals("V") || newtypeNoeuds.equals("R") || newtypeNoeuds.equals("L")) {
            typeNoeuds = newtypeNoeuds;
            nomNoeuds = newnomNoeuds;
            connection = new ArrayList<>();
            vPosX = 1;
            vPosY = 1;
            labelNoeuds = new JLabel(newnomNoeuds);
            labelNoeuds.setBounds(0, 0, 200, 20);
            labelNoeuds.setLocation(0, 20);
        } else {
            throw new TypeNotSupportedException();
        }

    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Methodes"> 
    /**
     * Getter nom Noeud
     *
     * @return type nomNoeuds
     */
    public String getNom() {
        return nomNoeuds;
    }

    /**
     * Getter type noeud
     *
     * @return type String
     */
    public String getType() {
        return typeNoeuds;
    }

    /**
     * Getter taille noeud
     *
     * @return type int
     */
    public int getWidth() {
        return width;
    }

    /**
     * Getter Label associé au noeud
     *
     * @return type JLabel
     */
    public JLabel getLabelNoeuds() {
        this.updateLabelPos();
        return labelNoeuds;
    }

    /**
     * Getter liste des liens connceté
     *
     * @return type ArrayList de Lien
     */
    public ArrayList<Lien> getConnection() {
        return connection;
    }
    
    /**
     * Getter est sélectionner
     * @return type boolean
     */
    public boolean isSelected() {
        return selected;
    }
    
    /**
     * Setter est sélectionner
     * @param selected type boolean
     */
    public void setSelected(boolean selected) {
        this.selected = selected;
    }
    
    
    /**
     * Getter pos en x
     *
     * @return type int
     */
    public int getPosX() {
        return vPosX;
    }

    /**
     * Stter pos en x
     *
     * @param vPosX type int
     */
    protected void setPosX(int vPosX) {
        this.vPosX = vPosX;
    }

    /**
     * Getter pos en y
     *
     * @return type int
     */
    public int getPosY() {
        return vPosY;
    }

    /**
     * Setter pos en y
     * @param vPosY type int
     */
    protected void setPosY(int vPosY) {
        this.vPosY = vPosY;
    }
    
    /**
     * Met à jour la position du label par rapport au nœud
     */
    public void updateLabelPos() {
        labelNoeuds.setLocation((int) (vPosX - nomNoeuds.length() * 2.5), vPosY - 15);
    }
    
    /**
     * Ajoute le lien à la liste et retourne true si elle y était déjà.
     * @param tmp type Lien
     * @return type boolean
     */
    public boolean addConnection(Lien tmp) {
        if (!this.connection.contains(tmp)) {
            this.connection.add(tmp);
            return true;
        }
        return false;
    }
    
    
    /**
     * Retourne la liste de noeud voisin
     * @return type ArrayList de Noeud
     */
    public ArrayList<Noeud> getVoisin() {
        ArrayList<Noeud> voi = new ArrayList<>();
        for (Lien tmp : this.connection) {
            voi.add(tmp.getOppose(this));
        }
        return voi;
    }
    
    /**
     * Retourne true si la noeud en paramètre est un voisin
     * @param autre type Noeud
     * @return type boolean
     */
    public boolean estVoisin(Noeud autre) {
        for (Lien tmp : this.connection) {
            if (tmp.getOppose(this).equals(autre)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Retourne la distance avec se voisin
     * @param tmpVoisin type Noeud
     * @return type float
     */
    protected float getDistanceFromVoisin(Noeud tmpVoisin) {
        for (Lien tmpLiens : this.connection) {
            if (tmpLiens.getOppose(this).equals(tmpVoisin)) {
                return tmpLiens.getPoidsLiens();
            }
        }
        return 0;
    }
    
    /**
     * Retourne la distance moyen avec ses voisins
     * @return type float
     */
    protected float getAverageDistDromV() {
        int somme = 0;
        for (Noeud i : this.getVoisin()) {
            somme += getDistanceFromVoisin(i);

        }
        return somme / this.getVoisin().size();
    }
    
    /**
     * Retourne la distance moyen avec ses voisins si le nœud a 
     * les coordonnees passé en paramètre
     * @param tmpx type int
     * @param tmpy type int
     * @return type float
     */
    protected float getAverageDistDromV(int tmpx, int tmpy) {
        int bx = vPosX;
        int by = vPosY;
        vPosX = tmpx;
        vPosY = tmpy;
        int somme = 0;
        for (Noeud i : this.getVoisin()) {
            somme += getDistanceFromVoisin(i);
        }
        vPosX = bx;
        vPosY = by;
       
        return somme;//this.getVoisin().size();
    }
    
    
    
    /**
     * Retourne combien il faut déplacer le noeud sur l'axe X
     * pour ne respecter une distance minimum  avec un autre noeud
     * @param tmpVoisin type Noeud
     * @param goal type int
     * @return type double
     */
    protected double getPosDeltaX(Noeud tmpVoisin) {
        if (tmpVoisin.equals(this)) {
            return 0.0;
        }
        int dX = tmpVoisin.getPosX() - this.getPosX() + (tmpVoisin.width / 2) - (this.width / 2);

        return dX;
    }
    
    /**
     * Retourne combien il faut déplacer le noeud sur l'axe Y
     * pour ne respecter une distance minimum  avec un autre noeud
     * @param tmpVoisin type Noeud
     * @param goal type int
     * @return type double
     */
    protected double getPosDeltaY(Noeud tmpVoisin) {
        if (tmpVoisin.equals(this)) {
            return 0.0;
        }
        int dY = tmpVoisin.getPosY() - this.getPosY() + (tmpVoisin.width / 2) - (this.width / 2);
  
        return dY;
    }

    @Override
    public String toString() {
        return ("Noeuds : " + typeNoeuds + " " + nomNoeuds);
    }
    
    /**
     * toString mais avec moin d'info
     * @return type String
     */
    public String toStringList() {
        return (typeNoeuds + " : " + nomNoeuds);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;

        }
        if (obj.getClass() != getClass()) {
            return false;
        }
        final Noeud other = (Noeud) obj;

        return (this.getNom().equals(other.getNom()) && this.getType().equals(other.getType()));

    }
    // </editor-fold>

}
