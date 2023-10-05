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

        // galeShapleyAlgorithm(M, W);

        int n = 10;
        int[][] randomM = randomPreferenceList(n);
        printMatrix(randomM);
        System.out.println();
        int[][] randomW = randomPreferenceList(n);
        printMatrix(randomW);
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

    public static int[][] randomPreferenceList(int n) {
        int[][] randomMatrix = new int[n][n];
        int[] nums = new int[n];
        for (int i = 0; i < nums.length; i++) {
            nums[i] = i;
        }

        for (int r = 0; r < randomMatrix.length; r++) {
            int[] shuffled = shuffle(nums);
            for (int c = 0; c < randomMatrix[0].length; c++) {
                randomMatrix[r][c] = shuffled[c];
            }
        }

        return randomMatrix;
    }

    public static int[] shuffle(int[] arr) {
        Random rand = new Random();
        for (int i = arr.length - 1; i > 0; i--) {
            int index = rand.nextInt(i + 1);
            int temp = arr[index];
            arr[index] = arr[i];
            arr[i] = temp;
        }

        return arr;
    }

    public static void printMatrix(int[][] matrix) {
        for (int[] row : matrix) {
            System.out.println(Arrays.toString(row));
        }
    }
}