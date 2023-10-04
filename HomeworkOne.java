import java.util.*;
public class HomeworkOne {
    public static void main(String[] args) {
        int[][] M = {
                        {0, 1, 2},
                        {1, 0, 2},
                        {0, 1, 2}
                    };
        int[][] W = {
                        {1, 2, 0},
                        {0, 1, 2},
                        {0, 1, 2}
                    };

        galeShapleyAlgorithm(M, W);
    }

    public static void galeShapleyAlgorithm(int[][] M, int[][] W) {
        Queue<Integer> unmatchedMen = new LinkedList<>();
        Map<Integer, Integer> mRank = new HashMap<>();
        initializeAll(M, unmatchedMen, mRank);
        Map<Integer, Integer> matches = new HashMap<>();
        System.out.println("Trace:");
        while (!unmatchedMen.isEmpty()) {
            int m = unmatchedMen.poll();
            int[] mPreferenceList = M[m];
            int rank = mRank.get(m);
            int w = mPreferenceList[rank];
            System.out.print(m + " proposes to " + w + " [" + w + "," +
                    matches.getOrDefault(w, -1) + "]  ");
            if (!matches.containsKey(w)) {
                System.out.println("Accepted");
                matches.put(w, m);
            } else {
                int[] wPreferenceList = W[w];
                int prevMatch = matches.get(w);
                if (wPrefersM(wPreferenceList, m, prevMatch)) {
                    System.out.println("Accepted");
                    matches.put(w, m);
                    unmatchedMen.add(prevMatch);
                } else {
                    System.out.println("Rejected");
                    unmatchedMen.add(m);
                }
            }

            rank++;
            mRank.put(m, rank);
        }

        System.out.println();
        printOutput(matches);
    }

    public static void initializeAll(int[][] M, Queue<Integer> unmatchedMen,
                                     Map<Integer, Integer> mRank) {
        for (int i = 0; i < M.length; i++) {
            unmatchedMen.add(i);
            mRank.put(i, 0);
        }
    }

    public static boolean wPrefersM(int[] wPreferenceList, int currM, int prevMatch) {
        for (int m : wPreferenceList) {
            if (m == currM) return true;
            if (m == prevMatch) break;
        }

        return false;
    }

    public static void printOutput(Map<Integer, Integer> matches) {
        int[] output = new int[matches.size()];
        for (int w = 0; w < output.length; w++) {
            int m = matches.get(w);
            output[m] = w;
        }

        System.out.println("Output:");
        System.out.println(Arrays.toString(output));
    }
}