//Name: Safin Rashid
//EID: srr3288

public class Program3 {

    // DO NOT REMOVE OR MODIFY THESE VARIABLES (calculator and treatment_plan)
    ImpactCalculator calculator;    // the impact calculator
    int[] treatment_plan;           // array to store the treatment plan

    public Program3() {
        this.calculator = null;
    }

    /*
     * This method is used in lieu of a required constructor signature to initialize
     * your Program3. After calling a default (no-parameter) constructor, we
     * will use this method to initialize your Program3.
     *
     *  DO NOT MODIFY THIS METHOD
     *
     */
    public void initialize(ImpactCalculator ic) {
        this.calculator = ic;
        this.treatment_plan = new int[ic.getNumMedicines()];
    }


    /*
     * This method computes and returns the total impact of the treatment plan. It should
     * also fill in the treatment_plan array with the correct values.
     *
     * Each element of the treatment_plan array should contain the number of hours
     * that medicine i should be administered for. For example, if treatment_plan[2] = 5,
     * then medicine 2 should be administered for 5 hours.
     */

    public int computeImpact() {

        //TODO: initialize
        int totalTime = calculator.getTotalTime(), numMedicines = calculator.getNumMedicines(),
                c = totalTime, totalImpact, maxT, maxImpact;
        int[][] DP = new int[numMedicines + 1][totalTime + 1]; //initially all 0

        //TODO: dynamic programming
        for (int i = 1; i <= numMedicines; i++) { //Base case: leave first row/column 0
            for (int j = 1; j <= totalTime; j++) { //for each time in each medicine
                for (int t = 1; t <= j; t++) {
                    DP[i][j] = Math.max(DP[i][j], DP[i - 1][j - t] + calculator.calculateImpact(i - 1, t)); //OPT(max)
                }
            }
        }
        totalImpact = DP[numMedicines][totalTime]; //return last table element

        //TODO: backtrack, populate treatment plan
        for (int i = numMedicines; i > 0; i--) { //for each medicine
            maxT = 0; maxImpact = 0; //reset max
            for (int t = 0; t <= c; t++) { //for each time
                int impact = DP[i - 1][c - t] + calculator.calculateImpact(i-1, t); //impact with previous time
                if (impact > maxImpact) { //if highest impact diff
                    maxT = t;
                    maxImpact = impact;
                }
            }
            treatment_plan[i - 1] = maxT; //set treatment plan
            c = c - treatment_plan[i - 1]; //decrement time "capacity"
        }

        return totalImpact;
    }


    /*
     * This method prints the treatment plan.
     */
    public void printTreatmentPlan() {
        System.out.println("Please administer medicines 1 through n for the following amounts of time:\n");
        int hoursForI = 0;
        int n = calculator.getNumMedicines();
        for(int i = 0; i < n; i++){
            // retrieve the amount of hours for medicine i
            hoursForI = treatment_plan[i]; // ... fill in here ...
            System.out.println("Medicine " + i + ": " + hoursForI); 
        }
    }
}


