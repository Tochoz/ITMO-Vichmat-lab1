import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) {
        try {
            SLAE_GAUSS system = new SLAE_GAUSS();

            system.init("data.txt");          // �������� ������� �������
            system.print();                     // ����� ������� � �������
            System.out.println();
            int resultTriangle = system.makeTriangle(); // ���������� � ������������ ����
            system.print();                     // ����� ������� � ����������� ����
            System.out.println();
            double[] ans = system.computeAnss(resultTriangle);// ������� �������
            if (ans != null) // ����� ������� ������� �������
                system.printAns();

        } catch (FileNotFoundException e){
            System.out.println("File not found");
        }

    }
}