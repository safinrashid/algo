import java.util.ArrayList;

/**
 * A Matching represents a candidate solution to the stable matching problem. A Matching may or may
 * not be stable.
 */
public class Matching {
    /**
     * Number of hospitals.
     */
    private Integer m;

    /**
     * Number of donors.
     */
    private Integer n;

    /**
     * A list containing each hospital's preference list.
     */
    private ArrayList<ArrayList<Integer>> hospital_preference;

    /**
     * A list containing each donor's preference list.
     */
    private ArrayList<ArrayList<Integer>> donor_preference;

    /**
     * Number of positions available in each hospital.
     */
    private ArrayList<Integer> hospital_positions;

    /**
     * Matching information representing the index of hospital a donor is matched to, -1 if not
     * matched.
     *
     * <p>An empty matching is represented by a null value for this field.
     */
    private ArrayList<Integer> donor_matching;

    public Matching(
            Integer m,
            Integer n,
            ArrayList<ArrayList<Integer>> hospital_preference,
            ArrayList<ArrayList<Integer>> donor_preference,
            ArrayList<Integer> hospital_positions) {
        this.m = m;
        this.n = n;
        this.hospital_preference = hospital_preference;
        this.donor_preference = donor_preference;
        this.hospital_positions = hospital_positions;
        this.donor_matching = null;
    }

    public Matching(
            Integer m,
            Integer n,
            ArrayList<ArrayList<Integer>> hospital_preference,
            ArrayList<ArrayList<Integer>> donor_preference,
            ArrayList<Integer> hospital_positions,
            ArrayList<Integer> donor_matching) {
        this.m = m;
        this.n = n;
        this.hospital_preference = hospital_preference;
        this.donor_preference = donor_preference;
        this.hospital_positions = hospital_positions;
        this.donor_matching = donor_matching;
    }

    /**
     * Constructs a solution to the stable matching problem, given the problem as a Matching. Take a
     * Matching which represents the problem data with no solution, and a donor_matching which
     * solves the problem given in data.
     *
     * @param data              The given problem to solve.
     * @param donor_matching The solution to the problem.
     */
    public Matching(Matching data, ArrayList<Integer> donor_matching) {
        this(
                data.m,
                data.n,
                data.hospital_preference,
                data.donor_preference,
                data.hospital_positions,
                donor_matching);
    }

    /**
     * Creates a Matching from data which includes an empty solution.
     *
     * @param data The Matching containing the problem to solve.
     */
    public Matching(Matching data) {
        this(
                data.m,
                data.n,
                data.hospital_preference,
                data.donor_preference,
                data.hospital_positions,
                new ArrayList<Integer>(0));
    }

    public void setDonorMatching(ArrayList<Integer> donor_matching) {
        this.donor_matching = donor_matching;
    }

    public Integer getHospitalCount() {
        return m;
    }

    public Integer getDonorCount() {
        return n;
    }

    public ArrayList<ArrayList<Integer>> getHospitalPreference() {
        return hospital_preference;
    }

    public ArrayList<ArrayList<Integer>> getDonorPreference() {
        return donor_preference;
    }

    public ArrayList<Integer> getHospitalPositions() {
        return hospital_positions;
    }

    public ArrayList<Integer> getDonorMatching() {
        return donor_matching;
    }

    public int totalHospitalPositions() {
        int positions = 0;
        for (int i = 0; i < m; i++) {
            positions += hospital_positions.get(i);
        }
        return positions;
    }

    public String getInputSizeString() {
        return String.format("m=%d n=%d\n", m, n);
    }

    public String getSolutionString() {
        if (donor_matching == null) {
            return "";
        }

        StringBuilder s = new StringBuilder();
        for (int i = 0; i < donor_matching.size(); i++) {
            String str = String.format("Donor %d Hospital %d", i, donor_matching.get(i));
            s.append(str);
            if (i != donor_matching.size() - 1) {
                s.append("\n");
            }
        }
        return s.toString();
    }

    public String toString() {
        return getInputSizeString() + getSolutionString();
    }
}