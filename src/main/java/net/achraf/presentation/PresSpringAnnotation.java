package net.achraf.presentation;

import net.achraf.metier.IMetier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class PresSpringAnnotation {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext("net.achraf");
        IMetier metier = (IMetier) context.getBean("metier");
        System.out.println(metier.calcul());

    }
}
