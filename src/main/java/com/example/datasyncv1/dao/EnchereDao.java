package com.example.datasyncv1.dao;

import com.example.datasyncv1.connex.Connexion;
import com.example.datasyncv1.models.Enchere;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EnchereDao {
    Enchere ench;
    public List<Enchere> getListEnchere(Connection con) throws Exception {
        List<Enchere> liste = new ArrayList<>();
        Enchere e = new Enchere();
        Object[] result = e.findAll(con,"");
        for(Object o:result)
        {
            liste.add((Enchere) o);
        }
        if(liste.size() != 0)
        {
            return liste;
        }
        else {
            return null;
        }
    }

    public List<Enchere> getListeEnchereUser(Connection con,int idutilisateur) throws Exception {
        List<Enchere> liste = new ArrayList<>();
        Enchere e = new Enchere();
        Object[] result = e.findAll(con,"idutilisateur="+idutilisateur+"");
        for(Object o:result)
        {
            liste.add((Enchere) o);
        }
        if(liste.size() != 0)
        {
            return liste;
        }
        else {
            return null;
        }
    }


    public List<Enchere> getFicheEnchere(Connection con,int idenchere) throws Exception {
        List<Enchere> liste = new ArrayList<>();
        Enchere e = new Enchere();
        Object[] result = e.findAll(con,"idenchere="+idenchere+"");
        for(Object o:result)
        {
            liste.add((Enchere) o);
        }
        if(liste.size() != 0)
        {
            return liste;
        }
        else {
            return null;
        }
    }

    public void AjouterEnchere(Connexion con,int idutilisateur,String description,float prixminimumvente,int durreenchere) throws Exception
    {
        String requete="INSERT INTO Enchere (idUtilisateur, description, prixMinimumVente,durreEnchere) values ("+idutilisateur+",'"+description+"',"+prixminimumvente+","+durreenchere+")";
        con = new Connexion(requete);
        System.out.println(requete);
    }

    public float getPourcentage() throws SQLException {
        String requete = "select pourcentage from PourcentagePrelevee" ;
        Connexion con = new Connexion(requete,"hh");
        con.getResultset().next();
        float pourcentage = con.getResultset().getFloat(1);
        return pourcentage;
    }

    public float getPrixDeVente(int idenchere) throws SQLException {
        String requete = "select prixMinimumVente from enchere where idenchere="+idenchere+"";
        Connexion con = new Connexion(requete,"hh");
        con.getResultset().next();
        float prixVente = con.getResultset().getFloat(1);
        return prixVente;
    }

    public float MontantPrelevee(int idenchere) throws Exception
    {
        float pourcentage = this.getPourcentage();
        float montantPrelevee = (this.getPrixDeVente(idenchere) * pourcentage)/100;
        return montantPrelevee;
    }

    public int AjouterEncher(Connexion con,int idutilisateur,String description,float prixminimumvente,int durreenchere) throws Exception
    {
        String requete="INSERT INTO Enchere (idUtilisateur, description, prixMinimumVente,durreEnchere) values ("+idutilisateur+",'"+description+"',"+prixminimumvente+","+durreenchere+") returning idenchere";
        con = new Connexion(requete,"");
        con.getResultset().next();
        int result = con.getResultset().getInt(1);
        return result;
    }

    public Enchere getObjetEchere(Connexion con,int idenchere) throws SQLException {
        String requete="select prixminimumvente , dureenchere from Enchere where idenchere="+idenchere+"";
        con = new Connexion(requete,"");
        con.getResultset().next();
        float prixminimumvente = con.getResultset().getFloat(1);
        int dureenchere = con.getResultset().getInt(2);
        Enchere e = new Enchere(prixminimumvente,dureenchere);
        return e;
    }

    public void setStatutEnchere(Connexion con,int idenchere) throws SQLException {
        String requete ="update enchere set status=1 where idenchere="+idenchere+"";
        con = new Connexion(requete);
    }

    public void EnchereTerminer(Connexion con,int idenchere) throws SQLException {
        String requete="SELECT (dateheureenchere + durreenchere * INTERVAL '1 minute') AS dateheurefin FROM enchere where idenchere="+idenchere+" ";
        con = new Connexion(requete,"");
        con.getResultset().next();
        Timestamp dateheurefin = con.getResultset().getTimestamp(1);
        Date currentDate = new Date();
        System.out.println(currentDate);
        if(dateheurefin.before(currentDate))
        {
           setStatutEnchere(con,idenchere);
           System.out.println("tafiditra V"+idenchere);
        }
    }
}
