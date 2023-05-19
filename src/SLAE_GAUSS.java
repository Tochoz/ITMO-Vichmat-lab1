import java.io.FileNotFoundException;
import java.io.File;
import java.util.Scanner;
import java.util.regex.*;

public class SLAE_GAUSS {
    private double[][] a; // Двумерный массив коэффициентов и
    private int num; // Количество уравнений в системе
    private double[] ans; // Массив решений системы, если система не была решена, содержит null

    // Конструктор по умолчанию, инициализация переменных класса нулями
    public SLAE_GAUSS(){
        num = 0;
        a = new double[0][0];
        ans = null;
    }

    // Метод выделяющий память под заданное количество строк
    private void create(int num){
        this.num = num;
        a = new double[num][num + 1];
        // Выделение памяти для массива num указателей
        // Для каждого из num массивов выделение памяти под num+1 элементов
    }

    // Инициализация системы чтением из файла, на вход имя файла выбрасывает исключение если такой файл не найден
    public void init(String s) throws FileNotFoundException{
        /*
        Создание объектов File, Scanner и Pattern
        Инициализация регулярного выражения в Pattern

        1. Чтение первой строки количеством уравнений в системе
            извлечение числа и присвоение в переменную n
        2. Выделение памяти под n уравнений вызовом create
        3. Построчное сканирование n строк с n+1 числами и запись в a[][]
        4. Закрытие сканера
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

    // Метод меняет две указанные строки в матрице местами
    private void swapRows(int i, int j){
        /*
        Создание переменной для временного хранения указаьеля на i массив
        Замена i ссылка на j ссылку
        Запись в j ссылку ссылки из временной переменной
         */
        double [] temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    private static boolean isZero(double a){
        return Math.abs(a) < 0.0001;
    }


    // Метод ищент наибольшее по модулю ненулевое число в конкретним столбще ниже конкретного элемента
    private int findMaxNotZero(int col){
        /*
        запись в переменную max a[start][col]
        перебор строк от start+1 до num
            если мах по модулю меньше a[i][col], замена его на новый
        вывод max
         */
        for (int i=col+1; i< num; i++)
            if (!isZero(a[i][col]))
                return i;
        return 0;
    }

    // Строка которую вычитаем из которой вычитаем
    private void calcStr(int i, int j){
        double m_i = a[j][i] / a[i][i];
        a[j][i] = 0;
        for (int col = i+1; col <= num; col++) {
            a[j][col] -= m_i * a[i][col];
            
            if (isZero(a[j][col]))
                a[j][col] = 0;
        }
    }

    // Метод преобразует матрицу к треугольному виду, если система вырожденная выбрасывает исключение
    // Если просто вырождена 0, имеет ни одного 1, имеет беконечно 2, если всё ок то 3
    public int makeTriangle() {
        /*
        Для всех строк от 1 до num-1:
            k=0
            ищем среди a_-_k максимальный по модулю ненулевой коэффициент
            если такового элемента нет, выбрасываем исключение с сообщением "Система вырожденная"
            переставляем со строкой k

            для каждой из num-k следующих после k строк:
                вычисляем m_i
                вычитаем из данной строки строку k умноженную на m_i
            k++

        Для последней строки
        если a[num][num] = 0:
            если a[num][num+1] = 0:
                выбросить исключение с сообщением "Бесконечно много решений"
            иначе:
                выбросить исключение с сообщением "Решений нет"
        */
        int sec;
        for (int i=0; i<num; i++){
            if (isZero(a[i][i])) {
                sec = findMaxNotZero(i); // Переменная хранящая номер новой строки с которой поменять
                if (sec != 0)
                    swapRows(i, sec);
                else
                    return 0;
            }
            for (int j=i+1; j<num; j++)
                calcStr(i, j);
        }

        // Проверить вырожденная ли система
        if (isZero(a[num-1][num])){
            if (isZero(a[num-1][num]))
                return 2;
            return 1;
        }
        return 3;
    }
    // Метод вычисляет решение системы, если она до этого была приведена к треугольному виду    (НАДО ЛИ ПРОВЕРЯТЬ)
    public double[] computeAnss(int input){ // TODO вывод ссылку на решения, в аргументы давать вывод triangle
        // Обработать результат приведения к треугольному виду
        switch (input){
            case 0:
                System.out.println("Система вырожденная");
                return null;
            case 1:
                System.out.println("Система не имеет решений");
                return null;
            case 2:
                System.out.println("Система имеет бесконечно много решений");
                return null;
        }
        /*


        записать ans[num] как a[num][num+1]/a[num][num]

        Вычислить решения для всех неизвестных с конца
        для i от num-1 до 0
            ans[i] = a[i][num+1]
            для j от num до i
                ans[i] -= a[i][j]*ans[j]
            ans /= a[i][i]

         */
        // Вызвать метод обратной подстановки
        reverseCompute();
        return ans;
    }

    // Метод выполнет обратную подстановку
    private void reverseCompute(){
        ans = new double[num];
        ans[num-1] = a[num-1][num]/a[num-1][num-1];
        // Вычислить решения для всех неизвестных с конца
        for (int i=num-2; i >= 0; i--){
            ans[i] = a[i][num];
            for (int j=num-1; j>i; j--)
                ans[i] -= a[i][j]*ans[j];
            ans[i] /= a[i][i];
        }
    }

    // Метод вывода двумерного массива коэффициентов системы в экспонениальном виде
    public void print(){
        /*
        Для каждого элемента каждой строки двумерного массива a[][] выполнять
		Форматированный вывод значения в экпоненциальном виде
        */
        int i, j;
        for (i=0; i<num; i++){
            for (j=0; j<num+1; j++)
                System.out.printf("%15.6E",a[i][j]);
            System.out.println();
        }
    }

    // Метод получения количества уравнений(неизвестных системы)
    public int getNum() {
        return num;
    }

    // Метод получения массива решений системы
    public double[] getAns() {
        return ans;
    }



    public void printAns(){
        for (int i=0; i<num; i++)
            System.out.printf("%15.6E",ans[i]);
        System.out.println();
    }
}
