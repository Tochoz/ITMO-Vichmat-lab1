import java.io.FileNotFoundException;

public class SLAE_GAUSS {
    private double[][] a; // Двумерный массив коэффициентов и
    private int num; // Количество уравнений в системе
    private double[] ans; // Массив решений системы, если система не была решена, содержит null


    // Конструктор по умолчанию, инициализация переменных класса нулями
    public SLAE_GAUSS(){
        num = 0;
        a = new double[0][0];
        ans = new double[0];
    }

    // Метод выделяющий память под заданное количество строк
    private void create(int num){
        // Выделение памяти для массива num указателей
        // Для каждого из num массивов выделение памяти под num+1 элементов
        // Инициализация массива решений
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
    }

    // Метод меняет две указанные строки в матрице местами
    private void swapRows(int i, int j){
        /*
        Создание переменной для временного хранения указаьеля на i массив
        Замена i ссылка на j ссылку
        Запись в j ссылку ссылки из временной переменной
         */
    }

    // Метод проверяет является первый аргумент по модулю больше второго и выводит true или false
    private static boolean absMore(double a, double b){
        return false;
    }

    // Метод ищент наибольшее по модулю ненулевое число в конкретним столбще ниже конкретного элемента, включая его
    private int findMaxNotZero(int start, int col){
        /*
        запись в переменную max a[start][col]
        перебор строк от start+1 до num
            если мах по модулю меньше a[i][col], замена его на новый
        вывод max
         */
        return 0;
    }

    // Метод преобразует матрицу к треугольному виду, если система вырожденная выбрасывает исключение
    public void makeTriangle() throws SLAEException{
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

        */
    }
    // Метод вычисляет решение системы, если она до этого была приведена к треугольному виду    (НАДО ЛИ ПРОВЕРЯТЬ)
    public void computeAnss() throws SLAEException{
        /*
        Проверить я
        Для последней строки
        если a[num][num] = 0:
            если a[num][num+1] = 0:
                выбросить исключение с сообщением "Бесконечно много решений"
            иначе:
                выбросить исключение с сообщением "Решений нет"

        записать ans[num] как a[num][num+1]/a[num][num]

        Вычислить решения дя всех неизвестных с конца
        для j от num-1 до 0
            ans[j] = a[j][num+1]
            для i от num до j
                ans[j] -= a[j][i]*ans[i]
            ans/=a[j][j]

         */

    }

    // Метод вывода двумерного массива коэффициентов системы в экспонениальном виде
    public void print(){
        /*
        Для каждого элемента каждой строки двумерного массива a[][] выполнять
		Форматированный вывод значения в экпоненциальном виде
        */
    }

    // Метод получения количества уравнений(неизвестных системы)
    public int getNum() {
        return num;
    }

    // Метод получения массива решений системы
    public double[] getAns() {
        return ans;
    }
}