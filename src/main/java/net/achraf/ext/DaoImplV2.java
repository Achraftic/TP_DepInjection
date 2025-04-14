package net.achraf.ext;

import net.achraf.dao.Idao;

public class DaoImplV2 implements Idao {
    public double getData() {
        System.out.println("version capteur");
        double Capteur=999;
        return Capteur;
    }
}
