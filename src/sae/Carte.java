/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sae;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import sae.myInterface.NoeudListener;

/**
 *
 * @author Loan
 */
public class Carte extends JPanel {

    // <editor-fold defaultstate="collapsed" desc="Déclaration">  
    ArrayList<Noeud> toDraw; // Noeuds à dessiner

    ArrayList<Lien> toDrawLiens; // Liens à dessiner

    int circleWidth = 20; // Taille cercle noeud

    int edgeMarginV = 1 * circleWidth; // padding veritcale

    int edgeMarginH = (int) 4.5 * circleWidth; // padding horizontal

    boolean running = false; // vraie si calcule de possition en cour

    NoeudListener listenner = null; // le neoud sélectionner

    JPanel colorIndicator = null; // panneau qui indique l'état  avec une couleur

    ArrayList<String> typeToDraw = new ArrayList<>(Arrays.asList("V", "L", "R")); // Liste des types de noeuds à dessiner

    ArrayList<String> typeToDrawLiens = new ArrayList<>(Arrays.asList("A", "D", "N")); // Liste des types de liens à dessiner

    //</editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Constructeur">  
    public Carte() {
        initComp(); // initalisation
    }
    //</editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Methodes">  
    /**
     * Première méthode à exécuter, exécutée par le constructeur Initialise les
     * listes et les listeners
     */
    public void initComp() {

        // initaliastion des ArrayList
        this.toDraw = new ArrayList<>();
        this.toDrawLiens = new ArrayList<>();

        // Ajouter Listener pour le nœud sélectionner
        this.addMouseListener(new NoeudsSelecter());

        // Ajouter Listener pour le redimensionnement de fenêtre
        this.addComponentListener(new ComponentAdapter() {

            @Override
            public void componentResized(ComponentEvent ev) {

                // si ne tourne pas deja
                if (!toDraw.isEmpty() && !running) {

                    // crée le thread
                    Thread palceThreadtmp = new Thread(() -> {
                        run();
                    });

                    // lancer le thread
                    palceThreadtmp.start();
                }
            }
        });

    }

    /**
     * Enregistrer le JPanel qui indique la progression
     *
     * @param indi type JPanel
     */
    public void addColorIndicator(JPanel indi) {
        colorIndicator = indi;
    }

    /**
     * Redéfinition de painComponent.
     *
     * @param g type Graphics
     */
    @Override
    public void paintComponent(Graphics g) {

        // Effacer le dernier paintComp
        g.setColor(this.getBackground());
        g.fillRect(0, 0, this.getWidth(), this.getHeight());

        // Dessiner les liens
        paintLien(g);

        // Desssiner les noeuds
        paintNoeud(g);

        // Dessiner les noms de neouds
        paintLabel();

    }

    /**
     * Dessiner les liens
     *
     * @param g type Graphics
     */
    public void paintLien(Graphics g) {
        // Dessiner les liens
        for (int i = 0; i < toDrawLiens.size(); i++) {
            if (typeToDraw.contains(toDrawLiens.get(i).getNoeuds1().getType())
                    && typeToDraw.contains(toDrawLiens.get(i).getNoeuds2().getType())
                    && typeToDrawLiens.contains(toDrawLiens.get(i).getTypeLiens())) {
                Noeud noeudsUn = toDrawLiens.get(i).getNoeuds1();
                Noeud noeudsDeux = toDrawLiens.get(i).getNoeuds2();

                g.setColor(new Color(150, 100, 100));
                if (toDrawLiens.get(i).getTypeLiens().equals("D")) {
                    g.setColor(new Color(100, 150, 100));
                }
                if (toDrawLiens.get(i).getTypeLiens().equals("N")) {
                    g.setColor(new Color(100, 100, 150));
                }

                g.drawLine(noeudsUn.getPosX() + (circleWidth / 2), noeudsUn.getPosY() + (circleWidth / 2),
                        noeudsDeux.getPosX() + (circleWidth / 2),
                        noeudsDeux.getPosY() + (circleWidth / 2));
            }
        }
    }

    /**
     * Desssiner les noeuds
     *
     * @param g type Graphics
     */
    public void paintNoeud(Graphics g) {

        // Dessiner les noeuds
        for (Noeud tmp : toDraw) {
            if (typeToDraw.contains(tmp.getType())) {
                Color tmpColor = Color.red;
                if (tmp.getType().equals("R")) {
                    tmpColor = Color.GREEN;
                }
                if (tmp.getType().equals("L")) {
                    tmpColor = Color.BLUE;
                }

                g.setColor(tmpColor);
                g.fillOval(tmp.getPosX(), tmp.getPosY(), circleWidth, circleWidth);
            }
        }

    }

    /**
     * Dessiner les noms de neouds
     */
    public void paintLabel() {
        this.removeAll();
        for (Noeud tmp : toDraw) {
            if (typeToDraw.contains(tmp.getType())) {
                this.add(tmp.getLabelNoeuds());
            }
        }

    }
    
    /**
     * Getter
     * @return type ArrayList<Noeud>
     */
    public ArrayList<Noeud> getToDraw() {
        return toDraw;
    }
    
    /**
     * Setter
     * @param toDraw type ArrayList<Noeud>
     */
    public void setToDraw(ArrayList<Noeud> toDraw) {
        this.toDraw = toDraw;
    }
    
    /**
     * Ajoute le noeud a toDraw
     * @param toDraw type Noeud
     */
    public void addToDraw(Noeud toDraw) {
        this.toDraw.add(toDraw);
    }
    
    
    /**
     * Setter
     * @param toDrawLiens type ArrayList<Lien>
     */
    public void setToDrawLiens(ArrayList<Lien> toDrawLiens) {
        this.toDrawLiens = toDrawLiens;
    }
    
    
    /**
     * Définit les nœuds à afficher
     * @param type type String
     * @param affich type boolean
     */
    public void setTypeToDraw(String type, boolean affich) {
        //System.out.println("check" + type + " " + affich);
        
        // Ajouter si il n'est pas dans la liste
        if (affich && !typeToDraw.contains(type)) {
            typeToDraw.add(type);
        } else if (!affich && typeToDraw.contains(type)) {
            typeToDraw.remove(type);
        }
        
        // Si il y a qlqc à dessiner  alors on dessine
        if (!toDraw.isEmpty() && !running) {
            Thread palceThreadtmp = new Thread(() -> {
                run();
            });
            palceThreadtmp.start();
        }
    }
    
    /**
     * Définit les liens à afficher
     * @param type type String
     * @param affich type boolean
     */
    public void setTypeToDrawLiens(String type, boolean affich) {
        //System.out.println("check" + type + " " + affich);
        
        // Ajouter si il n'est pas dans la liste
        if (affich && !typeToDrawLiens.contains(type)) {
            typeToDrawLiens.add(type);
        } else if (!affich && typeToDrawLiens.contains(type)) {
            typeToDrawLiens.remove(type);
        }
        
        // Si il y a qlqc à dessiner  alors on dessine
        if (!toDraw.isEmpty() && !running) {
            Thread palceThreadtmp = new Thread(() -> {
                run();
            });
            palceThreadtmp.start();
        }
    }
    
    
    /**
     * méthode à exécuter avant run, permet d'initialiser 
     * la position des nœuds sur la carte
     */
    public void init() {
        if (colorIndicator != null) {
            colorIndicator.setBackground(Color.yellow);
        }
        Random random = new Random();

        for (Noeud tmpNoeuds2 : toDraw) {
            tmpNoeuds2.setPosX(random.nextInt(edgeMarginH, this.getSize().width - edgeMarginH));
            tmpNoeuds2.setPosY(random.nextInt(edgeMarginV, this.getSize().height - edgeMarginV));
        }
        
        // Si il y a qlqc à dessiner  alors on dessine
        if (!toDraw.isEmpty() && !running) {
            Thread palceThreadtmp = new Thread(() -> {
                run();
            });
            palceThreadtmp.start();
        }
    }

    /**
     * Méthode qui calcule des nouvelles positions pour pas que les nœuds se chevauchent
     */
    public void run() {
        running = true;
        boolean edit = true;

       
        while (edit) {
            edit = false;
            Dimension tmpSize = this.getSize();
            int maxH = tmpSize.width - edgeMarginH - circleWidth / 2;
            int maxV = tmpSize.height - edgeMarginV - circleWidth / 2;
            for (Noeud tmp : toDraw) {
                if (typeToDraw.contains(tmp.getType())) {

                    int tmpPosX = tmp.getPosX();
                    int tmpPosY = tmp.getPosY();
                    int goal = 120;

                    int minSpace = 85;

                    for (Noeud tmpNoeuds2 : toDraw) {
                        if (typeToDraw.contains(tmpNoeuds2.getType())) {
                            tmpPosX += tmp.getPosDeltaX(tmpNoeuds2, minSpace) * 0.1;
                            tmpPosY += tmp.getPosDeltaY(tmpNoeuds2, minSpace) * 0.1;
                        }

                    }

                    //System.out.println(tmp.toString());
                    //System.out.println("se raprroche de ");
                    for (Noeud tmpNoeuds : tmp.getVoisin()) {// chanegr de toDraw a voisin
                        //if (typeToDraw.contains(tmpNoeuds2.getType())){
                        //System.out.println(tmpNoeuds.toString());
                        //System.out.println(" diresction "+direction+" dsiatnce "+(Math.sqrt(dX*dX)+(dY*dY)));
                        //tmpPosX += tmp.getDeltaX(tmpNoeuds,(int) tmp.getDistanceFromVoisin(tmpNoeuds));
                        //tmpPosY += tmp.getDeltaY(tmpNoeuds, (int) tmp.getDistanceFromVoisin(tmpNoeuds));

                        //important ne va plus se raprocher de ses voisins
                        //tmpPosX += tmp.getDeltaX(tmpNoeuds,goal)*0.1;
                        //tmpPosY += tmp.getDeltaY(tmpNoeuds, goal)*0.1;
                        //}
                    }

                    if (tmpPosY != tmp.getPosY() && tmpPosX != tmp.getPosX()) {

                        System.out.println(tmp.getNom());
                        edit = true;

                    }

                    tmpPosX = (int) (tmpPosX > maxH ? maxH - tmpPosX % maxH * 0 : tmpPosX);
                    tmpPosY = (int) (tmpPosY > maxV ? maxV - tmpPosY % maxV * 0 : tmpPosY);

                    tmpPosX = tmpPosX < edgeMarginH - circleWidth / 2 ? tmpPosX + edgeMarginH - circleWidth / 2 : tmpPosX;
                    tmpPosY = tmpPosY < edgeMarginV - circleWidth / 2 ? tmpPosY + edgeMarginV - circleWidth / 2 : tmpPosY;

                    tmp.setPosX(tmpPosX);
                    tmp.setPosY(tmpPosY);

                }
                if (edit) {
                    System.out.println("==");
                }
                /*for(Noeuds tmp: toDraw){
                tmp.setPosX((tmp.getPosX())+1+(tmp.getPosY()/100));
                }*/
                this.repaint();

            }
        }

        running = false;
        if (colorIndicator != null) {
            colorIndicator.setBackground(Color.green);
        }

    }
    
    
    /**
     * Méthode expérimentale qui permet d'échanger la position 
     * des nœuds pour raccourcir la distance entre leurs voisins
     */
    public void swap() {
        for (Noeud i : toDraw) {
            int Xo = i.getPosX();
            int Yo = i.getPosY();
            for (Noeud w : toDraw) {
                System.out.println(Xo + " " + Yo + " " + w.getPosX() + " " + w.getPosY() + " " + i.getAverageDistDromV() + " " + w.getAverageDistDromV(Xo, Yo));
                if (w.getAverageDistDromV(Xo, Yo) < i.getAverageDistDromV()) {
                    System.out.println("swaps");
                    i.setPosX(w.getPosX());
                    i.setPosY(w.getPosY());

                    w.setPosX(Xo);
                    w.setPosY(Yo);
                    Xo = i.getPosX();
                    Yo = i.getPosY();
                }
            }
        }
        repaint();

    }
    
    
    /**
     * Ajouter un Listener appeler quand un nœud est sélectionné
     * @param newlistenner type NoeudListener
     */
    public void addNoeudListener(NoeudListener newlistenner) {
        listenner = newlistenner;
    }
    
    //</editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classe Interne">  
   
    
    /**
     * Classe chargée de gérer la sélection des nœuds
     */
    public class NoeudsSelecter extends MouseAdapter {
        
        
        /**
         * Redéfinition de mouseClicked
         * @param e type MouseEvent
         */
        @Override
        public void mouseClicked(MouseEvent e) {
            super.mouseClicked(e);
            Noeud clossest = null;
            double d;
            double best_lengest = Integer.MAX_VALUE;
            for (Noeud i : toDraw) {

                d = Math.pow(e.getX() - i.getPosX() - i.width / 4, 2) + Math.pow(e.getY() - i.getPosY() - i.width / 4, 2);
                d = Math.sqrt(d);
                if (d < best_lengest) {
                    best_lengest = d;
                    clossest = i;
                }

            }
            System.out.println((int) best_lengest + " " + clossest + " X " + e.getX() + " Y " + e.getY());
            if (listenner != null && best_lengest < clossest.width / 2) {
                listenner.noeudSelected(clossest);
            }
        }

    }
    //</editor-fold>
    

}
