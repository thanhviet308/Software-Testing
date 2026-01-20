import java.util.List;

public class Bai2 {
    private int n;          // bậc của đa thức
    private List<Integer> a; // danh sách hệ số a0..an

    public Bai2(int n, List<Integer> a) {
        // nếu n âm hoặc không đủ n+1 hệ số -> ném ngoại lệ
        if (n < 0 || a.size() != n + 1) {
            throw new IllegalArgumentException("Invalid Data");
        }
        this.n = n;
        this.a = a;
    }

    // tính giá trị đa thức tại x
    public int Cal(double x) {
        int result = 0;
        for (int i = 0; i <= this.n; i++) {  // từ a0 tới an
            result += (int) (a.get(i) * Math.pow(x, i));
        }
        return result;
    }
}
