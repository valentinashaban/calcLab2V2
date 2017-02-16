import java.util.ArrayList;

public class Poly {
    ArrayList<ArrayList<Double>> newtonsArrays;
    ArrayList<Double> x = new ArrayList<>();
    ArrayList<Double> y = new ArrayList<>();
    ArrayList<Double> z = new ArrayList<>();

    double func(double x) {
        double res = Math.cos(x) - Math.sin(x);
        return res;
    }

    double maxFPThree(double a, double b) {
        double res = Math.sin(a) + Math.cos(a);
        double res2 = Math.sin(b) + Math.cos(b);
        return Math.abs(res) > Math.abs(res2) ? res : res2;
    }

    double Rn(double z, double a, double b) {
        double res = maxFPThree(a, b) / (1 * 2 * 3) * (z - x.get(0)) * (z - x.get(1)) * (z - x.get(2));
        return res;
    }

    void initXY(double a, double b, double n) {
        double h = (b - a) / n;
        System.out.printf("h = %2.2f\n", h);
        for (int i = 0; i <= n; i++) {
            x.add(a + i * h);
            y.add((double) Math.round(func(x.get(i))));
            System.out.printf("x[%d] = %2.2f; y[%d] = %2.2f\n", i, x.get(i), i, y.get(i));
        }
    }

    void initZ(double m) {
        /*for (int i = 0; i < m; i++)
            z.add(x.get(i) * 1 / 2);*/
        z.add(Math.PI/4);
    }

    double Lagrange(double xx, double n) {
        double li;
        double result = 0.0;
        for (int i = 0; i < n; i++) {
            li = 1;
            for (int j = 0; j < n; j++)
                if (i != j) li *= (xx - x.get(j)) / (x.get(i) - x.get(j));
            result += li * y.get(i);
        }
        return result;
    }

    double Newton(double xx, double n) {
        newtonsArrays = new ArrayList<>((int)n);
        double result = y.get(0);
        double temp;

        for (int i = 0; i < newtonsArrays.size(); i++) {
            System.out.println("Inside Newton loop");
            if (i == 0) {
                for (int j = 1; j <= x.size(); j++) {
                    temp = (func(x.get(j)) - func(x.get(j - 1))) / (x.get(j) - x.get(j - 1));
                    newtonsArrays.get(i).add(temp);
                    System.out.println("temp = " + temp);
                }
            } else {
                for (int j = 1; j < newtonsArrays.get(i - 1).size(); j++) {
                    temp = (newtonsArrays.get(i - 1).get(j) - newtonsArrays.get(i - 1).get(j - 1)) / (x.get(j) - x.get(0));
                    newtonsArrays.get(i).add(temp);
                    System.out.println("temp = " + temp);
                }
            }
        }
        //result += newtonsArrays.get(0).get(0) * (xx - x.get(0));
        //result += newtonsArrays.get(1).get(0) * (xx - x.get(0) * (xx - x.get(1)));

        return result;
        /*double result = y.get(0);
        double F;
        double den;
        for (int i = 1; i < n; i++) {
            F = 0;

            //следующее слагаемое многочлена
            for (int j = 0; j <= i; j++) {
                den = 1;

                //знаменатель
                for (int k = 0; k <= i; k++)
                    if (k != j) den *= (x.get(j) - x.get(k));

                //разделенная разность
                F += y.get(j) / den;
            }

            //(x - x[0])...(x - x[i - 1])
            for (int k = 0; k < i; k++) F *= (xx - x.get(k));
            result += F;
        }
        return result;*/
    }

    void printRes() {
        System.out.print("\nxi || ");
        for (int i = 0; i < x.size(); i++)
            System.out.printf("%2.2f | ", x.get(i));
        System.out.print("\nyi || ");
        for (int i = 0; i < y.size(); i++)
            System.out.print(y.get(i) + " | ");
    }

    void printZ() {
        System.out.print("\nzi || ");
        for (int i = 0; i < z.size(); i++)
            System.out.printf("%2.2f | ", z.get(i));
    }

    void calcZFunc(double a, double b, double n) {
        System.out.println("\n\nj | zj | Ln(zj) | Nw(zj) | f(zj) | rn(zj)");
        for (int j = 0; j < z.size(); j++)
            System.out.printf("%d | %2.2f | %2.2f | %2.2f | %2.2f | %2.2f\n", j, z.get(j), Lagrange(z.get(j), n), Newton(z.get(j), n), func(z.get(j)), Math.abs(Rn(z.get(j), a, b)));
    }

    public static void main(String[] args) {
        double a = -Math.PI / 2;
        double b = Math.PI / 2;
        double n = 2.0;
        double m = 1.0;
        Poly p = new Poly();
        p.initXY(a, b, n);
        p.initZ(m);
        p.printZ();
        p.printRes();
        p.calcZFunc(a, b, n);
    }
}