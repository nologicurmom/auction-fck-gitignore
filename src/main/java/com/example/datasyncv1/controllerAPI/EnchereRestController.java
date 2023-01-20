package com.example.datasyncv1.controllerAPI;

import com.example.datasyncv1.connex.Connexion;
import com.example.datasyncv1.dao.*;
import com.example.datasyncv1.models.Enchere;
import com.example.datasyncv1.models.Response;
import com.example.datasyncv1.models.TokenUser;
import com.example.datasyncv1.models.Utilisateur;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Connection;
import java.util.List;

@RestController
@RequestMapping("/api/enchere")
@CrossOrigin
public class EnchereRestController {

    EnchereDao ed = new EnchereDao();

    ProduitDao p = new ProduitDao();
    HistoriqueOffreDao hod = new HistoriqueOffreDao();

    PrelevementEnchereDao ped = new PrelevementEnchereDao();
  Connexion con1 = new Connexion();
    Connection con;
    {
        try {
            con = objectBdd.ManipDb.pgConnect("postgres","railway","2HxL1J7BJORnaDsau1wQ");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("listeEnchere")
    public ResponseEntity<List<Enchere>> getListeEnchere(){
        try{
            List<Enchere> list = ed.getListEnchere(con);
            for(Enchere e : list)
            {
                   ed.EnchereTerminer(con1,e.getIdEnchere());
            }
            return new ResponseEntity<List<Enchere>>(list,HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("ficheEnchere/{idEnchere}")
    public ResponseEntity<List<Enchere>> getFicheEnchere(@PathVariable int idEnchere){
        try{
            return new ResponseEntity<List<Enchere>>(new EnchereDao().getFicheEnchere(con,idEnchere), HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @GetMapping("ListeEnchereUser")
    public ResponseEntity<List<Enchere>> ListeEnchereUser(@RequestHeader("token") String token){
        TokenUserDao tud = new TokenUserDao();
        TokenUser tu = null;
        try{
            if(tud.validTokenUser(token)!=0)
            {
                tu = tud.getTokenUser(token);
                return new ResponseEntity<List<Enchere>>(new EnchereDao().getListeEnchereUser(con,tu.getIdUtilisateur()), HttpStatus.OK);
            }
            else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("AjoutEnchere/{id}")
    public Response AjoutEnchere(@PathVariable int id,@RequestParam("description") String description,@RequestParam("prixminimumvente") float prixminimumvente,@RequestParam("durreenchere") int durreenchere) throws Exception {
        Response response = new Response();
        TokenUserDao tud = new TokenUserDao();
        TokenUser tu = new TokenUser();
     //   if(tud.validTokenUser(token)!=0)
       // {
         //   tu = tud.getTokenUser(token);
          float montant_user = new UtilisateurDao().getCompteUser(id,con1);
          if(montant_user<prixminimumvente)
          {
              response.setMessage("votre solde est insuffisante");
          }
          else{
              int result = ed.AjouterEncher(con1,id,description,prixminimumvente,durreenchere);
              //compte user
              hod.setCompteUser(id,prixminimumvente,con1);
              //commission
              ped.Inserer(con1,result,ed.MontantPrelevee(result));
              response.setMessage("votre vente a été bien prise en compte");
          }
       // }
       // else{
       //     response.setMessage("veuillez dabord vous authentifier");
       // }
        return response;
    }


    @PostMapping("pic/{idproduit}")
    public Response AjoutPhotoEnchere(@PathVariable int idproduit,@RequestParam("photo") String photo) throws Exception {
        Response response = new Response();
        TokenUserDao tud = new TokenUserDao();
        TokenUser tu = new TokenUser();
        //   if(tud.validTokenUser(token)!=0)
        // {
        //   tu = tud.getTokenUser(token);
        p.AjouterPhotoProduit(con1,idproduit,photo);
        // }
        // else{
        //     response.setMessage("token expiré");
        // }
        response.setMessage("mety");
        return response;
    }
}
