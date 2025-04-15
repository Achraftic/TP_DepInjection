package net.achraf.ext;

import net.achraf.dao.Idao;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository("d2")
public class DaoImplV2 implements Idao {
    public double getData() {
        System.out.println("version capteur");
        double Capteur=999;
        return Capteur;
    }
}
