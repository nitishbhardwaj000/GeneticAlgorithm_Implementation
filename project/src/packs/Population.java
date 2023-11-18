package packs;

import java.util.ArrayList;
import java.util.Random;

public class Population {
    /**
     * Generates some random  strings whcih act as initil pop
     * @param -popsize
     * @param -keysize
     * @return -list
     */
    public static ArrayList<String> generateinitialpop(int popsize, String keysize)
    {
        //key_arr has all random keys of popsize in arryalist
        ArrayList<String> key_arr = new ArrayList<>();
        int lower = 0;
        final int upper = Integer.parseInt(keysize); //keysize (26/40) is string convert to Intege obj
        String characters = "abcdefghijklmnopqrstuvwxyz";
        for (int i = 0; i < popsize; i++) {
            StringBuilder sb = new StringBuilder();
            int randomnokeysize = (int) (Math.random() * ((upper - lower) + 1) + lower);//randomno bw 0to 26 /40keysize
            int lengthofrandomkeysize = randomnokeysize;

            String random_key = generaterandomkey(lengthofrandomkeysize, characters);
            //randon_key has randomkey generartd
            //putting extra dashes so that strings length is ==
            for (int j = random_key.length(); j <= 2 * Integer.parseInt(keysize); j++) {
                random_key += "-";
            }
            System.out.println(random_key);
            key_arr.add(random_key);
        }
        return key_arr;
    }
    /**
     * This fn generates random key
     * @param -len
     * @param -characters
     * @return String
     */
    public static String generaterandomkey(int len, String characters) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < len; i++) {
            Random random = new Random();
            int idx = random.nextInt(characters.length());
            char random_char_gen = characters.charAt(idx);
            sb.append(random_char_gen);
            // dash after character but not last
            if (i < len - 1) {
                sb.append("-");
            }
        }
        return sb.toString();
    }
}
