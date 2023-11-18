package packs;

import java.util.List;
import java.util.Random;

public class Selection {
    /**
     * Performs tournament selection and returns fittest string based on fitness value
     * @param -tournamentsize
     * @param -key_arr
     * @param -text
     * @return String
     */
    public static String tournamentslection(int tournamentsize, List<String> key_arr, String text)
    {
        double bestfit=Double.MAX_VALUE;
        String fitestkey="";
        for (int i = 0; i < tournamentsize; i++) {
            Random random=new Random();
            int indxofkey=random.nextInt(key_arr.size()-1);
            String pk=key_arr.get(indxofkey);
            Evaluation e=new Evaluation();
            double fitness= e.fitness(pk,text);
            //key with least fitness value is considered as fittest
            if(fitness<bestfit)
            {
                bestfit=fitness;
                fitestkey=pk;
            }
        }
        return fitestkey;
    }
}
