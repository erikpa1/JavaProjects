import OSPRNG.NormalRNG;
import OSPRNG.TriangularRNG;
import OSPRNG.UniformContinuousRNG;

public class MainLetenky {

    static double POCETPOKUSOV = 1000;

    static UniformContinuousRNG uniformcontinuoe = new UniformContinuousRNG(5.0, 14.0);
    static TriangularRNG triangular = new TriangularRNG(1.0, 4.0, 11.0);


    public static void main(String args[])
    {

        double days[] = new double[]{0,0,0,0,0,0};
        double daysobsadenost[] = new double[]{0,0,0,0,0,0,0};
        double celkCenaListka = 0;


        for (int i = 0; i < POCETPOKUSOV; i++)
        {
            double cenaListka = 500;
            double obsadenost = 27;
            double tyzdennaPriemernaCenna = 500;

            days[0] += cenaListka;
            daysobsadenost[0] += obsadenost;

            boolean onceRaised = false;

            for (int j = 0; j < 5; j++)
            {
                if (obsadenost < 75)
                {
                    cenaListka *= (1 - (triangular.sample()/100));
                    obsadenost *= (1 - (uniformcontinuoe.sample()/100));
                } else {

                    if (onceRaised == false)
                    {
                        cenaListka = cenaListka + (cenaListka*0.3);
                        onceRaised = true;
                    }
                    obsadenost *= (1 - (uniformcontinuoe.sample()/100));
                }

                days[j+1] += cenaListka;
                daysobsadenost[j+1] += obsadenost;
                tyzdennaPriemernaCenna += cenaListka;
            }

            celkCenaListka += tyzdennaPriemernaCenna/6;
        }

        for (int i = 0; i < 6; i++)
        {
            days[i] = days[i]/POCETPOKUSOV;
            daysobsadenost[i] = daysobsadenost[i]/POCETPOKUSOV;
        }

        int index = 0;
        int lastfound = 0;
        double min = 9999999999999.0;

        for (double tmp : days)
        {
            if (min > tmp)
            {
                min = tmp;
                lastfound = index;
            }
            index++;
        }

        System.out.println("Najvyhodnejsi den bol: " + lastfound + " s hodnotou " + min);
        System.out.println("Obsadenost bola: " + daysobsadenost[lastfound]);
        System.out.println("Priemerna cena listka bola: " + celkCenaListka/POCETPOKUSOV);

    }
}
