package net.achraf.dao;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository("d")
public class DaoImpl implements Idao {
    @Override
    public double getData() {
        System.out.println("version base de donnes");
        double t=30;
        return t;
    }
}
