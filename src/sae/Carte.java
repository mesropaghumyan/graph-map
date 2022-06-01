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
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;

/**
 *
 * @author Loan
 */
public class Carte extends JPanel{
    ArrayList<Noeuds> toDraw;
    ArrayList<Liens> toDrawLiens;
    int circleWidth = 20;
    int edgeMarginV = 1*circleWidth;
    int edgeMarginH = (int)4.5*circleWidth;
    boolean running =false;
    JPanel colorIndicator = null;
    

                

    public Carte() {
       
        this.toDraw = new ArrayList<>();
        this.toDrawLiens = new ArrayList<>();
        this.addMouseListener(new NoeudsSelecter());
        
        
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent ev) {
                if (!toDraw.isEmpty() && !running){
                    Thread palceThreadtmp = new Thread(() -> {
                        run();
                        });
                    palceThreadtmp.start();
                }
                
                
                
                
                    
            }
            
        });
    }
    public void addColorIndicator(JPanel indi){
        colorIndicator= indi;
    }
    @Override
    public void paintComponent(Graphics g){
        g.setColor(this.getBackground());
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        
        g.setColor(Color.GRAY);
        int width = circleWidth;
        for(int i=0; i<toDrawLiens.size();i++){
            Noeuds noeudsUn = toDrawLiens.get(i).getNoeuds1();
            Noeuds noeudsDeux = toDrawLiens.get(i).getNoeuds2();
            
            
            g.drawLine(noeudsUn.getPosX()+(width/2), noeudsUn.getPosY()+(width/2)
                    , noeudsDeux.getPosX()+(width/2)
                    , noeudsDeux.getPosY()+(width/2));
        }
        for(Noeuds tmp: toDraw){
            Color tmpColor = Color.red;
            if(tmp.getType().equals("R"))
                tmpColor = Color.GREEN;
            if(tmp.getType().equals("L"))
                tmpColor = Color.BLUE;
                
            g.setColor(tmpColor);
            g.fillOval(tmp.getPosX(), tmp.getPosY(), width, width);
        }
        this.paintLabel();
    }
    
    public void paintLabel(){
        this.removeAll();
        for(Noeuds tmp: toDraw){
            this.add(tmp.getLabelNoeuds());
            
        }
        
    }

    public ArrayList<Noeuds> getToDraw() {
        return toDraw;
    }

    public void setToDraw(ArrayList<Noeuds> toDraw) {
        this.toDraw = toDraw;
    }
    public void addToDraw(Noeuds toDraw) {
        this.toDraw.add(toDraw);
    }
    public void setToDrawLiens(ArrayList<Liens> toDrawLiens){
        this.toDrawLiens = toDrawLiens;
    }
    
    public void init(){
        if (colorIndicator != null){
            colorIndicator.setBackground(Color.yellow);}
        Random random = new Random();
     
        for(Noeuds tmpNoeuds2 : toDraw){
            
            tmpNoeuds2.setPosX(random.nextInt(edgeMarginH, this.getSize().width-edgeMarginH));
            tmpNoeuds2.setPosY(random.nextInt(edgeMarginV, this.getSize().height-edgeMarginV));

            
        }
        if (!toDraw.isEmpty() && !running){
                    Thread palceThreadtmp = new Thread(() -> {
                        run();
                        });
                    palceThreadtmp.start();
                }
    }
    
    public void run(){
        running = true;
        boolean edit = true;
        
        //for(int i=0; i<2000; i++){
        
        while (edit){
            edit = false;
            Dimension tmpSize = this.getSize();
            int maxH =  tmpSize.width-edgeMarginH-circleWidth/2;
            int maxV = tmpSize.height-edgeMarginV-circleWidth/2 ;
            for(Noeuds tmp : toDraw){
                
                int tmpPosX = tmp.getPosX();
                int tmpPosY = tmp.getPosY();
                int goal = 100;

                int minSpace = 85;
            
            
 
            for(Noeuds tmpNoeuds2 : toDraw){
                tmpPosX += tmp.getPosDeltaX(tmpNoeuds2, minSpace)*0.5;
                tmpPosY += tmp.getPosDeltaY(tmpNoeuds2, minSpace)*0.5; 
       
     
            }
 
   
            for(Noeuds tmpNoeuds : toDraw){
                
                //System.out.println(" diresction "+direction+" dsiatnce "+(Math.sqrt(dX*dX)+(dY*dY)));
                //tmpPosX += tmp.getDeltaX(tmpNoeuds,(int) tmp.getDistanceFromVoisin(tmpNoeuds));
                //tmpPosY += tmp.getDeltaY(tmpNoeuds, (int) tmp.getDistanceFromVoisin(tmpNoeuds));
                
                //important ne va plus se raprocher de ses voisins
                //tmpPosX -= tmp.getDeltaX(tmpNoeuds,goal)*0.1;
                //tmpPosY -= tmp.getDeltaY(tmpNoeuds, goal)*0.1;
            }
            
            if (tmpPosY != tmp.getPosY() && tmpPosX != tmp.getPosX()){
        
                System.out.println(tmp.getNom());
                edit = true;
                
            }
            
            
            tmpPosX = (int) (tmpPosX > maxH ? maxH -tmpPosX%maxH*0: tmpPosX);
            tmpPosY = (int) (tmpPosY > maxV ?maxV - tmpPosY%maxV*0: tmpPosY) ;
            
            tmpPosX = tmpPosX < edgeMarginH-circleWidth/2 ? tmpPosX +edgeMarginH-circleWidth/2: tmpPosX ;
            tmpPosY = tmpPosY < edgeMarginV-circleWidth/2 ? tmpPosY +edgeMarginV-circleWidth/2: tmpPosY ;
            
            tmp.setPosX(tmpPosX );
            tmp.setPosY(tmpPosY);
    
            
            
        }
            if (edit)
                System.out.println("==");
            /*for(Noeuds tmp: toDraw){
            tmp.setPosX((tmp.getPosX())+1+(tmp.getPosY()/100));
            }*/
            this.repaint();
            try {
                Thread.sleep(0);
            } catch (InterruptedException ex) {
                Logger.getLogger(IHM.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        
        running =false;
        if (colorIndicator != null){
            colorIndicator.setBackground(Color.green);}
        
    }
    
    
    
    
    
    public class NoeudsSelecter extends MouseAdapter{

        @Override
        public void mouseClicked(MouseEvent e) {
            super.mouseClicked(e);
            Noeuds clossest = null;
            double d;
            double best_lengest = Integer.MAX_VALUE;
            for (Noeuds i : toDraw){
                
                d =   Math.pow(e.getX() - i.getPosX()-i.width/4,2)+  Math.pow(e.getY()-i.getPosY()-i.width/4,2);
                d=  Math.sqrt(d);
                if (d<best_lengest){
                    best_lengest = d;
                    clossest = i;
                }
                
            }
            System.out.println((int)best_lengest+" "+clossest+" X " + e.getX() + " Y " + e.getY());
        }
    
        
    
}
    
    
}
