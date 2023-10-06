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

        int n = 1000;
        int[][] randomM = randomPreferenceList(n);
        int[][] randomW = randomPreferenceList(n);
        int[] mRankOfM = new int[randomM.length];
        int[] wRankOfW = new int[randomW.length];
        galeShapleyAlgorithm(randomM, randomW, mRankOfM, wRankOfW);
        defineGoodness(mRankOfM, wRankOfW, n);
    }

    public static void galeShapleyAlgorithm(int[][] M, int[][] W, int[] mRankOfM, int[] wRankOfW) {
        Queue<Integer> unmatchedMen = new LinkedList<>();
        Map<Integer, Integer> mPositionInList = new HashMap<>();
        initializeAll(M, unmatchedMen, mPositionInList);
        Map<Integer, Integer> matches = new HashMap<>();
        System.out.println("Trace:");
        while (!unmatchedMen.isEmpty()) {
            int m = unmatchedMen.poll();
            int[] mPreferenceList = M[m];
            int mPosition = mPositionInList.get(m);
            int w = mPreferenceList[mPosition];
            int[] wPreferenceList = W[w];
            System.out.print(m + " proposes to " + w + " [" + w + "," + matches.getOrDefault(w, -1) + "]  ");
            if (!matches.containsKey(w)) {
                System.out.println("Accepted");
                matches.put(w, m);
                int wPosition = findM(wPreferenceList, m);
                mRankOfM[m] = mPosition + 1;
                wRankOfW[w] = wPosition + 1;
            } else {
                int prevMatch = matches.get(w);
                if (wPrefersThisM(wPreferenceList, m, prevMatch)) {
                    System.out.println("Accepted");
                    matches.put(w, m);
                    unmatchedMen.add(prevMatch);
                    int wPosition = findM(wPreferenceList, m);
                    mRankOfM[m] = mPosition + 1;
                    wRankOfW[w] = wPosition + 1;
                } else {
                    System.out.println("Rejected");
                    unmatchedMen.add(m);
                }
            }
            mPosition++;
            mPositionInList.put(m, mPosition);
        }
        System.out.println();
        printOutput(matches);
    }

    public static void initializeAll(int[][] M, Queue<Integer> unmatchedMen, Map<Integer, Integer> mPositionInList) {
        for (int i = 0; i < M.length; i++) {
            unmatchedMen.add(i);
            mPositionInList.put(i, 0);
        }
    }

    public static boolean wPrefersThisM(int[] wPreferenceList, int currM, int prevMatch) {
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

    public static int findM(int[] wPreferenceList, int m) {
        int index = 0, l = 0, r = wPreferenceList.length - 1;
        while (r >= l) {
            if (wPreferenceList[l] == m) {
                index = l;
                break;
            } else if (wPreferenceList[r] == m) {
                index = r;
                break;
            }

            l++;
            r--;
        }
        return index;
    }

    public static void defineGoodness(int[] mRankOfM, int[] wRankOfW, int n) {
        int mSum = 0;
        int wSum = 0;
        for (int mVal : mRankOfM) mSum += mVal;
        for (int wVal : wRankOfW) wSum += wVal;
        System.out.println();
        System.out.println("MGoodness = " + mSum / n + ", WGoodness = " + wSum / n);
    }
}