# Rapport de TP : Injection de Dépendances et Inversion de Contrôle

## Introduction

Dans le développement logiciel moderne, il est crucial de concevoir des applications modulaires, extensibles et faciles à maintenir. Deux concepts majeurs pour atteindre cet objectif sont l'**Injection de Dépendances (DI)** et l'**Inversion de Contrôle (IoC)**. Ces concepts permettent de réduire le couplage entre les composants d'une application et facilitent la gestion des dépendances.

L'objectif de ce TP est de mettre en œuvre l'injection de dépendances sous différentes formes : **statique**, **dynamique**, et via le framework **Spring**. Ce tp illustre également comment l'IoC et le DI peuvent être appliqués dans un contexte Java pour créer une architecture flexible et testable.

## Objectifs du TP

1. **Créer les interfaces et implémentations nécessaires :**
    - `IDao` : Interface représentant l'accès aux données avec une méthode `getData()`.
    - `IMetier` : Interface représentant le calcul métier avec une méthode `calcul()`.

2. **Mettre en œuvre l'injection de dépendances sous différentes formes :**
    - Injection par **instanciation statique**.
    - Injection par **instanciation dynamique** (utilisation de la réflexion).
    - Injection via **Spring** :
        - **Version XML**.
        - **Version Annotations**.

---

## Architecture du TP

L'architecture du TP est structurée de la manière suivante :

```
src/
├── net.achraf.dao
│   ├── Idao.java
│   ├── DaoImpl.java
├── net.achraf.metier
│   ├── IMetier.java
│   └── MetierImpl.java
├── net.achraf.ext
│  └── DaoImplV2.java
└── net.achraf.presentation
    ├── Pres1.java
    ├── Pres2.java
    ├── PresSpringAnnotation.java
    ├── PresSpringXML.java
    └── config.xml
```

### 1. Interface `IDao`

```java
public interface Idao {
    double getData();
}
```

Cette interface représente la couche d'accès aux données. Elle expose une méthode `getData()` qui retourne une valeur numérique.

### 2. Implémentation `DaoImpl`

```java
@Repository("d")
public class DaoImpl implements Idao {
    @Override
    public double getData() {
        System.out.println("Version base de données");
        return 30;
    }
}
```

L'implémentation de `IDao`, ici `DaoImpl`, retourne une donnée fixe, `30`, pour simuler un accès aux données.

### 3. Implémentation alternative `DaoImplV2`

```java
@Repository("d2")
public class DaoImplV2 implements Idao {
    @Override
    public double getData() {
        System.out.println("Version capteur");
        return 999;
    }
}
```

Une deuxième implémentation de `IDao`, `DaoImplV2`, retourne une donnée différente pour simuler une autre source de données.

### 4. Interface `IMetier`

```java
public interface IMetier {
    double calcul();
}
```

Cette interface représente la couche métier de l'application, avec une méthode `calcul()` qui effectue un traitement métier.

### 5. Implémentation `MetierImpl`

```java
@Service("metier")
public class MetierImpl implements IMetier {

    @Autowired
    @Qualifier("d")
    private Idao dao;

    public MetierImpl(Idao dao) {
        this.dao = dao;
    }

    @Override
    public double calcul() {
        double t = dao.getData();
        return t * 12 * Math.PI / 2 * Math.cos(t);
    }

    public void setDao(Idao dao) {
        this.dao = dao;
    }
}
```

`MetierImpl` implémente l'interface `IMetier` et utilise un objet `IDao` pour effectuer un calcul. La méthode `calcul()` multiplie la donnée récupérée par une formule mathématique.

---

## Injection des Dépendances

### a) **Injection par Instanciation Statique**

Dans cette méthode, les objets sont instanciés directement dans le code, ce qui entraîne un couplage fort entre les classes.

```java
public class Pres1 {
    public static void main(String[] args) {
        DaoImpl dao = new DaoImpl();
        MetierImpl metier = new MetierImpl(dao);
        System.out.println("Résultat: " + metier.calcul());
    }
}
```

### b) **Injection par Instanciation Dynamique (Réflexion)**

L'injection dynamique permet de créer des objets à la volée en utilisant la réflexion Java, ce qui réduit le couplage.

```java
public class Pres2 {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(new File("config.txt"));
        String daoClassName = sc.nextLine();
        Class cDao = Class.forName(daoClassName);
        Idao dao = (Idao) cDao.newInstance();
        System.out.println(dao.getData());

        String metierClassName = sc.nextLine();
        Class cMetier = Class.forName(metierClassName);
        IMetier metier = (IMetier) cMetier.getConstructor(Idao.class).newInstance(dao);
        System.out.println("Résultat: " + metier.calcul());
    }
}
```

### c) **Injection via Spring — Version XML**

Avec Spring, on peut configurer l'injection de dépendances de manière déclarative à l'aide d'un fichier XML.

**config.xml**

```xml
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
                           https://www.springframework.org/schema/beans/spring-beans.xsd">

    <bean id="d" class="net.achraf.dao.DaoImpl"></bean>
    <bean id="metier" class="net.achraf.metier.MetierImpl">
        <property name="dao" ref="d"></property>
        <constructor-arg ref="d"></constructor-arg>
    </bean>
</beans>
```

Ensuite, on peut récupérer les beans et injecter les dépendances comme suit :

```java
public class PresSpringXML {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("config.xml");
        IMetier metier = (IMetier) context.getBean("metier");
        System.out.println(metier.calcul());
    }
}
```

### d) **Injection via Spring — Version Annotations**

Spring permet d'utiliser des annotations pour simplifier la configuration.

**MetierImpl.java**

```java
@Component("metier")
public class MetierImpl implements IMetier {

    @Autowired
    @Qualifier("d")
    private Idao dao;

    @Override
    public double calcul() {
        double t = dao.getData();
        return t * 12 * Math.PI / 2 * Math.cos(t);
    }
}
```

**Configuration et Exécution avec annotations :**

```java
public class PresSpringAnnotation {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext("net.achraf");
        IMetier metier = (IMetier) context.getBean("metier");
        System.out.println(metier.calcul());
    }
}
```

---

## Conclusion

### Comparaison des Méthodes d'Injection

| Méthode                  | Couplage      | Avantages                       | Inconvénients                |
|---------------------------|----------------|----------------------------------|-------------------------------|
| **Instanciation statique**    | Fort           | Simple à coder                   | Maintenance difficile         |
| **Instanciation dynamique**   | Moyen-faible   | Flexible (nom des classes externe) | Code complexe                 |
| **Spring XML**                | Faible         | Configuration externe claire     | Besoin d’un fichier XML       |
| **Spring Annotations**        | Très faible    | Léger et moderne                  | Dépendant de Spring           |

### Conclusion générale

L'injection de dépendances, qu'elle soit statique ou dynamique, ainsi que l'utilisation du framework Spring, apportent une grande flexibilité et une meilleure gestion des dépendances au sein d'une application. La version statique crée un fort couplage et rend la maintenance difficile, tandis que la version dynamique et les solutions Spring permettent un meilleur découplage et une plus grande modularité, facilitant ainsi la maintenance et l'évolution de l'application.

L'injection de dépendances par Spring, tant en XML qu'en annotations, simplifie l'implémentation et la gestion des dépendances, et s'avère être une solution puissante pour créer des applications évolutives.
