import com.google.ortools.Loader;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public abstract class Solver {
    // data
    int n;
    int m;
    double[] d;
    double[] t;
    ArrayList<Integer>[] D;
    int[][] c;
    // time start
    long start;
    // data filename
    String filename;

    public static void main(String[] args) {
        Loader.loadNativeLibraries();
        Solver solver;
        solver = new Heuristic();
        solver.start = System.currentTimeMillis();
        solver.readData("50_500_10_(1).txt");
        solver.solve();
        System.out.printf("Runtime: %d (ms)\n", System.currentTimeMillis() - solver.start);
    }

    public static FileWriter getWriter(String filename) {
        File output = new File("output/" + filename.split("\\.")[0] + "-out.txt");
        FileWriter writer = null;
        try {
            boolean created = output.createNewFile();
            writer = new FileWriter(output, false);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return writer;
    }

    void solve() {
        System.out.println("This is solve method!");
    }

    void readData(String _filename) {
        filename = _filename;
        Scanner scanner;
        try {
            scanner = new Scanner(new File(Objects.requireNonNull(getClass().getResource("/input/" + _filename)).getFile()));
            n = scanner.nextInt();
            m = scanner.nextInt();
            d = new double[n];
            for (int i = 0; i < n; i++) {
                d[i] = scanner.nextDouble();
            }
            t = new double[m];
            for (int i = 0; i < m; i++) {
                t[i] = scanner.nextDouble();
            }
            D = new ArrayList[n];
            for (int i = 0; i < n; i++) {
                D[i] = new ArrayList<>();
                int k = scanner.nextInt();
                for (int j = 0; j < k; j++) {
                    D[i].add(scanner.nextInt());
                }
            }
            c = new int[n][n];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    c[i][j] = scanner.nextInt();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
