import java.io.FileNotFoundException;
import java.io.File;
import java.util.Scanner;
import java.util.regex.*;

public class SLAE_GAUSS {
    private double[][] a; // ��������� ������ ������������� �
    private int num; // ���������� ��������� � �������
    private double[] ans; // ������ ������� �������, ���� ������� �� ���� ������, �������� null

    // ����������� �� ���������, ������������� ���������� ������ ������
    public SLAE_GAUSS(){
        num = 0;
        a = new double[0][0];
        ans = null;
    }

    // ����� ���������� ������ ��� �������� ���������� �����
    private void create(int num){
        this.num = num;
        a = new double[num][num + 1];
        // ��������� ������ ��� ������� num ����������
        // ��� ������� �� num �������� ��������� ������ ��� num+1 ���������
    }

    // ������������� ������� ������� �� �����, �� ���� ��� ����� ����������� ���������� ���� ����� ���� �� ������
    public void init(String s) throws FileNotFoundException{
        /*
        �������� �������� File, Scanner � Pattern
        ������������� ����������� ��������� � Pattern

        1. ������ ������ ������ ����������� ��������� � �������
            ���������� ����� � ���������� � ���������� n
        2. ��������� ������ ��� n ��������� ������� create
        3. ���������� ������������ n ����� � n+1 ������� � ������ � a[][]
        4. �������� �������
        */
        File file = new File(s);
        Scanner scan = new Scanner(file);
        Pattern pat = Pattern.compile("[\\s\\t]+");
        String str = scan.nextLine();
        String [] sn = pat.split(str.trim());
        num = Integer.parseInt(sn[0]);
        create(num);
        int i, j;
        for (i=0; i<num; i++){
            str = scan.nextLine();
            sn = pat.split(str.trim());
            for (j=0; j <= num; j++)
                a[i][j] = Double.parseDouble(sn[j]);
        }
        scan.close();
    }

    // ����� ������ ��� ��������� ������ � ������� �������
    private void swapRows(int i, int j){
        /*
        �������� ���������� ��� ���������� �������� ��������� �� i ������
        ������ i ������ �� j ������
        ������ � j ������ ������ �� ��������� ����������
         */
        double [] temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    private static boolean isZero(double a){
        return Math.abs(a) < 0.0001;
    }


    // ����� ����� ���������� �� ������ ��������� ����� � ���������� ������� ���� ����������� ��������
    private int findMaxNotZero(int col){
        /*
        ������ � ���������� max a[start][col]
        ������� ����� �� start+1 �� num
            ���� ��� �� ������ ������ a[i][col], ������ ��� �� �����
        ����� max
         */
        for (int i=col+1; i< num; i++)
            if (!isZero(a[i][col]))
                return i;
        return 0;
    }

    // ������ ������� �������� �� ������� ��������
    private void calcStr(int i, int j){
        double m_i = a[j][i] / a[i][i];
        a[j][i] = 0;
        for (int col = i+1; col <= num; col++) {
            a[j][col] -= m_i * a[i][col];

            if (isZero(a[j][col]))
                a[j][col] = 0;
        }
    }

    // ����� ����������� ������� � ������������ ����, ���� ������� ����������� ����������� ����������
    // ���� ������ ��������� 0, ����� �� ������ 1, ����� ��������� 2, ���� �� �� �� 3
    public int makeTriangle() {
        /*
        ��� ���� ����� �� 1 �� num-1:
            k=0
            ���� ����� a_-_k ������������ �� ������ ��������� �����������
            ���� �������� �������� ���, ����������� ���������� � ���������� "������� �����������"
            ������������ �� ������� k

            ��� ������ �� num-k ��������� ����� k �����:
                ��������� m_i
                �������� �� ������ ������ ������ k ���������� �� m_i
            k++

        ��� ��������� ������
        ���� a[num][num] = 0:
            ���� a[num][num+1] = 0:
                ��������� ���������� � ���������� "���������� ����� �������"
            �����:
                ��������� ���������� � ���������� "������� ���"
        */
        int sec;
        for (int i=0; i<num; i++){
            if (isZero(a[i][i])) {
                sec = findMaxNotZero(i); // ���������� �������� ����� ����� ������ � ������� ��������
                if (sec != 0)
                    swapRows(i, sec);
                else
                    return 0;
            }
            for (int j=i+1; j<num; j++)
                calcStr(i, j);
        }
        if (isZero(a[num-1][num-1])){
            if (isZero(a[num-1][num]))
                return 2;
            return 1;
        }
        return 3;
    }
    // ����� ��������� ������� �������, ���� ��� �� ����� ���� ��������� � ������������ ����    (���� �� ���������)
    public double[] computeAnss(int input){
        // ���������� ��������� ���������� � ������������ ����
        switch (input){
            case 0:
                System.out.println("������� �����������");
                return null;
            case 1:
                System.out.println("������� �� ����� �������");
                return null;
            case 2:
                System.out.println("������� ����� ���������� ����� �������");
                return null;
        }
        /*


        �������� ans[num] ��� a[num][num+1]/a[num][num]

        ��������� ������� �� ���� ����������� � �����
        ��� i �� num-1 �� 0
            ans[i] = a[i][num+1]
            ��� j �� num �� i
                ans[i] -= a[i][j]*ans[j]
            ans /= a[i][i]

         */
        // ������� ����� �������� �����������
        reverseCompute();
        return ans;
    }

    // ����� �������� �������� �����������
    private void reverseCompute(){
        ans = new double[num];
        ans[num-1] = a[num-1][num]/a[num-1][num-1];
        // ��������� ������� ��� ���� ����������� � �����
        for (int i=num-2; i >= 0; i--){
            ans[i] = a[i][num];
            for (int j=num-1; j>i; j--)
                ans[i] -= a[i][j]*ans[j];
            ans[i] /= a[i][i];
        }
    }

    // ����� ������ ���������� ������� ������������� ������� � ��������������� ����
    public void print(){
        /*
        ��� ������� �������� ������ ������ ���������� ������� a[][] ���������
		��������������� ����� �������� � ��������������� ����
        */
        int i, j;
        for (i=0; i<num; i++){
            for (j=0; j<num+1; j++)
                System.out.printf("%15.6E",a[i][j]);
            System.out.println();
        }
    }

    // ����� ��������� ���������� ���������(����������� �������)
    public int getNum() {
        return num;
    }

    // ����� ��������� ������� ������� �������
    public double[] getAns() {
        return ans;
    }

    public void printAns(){
        for (int i=0; i<num; i++)
            System.out.printf("%15.6E",ans[i]);
        System.out.println();
    }
}
