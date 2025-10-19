package lab10.task_1;

public class MyString {
    private final byte[] value;

    public MyString(String value) {
        this.value = value.getBytes();
    }

    @Override
    public String toString() {
        return new String(value);
    }
}
