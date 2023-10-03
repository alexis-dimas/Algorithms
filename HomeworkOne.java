import java.util.*;
public class HomeworkOne {
    public static void main(String[] args) {
        int[][] M = {
                        {2, 1, 3, 0},
                        {0, 1, 3, 2},
                        {0, 1, 2, 3},
                        {0, 1, 2, 3}
                    };
        int[][] W = {
                        {0, 2, 1, 3},
                        {2, 0, 3, 1},
                        {3, 2, 1, 0},
                        {2, 3, 1, 0}
                    };

        questionFour(M, W);
    }

    public static void questionFour(int[][] M, int[][] W) {
        Queue<Integer> unmatchedMen = new LinkedList<>();
        Set<Integer> unmatchedWomen = new HashSet<>();
        Map<Integer, Integer> mRank = new HashMap<>();
        initialize(M, W, unmatchedMen, unmatchedWomen, mRank);

        Map<Integer, Integer> matches = new HashMap<>();
        while (!unmatchedMen.isEmpty()) {
            int m = unmatchedMen.poll();
            int[] mPreferenceList = M[m];
            int rank = mRank.get(m);
            int w = mPreferenceList[rank];

            System.out.print(m + " proposes to " + w + " [" + w + "," + matches.getOrDefault(w, -1) + "]  ");
            if (unmatchedWomen.contains(w)) {
                System.out.print("Pair = " + "(" + m + "," + w + ") ");
                System.out.print("Accepted");
                matches.put(w, m);
                unmatchedWomen.remove(w);
            } else {
                int[] wPreferenceList = W[w];

                // I think it has to do with the wPreferenceList[0]
                if (getHighest(wPreferenceList, m, matches.get(w))) {
                    System.out.print("Pair = " + "(" + m + "," + w + ") ");
                    System.out.print("Accepted");
                    int prevM = matches.get(w);
                    matches.put(w, m);
                    unmatchedMen.add(prevM);
                } else { // else w rejects m
                    System.out.print("Rejected " + rank);
                    unmatchedMen.add(m); // m remains free
                }
            }

            System.out.println();
            rank++;
            mRank.put(m, rank);
        }

        for (Map.Entry entry : matches.entrySet()) {
            System.out.println("M = " + entry.getValue() + ", W = " + entry.getKey());
        }


    }

    public static void initialize(int[][] M, int[][] W, Queue<Integer> unmatchedMen,
                                  Set<Integer> unmatchedWomen, Map<Integer, Integer> mRank) {
        for (int r = 0; r < M.length; r++) {
            unmatchedMen.add(r);
            mRank.put(r, 0);
        }

        for (int r = 0; r < W.length; r++) {
            unmatchedWomen.add(r);
        }
    }

    public static boolean getHighest(int[] preferenceList, int curr, int prev) {

        // which does it encounter first?
        boolean value = false;
        for (int i = 0; i < preferenceList.length; i++) {
            if (preferenceList[i] == curr) {
                value = true;
                break;
            } else if (preferenceList[i] == prev) {
                break;
            }
        }

        return value;
    }
}