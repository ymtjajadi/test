/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package geneticalgorithm;

/**
 *
 * @author Travelmate 3270
 */
public class EightQueenKromosom {
    private int[] kromosom;
    public EightQueenKromosom(){
        kromosom = new int[9];
        generateKromosom();
    }
    public void generateKromosom(){
        for(int a = 1 ; a < 9 ; a++){
            kromosom[a] = a;
        }
        for(int a = 1 ; a < 9 ; a++){
            int r = (int)(Math.random()*8 + 1);
            if(a != r){
                int temp = getKromosom()[a];
                kromosom[a] = getKromosom()[r];
                kromosom[r] = temp;
            }
        }
//        for(int a = 1 ; a < 9 ; a++){
//            kromosom[a] = (int) (Math.random()*8) + 1;
//            for(int b = 1 ; b < a ; b++){
//                if(kromosom[a] == kromosom[b]){
//                    a--;
//                    break;
//                }
//            }
//        }
    }
    public int getDistance(){
        int distance = 0;
        for(int a = 1 ; a < 9 ; a++){
            for(int b = a + 1, c = getKromosom()[a] + 1 ; b < 9 && c < 9 ; b++, c++){
                if(getKromosom()[b] == c){
                    distance++;
                }
            }
            for(int b = a + 1, c = getKromosom()[a] - 1 ; b < 9 && c > 0 ; b++, c--){
                if(getKromosom()[b] == c){
                    distance++;
                }
            }
        }
        return distance;
    }
    public int getFitness(){
        return 29 - getDistance();
    }
    
    public int[] getCut( int cut )
    {
        int[] cutPart = new int[cut+1];
        
        for (int i = 1; i <= cut; i++) {
            cutPart[i] = getKromosom()[i];
        }
        
        return cutPart;
    }
    
    public void crossOver(int cut, int[] parent_1_cut, int[] parent_2_cut)
    {   
        int index = 0;
        for (int i = 1; i < parent_1_cut.length; i++) {
            kromosom[i] = parent_1_cut[i];
            index = i;
        }
        
        for (int i = index+1; i < ( index + parent_2_cut.length) ; i++) {
            kromosom[i] = parent_2_cut[i-index];
        }
    }
    
    public void printKromosom(){
        for(int a = 1 ; a < 9 ; a++){
            System.out.print(getKromosom()[a] + " ");
        }
        System.out.println("");
    }
    public static void main(String[] args) {
        EightQueenKromosom eqk = new EightQueenKromosom();
        eqk.printKromosom();
    }

    public int[] getKromosom() {
        return kromosom;
    }

    public void setKromosom(int[] kromosom) {
        this.kromosom = kromosom;
    }
}
