import java.util.*;
public class HomeworkOne {
    public static void main(String[] args) {
        int[][] M = {
                        {2, 0, 1},
                        {2, 0, 1},
                        {2, 1, 0}
                    };
        int[][] W = {
                        {2, 0, 1},
                        {0, 2, 1},
                        {2, 0, 1}
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
                System.out.print("Accepted");
                matches.put(w, m);
                unmatchedWomen.remove(w);
            } else {
                int[] wPreferenceList = W[w];
                if (matches.get(w) != wPreferenceList[0]) {
                    System.out.print("Rejected");
                    unmatchedMen.add(m); // m remains free
                } else if (wPreferenceList[0] == m) {
                    System.out.print("Accepted");
                    int prevM = matches.get(w);
                    matches.replace(w, m);
                    unmatchedMen.add(prevM);
                }
            }

            System.out.println();
            rank += 1;
            mRank.replace(m, rank);
        }

        for (Map.Entry entry : matches.entrySet()) {
            System.out.println("(" + entry.getKey() + ", " + entry.getValue() + ")");
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
}