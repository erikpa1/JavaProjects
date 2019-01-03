
import OSPRNG.TriangularRNG;
import OSPRNG.UniformContinuousRNG;

import java.util.ArrayList;

public class MainSpecialitky {

    static UniformContinuousRNG uniform = new UniformContinuousRNG(0.4, 0.7);
    static TriangularRNG triangular = new TriangularRNG(70.0, 90.0, 110.0);
    static double POCETPOKUSOV = 10000000;

    public static void main(String[] args) {

        ArrayList<Double> vysledky = new ArrayList<>();

        double objednaneMnozstvo = 80;
        double nakupnaCena = 0;
        double dopyt = 0;
        double predanemnozstvo = 0;
        double predajnaCena = 0.89;
        double zostatokNaKonciDna = 0;
        double vykupnaCena = 0;

        double hospodarskyvysledok = 0;
        double celkVysledok = 0;
        double celkVysledokNaDruhu = 0;

        for (int i = 0; i < POCETPOKUSOV; i++)
        {
            nakupnaCena = uniform.sample();
            dopyt = triangular.sample();
            predanemnozstvo = Math.min(objednaneMnozstvo, dopyt);
            zostatokNaKonciDna = predanemnozstvo - dopyt;
            vykupnaCena = nakupnaCena / 2;

            hospodarskyvysledok = (predanemnozstvo * predajnaCena) + (zostatokNaKonciDna * vykupnaCena) - (objednaneMnozstvo * nakupnaCena);
            celkVysledok +=hospodarskyvysledok;
            celkVysledokNaDruhu += Math.pow(hospodarskyvysledok, 2);
            //vysledky.add(hospodarskyvysledok);
        }

        double celkVysledok_mean = (celkVysledok / POCETPOKUSOV);
        System.out.println("Priemerny hospodarsky vysledok: " + celkVysledok_mean);

        double smerodajnaOdchylka_left = (1/(POCETPOKUSOV-1)*celkVysledokNaDruhu);
        double smerodajnaOdchylka_right = Math.pow((1/(POCETPOKUSOV-1)*celkVysledok),2);
        double smerodajnaOdchylka = Math.sqrt(smerodajnaOdchylka_left - smerodajnaOdchylka_right);

        System.out.println("Priemerny hospodarsky vysledok: " + smerodajnaOdchylka);

        double polovicnaSirkaSpolahlivosti = (smerodajnaOdchylka * 1.96)/Math.sqrt(POCETPOKUSOV);
        System.out.println("Intervali spolahlivosti: <" + (celkVysledok_mean - polovicnaSirkaSpolahlivosti) + ";" + (celkVysledok_mean + polovicnaSirkaSpolahlivosti) + ">");


    }
}
