package net.achraf.presentation;

import net.achraf.dao.Idao;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Pres2 {
    public static void main(String[] args) throws FileNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        Scanner sc = new Scanner(new File("config.txt"));
        String doaClassName = sc.nextLine();
        Class cDao = Class.forName(doaClassName);
        Idao dao = (Idao) cDao.newInstance();
        System.out.println(dao.getData());

    }
}

