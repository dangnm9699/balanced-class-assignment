public class Backtracking extends Solver {

    double[] load;

    boolean[][] taken;

    int[] X;
    int[] X_best;
    // result
    int best = 0;

    /**
     * RB1: Mỗi lớp được phân tối đa 1 giáo viên trong danh sách
     * RB2: 2 lớp trùng TKB không thể được phân bởi cùng giáo viên
     * RB3: Tổng thời lượng của giáo viên không vượt quá thời lượng tối đa
     */
    void solve() {
        X = new int[n];
        X_best = new int[n];
        load = new double[m];
        //
        taken = new boolean[n][m];
        //
        find(0);
        System.out.println(best);
    }

    void find(int c) {
        if (c == n) {
            int temp = 0;
            for (int i = 0; i < n; i++) {
                if (X[i] > -1) temp++;
            }
            if (temp > best) {
                best = temp;
                System.arraycopy(X, 0, X_best, 0, n);
            }
            return;
        }
        for (int i = -1; i < m; i++) {
            if (i != -1) {
                if (check(c, i)) {
                    load[i] += d[c];
                    taken[c][i] = true;
                    X[c] = i;
                    find(c + 1);
                    taken[c][i] = false;
                    load[i] -= d[c];
                }
            } else {
                X[c] = -1;
                find(c+1);
            }
        }
    }

    boolean check(int lop, int gv) {
        if (!D[lop].contains(gv)) return false;
        for (int j = 0; j < n; j++) {
            if (j != lop && c[j][lop] == 1 && taken[j][gv]) return false;
        }
        return !(load[gv] + d[lop] > t[gv]);
    }
}
