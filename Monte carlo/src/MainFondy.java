import OSPRNG.NormalRNG;
import OSPRNG.TriangularRNG;
import OSPRNG.UniformContinuousRNG;

public class MainFondy {

    static NormalRNG normal = new NormalRNG(1.0, 3.0);
    static TriangularRNG triangular = new TriangularRNG(-9.25, 3.37, 5.98);

    static double POCETPOKUSOV = 10000;

    static double POCETDNI = 10680;
    static double POCETMESIACOV = 30*12 ;

    static double MESACNY_VLKAD = 100;

    static double MESACNYPOPLATOK_A = 5.75;
    static double MESACNYPOPLATOK_B = 8.75;


    public static void main(String args[])
    {
        double vysledokFonduA = 0;
        double vysledokFonduB = 0;
        double fondAPodiel = 0;
        double fondBPodiel = 0;

        double hodnotaA_replikacie = 0;
        double hodnotaB_replikacie = 0;

        for (int x = 0; x < POCETPOKUSOV; x++) {

            double hodnotaA = MESACNY_VLKAD;
            double hodnotaB = MESACNY_VLKAD;

            for (int i = 0; i < POCETDNI; i++)
            {
                if (i % 29 == 0)
                {
                    hodnotaA += MESACNY_VLKAD;
                    hodnotaA -= MESACNYPOPLATOK_A;
                }

                fondAPodiel = triangular.sample();
                hodnotaA = hodnotaA * (1 + (fondAPodiel / MESACNY_VLKAD));
            }

            for (int i = 0; i < POCETMESIACOV; i++)
            {
                fondBPodiel = normal.sample();
                hodnotaB = hodnotaB * (1 + (fondBPodiel / MESACNY_VLKAD));

                hodnotaB += MESACNY_VLKAD;
                hodnotaB -= MESACNYPOPLATOK_B;
            }
            hodnotaA_replikacie += hodnotaA;
            hodnotaB_replikacie += hodnotaB;

            //System.out.println("Dostal som sa ku koncu:" + x);
        }

        System.out.println("Fond A priemer: " + hodnotaA_replikacie/POCETPOKUSOV);
        System.out.println("Fond B priemer: " + hodnotaB_replikacie/POCETPOKUSOV);

    }
}
