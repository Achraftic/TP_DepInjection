package net.achraf.metier;

import net.achraf.dao.Idao;

public class MetierImpl implements IMetier {
    private Idao dao; //couplage faible

    public MetierImpl(Idao dao) {
        this.dao = dao;
    }

    public MetierImpl() {
    }

    @Override
    public double calcul() {
        double t = dao.getData();
        return t*12*Math.PI/2*Math.cos(t);
    }

    public void setDao(Idao dao) {
        this.dao = dao;
    }
}
