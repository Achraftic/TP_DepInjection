package net.achraf.presentation;

import net.achraf.dao.DaoImpl;
import net.achraf.metier.MetierImpl;

public class Pres1 {

    public static void main(String[] args) {
        DaoImpl dao = new DaoImpl();
        MetierImpl metier = new MetierImpl(dao);

        System.out.println("RES= "+metier.calcul());
    }
}
