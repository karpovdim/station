package by.karpov.exeption;


import lombok.Getter;

@Getter
public class NotFoundException extends RuntimeException {
    private final int id;

    public NotFoundException(int id) {
        this.id = id;
    }
}

