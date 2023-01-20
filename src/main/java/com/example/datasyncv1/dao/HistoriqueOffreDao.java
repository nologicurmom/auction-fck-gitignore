package com.example.datasyncv1.dao;

import com.example.datasyncv1.connex.Connexion;
import com.example.datasyncv1.models.HistoriqueOffre;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HistoriqueOffreDao {

    UtilisateurDao ud = new UtilisateurDao();

    public void Encherir(Connexion con,int idenchere,int idutilisateur,float montant)
    {
        try{
            String requete="INSERT INTO HistoriqueOffre(idEnchere,idUtilisateur,montant_offre) values ("+idenchere+","+idutilisateur+","+montant+")";
            con = new Connexion(requete);
            System.out.println(requete);
            con.getCommit();
        }
        catch(Exception e)
        {
            try {
                con.getRollBack();
                System.out.println("Transaction échouée : annulation");
            } catch (Exception exc) {}
        }
        finally {}
    }

    public void setCompteUser(int iduser,float montant,Connexion con) throws  Exception
    {
        float soldeUser = ud.getCompteUser(iduser,con)-montant;
        try {
            String requete = "update utilisateur set compte="+soldeUser+" where idutilisateur="+iduser+"";
            con = new Connexion(requete);
            con.getCommit();
        } catch (Exception exc) {
            try {
                con.getRollBack();
                System.out.println("Transaction échouée : annulation");
            } catch (SQLException ex) {}
        }
        finally {}
    }


    public List<HistoriqueOffre> ListeOffre(Connection con, int idenchere) throws Exception {
        List<HistoriqueOffre> liste = new ArrayList<>();
        HistoriqueOffre e = new HistoriqueOffre();
        Object[] result = e.findAll(con,"idenchere="+idenchere+"");
        for(Object o:result)
        {
            liste.add((HistoriqueOffre) o);
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
