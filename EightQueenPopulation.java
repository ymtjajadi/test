/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package geneticalgorithm;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.Vector;

/**
 *
 * @author Travelmate 3270
 */
public class EightQueenPopulation {
    
    private final double 
            crossoverPercentage = 0.001,
            mutationPercentage = 0.3;

    private final int
            size = 8,
            winningFitness = 29;
    
    private int populationSize, generationIndex = 1;
    
    private EightQueenKromosom parent_1 = new EightQueenKromosom(), parent_2 = new EightQueenKromosom(), child_1 = new EightQueenKromosom(), child_2 = new EightQueenKromosom();
    
    private Vector<EightQueenKromosom> currentGeneration, nextGeneration = new Vector<EightQueenKromosom>(); //population direname jadi currentGeneration
    
    public EightQueenPopulation(int populationSize){
        this.currentGeneration = new Vector<EightQueenKromosom>();
        this.populationSize = populationSize;
        this.generatePopulation();
    }
    
    private void generatePopulation(){
        for(int a = 0 ; a < populationSize ; a++){
            EightQueenKromosom eqk = new EightQueenKromosom();
            currentGeneration.addElement(eqk);
        }
    }
    
//    public EightQueenKromosom getParent(){
//        EightQueenKromosom parent = null;
//        double totalFitness = 0;
//        for(int a = 0 ; a < population.size() ; a++){
//            EightQueenKromosom eqk = (EightQueenKromosom) population.elementAt(a);
//            totalFitness = totalFitness + eqk.getFitness();
//        }
//        double r = Math.random();
//        double low = 0;
//        double high = 0;
//        for(int a = 0 ; a < population.size() ; a++){
//            EightQueenKromosom eqk = (EightQueenKromosom) population.elementAt(a);
//            low = high;
//            high = high + eqk.getFitness() / totalFitness;
//            if(r >= low && r <= high){
//                parent = eqk;
//                break;
//            }
//        }
//        return parent;
//    }
    
    private void generateParent(){ //aku rubah getParent dikit biar langsung keluar 2 parent
        double totalFitness = 0;
        for(int a = 0 ; a < currentGeneration.size() ; a++){
            EightQueenKromosom eqk = (EightQueenKromosom) currentGeneration.elementAt(a);
            totalFitness = totalFitness + eqk.getFitness();
        }
        double r = Math.random();
        double r2 = Math.random();
        double low = 0;
        double high = 0;
        
        for(int a = 0 ; a < currentGeneration.size() ; a++){
            EightQueenKromosom eqk = (EightQueenKromosom) currentGeneration.elementAt(a);
            low = high;
            high = high + eqk.getFitness() / totalFitness;
            if(r >= low && r <= high){
                parent_1 = eqk;
                if(parent_2 != null){break;}
            }
            if(r2 >= low && r2 <= high){
                parent_2 = eqk;
                if(parent_1 != null){break;}
            }
        }
    }
    
    private void mating()
    {
        
//        System.out.println("mating..");
        //child sama parent diambil dari global
        generateParent();
        
        
        if(Math.random() < getCrossoverPercentage())
        {
            crossover();
        }
        else
        {
            //parent diclone langsung
            nextGeneration.add(parent_1);
            nextGeneration.add(parent_2);
        }
        
        if(Math.random() <= mutationPercentage)
        {
            mutation(child_1);
        }
        
        if(Math.random() <= mutationPercentage)
        {
            mutation(child_2);
        }
        
        
    }
    
    private void crossover()
    {
        // proses crossovere itu berupa ngambil dari parent_1 secara random, 
        // ditambahi parent_2 random juga. Tapi harus dicek supaya gak oleh dobel
        System.out.println("crossovering..");
        Random random = new Random();
        
//        for(;;)
//        {
//            System.out.println("checking1..");
//            int crossoverCut =  random.nextInt(size);
//            child_1.crossOver(crossoverCut, parent_1.getCut(crossoverCut), parent_2.getCut(size-crossoverCut));
//
//            if(!hasDuplicate(child_1.getKromosom())){break;}
//        }
//
//        for(;;)
//        {
//            System.out.println("checking2..");
//            int crossoverCut =  random.nextInt(size);
//            child_2.crossOver(crossoverCut, parent_2.getCut(crossoverCut), parent_1.getCut(size-crossoverCut));
//
//            if(!hasDuplicate(child_2.getKromosom())){break;}
//        }

        System.out.println("checking1..");
        int crossoverCut =  random.nextInt(size);
        child_1.crossOver(crossoverCut, parent_1.getCut(crossoverCut), parent_2.getCut(size-crossoverCut));

        if(hasDuplicate(child_1.getKromosom())){
            forcedMutation(child_1);
        }

        System.out.println("checking2..");
        crossoverCut =  random.nextInt(size);
        child_2.crossOver(crossoverCut, parent_2.getCut(crossoverCut), parent_1.getCut(size-crossoverCut));

        if(hasDuplicate(child_2.getKromosom())){
           forcedMutation(child_2);
        }
        
        nextGeneration.add(child_1);
        nextGeneration.add(child_2);
    }

    public void forcedMutation(EightQueenKromosom child){
        // buat gen dobel stelah crossover
        int[] krom = child.getKromosom();

        for (int i = 1; i < 9; i++) {
            for (int j = i + 1; j < 9; j++) {
                if(krom[i] == krom[j]){
                    krom[j] = getNonDuplicateGen(child);
                }
            }
        }
    }

    public int getNonDuplicateGen(EightQueenKromosom child){
        // cari gen yang ga ada di dalem kromosom
        int gen = 1;
        int[] krom = child.getKromosom();
        boolean check = true;
        for (int i = 1; i < 9; i++) {
            gen = i;
            check = true;
            for (int j = 1; j < 9; j++) {
                if(gen == krom[j]){
                    check = false;
                    break;
                }
            }
            if(check)
                break;
        }
        return gen;
    }
    
    private void mutation(EightQueenKromosom child)
    {
        int[] krom = child.getKromosom();
        
        int random = new Random().nextInt(size-1)+1; //biar gak isa dapet 0
        int random2 = new Random().nextInt(size-1)+1;
        
        //dituker
        int temp = krom[random];
        krom[random] = krom[random2];
        krom[random2] = temp;
        
    }
    
    public EightQueenKromosom findBestSolution()
    {
        EightQueenKromosom solution = null;
        
        for(;;)
        {
            if(child_1.getFitness() == winningFitness)//winning
            {
                solution = child_1;
                break;
            }
            else if(child_2.getFitness() == winningFitness)//winning
            {
                solution = child_2;
                break;
            }
            else
            {
                if( nextGeneration.size() < populationSize-1 ) //min 1 soale mating generate 2 child sekaligus
                {
                    mating();
                }
                else
                {
                    //ganti generasi baru
                    currentGeneration = nextGeneration;
                    nextGeneration = new Vector<EightQueenKromosom>();
                    generationIndex++;
                }
//                System.out.println(generationIndex);
            }
        }
        
        return solution;
        
    }
    
    public EightQueenKromosom getParent( int number )
    {
        EightQueenKromosom parentToBeReturned = null;
        
        switch( number )
        {
            case 1:
                parentToBeReturned =  parent_1;
                break;
            case 2:
                parentToBeReturned =  parent_2;
                break;
        }
        
        return parentToBeReturned;
    }
    
    public EightQueenKromosom getChild( int number )
    {
        EightQueenKromosom childToBeReturned = null;
        
        switch( number )
        {
            case 1:
                childToBeReturned =  child_1;
                break;
            case 2:
                childToBeReturned =  child_2;
                break;
        }
        
        return childToBeReturned;
    }
    
    public Vector<EightQueenKromosom> getPopulation()
    {
        return this.currentGeneration;
    }
    public Vector<EightQueenKromosom> getNewPopulation()
    {
        return this.nextGeneration;
    }
    
    public boolean hasDuplicate(int[] integer) { //ngecek ada duplikat gak di suatu kromosom
        Set<Integer> set = new HashSet<Integer>();
        for (int i = 1; i < size+1; i++) {
            set.add(integer[i]);
        }
        
        if( set.size() == size ) {return false;} else {return true;}
    }

    
    public static void main(String[] args) {
        EightQueenPopulation eqp = new EightQueenPopulation(10);
        
//        for (int i = 0; i < 10; i++) {
//            eqp.getPopulation().get(i).printKromosom();
//            System.out.println("--fitness = " + eqp.getPopulation().get(i).getFitness());
//        }
//        
//        System.out.println("");
//        System.out.println("");
//        eqp.mating();
//        
//        for (int i = 0; i < eqp.getNewPopulation().size() ; i++) {
//            eqp.getNewPopulation().get(i).printKromosom();
//            System.out.println("--fitness = " + eqp.getPopulation().get(i).getFitness());
//        }
//        eqp.generateParent();
//        eqp.crossover();
//        eqp.getChild(1).printKromosom();
//        eqp.getChild(2).printKromosom();
        
        EightQueenKromosom solution = eqp.findBestSolution();
        solution.printKromosom();
        System.out.println("Fitness = " + solution.getFitness());
        System.out.println("Generation = " + eqp.getGenerationIndex());
    }

    public double getCrossoverPercentage() {
        return crossoverPercentage;
    }

    public int getGenerationIndex() {
        return generationIndex;
    }
}
