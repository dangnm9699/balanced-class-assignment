import com.google.ortools.Loader;
import com.google.ortools.linearsolver.MPConstraint;
import com.google.ortools.linearsolver.MPObjective;
import com.google.ortools.linearsolver.MPSolver;
import com.google.ortools.linearsolver.MPVariable;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class BCA {

    // data
    int n;
    int m;
    double[] d;
    double[] t;
    ArrayList<Integer>[] D;
    int[][] c;

    // mp
    MPSolver mpSolver;
    MPVariable[][] X;
    MPObjective objective;

    // runtime
    long start;

    // data filename
    String filename = "500.txt";

    public static void main(String[] args) {
        Loader.loadNativeLibraries();
        BCA bca = new BCA();
        bca.readData();
        bca.solve();
    }

    void solve() {
        start = System.currentTimeMillis();

        // define solver
        mpSolver = MPSolver.createSolver("SCIP");
//        mpSolver.setTimeLimit(12 * 1000);

        // define variables
        X = new MPVariable[n][m];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                X[i][j] = mpSolver.makeIntVar(0, 1, "X[" + i + "][" + j + "]");
            }
        }

        // define constraints
        // constraint 1: Chỉ giảng viên trong danh sách mới có thể giảng dạy cho lớp đó
        for (int i = 0; i < n; i++) {
            MPConstraint constraint = mpSolver.makeConstraint(0, 0);
            for (int j = 0; j < m; j++) {
                if (!D[i].contains(j)) constraint.setCoefficient(X[i][j], 1);
            }
        }
        // constraint 2: Mỗi lớp chỉ được phân công nhiều nhất một giảng viên trong danh sách
        for (int i = 0; i < n; i++) {
            MPConstraint constraint = mpSolver.makeConstraint(0, 1);
            for (int j = 0; j < m; j++) {
                if (D[i].contains(j)) constraint.setCoefficient(X[i][j], 1);
            }
        }
        // constraint 3: Nếu 2 lớp học trùng TKB thì không thể do cùng một giảng viên giảng dạy
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (c[i][j] == 1) {
                    for (int k = 0; k < m; k++) {
                        MPConstraint constraint = mpSolver.makeConstraint(0, 1 + ((i == j) ? 1 : 0));
                        constraint.setCoefficient(X[i][k], 1);
                        constraint.setCoefficient(X[j][k], 1);
                    }
                }
            }
        }
        // constraint 4: Tổng thời lượng giảng dạy của giảng viên
        for (int i = 0; i < m; i++) {
            MPConstraint constraint = mpSolver.makeConstraint(0, t[i]);
            for (int j = 0; j < n; j++) {
                constraint.setCoefficient(X[j][i], d[j]);
            }
        }

        // define objective
        objective = mpSolver.objective();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                objective.setCoefficient(X[i][j], 1);
            }
        }
        objective.setMaximization();

        final MPSolver.ResultStatus resultStatus = mpSolver.solve();

        if (resultStatus == MPSolver.ResultStatus.OPTIMAL) {
//            // create/open file
//            File output = new File(filename.split("\\.")[0] + "-out.txt");
//            FileWriter writer;
//            try {
//                boolean created = output.createNewFile();
//                wr
//            } catch (IOException e) {
//                e.printStackTrace();
//                System.exit(1);
//            }
            printSolution();
        } else {
            System.err.println("The problem does not have optimal solution");
            printSolution();
        }
    }

    void printSolution() {
        for (int i = 0; i < n; i++) {
            int lecturer = -1;
            for (int j = 0; j < m; j++) {
                if (X[i][j].solutionValue() == 1) {
//                        lecturer = j;
                    if (!D[i].contains(j))
                        System.err.println("Violation of Constraints");
                    if (lecturer < 0) lecturer = j;
                    else System.err.println("Violation of Constraints");
                }
            }
            System.out.printf("%d %d\n", i, lecturer);
        }
        System.out.println((int) objective.value());
        for (int j = 0; j < m; j++) {
            double total = 0;
            for (int i = 0; i < n; i++) {
                if (X[i][j].solutionValue() == 1) total += d[i];
            }
            if (total > t[j]) System.err.printf("Teacher %d: Violation of Constraints\n", j);
        }
        System.out.printf("Runtime = %d (ms)\n", System.currentTimeMillis() - start);
    }

    void readData() {
        Scanner scanner;
        try {
            scanner = new Scanner(new File(Objects.requireNonNull(getClass().getResource("/input/" + filename)).getFile()));
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
