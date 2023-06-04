import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) {
        try {
            SLAE_GAUSS system = new SLAE_GAUSS();

            system.init("data.txt");          // создание объекта системы
            system.print();                     // вывод системы в консоль
            System.out.println();
            int resultTriangle = system.makeTriangle(); // приведение к треугольному виду
            system.print();                     // вывод системы в треугольном виде
            System.out.println();
            double[] ans = system.computeAnss(resultTriangle);// решение системы
            if (ans != null) // вывод массива ответов системы
                system.printAns();

        } catch (FileNotFoundException e){
            System.out.println("File not found");
        }

    }
}