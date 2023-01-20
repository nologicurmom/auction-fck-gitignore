package com.example.datasyncv1.controllerVIEW;

import com.example.datasyncv1.connex.Connexion;
import com.example.datasyncv1.dao.StatistiqueDao;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class StatistiqueController {

    Connexion con = new Connexion();
    StatistiqueDao sd = new StatistiqueDao();
    @GetMapping("/Statistique")
    public String Statistique(HttpServletRequest request)
    {
        //chiffre d'affaire par annee , mois
        List<Object[]> graphe = sd.chiffreAffaireAnneeMois(con,2023);

        //nombre total des produits vendus par catégorie
        List<Object[]> NombreTotalProduitVendu = sd.NombreTotalProduitVendu(con);

        //Stat membres
        List<Object[]> StatMembre = sd.StatMembre(con);

        //Stat Enchere
        List<Object[]> StatEnchere = sd.StatEnchere(con);

        request.setAttribute("graphe",graphe);
        request.setAttribute("NombreTotalProduitVendu",NombreTotalProduitVendu);
        request.setAttribute("StatMembre",StatMembre);
        request.setAttribute("StatEnchere",StatEnchere);
        request.setAttribute("annee",2023);
        return "Statistique";
    }

    @GetMapping("/graphe/{annee}")
    public String RechercheStatistique(HttpServletRequest request, @PathVariable int annee)
    {
        List<Object[]> graphe = sd.chiffreAffaireAnneeMois(con,annee);
        request.setAttribute("graphe",graphe);
        request.setAttribute("annee",annee);
        return "Statistique";
    }
}
