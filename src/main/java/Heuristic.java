import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

public class Heuristic extends Solver {

    int[] X_best;
    int res_best;

    // param
    double alpha = 0.3;

    // random
    Random random = new Random();

    void solve() {
        X_best = new int[n];

        // init best
        res_best = 0;

        // find better feasible solution
        for (int it = 0; it < 3000; it++) {
            iterator();
        }

        // print and check violations
        double[] total = new double[m];
        ArrayList<Integer>[] take = new ArrayList[m];
        for (int i = 0; i < m; i++) {
            take[i] = new ArrayList<>();
        }
        FileWriter writer = Solver.getWriter(filename);
        StringBuilder fileContent = new StringBuilder();
        for (int i = 0; i < n; i++) {
            if (X_best[i] != -1) {
                if (!D[i].contains(X_best[i])) System.err.println("Error Not Allowed");
                for (int taken : take[X_best[i]])
                    if (c[taken][i] == 1) System.err.println("Error Conflict");
                total[X_best[i]] += d[i];
                take[X_best[i]].add(i);
            }
            fileContent.append(i).append(" ").append(X_best[i]).append("\n");
        }
        for (int i = 0; i < m; i++) {
            if (total[i] > t[i]) System.err.println("Error Time");
        }
        fileContent.append(res_best).append("\n");
        System.out.println(fileContent);
        try {
            writer.write(fileContent.toString());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    void iterator() {
        int[] X = new int[n];
        int res = 0;
        double[] load = new double[m];
        ArrayList<Integer>[] scheduled = new ArrayList[m];
        for (int i = 0; i < m; i++) {
            scheduled[i] = new ArrayList<>();
        }
        for (int i = 0; i < n; i++) {
            double total = 0;
            ArrayList<Candidate> candidates = new ArrayList<>();
            for (int j : D[i]) {
                if (t[j] - load[j] < d[i]) {
                    candidates.add(new Candidate(j, 0));
                    continue;
                }
                int conflict = 0;
                for (int k : scheduled[j]) {
                    if (c[k][i] == 1) {
                        conflict = 1;
                        break;
                    }
                }
                double eval = (t[j] - load[j]) * Math.pow(20, X_best[i] == j ? 1.0 / alpha : 1.0) * (1 - conflict);
                total += eval;
                candidates.add(new Candidate(j, eval));
            }
            double eval = Math.pow(20, X_best[i] == -1 ? alpha : 1.0);
            total += eval;
            candidates.add(new Candidate(-1, eval));
            candidates.sort(Candidate.compareEval());
            double prob = 0;
            for (Candidate candidate : candidates) {
                prob += candidate.eval;
                candidate.eval = prob / total;
            }
            double rand = random.nextDouble();
            int chosen = -1;
            for (Candidate candidate : candidates) {
                if (rand < candidate.eval) {
                    chosen = candidate.index;
                    break;
                }
            }
            X[i] = chosen;
            if (chosen > -1) {
                load[chosen] += d[i];
                scheduled[chosen].add(i);
            }
        }
        for (int i = 0; i < n; i++) {
            if (X[i] != -1) {
                res += 1;
            }
        }
        if (res > res_best) {
            res_best = res;
            System.err.println("Update " + res_best);
            System.arraycopy(X, 0, X_best, 0, n);
        }
    }

    static class Candidate {
        int index;
        double eval;

        Candidate(int _index, double _eval) {
            index = _index;
            eval = _eval;
        }

        public static Comparator<Candidate> compareEval() {
            return (o1, o2) -> Double.compare(o2.eval, o1.eval);
        }
    }

}
