package packs;

import java.util.Random;

public class mutations {
    /**
     *
     * Mutator_fn-FOr performing mutation  on child generated from crossover
     * @param -child
     * returntype-string
     * Could have also implemeted some other ways of mutation like displacement ,inversion but chose reciprocal exchange
     */
    public static String mutator_fn(String child) {
        StringBuilder child1sb=new StringBuilder(child);
        Random rn=new Random();
        int length=child.length();
        int first=rn.nextInt(child.length());
        int second=rn.nextInt(child.length());
        char f_char=child1sb.charAt(first);
        char temp=f_char;
        char s_char=child1sb.charAt(second);
        child1sb.setCharAt(first, s_char);
        child1sb.setCharAt(second, temp);
        String c1str=child1sb.toString();
        return c1str;
    }
}
