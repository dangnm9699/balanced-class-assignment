import com.google.ortools.linearsolver.MPConstraint;
import com.google.ortools.linearsolver.MPObjective;
import com.google.ortools.linearsolver.MPSolver;
import com.google.ortools.linearsolver.MPVariable;

import java.io.FileWriter;
import java.io.IOException;

public class LinearSolver extends Solver {

    // mp
    MPSolver mpSolver;
    MPVariable[][] X;
    MPObjective y;

    void solve() {
        start = System.currentTimeMillis();
        // define solver
        mpSolver = MPSolver.createSolver("SCIP");
        mpSolver.setTimeLimit(9200);

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
                for (int k = 0; k < m; k++) {
                    if (c[i][j] == 1 && j != i) {
                        MPConstraint constraint = mpSolver.makeConstraint(0, 1);
                        constraint.setCoefficient(X[i][k], c[i][j]);
                        constraint.setCoefficient(X[j][k], c[i][j]);
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
        y = mpSolver.objective();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                y.setCoefficient(X[i][j], 1);
            }
        }
        y.setMaximization();


        final MPSolver.ResultStatus resultStatus = mpSolver.solve();
        if (resultStatus == MPSolver.ResultStatus.OPTIMAL) {
            System.out.println("Optimal solution found");
        } else {
            System.err.println("Optimal solution not found");
        }
        printSolution();
    }

    void printSolution() {
        // create/open file
        FileWriter writer = Solver.getWriter(filename);
        StringBuilder fileContent = new StringBuilder();
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
            fileContent.append(i).append(" ").append(lecturer).append("\n");
        }
        fileContent.append((int) y.value()).append("\n");
        System.out.println(fileContent);
        try {
            writer.write(fileContent.toString());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        for (int j = 0; j < m; j++) {
            double total = 0;
            for (int i = 0; i < n; i++) {
                if (X[i][j].solutionValue() == 1) total += d[i];
            }
            if (total > t[j]) System.err.printf("Teacher %d: Violation of Constraints\n", j);
        }
    }
}
