import java.io.FileNotFoundException;


/***
 * Usage:
 * Driver takes in 1 parameter: the input file
 */
public class Driver {

    public static int testResult; /////////////////////////////////////
    
    public static String filename;

    private static int testProgram(Program3 program, String filename) {

        ImpactCalculator calculator = null;

        try{
            calculator = new ImpactCalculator(filename);
        } catch (FileNotFoundException e){
            System.err.printf("Could not find file : %s\n", filename);
            usage();
        }

        program.initialize(calculator);
	    int result = program.computeImpact();
	    program.printTreatmentPlan();
		
        return result;
    }

    private static void usage() {
        System.err.println("usage: java Driver <filename>");
        System.exit(1);
    }

    public static void parseArgs(String[] args) {
        filename = null;

        if (args.length != 1) {
            usage();
        }
        
        filename = args[0];
    }

    public static void main(String[] args) {

        Program3 program = new Program3();
        parseArgs(args);
        int result;

        result = testProgram(program, filename);

        testResult = result; ////////////////////////////////////////

        System.out.printf("\nTotal Treatment Impact: %s\n", result);
    }
}
