package message;

@FunctionalInterface
public interface SelectionHandler {
    void handle(int index);
}
