// Класс исключения. Используется в случае если СЛАУ является вырожденной
public class SLAEException extends RuntimeException{
    private String message; // Сообщение для пользователя

    // Конструктор, на вход получает сообщение
    public SLAEException(String msg) {
        message = msg;
    }

    // Геттер, возвращает сообщение
    public String getMessage() {
        return message;
    }

    // Метод, возвращающий строковое представление исключения
    public String toString() {
        return "MyException{" +
                "message=" + message +
                '}';
    }
}
