package backtracking;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;

public class BusRouting {

    public int n;
    public int Q;
    public int[][] d;

    // variable
    public int[] x;
    public int temp_val;
    public ArrayList<Integer> loading;
    public int best_val;
    public boolean[] visited;
    public int min_d;

    public int[] res;

    public static void main(String[] args) {
        BusRouting busRouting = new BusRouting();
        busRouting.readData();
        busRouting.exec();
    }

    public void readData() {
        Scanner scanner;
        try {
            scanner = new Scanner(new File(Objects.requireNonNull(getClass().getResource("/BusRouting.txt")).getFile()));
            n = scanner.nextInt();
            Q = scanner.nextInt();
            d = new int[2 * n + 1][2 * n + 1];
            min_d = 1000000000;
            for (int i = 0; i <= 2 * n; i++) {
                for (int j = 0; j <= 2 * n; j++) {
                    d[i][j] = scanner.nextInt();
                    min_d = Math.min(min_d, d[i][j]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void exec() {
        // init
        x = new int[2 * n + 1];
        res = new int[2 * n + 1];
        temp_val = 0;
        loading = new ArrayList<>();
        best_val = 1000000000;
        visited = new boolean[2 * n + 1];
        x[0] = 0;
        visited[0] = true;
        find(1);
        System.out.println(Arrays.toString(res) + " " + best_val);
    }

    public void find(int k) {
        if (k == 2 * n + 1) {
            if (temp_val + d[x[k - 1]][0] < best_val) {
                best_val = temp_val + d[x[k - 1]][0];
                System.arraycopy(x, 0, res, 0, x.length);
            }
        }
        for (int i = 1; i <= 2 * n; i++) {
            if (check(k, i)) {
                visited[i] = true;
                x[k] = i;
                if (i > n) {
                    loading.remove((Integer) (i - n));
                } else {
                    loading.add(i);
                }
                temp_val += d[x[k - 1]][x[k]];
                find(k + 1);
                if (i > n) {
                    loading.add((i - n));
                } else {
                    loading.remove((Integer) i);
                }
                temp_val -= d[x[k - 1]][x[k]];
                visited[i] = false;
            }
        }
    }

    public boolean check(int k, int i) {
        if (i > n && !loading.contains(i - n)) return false;
        if (loading.size() == Q && i <= n) return false;
        if (temp_val + d[x[k - 1]][i] + min_d * (2 * n + 1 - k) > best_val) return false;
        return !visited[i];
    }
}
