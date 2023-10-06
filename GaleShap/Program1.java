
/*
 * Name: Safin Rashid
 * EID: srr3288
 */

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Objects;

/**
 * Your solution goes in this class.
 * *
 * Please do not modify the other files we have provided for you, as we will use
 * our own versions of those files when grading your project. You are
 * responsible for ensuring that your solution works with the original version
 * of all the other files we have provided for you.
 * *
 * That said, please feel free to add additional files and classes to your
 * solution, as you see fit. We will use ALL of your additional files when
 * grading your solution. However, do not add extra import statements to this file.
 */
public class Program1 extends AbstractProgram1 {

    /**
     * Determines whether a candidate Matching represents a solution to the stable matching problem.
     * Study the description of a Matching in the project documentation to help you with this.
     */
     /*
      for a donor d0 in D:
       let h0 be the hospital d0 is matched to.

       for each donor d1 in h0's preference_list (from rank 0 to d1 == d0, s.t. h0 always prefers d1 over d0)
           let h1 be the hospital d1 is matched to
           if d1 prefers h0 over h1:
              return false (instability)
           if h1 == -1: (d1 is preferred and isn't matched to an univ)
               return false (instability)
           if(d1 == d0) break
      */
    @Override
    public boolean isStableMatching(Matching problem) {

        ArrayList<Integer> solution = problem.getDonorMatching();
        ArrayList<ArrayList<Integer>> H = problem.getHospitalPreference();
        ArrayList<ArrayList<Integer>> D = problem.getDonorPreference();

        //donor_matching[index];  1d array where: index = a donor; donor_matching[index] is the hospital donor is matched to
        //hospital_preference[index_1][index_2]; 2d array where: index_1 is a hospital, index_2 is the ranking
        //donor_preference[index_1][index_2]; 2d array where: index_1 is a donor index_2 is the ranking

        for (int i = 0; i < D.size(); i++) { //for each donor in D
            int h0 = solution.get(i);
            for (int j = 0; j < D.size(); j++) { //from the highest rank to match rank (0 to d1=d0)
                int h1 = solution.get(j);
                if (h1 == -1) continue; //no match pair made to check
                if (h0 != -1) { //matched
                    boolean d_instable =  D.get(i).indexOf(h0)  >  D.get(i).indexOf(h1); //d1 over d0
                    boolean h_instable =  H.get(h1).indexOf(i)  <  H.get(h1).indexOf(j); //h0 over h1
                    if (d_instable && h_instable)
                        //h1 prefers d1 over d0 AND d0 prefers h0 over h1
                        return false;
                    continue;
                }//unmatched
                if (H.get(h1).indexOf(j)  >  H.get(h1).indexOf(i))
                    //hospital match preferred less than unmatched preference
                    return false;
            }
        }
        return true;
    }


    /**
     * Determines a solution to the stable matching problem from the given input set. Study the
     * project description to understand the variables which represent the input to your solution.
     *
     * @return A stable Matching.
     */
     /*
     for each hospital while there are any open positions(m):
	    while hospital m has openings
	        for each donor in hospital’s preferences (n):
                if (donor is unmatched):match donor & hospital
			        decrement opening from hospital
                    break, go to next hospital opening
                if (matched and donor is higher preference over current hospital matching):
	                match donor & hospital
			        decrement opening from new match hospital
			        add opening to previous match
			        break, go to next hospital opening
      */
    @Override
    public Matching stableMatchingGaleShapley_hospitaloptimal(Matching problem) {
        /* TODO implement this function */

        Matching solution = new Matching(problem); //solution to return
        int M = problem.getHospitalCount(), N = problem.getDonorCount();
        ArrayList<ArrayList<Integer>> H = problem.getHospitalPreference();
        ArrayList<ArrayList<Integer>> D = problem.getDonorPreference();
        int totalAvailable = problem.totalHospitalPositions(); //total openings to measure progress
        ArrayList<Integer> openings = new ArrayList(); //let openings = number of hospital positions
        ArrayList<Integer> donorMatch = new ArrayList(); //let matching = array of matches
        int d_pref[][] = new int[N][M]; //initialize inverse array for donor preference in O(n)

        //initialize hospital openings array
        for (int i = 0 ; i < M; i++)
            openings.add(problem.getHospitalPositions().get(i));
        for (int i = 0; i < N; i++) {
            donorMatch.add(i, -1); //initialize donorMatch as unmatched
            ArrayList<Integer> D_ind = D.get(i);
            for (int j = 0; j < M; j++){ //set inverse array values
                d_pref[i][D_ind.get(j)] = j;
            }
        }

        fillingAllPostions:
        while (totalAvailable > 0) { //while there are open positions
            for (int i = 0; i < M; i++) { //for each hospital (m)
                fillingHospPositions:
                while (openings.get(i) > 0) { //while hospital m has openings
                    ArrayList<Integer> d = H.get(i);
                    for (int j = 0; j < N; j++) { //for each donor in hospital’s preferences (n)

                        int d0 = d.get(j), d1 = donorMatch.get(d0);; //find d0 and d1
                        int decrement = openings.get(i) - 1;

                        //donor is unmatched
                        if (d1 == -1) {
                            openings.set(i, decrement); totalAvailable--; //decrement openings
                            donorMatch.set(d0, i); //match
                            break fillingHospPositions;
                        }

                        //matched
                        boolean betterThan = d_pref[d0][d1] > d_pref[d0][i];
                        if (betterThan && d1 != -1) { //matched donor is higher preference over current hospital matching
                            int increment = openings.get(d1) + 1;
                            openings.set(d1, increment); //add opening to previous match to remove
                            donorMatch.set(d0, i); //match
                            openings.set(i, decrement); //decrement opening from new match hospital
                            break fillingHospPositions;
                        }
                        if(totalAvailable == 0) break fillingAllPostions;
                        if(openings.get(i) == 0) break fillingHospPositions;
                    }
                }
            }
        }

        solution.setDonorMatching(donorMatch);
        return solution;
    }

    /**
     * Determines a solution to the stable matching problem from the given input set. Study the
     * project description to understand the variables which represent the input to your solution.
     *
     * @return A stable Matching.6
     */
     /*
     for each donor while any donor is free (n):
     for each hospital in donor’s preferences (m):
		if (hospital has opening):
			match donor & hospital
			decrement hospital openings
		else (hospital is full):
            for each donor (n):
				if (hospital prefers this donor over worst match donor):
					match donor & hospital
					unmatch previous/the worst donor

      */
    @Override
    public Matching stableMatchingGaleShapley_donoroptimal(Matching problem) {
        /* TODO implement this function */

        Matching solution = new Matching(problem); //solution to return
        int M = problem.getHospitalCount(), N = problem.getDonorCount();
        ArrayList<ArrayList<Integer>> H = problem.getHospitalPreference();
        ArrayList<ArrayList<Integer>> D = problem.getDonorPreference();
        ArrayList<Integer> openings = new ArrayList(); //let openings = number of hospital positions
        ArrayList<Integer> donorMatch = new ArrayList(); //let matching = array of matches
        int h_pref [][] = new int[M][N]; //initialize inverse array for hospital preference in O(n)
        boolean stable;

        //initialize arrays
        for (int i = 0; i < N; i++)
            donorMatch.add(i, -1); //initialize donorMatch as unmatched
        for (int i = 0; i < M; i++){
            openings.add(problem.getHospitalPositions().get(i));
            ArrayList<Integer> H_ind = H.get(i);
            for (int j = 0; j < N; j++){ //set inverse array values
                h_pref[i][H_ind.get(j)] = j;
            }
        }

        while(true) { //until enough donors for stability filled
            stable = true;
            for(int i = 0; i < N; i++) { //for each donor
                if(donorMatch.get(i) != -1) continue; //already matched

                ArrayList<Integer> d_pref = D.get(i);
                for (int j = 0; j < d_pref.size(); j++) { //for each hospital in donor pref
                    if (openings.get(d_pref.get(j)) > 0) { //if available, match
                        donorMatch.set(i,  d_pref.get(j)); //match
                        int decrement = openings.get(d_pref.get(j)) - 1;
                        openings.set(d_pref.get(j), decrement); //decrement hospitals openings
                        break;
                    } else {

                        for (int d = 0; d < N; d++) { //if not available, must be better than worse
                            int d_ind = d_pref.get(j);
                            boolean betterThan = h_pref[d_ind][d] > h_pref[d_ind][i] && //donor more preferred than current match d
                                    Objects.equals(d_ind, donorMatch.get(d)); //donor is a match preference

                            if (betterThan) {
                                donorMatch.set(d, -1); //unmatch old donor
                                donorMatch.set(i, d_ind); //match new donor
                                stable = false; //no longer stable since old donor is unmatched
                                break;
                            }
                        }
                    }
                    if(donorMatch.get(i) > -1) break; //match found
                }

            }
            if(stable) break; //stable == done matching
        }

        solution.setDonorMatching(donorMatch);
        return solution;
    }
}