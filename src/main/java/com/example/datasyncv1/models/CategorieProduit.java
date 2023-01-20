package com.example.datasyncv1.models;

import com.example.datasyncv1.connex.Connexion;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;


public class CategorieProduit extends objectBdd.Mere {

    public Integer IdCategorieProduit;
    public String typeCategorie;

    public CategorieProduit(Integer idCategorieProduit, String typeCategorie) {
        IdCategorieProduit = idCategorieProduit;
        this.typeCategorie = typeCategorie;
    }

    public CategorieProduit() {
    }

    public CategorieProduit(String typeCategorie) {
        this.typeCategorie = typeCategorie;
    }

    public Integer getIdCategorieProduit() {
        return IdCategorieProduit;
    }

    public void setIdCategorieProduit(Integer idCategorieProduit) {
        IdCategorieProduit = idCategorieProduit;
    }

    public String getTypeCategorie() {
        return typeCategorie;
    }

    public void setTypeCategorie(String typeCategorie) {
        this.typeCategorie = typeCategorie;
    }

    public void NewCategorie(Connexion con) throws Exception
    {
        String requete="insert into categorieproduit(typecategorie) values ('"+this.getTypeCategorie()+"')";
        con = new Connexion(requete);
        con.getResultset();
    }
    public List<CategorieProduit> getListCategorie(Connection con) throws Exception {
        List<CategorieProduit> liste = new ArrayList<>();
        CategorieProduit e = new CategorieProduit();
        Object[] result = e.findAll(con,"");
        for(Object o:result)
        {
            liste.add((CategorieProduit) o);
        }
        if(liste.size() != 0)
        {
            return liste;
        }
        else {
            return null;
        }
    }

}
