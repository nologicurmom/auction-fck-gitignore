package com.example.datasyncv1.controllerAPI;


import com.example.datasyncv1.connex.Connexion;
import com.example.datasyncv1.dao.TokenUserDao;
import com.example.datasyncv1.dao.UtilisateurDao;
import com.example.datasyncv1.models.Response;
import com.example.datasyncv1.models.TokenUser;
import com.example.datasyncv1.models.Utilisateur;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class UtilisateurRestController {

    UtilisateurDao ud = new UtilisateurDao();
    Connection con;
    {
        try {
            con = objectBdd.ManipDb.pgConnect("postgres","postgres","1618");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    Connexion con1 = new Connexion();

    @PostMapping("/traitementInscription")
    public Response traitementInscription(@RequestParam("nom") String nom, @RequestParam("prenom") String prenom, @RequestParam("email") String email, @RequestParam("mdp") String mdp)
    {
        Response response = new Response();
        try{

            ud.Inscription(con1,nom,prenom,email,mdp);

            response.setStatus("200");
            response.setMessage("Iscription reussie");
        }
        catch(Exception e)
        {
            response.setStatus("400");
            response.setMessage("Inscripiton impossible");
        }
        return response;
    }

    @PostMapping("/login")
    public Response login(@RequestParam("email")String email,@RequestParam("mdp")String mdp) throws Exception {
        String token = null;
        Response response = new Response();
        TokenUserDao t = new TokenUserDao();
        Utilisateur u = ud.login(email,mdp);
        if(u!=null)
        {
            token = t.insertTokenUser(u);
            response.setStatus("200");
            response.setMessage("login reussi");
            response.setDatas(token);
        }else{
            response.setStatus("401");
            response.setMessage("Mot de passe ou email incorrect");
        }
        return response;
    }

    @PostMapping("/rechargementCompte/{id}")
    public Response rechargementCompte(@RequestParam("montant") float montant,@PathVariable int id)
    {
        Response response = new Response();
        TokenUserDao tud = new TokenUserDao();
        TokenUser tu = null;
        try {
            //        if(tud.validTokenUser(token)!=0)
            //      {
            //        tu = tud.getTokenUser(token);
            ud.rechargerCompte(id,montant,con1);

            response.setMessage("transaction effectuee");
            //  }
            // else
            // {
            //    response.setMessage("expiration token")
            // }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return response;
    }



}

