package modeling;

import com.google.ortools.Loader;
import com.google.ortools.linearsolver.MPConstraint;
import com.google.ortools.linearsolver.MPObjective;
import com.google.ortools.linearsolver.MPSolver;
import com.google.ortools.linearsolver.MPVariable;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;

public class CVRP {

    // data
    int N;
    int K;
    int[] r;
    int[] c;
    int[][] d;

    // solver
    MPSolver mpSolver;
    MPVariable[][][] X;
    MPVariable[][] Y;
    MPVariable[] I;
    MPObjective objective;

    // inf
    double pInf = Double.POSITIVE_INFINITY;
    double M = 10000007;

    public static void main(String[] args) {
        Loader.loadNativeLibraries();
        CVRP cvrp = new CVRP();
        cvrp.readData();
        cvrp.solve();
    }

    void readData() {
        Scanner scanner;
        try {
            scanner = new Scanner(new File(Objects.requireNonNull(getClass().getResource("/CVRP.txt")).getFile()));
            N = scanner.nextInt();
            K = scanner.nextInt();
            r = new int[2 * N + K];
            c = new int[K];
            d = new int[N + 2 * K][N + 2 * K];
            for (int i = 0; i < N; i++) {
                r[i] = scanner.nextInt();
            }
            for (int i = 0; i < K; i++) {
                c[i] = scanner.nextInt();
            }
            for (int i = 0; i < N + 2 * K; i++) {
                for (int j = 0; j < N + 2 * K; j++) {
                    d[i][j] = scanner.nextInt();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    void solve() {
        // define solver
        // CBC: COIN-OR Branch-and-Cut
        // CLP: COIN-OR Linear Programming
        // GLPK: GNU Linear Programming Kit
        // GLOP: Google's Linear Optimization Programming
        // SCIP: Solving Constraint Integer Programs (3rd party)
        mpSolver = MPSolver.createSolver("SCIP");

        // define variables
        X = new MPVariable[K][N + 2 * K][N + 2 * K];
        Y = new MPVariable[K][N + 2 * K];
        I = new MPVariable[N + 2 * K];

        for (int k = 0; k < K; k++) {
            for (int i = 0; i < N + 2 * K && i != K + N + k; i++) {
                for (int j = 0; j < N + 2 * K && j != k + N && j != i; j++) {
                    X[k][i][j] = mpSolver.makeIntVar(0, 1, "X[" + k + "][" + i + "][" + j + "]");
                }
            }
        }

        for (int k = 0; k < K; k++) {
            for (int i = 0; i < N + 2 * K; i++) {
                Y[k][i] = mpSolver.makeIntVar(0, pInf, "Y[" + k + "][" + i + "]");
            }
        }

        for (int i = 0; i < N + 2 * K; i++) {
            I[i] = mpSolver.makeIntVar(0, K - 1, "I[" + i + "]");
        }

        // define constraints
        // constraint 1
        for (int i = 0; i < N; i++) {
            MPConstraint constraint = mpSolver.makeConstraint(1, 1);
            for (int k = 0; k < K; k++) {
                for (int j = 0; j < N + 2 * K && j != k + N && j != i; j++) {
                    constraint.setCoefficient(X[k][i][j], 1);
                }
            }
        }
        for (int i = 0; i < N; i++) {
            MPConstraint constraint = mpSolver.makeConstraint(1, 1);
            for (int k = 0; k < K; k++) {
                for (int j = 0; j < N + 2 * K && j != k + K + N && j != i; j++) {
                    constraint.setCoefficient(X[k][j][i], 1);
                }
            }
        }
        // constraint 2
        for (int k = 0; k < K; k++) {
            for (int i = 0; i < N; i++) {
                MPConstraint constraint = mpSolver.makeConstraint(0, 0);
                for (int j = 0; j < N + 2 * K && j != N + k && j != i; j++) {
                    constraint.setCoefficient(X[k][i][j], 1);
                }
                for (int j = 0; j < N + 2 * K && j != N + K + k && j != i; j++) {
                    constraint.setCoefficient(X[k][j][i], -1);
                }
            }
        }
        // constraint 3
        for (int k = 0; k < K; k++) {
            MPConstraint constraint1 = mpSolver.makeConstraint(1, 1);
            MPConstraint constraint2 = mpSolver.makeConstraint(1, 1);
            for (int j = 0; j < N; j++) {
                constraint1.setCoefficient(X[k][k + N][j], 1);
                constraint2.setCoefficient(X[k][j][k + K + N], 1);
            }
        }
        // constraint 4
        for (int k = 0; k < K; k++) {
            for (int i = 0; i < N + 2 * K && i != k + K + N; i++) {
                for (int j = 0; j < N + 2 * K && j != k + N && j != i; j++) {
                    MPConstraint constraint1 = mpSolver.makeConstraint(-M, pInf);
                    constraint1.setCoefficient(I[i], 1);
                    constraint1.setCoefficient(I[j], -1);
                    constraint1.setCoefficient(X[k][i][j], -M);
                    MPConstraint constraint2 = mpSolver.makeConstraint(-M, pInf);
                    constraint2.setCoefficient(I[i], -1);
                    constraint2.setCoefficient(I[j], 1);
                    constraint2.setCoefficient(X[k][i][j], -M);
                }
            }
        }
        // constraint 5
        for (int k = 0; k < K; k++) {
            for (int i = 0; i < N + 2 * K && i != k + K + N; i++) {
                for (int j = 0; j < N + 2 * K && j != k + N && j != i; j++) {
                    MPConstraint constraint1 = mpSolver.makeConstraint(-M - r[j], pInf);
                    constraint1.setCoefficient(Y[k][i], 1);
                    constraint1.setCoefficient(Y[k][j], -1);
                    constraint1.setCoefficient(X[k][i][j], -M);
                    MPConstraint constraint2 = mpSolver.makeConstraint(-M + r[j], pInf);
                    constraint2.setCoefficient(Y[k][i], -1);
                    constraint2.setCoefficient(Y[k][j], 1);
                    constraint2.setCoefficient(X[k][i][j], -M);
                }
            }
        }
        // constraint 6
        for (int k = 0; k < K; k++) {
            MPConstraint constraint = mpSolver.makeConstraint(0, c[k]);
            constraint.setCoefficient(Y[k][k + K + N], 1);
        }
        // constraint 7
        for (int k = 0; k < K; k++) {
            MPConstraint constraint = mpSolver.makeConstraint(0, 0);
            constraint.setCoefficient(Y[k][k + N], 1);
        }
        // constraint 8
        for (int k = 0; k < K; k++) {
            MPConstraint constraint1 = mpSolver.makeConstraint(k, k);
            constraint1.setCoefficient(I[k + N], 1);

            MPConstraint constraint2 = mpSolver.makeConstraint(k, k);
            constraint2.setCoefficient(I[k + K + N], 1);
        }
        // define objective
        objective = mpSolver.objective();
        for (int k = 0; k < K; k++) {
            for (int i = 0; i < N + 2 * K && i != k + K + N; i++) {
                for (int j = 0; j < N + 2 * K && j != k + N && j != i; j++) {
                    objective.setCoefficient(X[k][i][j], d[i][j]);
                }
            }
        }
        objective.setMinimization();
        //solve
        MPSolver.ResultStatus status = mpSolver.solve();

        if (status == MPSolver.ResultStatus.OPTIMAL) {
            System.out.println(objective.value());
        }
        System.out.println("Done");
    }
}
