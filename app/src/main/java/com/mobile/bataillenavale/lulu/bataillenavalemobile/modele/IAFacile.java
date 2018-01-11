package com.mobile.bataillenavale.lulu.bataillenavalemobile.modele;


import java.util.Vector;

/**
 * Created by simon on 08/01/18.
 */

public class IAFacile extends Joueur {
    private int[][] adverse;
    //0 non decouvert
    //1 toucher
    //2 couler
    private Vector<Integer> dernierCoup = null;
    private int tailleX;
    private int tailleY;

    public IAFacile(int tailleX, int tailleY, int nbTorpilleur, int nbContreTorpilleur, int nbCroiseur, int nbPorteAvion){
        super(tailleX, tailleY);
        adverse = new int[tailleX][tailleY];
        this.tailleX = tailleX;
        this.tailleY = tailleY;
        Runnable run = new initPlateau(plateauModele,nbTorpilleur,nbContreTorpilleur,nbCroiseur,nbPorteAvion,tailleX,tailleY);
        Thread t = new Thread(run);
        t.start();
    }

    public Vector<Integer> tirer(){
        Vector<Integer> cible = new Vector<>();
        int x,y;
        do {
            x = (int) (Math.random() * adverse.length);
            y = (int) (Math.random() * adverse[0].length);
        }while(adverse[x][y] != 0);
        cible.add(0,x);
        cible.add(1,y);

        return cible;
    }

    public void reponse(int x, int y, boolean toucher){
        if (toucher)
            adverse[x][y] = 1;
        else
            adverse[x][y] = 2;
    }

    private class initPlateau implements Runnable{
        private PlateauModele plateau;
        private int nbTorpilleur;
        private int nbContreTorpilleur;
        private int nbCroiseur;
        private int nbPorteAvion;
        private int sizeX;
        private int sizeY;

        public initPlateau(PlateauModele plateau,int nbTorpilleur, int nbContreTorpilleur, int nbCroiseur, int nbPorteAvion, int sizeX, int sizeY){
            this.plateau = plateau;
            this.nbTorpilleur = nbTorpilleur;
            this.nbContreTorpilleur=nbContreTorpilleur;
            this.nbCroiseur=nbCroiseur;
            this.nbPorteAvion = nbPorteAvion;
            this.sizeX = sizeX;
            this.sizeY = sizeY;
        }

        @Override
        public void run() {
            final int tentativeMax = 5;
            int x ;
            int y ;
            double direction;
            boolean reussi;
            do {
                reussi = true;
                plateau.clear();
                for (int i = 0; i < nbPorteAvion; i++) {
                    if(reussi) {
                        int nbTentative = 0;
                        do {
                            direction = Math.random();
                            if (direction < 0.5) {
                                //Vertical
                                x = (int) (Math.random() * sizeX);
                                y = (int) (Math.random() * (sizeY - 5));
                                direction = Bateau.VERTICAL;
                            } else {
                                //Horizontal
                                x = (int) (4 + Math.random() * (sizeX - 5));
                                y = (int) (Math.random() * (sizeY - 5));
                                direction = Bateau.HORIZONTAL;
                            }
                            reussi = plateau.poser(x, y, (int) direction, Bateau.PORTE_AVION);
                        } while (!reussi && nbTentative++ < tentativeMax);
                    }else
                        break;
                }
                if(reussi)
                    for (int i = 0; i < nbCroiseur; i++) {
                        int nbTentative = 0;
                        do {
                            direction = Math.random();
                            if (direction < 0.5) {
                                //Vertical
                                x = (int) (Math.random() * sizeX);
                                y = (int) (Math.random() * (sizeY - 4));
                                direction = Bateau.VERTICAL;
                            } else {
                                //Horizontal
                                x = (int) (3 + Math.random() * (sizeX - 4));
                                y = (int) (Math.random() * (sizeY - 4));
                                direction = Bateau.HORIZONTAL;
                            }
                            reussi = plateau.poser(x, y, (int) direction, Bateau.CROISEUR);
                        } while (!reussi && nbTentative++ < tentativeMax);
                    }
                if(reussi)
                    for (int i = 0; i < nbContreTorpilleur; i++) {
                        int nbTentative = 0;
                        do {
                            direction = Math.random();
                            if (direction < 0.5) {
                                //Vertical
                                x = (int) (Math.random() * sizeX);
                                y = (int) (Math.random() * (sizeY - 3));
                                direction = Bateau.VERTICAL;
                            } else {
                                //Horizontal
                                x = (int) (2 + Math.random() * (sizeX - 3));
                                y = (int) (Math.random() * (sizeY - 3));
                                direction = Bateau.HORIZONTAL;
                            }
                            reussi = plateau.poser(x, y, (int) direction, Bateau.CONTRE_TORPILLEUR);
                        } while (!reussi && nbTentative++ < tentativeMax);
                    }
                if(reussi)
                    for (int i = 0; i < nbTorpilleur; i++) {
                        int nbTentative = 0;
                        do {
                            direction = Math.random();
                            if (direction < 0.5) {
                                //Vertical
                                x = (int) (Math.random() * sizeX);
                                y = (int) (Math.random() * (sizeY - 2));
                                direction = Bateau.VERTICAL;
                            } else {
                                //Horizontal
                                x = (int) (1 + Math.random() * (sizeX - 2));
                                y = (int) (Math.random() * (sizeY - 2));
                                direction = Bateau.HORIZONTAL;
                            }
                            reussi = plateau.poser(x, y, (int) direction, Bateau.TORPILLEUR);
                        } while (!reussi && nbTentative++ < tentativeMax);
                    }
            }while(!reussi);
        }
    }
}