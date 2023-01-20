package com.example.datasyncv1.controllerAPI;

import com.example.datasyncv1.connex.Connexion;
import com.example.datasyncv1.dao.EnchereDao;
import com.example.datasyncv1.dao.HistoriqueEnchereDao;
import com.example.datasyncv1.dao.TokenUserDao;
import com.example.datasyncv1.models.Enchere;
import com.example.datasyncv1.models.TokenUser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/historique")
public class HistoriqueRestController {
    Connexion con = new Connexion();
    @GetMapping("HistoriqueEncherisseur")
    public ResponseEntity<List<Object[]>> HistoriqueEncherisseur(@RequestHeader("token") String token)
    {
        TokenUserDao tud = new TokenUserDao();
        TokenUser tu = null;
         try {
             if(tud.validTokenUser(token)!=0)
             {
                 return new ResponseEntity<List<Object[]>>(new HistoriqueEnchereDao().HistoriqueEncherisseur(con,tu.getIdUtilisateur()), HttpStatus.OK);
             }
             else {
                 return new ResponseEntity<>(HttpStatus.NOT_FOUND);
             }

         }
         catch(Exception e)
         {
             return new ResponseEntity<>(HttpStatus.NOT_FOUND);
         }
    }

    @GetMapping("HistoriqueVente")
    public ResponseEntity<List<Object[]>> HistoriqueVente(@RequestHeader("token") String token)
    {
        TokenUserDao tud = new TokenUserDao();
        TokenUser tu = null;
        try {
            if(tud.validTokenUser(token)!=0)
            {
                return new ResponseEntity<List<Object[]>>(new HistoriqueEnchereDao().HistoriqueVente(con,tu.getIdUtilisateur()), HttpStatus.OK);
            }
            else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
        catch(Exception e)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
