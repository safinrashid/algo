import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

//NOTE: DO NOT CHANGE THIS CODE
//

/***
 * NOTE: DO NOT CHANGE THIS CODE
 * Program 3 contains an ImpactCalculator object that is initialized in the Driver.
 * Do not intialize a new ImpactCalculator object. Use the calculateImpact function associated with the
 * ImpactCalculator object that we have already intialized for you
 */
public class ImpactCalculator {
    
    private int totalTime;
    private int numMedicines;
    private int[][] function;

    public ImpactCalculator(String fileName) throws FileNotFoundException{

        Scanner sc = new Scanner(new File(fileName));
        String nextLine = sc.nextLine();
        String[] split = nextLine.split(" ");
        numMedicines = Integer.parseInt(split[0]);
        totalTime = Integer.parseInt(split[1]);
        function = new int[numMedicines][totalTime + 1];

        for(int i = 0; i < numMedicines; i++){
            function[i][0] = 0;
        }

        int i = 0;
        while(sc.hasNextLine()){
            nextLine = sc.nextLine();
            split = nextLine.split(" ");
            for(int j = 0; j < totalTime; j++){
                function[i][j + 1] = Integer.parseInt(split[j]);
            }
            i++;
        }

    }

    /***
     * @param medicine: Medicine number
     * @param time: Amount of time in the dose
     * @return impact of applying medicine
     */
    public int calculateImpact(int medicine, int time) {
        return function[medicine][time];
    }

    public int getNumMedicines() {
        return numMedicines;
    }

    public int getTotalTime() {
        return totalTime;
    }
}
