/**
 * NAME-nitish
 * studnetno-7201791
 *
 */
package packs;
import packs.Evaluation;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
public class Main {

 public static void main(String[] args) {
    Main n=new Main();
    //experimentation paramters used-popsize-2300 ,maxgeneration-50,tournamentsize-3 and user cross and muatation rate varying
    n.gaParameters(2300,50,3, 0.95,0.2);
}
    /**
     * Passing all the parameters so thtAT GA can be tested
     * @param -popsize
     * @param- maxgen
     * @param -tournamentsize
     * @param -user_crossoverrate
     * @param -user_mutationrate
     */
    public static void gaParameters(int popsize,int maxgen,int tournamentsize,double user_crossoverrate,double user_mutationrate) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("Data1.txt"));
            String enctext;
            //this will store averagefitness val of uniformcrossover
            ArrayList<Double>avgfitnesseachgenUNcrossover=new ArrayList<>();
            //this will store averagefitness val of OPcrossover
            ArrayList<Double>avgfitnesseachgenOPcrossover=new ArrayList<>();
            ArrayList<Double>bestfitnesseachgenUNcrossover=new ArrayList<>();
            //this will store bestfitness val of OPcrossover
            ArrayList<Double>bestfitnesseachgenOPcrossover=new ArrayList<>();
            String text = "";//text has entire encrpyted text as a one string
            String keysize = reader.readLine();
            while ((enctext = reader.readLine()) != null) {
                text += enctext;  //text has encruypted string
            }

 //key_arr has initial population randomly generated will get updated with next genrtion after unfiormcross and mutation
            Population q=new Population();
            // keys_arr get updated with next genrtion after uniform crossover and mutation
            ArrayList<String>key_arr=q.generateinitialpop(popsize,keysize);//for unifromcrossover
            ArrayList<String>keys_One_arr=new ArrayList<>();
            //keys_One_arr has same initial population as in key_Arr
            //copying initial pop in keys_one_arr
            // keys_One_arr get updated with next genrtion after onepoint crossover and mutation
            for (int i = 0; i < key_arr.size(); i++) {
                keys_One_arr.add(key_arr.get(i));
            }

            for (int gen= 1; gen <=maxgen ; gen++) {
                System.out.println();
                    System.out.println("GENERATION "+gen);
                System.out.println();
                //I will fist store all the offsprings which genetciAlgorithm() gives in mutatedchilds arraylist
                //and then traverse this mutatedchilds arraylist and set elements in key_arr arraylist
                //and updated key_arr will be new generation
                ArrayList<String>uniformchilds=new ArrayList<>();//store children after unfrmcrossover
                ArrayList<String>onepointchilds=new ArrayList<>();//store children after onepointcrossover
                for (int i = 0; i <popsize/2 ; i++) {

                    geneticAlgorithm(key_arr, keys_One_arr, keysize, popsize, tournamentsize, user_crossoverrate, user_mutationrate, text, uniformchilds, onepointchilds);
                }
                //THESE 2 methods will print output for uniformcroossover followed by onepoint crssover
                // so that both can be comapred based on fitness value
                uniformcrossoutput(gen,key_arr,bestfitnesseachgenUNcrossover,avgfitnesseachgenUNcrossover,keysize,popsize,user_crossoverrate,user_mutationrate,text,uniformchilds);
                onepointoutput(gen,keys_One_arr,bestfitnesseachgenOPcrossover,avgfitnesseachgenOPcrossover,keysize,popsize,user_crossoverrate,user_mutationrate,text,onepointchilds);
            }
            System.out.println("BEST FINTESS OF EACH GEN UN CRSSOVER");
            for(Double val:bestfitnesseachgenUNcrossover)
            {
                System.out.println(val);
            }
            System.out.println("AVERAGE FITNESS OF EACH GEN UN CROSSOVER");
            for (Double val:avgfitnesseachgenUNcrossover)
            {
                System.out.println(val);
            }
            System.out.println("BEST FITNESS OF EACH GEN OP CROSSOVER");
            for(Double val:bestfitnesseachgenOPcrossover)
            {
                System.out.println(val);
            }
            System.out.println();
            System.out.println("AVERAGE FITNESS OF EACH GEN OP CROSSOVER");
            for (Double val:avgfitnesseachgenOPcrossover)
            {
                System.out.println(val);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
     * This method prints the output for uniform crossver
     * @param -gen
     * @param -key_arr
     * @param -keysize
     * @param -popsize
     * @param -user_crossoverrate
     * @param -user_mutationrate
     * @param -text
     * @param -uniformchilds
     */
    public static void uniformcrossoutput(int gen,List<String>key_arr,List<Double>bestfitnesseachgenUNcrossover,List<Double>avgfitnesseachgenUNcrossover,String keysize,int popsize,double user_crossoverrate,double user_mutationrate,String text,List<String>uniformchilds)
    {
        System.out.println("Crossover Method-UNIFORM CROSSOVER METHOD");
        System.out.println("Crossover Rate is "+user_crossoverrate+"and method used is UNIFORM CROSSOVER");
        System.out.println("Mutattion Rate is "+user_mutationrate+"and method used is RECIPROCAL EXCHANGE");
        System.out.println("No Of chromeosoems"+key_arr.size());
        double bestfit=Double.MAX_VALUE;
        double sum=0;
       int elitismcount=2;
        List<String>elite=ElitismChromosomes(key_arr,text, elitismcount);
//        List<String> eliteChromosomes = new ArrayList<>();
        String fittestvals="";//vals will store string that is fittest in generation
        for (int i = 0; i <key_arr.size() ; i++) {
            String val=key_arr.get(i);
            Evaluation e=new Evaluation();
            double fitness= e.fitness(val,text);
            sum+=fitness;
            //key with least fitness value is considered as fittest
            if(fitness<bestfit)
            {
                bestfit=fitness;
                fittestvals=fittestvals+val;
            }
        }

        System.out.println("Best fitness for gen "+gen+"is "+bestfit);
        Double avgFitness=sum/key_arr.size();
        System.out.println("Average fitness for "+gen+"is "+avgFitness);
        //this arraylist has avgfitness of each genration since we are running for 30 generations
        avgfitnesseachgenUNcrossover.add(avgFitness);
        bestfitnesseachgenUNcrossover.add(bestfit);
        System.out.println("Best chromosome of generation "+gen+"is "+fittestvals);
        //traversing list so that key_Arr (that had previous generation ) is updated
        //and this updated key_Arr will be used in new generation
        for (int i = 0; i < uniformchilds.size() ; i++) {
            String getchild= uniformchilds.get(i);
            key_arr.set(i,getchild);
        }
        key_arr.set(0,elite.get(0));
        key_arr.set(key_arr.size()-1,elite.get(1));

    }
    /**
     * Basically sort the chromosomes in ascending order based on fitness value
     * @param -keyArr
     * @param -text
     * @param -elitismcount
     * @return list
     */
    private static List<String> ElitismChromosomes(List<String> key_arr, String text, int elitismcount) {
        List<String> eliteChromosomes = new ArrayList<>();
        key_arr.sort(Comparator.comparingDouble(chromosome -> new Evaluation().fitness(chromosome, text)));
        for (int i = 0; i < elitismcount; i++) {
            eliteChromosomes.add(key_arr.get(i));
        }
        return eliteChromosomes;
    }
    /**
     * Prints output for onepoint crossover
     * @param -gen
     * @param -keys_One_arr
     * @param -keysize
     * @param -popsize
     * @param -user_crossoverrate
     * @param -user_mutationrate
     * @param -text
     * @param -uniformchilds
     */
    public static void onepointoutput(int gen,List<String>keys_One_arr,List<Double>bestfitnesseachgenOPcrossover,List<Double>avgfitnesseachgenOPcrossover,String keysize,int popsize,double user_crossoverrate,double user_mutationrate,String text,List<String>onepointmchilds)
    {
        System.out.println("Crossover Method-ONEPOINT CROSSOVER METHOD");
        System.out.println("Crossover Rate is "+user_crossoverrate+"and method used is ONEPOINT CROSSOVER");
        System.out.println("Mutattion Rate is "+user_mutationrate+"and method used is RECIPROCAL EXCHANGE");
        System.out.println("No Of chromeosoems"+keys_One_arr.size());
        double bestfit=Double.MAX_VALUE;
        double sum=0;
        int elitismcount=2;
        List<String>elite=ElitismChromosomes(keys_One_arr,text, elitismcount);
        String fittestvals="";//fittestvals will store string that is fittest in generation
        for (int i = 0; i <keys_One_arr.size() ; i++) {
            String val=keys_One_arr.get(i);
            Evaluation e=new Evaluation();
            double fitness= e.fitness(val,text);
            sum+=fitness;
            //key with least fitness value is considered as fittest
            if(fitness<bestfit)
            {
                bestfit=fitness;
                fittestvals=fittestvals+val;
            }
        }
        System.out.println("Best fitness for "+gen+"is "+bestfit);
        System.out.println("Average fitness for "+gen+"is "+sum/keys_One_arr.size());
        System.out.println("Best chromosome of generation "+gen+"is "+fittestvals);
       avgfitnesseachgenOPcrossover.add(sum/ keys_One_arr.size());
       bestfitnesseachgenOPcrossover.add(bestfit);
        //traversing list so that keys_One_Arr (that had previous generation ) is updated
        //and this updated keys_One_Arr will be used in new generation
        for (int i = 0; i < onepointmchilds.size() ; i++) {
            String getchild= onepointmchilds.get(i);
            keys_One_arr.set(i,getchild);
        }
        keys_One_arr.set(0,elite.get(0));
        keys_One_arr.set(keys_One_arr.size()-1,elite.get(1));
    }

    /**
     * This method completely implements GA based on parmaters recieved
     * Prforms tasks such as selection,crossover and mutation and result passed to next generation
     * @param -key_arr
     * @param -keys_One_Arr
     * @param- keysize
     * @param -popsize
     * @param -tournamentsize
     * @param- user_crossoverrate
     * @param -user_mutationrate
     * @param -text
     * @param -onepointchilds
     * @param-onepointchilds
     */
    public static void geneticAlgorithm(ArrayList<String>key_arr,ArrayList<String>keys_One_arr,String keysize,int popsize,int tournamentsize,double user_crossoverrate,double user_mutationrate,String text,ArrayList<String>uniformchilds,ArrayList<String>onepointchilds)
    {
        Selection s=new Selection();
        String unfcrossch1="";//HAS CHILD1 AFTER UNIFORM CROSSOVER
        String unfcrossch2="";
        String opch1="";//HAS CHILD1 AFTER ONEPOINT CROSSOVER
        String opch2="";
            String parent1 = s.tournamentslection(tournamentsize, key_arr, text);
            String parent2="";
           do {
            parent2 = s.tournamentslection(tournamentsize, key_arr, text);
            } while (parent1.equals(parent2));

          mutations rs=new mutations();
            Random random=new Random();
            String mk="10";
            String mask=createmask(parent1.length());
//             System.out.println("MK "+mask);
            double crossoverrate = randomNobetween0and1(0.0, 1.0);
            double mutationrate=randomNobetween0and1(0.0,1.0);
            if(crossoverrate<=user_crossoverrate)
            {
                String unformchild1= unicrossoverChild1(parent1, parent2,mask);
                String unformchild2=unicrossoverChild2(parent1,parent2,mask);
                String []onepoint=onepointcrossover(parent1,parent2,keys_One_arr);//returns child1 ,2 after oneptcrossover
                if(mutationrate<=user_mutationrate)
                {
                     unfcrossch1=rs.mutator_fn(unformchild1);
                     unfcrossch2=rs.mutator_fn(unformchild2);
                     opch1=rs.mutator_fn(onepoint[0]);
                     opch2=rs.mutator_fn(onepoint[1]);

                     uniformchilds.add(unfcrossch1);
                     uniformchilds.add(unfcrossch2);
                     onepointchilds.add(opch1);
                     onepointchilds.add(opch2);
                }
                else{
                    unfcrossch1=new String(unformchild1);
                    unfcrossch2=new String(unformchild2);
                    opch1=new String(onepoint[0]);
                    opch2=new String(onepoint[1]);
                    uniformchilds.add(unfcrossch1);
                    uniformchilds.add(unfcrossch2);
                    onepointchilds.add(opch1);
                    onepointchilds.add(opch2);
                }
            }else {
                if (mutationrate <= user_mutationrate) {
                    unfcrossch1 = rs.mutator_fn(parent1);
                    unfcrossch2 = rs.mutator_fn(parent2);
                    opch1=rs.mutator_fn(parent1);
                    opch2=rs.mutator_fn(parent2);
                    uniformchilds.add(unfcrossch1);
                    uniformchilds.add(unfcrossch2);
                    onepointchilds.add(opch1);
                    onepointchilds.add(opch2);
                }
            }
    }


    /**
     * This fn generates a random no between 0.0 and 1.0 so that
     * we can compare it with user entered crossover rate and check whether to perofrm
     * crosover or not
     * @return -double
     */
    public static double randomNobetween0and1(double min,double max) {
        Random rn=new Random();
        double ans=min+(max-min)*rn.nextDouble();
        return ans;
    }

    /**
     * UnicrossoverChild1-performs a crossover and returns child1 after mating parent1 and parent2
     * @param- parent1
     * @param -parent2
     * @param -mask
     * @return String
     */
    public static String unicrossoverChild1(String parent1, String parent2, String mask) {
        if (parent1 == null || parent1.isEmpty()) {
            parent1 = returnaString(mask.length());
        }
        if (parent2 == null || parent2.isEmpty()) {
            parent2 = returnaString(mask.length());
        }
        // Ensure the lengths of parents and mask are the same
        if (parent1.length() != mask.length() || parent1.length() != parent2.length()) {
            throw new IllegalArgumentException("Length of parent1 and parent 2 should be same as mask.");
        }
        String childgenes1 = new String();
        // Perform crossover based on the mask
        for (int i = 0; i < parent1.length(); i++) {
            if (mask.charAt(i) == '1') {
                // Take gene from parent1 if BIT mask is 1
                childgenes1 = childgenes1+parent1.charAt(i);
            } else  if(mask.charAt(i)=='0'){
                // Take gene from parent2 if mask is 0
                childgenes1 =childgenes1+ parent2.charAt(i);
            }
        }
        return childgenes1;
    }
    /**
     *
     * UnicrossoverChild2-performs a crossover and returns child2 after mating parent1 and parent2
     * @param- parent1
     * @param -parent2
     * @param -mask
     * @return String
     */
    public static String unicrossoverChild2(String parent1, String parent2, String mask) {
        if (parent1 == null || parent1.isEmpty()) {
            parent1 = returnaString(mask.length());
        }
        if (parent2 == null || parent2.isEmpty()) {
            parent2 = returnaString(mask.length());
        }
        if (parent2.length() != parent1.length() || parent1.length() != mask.length()) {
            throw new IllegalArgumentException("Lengths of parents and mask must be the same.");
        }
        String childgenes2 = new String();
        // Perform crossover based on the mask
        for (int i = 0; i < parent2.length(); i++) {
            if (mask.charAt(i) == '1') {
                // Take gene from parent2 if mask is 1
                childgenes2 = childgenes2+parent2.charAt(i);
            } else  if(mask.charAt(i)=='0'){
                // Take gene from parent1 if mask is 0
                childgenes2 =childgenes2+parent1.charAt(i);
            }
        }
        return childgenes2;
    }
    public static String returnaString(int length) {
        StringBuilder empty = new StringBuilder();
        for (int i = 0; i < length; i++) {
            empty.append("-");
        }
        return empty.toString();
    }

    /**
     * generates a ranodm binary string which will be used for uniform crossover
     * @param -length
     * @return String
     */
    public static String createmask(int length) {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            int randomNumber = random.nextInt(2); // 0 or 1
            sb.append(randomNumber);
        }
        return sb.toString();
    }
    /**
     * This fn takes parent 1 and parent 2 and returns child1 and 2 after crossover as array
     * @param -parent1
     * @param -parent2
     * @param -keys_One_arr
     * @return string array that has child1 and child2 after crossover
     */
    public static String[] onepointcrossover(String parent1,String parent2,ArrayList<String>keys_One_arr)
    {
        String []arr=new String[2];
        Random random=new Random();
        //get 2 parent from arryalist so that crossover can be perofrme

        int pointofcrossover=random.nextInt(parent1.length()<parent2.length()?parent1.length():parent2.length());
        String child1=parent1.substring(0,pointofcrossover)+parent2.substring(pointofcrossover,parent2.length());
        String child2=parent2.substring(0,pointofcrossover)+parent1.substring(pointofcrossover,parent1.length());
        arr[0]=child1;
        arr[1]=child2;
        return arr;
    }
    /*** Method name-ArithemeticCrossover
     * @param -parent1
     * @param -parent2
     * @param -mask
     * //this arithmetic crossover is not asked in assignment this is for  bonus
     *   //in this code I am just doing arithmetic crossover on parent 1,2 to generate child
     */
    public static String arithmeticCrossover(String parent1,String parent2,String mask)
    {
        Random rn = new Random();
        double weightfactor = rn.nextDouble(); //weigh factor
        String arthmeticrossover="";
        //ARITHMETIC CROSSOVER
        for (int i = 0; i < parent1.length(); i++) {
            if (parent1.charAt(i) == '-' || parent2.charAt(i) == '-') {
                // If  parent 1 or parent 2 has a '-' at idx i keep it
                arthmeticrossover =arthmeticrossover+"-";
            } else {
                // Take a weighted average of the corresponding genes from parents
                double gn1 = Character.getNumericValue(parent1.charAt(i));
                double gn2 = Character.getNumericValue(parent2.charAt(i));
                double childGene =weightfactor * gn1 + (1 - weightfactor) * gn2;
                arthmeticrossover =arthmeticrossover+ (char) Math.round(childGene);
            }
        }
        return  arthmeticrossover;
    }
}