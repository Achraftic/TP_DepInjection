package net.achraf.presentation;
import net.achraf.dao.Idao;
import net.achraf.metier.IMetier;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.util.Scanner;

public class Pres2 {
    public static void main(String[] args) throws FileNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Scanner sc = new Scanner(new File("config.txt"));
        String doaClassName = sc.nextLine();
        Class cDao = Class.forName(doaClassName);
        Idao dao = (Idao) cDao.newInstance();
        System.out.println(dao.getData());

        String metierClassName = sc.nextLine();
        Class cMetier = Class.forName(metierClassName);
        IMetier metier = (IMetier) cMetier.getConstructor(Idao.class).newInstance(dao);
        System.out.println("RES: " + metier.calcul());
    }
}

