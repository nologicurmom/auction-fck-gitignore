package com.example.datasyncv1.connex;

import com.example.datasyncv1.dao.*;
import com.example.datasyncv1.interfaces.NotificationRepository;
import com.example.datasyncv1.models.CategorieProduit;
import com.example.datasyncv1.models.Notification;
import com.example.datasyncv1.models.ProduitCategorie;
import com.example.datasyncv1.models.Utilisateur;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Connection;
import java.util.List;

public class Main {

    public static void main(String[] args) throws Exception{

      /*  ProduitDao pd = new ProduitDao();
        Connection con = objectBdd.ManipDb.pgConnect("postgres","cloudfinal","hardi");
        List<ProduitCategorie> list = pd.getListProduct(con);
        for (ProduitCategorie p : list){
            System.out.println(p.getTypeCategorie());
        }*/
        Connection con = objectBdd.ManipDb.pgConnect("postgres","cloudfinal","hardi");
        Connexion con1= new Connexion();
        ProduitDao p = new ProduitDao();
        p.AjouterPhotoProduit(con1,1,"ok");
    }
}
