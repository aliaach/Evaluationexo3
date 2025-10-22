package ma.projet.test;

import ma.projet.beans.Femme;
import ma.projet.beans.Homme;
import ma.projet.beans.Mariage;
import ma.projet.service.FemmeService;
import ma.projet.service.HommeService;
import ma.projet.service.MariageService;
import ma.projet.util.HibernateUtil;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.time.LocalDate;
import java.util.Arrays;

public class Test {
    public static void main(String[] args) {
        try (AnnotationConfigApplicationContext ctx =
                     new AnnotationConfigApplicationContext(HibernateUtil.class)) {

            FemmeService femmeService = ctx.getBean(FemmeService.class);
            HommeService hommeService = ctx.getBean(HommeService.class);
            MariageService mariageService = ctx.getBean(MariageService.class);

            Femme f1 = new Femme(); f1.setCin("FX10"); f1.setNom("Lamia");  f1.setPrenom("Soufi");   f1.setDateNaissance(LocalDate.of(1962,2,14));
            Femme f2 = new Femme(); f2.setCin("FX11"); f2.setNom("Nadia");  f2.setPrenom("Mansour"); f2.setDateNaissance(LocalDate.of(1973,5,9));
            Femme f3 = new Femme(); f3.setCin("FX12"); f3.setNom("Ikram");  f3.setPrenom("El Idrissi"); f3.setDateNaissance(LocalDate.of(1981,9,1));
            Femme f4 = new Femme(); f4.setCin("FX13"); f4.setNom("Bouchra");f4.setPrenom("Saber");   f4.setDateNaissance(LocalDate.of(1969,11,22));
            Femme f5 = new Femme(); f5.setCin("FX14"); f5.setNom("Khadija");f5.setPrenom("Zouhair");f5.setDateNaissance(LocalDate.of(1976,1,30));
            Arrays.asList(f1,f2,f3,f4,f5).forEach(femmeService::create);

            Homme h1 = new Homme(); h1.setNom("Youssef"); h1.setPrenom("Rami");
            Homme h2 = new Homme(); h2.setNom("Said");    h2.setPrenom("Tazi");
            Homme h3 = new Homme(); h3.setNom("Hakim");   h3.setPrenom("Bennani");
            Arrays.asList(h1,h2,h3).forEach(hommeService::create);

            Mariage m1 = new Mariage(); m1.setHomme(h1); m1.setFemme(f1); m1.setDateDebut(LocalDate.of(1985,4,10)); m1.setNbrEnfant(3);
            Mariage m2 = new Mariage(); m2.setHomme(h1); m2.setFemme(f2); m2.setDateDebut(LocalDate.of(1994,3,8));  m2.setNbrEnfant(1);
            Mariage m3 = new Mariage(); m3.setHomme(h1); m3.setFemme(f3); m3.setDateDebut(LocalDate.of(1999,7,21)); m3.setNbrEnfant(2);
            Mariage m4 = new Mariage(); m4.setHomme(h2); m4.setFemme(f3); m4.setDateDebut(LocalDate.of(2002,10,4)); m4.setNbrEnfant(0);
            Mariage m5 = new Mariage(); m5.setHomme(h2); m5.setFemme(f4); m5.setDateDebut(LocalDate.of(2007,6,16)); m5.setNbrEnfant(2);
            Mariage m6 = new Mariage(); m6.setHomme(h3); m6.setFemme(f1); m6.setDateDebut(LocalDate.of(2011,9,30)); m6.setNbrEnfant(1);
            Mariage m7 = new Mariage(); m7.setHomme(h3); m7.setFemme(f5); m7.setDateDebut(LocalDate.of(2014,12,25));m7.setNbrEnfant(0);
            Arrays.asList(m1,m2,m3,m4,m5,m6,m7).forEach(mariageService::create);

            System.out.println("\nFemmes enregistrées:");
            femmeService.findAll().forEach(f ->
                    System.out.printf("%s | %s %s | %s%n", f.getCin(), f.getNom(), f.getPrenom(), f.getDateNaissance()));

            var agee = femmeService.findAll().stream()
                    .min((a,b)->a.getDateNaissance().compareTo(b.getDateNaissance())).orElse(null);
            System.out.println("\nFemme la plus âgée: " +
                    (agee!=null ? agee.getCin()+" "+agee.getNom()+" "+agee.getPrenom() : "N/A"));

            System.out.println("\nÉpouses de " + h1.getNom() + ":");
            hommeService.afficherEpousesEntreDates(h1.getId(), LocalDate.of(1980,1,1), LocalDate.of(2030,1,1));

            System.out.println("\nFemmes mariées 2 fois ou plus:");
            femmeService.femmesMarieesAuMoinsDeuxFois().forEach(f ->
                    System.out.printf("%s | %s %s%n", f.getCin(), f.getNom(), f.getPrenom()));
        }
    }
}
