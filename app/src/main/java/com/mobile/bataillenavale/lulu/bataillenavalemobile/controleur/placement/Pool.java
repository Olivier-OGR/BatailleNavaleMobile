package com.mobile.bataillenavale.lulu.bataillenavalemobile.controleur.placement;

import android.app.Activity;
import android.util.SparseArray;
import android.widget.Button;
import android.widget.LinearLayout;

import com.mobile.bataillenavale.lulu.bataillenavalemobile.R;
import com.mobile.bataillenavale.lulu.bataillenavalemobile.vue.placement.BateauVue;


/**
 * Created by Simon on 01/01/2018.
 */

public class Pool {
    private SparseArray<BateauVue> bateaux;
    private LinearLayout pool;
    private Button finish = null;

    public Pool(int nbTorpilleur, int nbContreTorpilleur, int nbCroiseur, int nbPorteAvion, Activity activity,Controleur controleur){
        int nbBateau = 0;
        bateaux = new SparseArray<>();
        pool = (LinearLayout) activity.findViewById(R.id.pool);

        for(int i=0;i<nbTorpilleur;++i) {
            LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
            param.setMargins(5,5,5,5);
            BateauVue bateau =  new BateauVue(BateauVue.TORPILLEUR, nbBateau, activity,controleur);
            bateaux.put(nbBateau,bateau);
            pool.addView(bateau.getComplet(),param);
            nbBateau++;
        }

        for(int i=0;i<nbContreTorpilleur;++i) {
            LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
            param.setMargins(5,5,5,5);
            BateauVue bateau =  new BateauVue(BateauVue.CONTRE_TORPILLEUR, nbBateau, activity,controleur);
            bateaux.put(nbBateau,bateau);
            pool.addView(bateau.getComplet(),param);
            nbBateau++;
        }

        for(int i=0;i<nbCroiseur;++i) {
            LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
            param.setMargins(5,5,5,5);
            BateauVue bateau =  new BateauVue(BateauVue.CROISEUR, nbBateau, activity,controleur);
            bateaux.put(nbBateau,bateau);
            pool.addView(bateau.getComplet(),param);
            nbBateau++;
        }

        for(int i=0;i<nbPorteAvion;++i) {
            LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
            param.setMargins(5,5,5,5);
            BateauVue bateau =  new BateauVue(BateauVue.PORTE_AVION, nbBateau, activity,controleur);
            bateaux.put(nbBateau,bateau);
            pool.addView(bateau.getComplet(),param);
            nbBateau++;
        }

        Button rotate = (Button) activity.findViewById(R.id.rotate);
        rotate.setOnClickListener(v -> rotate());
    }

    public BateauVue getBoat(int key){
        return bateaux.get(key);
    }

    private void rotate(){
        for(int key = 0;key<bateaux.size();key++)
            if(!bateaux.get(key).isOnBoard())
               bateaux.get(key).rotate();
    }

    public void returnPool(int id) {
        if(finish != null)
            pool.removeView(finish);
        BateauVue bateau = bateaux.get(id);
        bateau.setCoord(-1,-1);
        pool.addView(bateau.getComplet());
    }

    public boolean isEmpty() {
        for(int key = 0;key<bateaux.size();key++)
            if(!bateaux.get(key).isOnBoard())
                return false;
        return true;
    }

    public void addFinishButton(Activity activity) {
        Button b = new Button(activity);
        b.setText("start");
        b.setOnClickListener(v -> System.out.println("finish"));
        finish = b;
        pool.addView(finish);
    }
}
