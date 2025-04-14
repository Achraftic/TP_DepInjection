package net.achraf.dao;

public class DaoImpl implements Idao {
    @Override
    public double getData() {
        System.out.println("version base de donnes");
        double t=30;
        return t;
    }
}
