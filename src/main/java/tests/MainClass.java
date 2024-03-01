package tests;
import java.util.ArrayList;
import java.util.List;

import entities.EspacePartenaire;
import entities.Categorie;
import services.EspaceServices;
import services.CategorieServices;

public class MainClass {

    public static void main(String[] args) {

        List<String> photos = new ArrayList<>();
        photos.add("url1");
        photos.add("url2");

        EspacePartenaire ep = new EspacePartenaire("test", "test", "ttt", "test", photos);
        Categorie ca = new Categorie(false,true,false,true);

        EspaceServices es = new EspaceServices();
        CategorieServices cs = new CategorieServices();
        //cs.addEntity(ca);
        //es.addEntity(ep);
        EspacePartenaire ep1 = new EspacePartenaire(7,"aziz", "test", "ttt", "test", photos);
        Categorie ca1 = new Categorie(7,true,true,false,true);



        //cs.deleteEntity(ca1);
        //es.deleteEntity(ep1);

        es.updateEntity(ep1);
        cs.updateEntity(ca1);
        System.out.println(es.getAllData());
        System.out.println(cs.getAllData());
    }
}
