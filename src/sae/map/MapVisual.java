/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sae.map;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import javax.swing.JPanel;
import sae.myInterface.NoeudListener;

/**
 *  Classe responsable de la représentation du graphe
 * @author Loan
 */
public class MapVisual extends JPanel {

    // <editor-fold defaultstate="collapsed" desc="Déclaration">  
    
    ArrayList<Noeud> toDraw; // Noeuds à dessiner

    ArrayList<Lien> toDrawLiens; // Liens à dessiner

    int avgTextWidth = 20; // Taille cercle noeud

    int edgeMarginV = (int) (1 * avgTextWidth); // padding veritcale

    int edgeMarginH = (int) 4.5 * avgTextWidth; // padding horizontal

    boolean running = false; // vraie si calcule de possition en cour

    NoeudListener listenner = null; // le neoud sélectionner

    JPanel colorIndicator = null; // panneau qui indique l'état  avec une couleur

    ArrayList<String> typeToDraw = new ArrayList<>(Arrays.asList("V", "L", "R")); // Liste des types de noeuds à dessiner

    ArrayList<String> typeToDrawLiens = new ArrayList<>(Arrays.asList("A", "D", "N")); // Liste des types de liens à dessiner
        
    ArrayList<Noeud> selectedNoeud = new ArrayList<>(); // la liste des noeud selectionner
    
    int selectedOffset = 4; // La largeur de l'anneau qui représente que le nœud est sélectionné
    
    
    //</editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructeur">  
    public MapVisual() {
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
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON);
        // Effacer le dernier paintComp
        g2.setColor(this.getBackground());
        g2.fillRect(0, 0, this.getWidth(), this.getHeight());

        // Dessiner les liens
        paintLien(g2);

        // Desssiner les noeuds
        paintNoeud(g2);

        // Dessiner les noms de neouds
        paintLabel();

    }

    /**
     * Dessiner les liens
     *
     * @param g type Graphics2D
     */
    public void paintLien(Graphics2D g) {
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

                g.drawLine(noeudsUn.getPosX() + (noeudsUn.getWidth() / 2), noeudsUn.getPosY() + (noeudsUn.getWidth() / 2),
                        noeudsDeux.getPosX() + (noeudsUn.getWidth() / 2),
                        noeudsDeux.getPosY() + (noeudsUn.getWidth() / 2));
            }
        }
    }

    /**
     * Desssiner les noeuds
     *
     * @param g type Graphics2D
     */
    public void paintNoeud(Graphics2D g) {

        // Dessiner les noeuds
        for (Noeud tmp : toDraw) {
            if (typeToDraw.contains(tmp.getType())) {
                if (tmp.isSelected()){
                    g.setColor( Color.BLACK);
                    g.fillOval(tmp.getPosX()-(selectedOffset/2), tmp.getPosY()-(selectedOffset/2), 
                            tmp.getWidth()+selectedOffset, tmp.getWidth()+selectedOffset);
                }
                Color tmpColor = Color.RED;
                if (tmp.getType().equals("R")) {
                    tmpColor = Color.GREEN;
                }
                if (tmp.getType().equals("L")) {
                    tmpColor = Color.BLUE;
                }

                g.setColor(tmpColor);
                g.fillOval(tmp.getPosX(), tmp.getPosY(), tmp.getWidth(), tmp.getWidth());
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
     * @return type ArrayList de Noeud
     */
    public ArrayList<Noeud> getToDraw() {
        return toDraw;
    }
    
    /**
     * Setter
     * @param toDraw type ArrayList de Noeud
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
     * @param toDrawLiens type ArrayList de Lien
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
        System.out.println("\u001B[32m"+"[INFO]"+"\u001B[0m"+" Affichage " + type + " " + affich);
        
        
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
        System.out.println("\u001B[32m"+"[INFO]"+"\u001B[0m"+" Affichage " + type + " " + affich);
        
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
            tmpNoeuds2.setPosX(random.nextInt( this.getSize().width - edgeMarginH*2)+edgeMarginH);
            tmpNoeuds2.setPosY(random.nextInt( this.getSize().height - edgeMarginV*2)+edgeMarginV);
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
            int maxH = tmpSize.width - edgeMarginH ;
            int maxV = tmpSize.height - edgeMarginV ;
            for (Noeud tmp : toDraw) {
                if (typeToDraw.contains(tmp.getType())) {

                    int tmpPosX = tmp.getPosX();
                    int tmpPosY = tmp.getPosY();
      

                    int minSpace = 85;

                    for (Noeud tmpNoeuds2 : toDraw) {
                        if (typeToDraw.contains(tmpNoeuds2.getType())) {
                            tmpPosX += tmp.getPosDeltaX(tmpNoeuds2, minSpace) * 0.2;
                            tmpPosY += tmp.getPosDeltaY(tmpNoeuds2, minSpace) * 0.2;
                        }

                    }

                    tmpPosX = (int) (tmpPosX > maxH ? maxH - tmpPosX % maxH * 0 : tmpPosX);
                    tmpPosY = (int) (tmpPosY > maxV ? maxV - tmpPosY % maxV * 0 : tmpPosY);

                    tmpPosX = tmpPosX < edgeMarginH + tmp.getWidth() / 2 ?  edgeMarginH + tmp.getWidth() / 2 : tmpPosX; //tmpPosX +
                    tmpPosY = tmpPosY < edgeMarginV + tmp.getWidth() / 2 ?  edgeMarginV + tmp.getWidth() / 2 : tmpPosY; //tmpPosY +
                    
                    if (edit == false && (tmpPosX != tmp.getPosX() || tmpPosY != tmp.getPosY())){
                        edit = true;
                    }
                    tmp.setPosX(tmpPosX);
                    tmp.setPosY(tmpPosY);

                }
       
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
                
                if (w.getAverageDistDromV(Xo, Yo) < i.getAverageDistDromV()) {
                    
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
        System.out.println("\u001B[32m"+"[INFO]"+"\u001B[0m"+" Swaped");

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
            Noeud closest = null;
            double d;
            double best_lengest = Integer.MAX_VALUE;
            for (Noeud i : toDraw) {

                d = Math.pow(e.getX() - i.getPosX() - i.width / 4, 2) + Math.pow(e.getY() - i.getPosY() - i.width / 4, 2);
                d = Math.sqrt(d);
                if (d < best_lengest) {
                    best_lengest = d;
                    closest = i;
                }

            }
            
            for (Noeud i : selectedNoeud){
                i.setSelected(false);
            }
            if(closest != null && best_lengest < closest.width ){
                closest.setSelected(true);
                selectedNoeud.add(closest);
                System.out.println("\u001B[32m"+"[INFO]"+"\u001B[0m"+ " Cliked "+(int) best_lengest + " " + closest );
                if (listenner != null ) {
                
                listenner.noeudSelected(closest);
            }
            }
            
            
            
            
            repaint();
        }

    }
    //</editor-fold>
    

}
