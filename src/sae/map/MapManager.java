/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sae.map;

import sae.exception.TypeNotSupportedException;
import java.awt.Color;
import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner;
import java.util.ArrayList;
import javax.swing.JPanel;

/**
 *
 * @author p2103642
 */
public class MapManager {

    // <editor-fold defaultstate="collapsed" desc="Déclaration">  
    ArrayList<Noeud> listeNoeuds; // liste de noeuds

    ArrayList<Lien> listeLiens; // liste de liens

    JPanel colorIndicator = null; // indicateur de progression

    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructeur">  
    public MapManager() {
        // initalisation des listes
        listeNoeuds = new ArrayList<>();
        listeLiens = new ArrayList<>();

    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Methodes"> 
    /**
     * Getter retourne la liste de noeud
     *
     * @return type ArrayList de Noeud
     */
    public ArrayList<Noeud> getListeNoeuds() {
        return listeNoeuds;
    }

    /**
     * Getter retourne la liste de Liens
     *
     * @return type ArrayList de Lien
     */
    public ArrayList<Lien> getlisteLiens() {
        return listeLiens;
    }

    /**
     * Retourne les liens reliés au nœud en paramètre
     *
     * @param depart type Noeud
     * @return type ArrayList de Lien
     */ 
    public ArrayList<Lien> getCoonections1Noeud(Noeud depart) {
        ArrayList<Lien> routesTrouver = new ArrayList<>();
        for (Lien tmpVoisin : this.listeLiens) {
            if (tmpVoisin.estExtremite(depart)) {
                routesTrouver.add(tmpVoisin);
            }
        }
        return routesTrouver;

    }

    /**
     * Retourne les nœuds voisins au nœud en paramètre
     *
     * @param depart type Noeud
     * @return type ArrayList de Noeud
     */
    public ArrayList<Noeud> getVoisins1Noeud(Noeud depart) {
        ArrayList<Noeud> villesTrouver = new ArrayList<>();
        for (Lien tmpVoisin : this.listeLiens) {
            if (!villesTrouver.contains(tmpVoisin.getOppose(depart))) {
                villesTrouver.add(tmpVoisin.getOppose(depart));
            }
        }
        return villesTrouver;

    }

    /**
     * Retourne le nombre de ville
     *
     * @return type int
     */
    public int getNbVilles() {
        int res = 0;
        for (Noeud i : listeNoeuds) {
            res = i.getType().equals("V") ? res + 1 : res;
        }
        return res;
    }

    /**
     * Retourne le nombre de centre de loisir
     *
     * @return type int
     */
    public int getNbLoisirs() {
        int res = 0;
        for (Noeud i : listeNoeuds) {
            res = i.getType().equals("L") ? res + 1 : res;
        }
        return res;
    }

    /**
     * Retourne le nombre de restaurant
     *
     * @return type int
     */
    public int getNbRestaurants() {
        int res = 0;
        for (Noeud i : listeNoeuds) {
            res = i.getType().equals("R") ? res + 1 : res;
        }
        return res;
    }

    /**
     * Retourne le nombre d'autoroute
     *
     * @return type int
     */
    public int getNbAutoroutes() {
        int res = 0;
        for (Lien i : listeLiens) {
            res = i.getTypeLiens().equals("A") ? res + 1 : res;
        }
        return res;
    }

    /**
     * Retourne le nombre de nationale
     *
     * @return type int
     */
    public int getNbNationales() {
        int res = 0;
        for (Lien i : listeLiens) {
            res = i.getTypeLiens().equals("N") ? res + 1 : res;
        }
        return res;
    }

    /**
     * Retourne le nombre de departementales
     *
     * @return type int
     */
    public int getNbDepartementales() {
        int res = 0;
        for (Lien i : listeLiens) {
            res = i.getTypeLiens().equals("D") ? res + 1 : res;
        }
        return res;
    }

    /**
     * Enregistre l'indicateur de progression
     *
     * @param indi type JPanel
     */
    public void addColorIndicator(JPanel indi) {

        colorIndicator = indi;
        System.out.println("\u001B[32m" + "[INFO]" + "\u001B[0m" + " Color Indicator added for Map Manager");
    }

    /**
     * Méthode générale pour charger un graphe à partir d'un fichier
     * techniquement elle ne gère que les exceptions de fichier elle même le
     * reste fait appel à d'autre méthode
     *
     * @param filename type String
     */
    public void loadMap(String filename) {
        if (colorIndicator != null) {
            colorIndicator.setBackground(Color.yellow);

        }

        listeNoeuds = new ArrayList<>();
        listeLiens = new ArrayList<>();
        String res_file = "";
        try {
            File fileMap = new File(filename);
            Scanner fileReader = new Scanner(fileMap);
            while (fileReader.hasNextLine()) {
                String data = fileReader.nextLine();
                res_file += data;

            }
            fileReader.close();
            this.stringMap(res_file);
            if (colorIndicator != null) {
                colorIndicator.setBackground(Color.green);
            }

        } catch (FileNotFoundException e) {
            System.out.println("\u001B[33m" + "[WARNING]" + "\u001B[0m" + " Fichier introuvable");
            if (colorIndicator != null) {
                colorIndicator.setBackground(Color.red);
            }

        } catch (Exception e) {
            System.out.println(e);
            if (colorIndicator != null) {
                colorIndicator.setBackground(Color.red);
            }

        }

    }

    /**
     * Convertit un string en graphe Techniquement ne fait qu' un split sur les
     * “;;” et d’autre méthode se charge du reste
     *
     * @param data type String
     */
    public void stringMap(String data) {
        String[] villeListe;

        String[] voisins;

        //this.stringToListNoeuds(data); recupere to les noeuds de departs
        villeListe = data.split(";;");
        for (int i = 0; i < villeListe.length; i++) {
            voisins = villeListe[i].substring(villeListe[i].indexOf(":") + 1, villeListe[i].length()).split(";");

            Noeud depart = this.stringToNoeud(villeListe[i].substring(0, villeListe[i].indexOf(":")));

            if (!this.addToListeNoeuds(depart)) {
                depart = this.getNoeudsByNameAndType(depart);
            }

            for (int z = 0; z < voisins.length; z++) {
                try {

                    this.ajouterRoute(voisins[z], depart);

                } catch (StringIndexOutOfBoundsException e) {
                    System.out.println("\u001B[33m" + "[WARNING]" + "\u001B[0m" + " Une connection n'a pas pu etre ajouté, elle ne respect pas la structure");
                } catch (Exception e) {
                    System.out.println(e);

                }

            }
            if (!listeNoeuds.contains(depart)) {
                this.listeNoeuds.add(depart);
            }

        }

        System.out.println("\u001B[32m" + "[INFO]" + "\u001B[0m" + " Importation terminé");

    }

    /**
     * Extrait le nœud à partir d’un String.
     *
     * @param villeInfo type String
     * @return type Noeud
     */
    public Noeud stringToNoeud(String villeInfo) {

        String villeType = villeInfo.substring(0, villeInfo.indexOf(",")).trim();
        String villeNom = villeInfo.substring(villeInfo.indexOf(",") + 1, villeInfo.length()).trim();

        try {
            Noeud newNoeuds = new Noeud(villeType, villeNom);
            return newNoeuds;
        } catch (TypeNotSupportedException e) {
            System.out.println("Exception : " + e);
        } catch (Exception ex) {
            System.out.println("Exception : " + ex);
        }
        return null;

    }

    /**
     * Extrait le lien à partir d’un String.
     *
     * @param data type String
     * @param depart type Noeud
     * @param arriver type Noeud
     * @return type Lien
     * @throws java.lang.Exception type non supporter
     */
    public Lien stringToLiens(String data, Noeud depart, Noeud arriver) throws Exception {
        String routeType;
        float routeLongueur;

        routeType = data.substring(0, data.indexOf(",")).trim();
        routeLongueur = Float.parseFloat(data.substring(data.indexOf(",") + 1, data.indexOf(":")).trim());
        return new Lien(routeType, depart, arriver, routeLongueur);

    }

    /**
     * Retourne un objet noeud qui a le même type et nom.
     * @param or type Noeud
     * @return Retourne le noeud is trouver sinon null
     */
    public Noeud getNoeudsByNameAndType(Noeud or) {
        for (Noeud ville : listeNoeuds) {

            if (or.equals(ville)) {
                return ville;
            }
        }
        return null;
    }

    /**
     * Ajoute un nœud à la liste tout en vérifiant qu’il n’est déjà pas présent.
     *
     * @param newVille Le nœud à ajouter.
     * @return Retourne true si le nœud a été ajouté sinon false.
     */
    public boolean addToListeNoeuds(Noeud newVille) {

        if (!listeNoeuds.contains(newVille)) {
            this.listeNoeuds.add(newVille);
            System.out.println("\u001B[32m" + "[INFO]" + "\u001B[0m" + " Noeud ajouté : " + newVille.getNom());
            return true;
        }
        return false;
    }

    /**
     * Ajoute un lein à la liste tout en vérifiant qu’il n’est déjà pas présent.
     *
     * @param newRoute Le nœud à ajouter.
     * @return Retourne true si le nœud a été ajouté sinon false.
     */
    public boolean addToListeLiens(Lien newRoute) {
        if (!this.listeLiens.contains(newRoute)) {
            this.listeLiens.add(newRoute);
            return true;
        }
        return false;
    }

    /**
     * Retire un lien de la liste si existant true sinon false
     *
     * @param newRoute type Lien
     * @return type boolean
     */
    public boolean removeFromListeRoutes(Lien newRoute) {
        if (this.listeLiens.contains(newRoute)) {
            this.listeLiens.remove(newRoute);
            return true;
        }
        return false;

    }

    /**
     * Convertit un string en noeud (parti a droite du ‘:’)
     *
     * @param data type String
     * @param depart type Noeud
     * @return type Boolean
     * @throws Exception Exception géré au dessus
     */
    public Boolean ajouterRoute(String data, Noeud depart) throws Exception {

        Noeud tmp = this.stringToNoeud(data.substring(data.indexOf("::") + 2, data.length()));
        if (!this.addToListeNoeuds(tmp)) {
            tmp = this.getNoeudsByNameAndType(tmp);
        }

        //Creation du lien
        Lien tmp_lien = this.stringToLiens(data, depart, tmp);

        //Ajout de la connection au deux extréminté
        if (depart.addConnection(tmp_lien) == true && tmp.addConnection(tmp_lien) == true) {
            System.out.println("\u001B[32m" + "[INFO]" + "\u001B[0m" + " Lien ajouté : " + depart.getNom() + " -> " + tmp.getNom() + " " + tmp_lien.getPoidsLiens() + "km");
        }

        return this.addToListeLiens(tmp_lien);

    }

    /**
     * Retourne true si les noeuds sont a 2 de distance
     *
     * @param depart type noeud
     * @param Arriver type noeud
     * @return type type boolean
     */
    public boolean estA2Distance(Noeud depart, Noeud Arriver) {
        if (depart == null || Arriver == null){
            return false;            
        }
        if (depart.equals(Arriver)){
            return false;
        }
        for (Lien voisin2Depart : depart.getConnection()) {
            for (Lien tmp : voisin2Depart.getOppose(depart).getConnection()) {
                if (tmp.getOppose(voisin2Depart.getOppose(depart)).equals(Arriver)) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Retourne le nombre de ville a 2 distance du noeud
     *
     * @param depart type noeud
     * @return return int
     */
    public int villeNoeuds(Noeud depart) {
        int c = 0;
        for (Lien voisin2Depart : depart.getConnection()) {
            for (Lien tmp : voisin2Depart.getOppose(depart).getConnection()) {
                if (tmp.getOppose(voisin2Depart.getOppose(depart)).getType().equalsIgnoreCase("V") && !tmp.getOppose(voisin2Depart.getOppose(depart)).equals(depart)) {
                    c++;
                }
            }
        }

        return c;
    }

    /**
     * Retourne le nombre de restaurant a 2 distance du noeud
     *
     * @param depart type noeud
     * @return return int
     */
    public int gastronomoieNoeuds(Noeud depart) {
        int c = 0;
        for (Lien voisin2Depart : depart.getConnection()) {
            for (Lien tmp : voisin2Depart.getOppose(depart).getConnection()) {
                if (tmp.getOppose(voisin2Depart.getOppose(depart)).getType().equalsIgnoreCase("R") && !tmp.getOppose(voisin2Depart.getOppose(depart)).equals(depart)) {
                    c++;
                }
            }
        }

        return c;
    }

    /**
     * Retourne le nombre de centre de loisir a 2 distance du noeud
     *
     * @param depart type noeud
     * @return return int
     */
    public int cultureNoeuds(Noeud depart) {
        int c = 0;
        for (Lien voisin2Depart : depart.getConnection()) {
            for (Lien tmp : voisin2Depart.getOppose(depart).getConnection()) {
                if (tmp.getOppose(voisin2Depart.getOppose(depart)).getType().equalsIgnoreCase("L") && !tmp.getOppose(voisin2Depart.getOppose(depart)).equals(depart)) {
                    c++;
                }
            }
        }

        return c;
    }

    /**
     * Retourne true si le nombre de ville a 2 distance de noeud sujet est
     * superieur au noeud autre sinon false
     *
     * @param sujet type noeud
     * @param autre type noeud
     * @return return boolean
     */
    public boolean estPlusOuvert(Noeud sujet, Noeud autre) {
        if(sujet == null && autre == null){
            return false;
        }
        if(sujet == null){
            return false;
        }
        if(autre == null){
            return true;
        }
        return (this.villeNoeuds(sujet) > this.villeNoeuds(autre));
    }

    /**
     * Retourne true si le nombre de restaurant a 2 distance de noeud sujet est
     * superieur au noeud autre sinon false
     *
     * @param sujet type noeud
     * @param autre type noeud
     * @return return boolean
     */
    public boolean estPlusGastronomique(Noeud sujet, Noeud autre) {
        if(sujet == null && autre == null){
            return false;
        }
        if(sujet == null){
            return false;
        }
        if(autre == null){
            return true;
        }
        return (this.gastronomoieNoeuds(sujet) > this.gastronomoieNoeuds(autre));
    }

    /**
     * Retourne true si le nombre de centre de loisir a 2 distance de noeud
     * sujet est superieur au noeud autre sinon false
     *
     * @param sujet type noeud
     * @param autre type noeud
     * @return return boolean
     */
    public boolean estPlusCulturel(Noeud sujet, Noeud autre) {
        if(sujet == null && autre == null){
            return false;
        }
        if(sujet == null){
            return false;
        }
        if(autre == null){
            return true;
        }
        return (this.cultureNoeuds(sujet) > this.cultureNoeuds(autre));
    }

    // </editor-fold>
}
