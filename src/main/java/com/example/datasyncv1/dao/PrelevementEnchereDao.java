package com.example.datasyncv1.dao;

import com.example.datasyncv1.connex.Connexion;
import com.example.datasyncv1.models.PrelevementEnchere;

import java.sql.Connection;

public class PrelevementEnchereDao {

    PrelevementEnchere p;
    public void Inserer(Connexion con,int idenchere,float montant) throws Exception
    {
        String requete="INSERT INTO prelevementenchere(idenchere,montant) values ("+idenchere+","+montant+")";
        con = new Connexion(requete);
        System.out.println(requete);
    }

    public float ChiffreAffaire(Connexion con)
    {
        float montant = 0.0f;
        try {
            String requete="select sum(montant) as ChiffreAffaire from PrelevementEnchere";
            con = new Connexion(requete,"");
            con.getResultset().next();
            montant = con.getResultset().getFloat(1);
        }
        catch(Exception e)
        {
            montant = 0.0f;
        }
        return montant;
    }
}
