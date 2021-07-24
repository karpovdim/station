package by.karpov.exeption;

public class DbException extends RuntimeException {
    public DbException() {
//        super();
    }

    public DbException(Throwable throwable) {
        super(throwable);
    }
}
