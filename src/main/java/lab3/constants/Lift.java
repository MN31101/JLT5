package lab3.constants;

public enum Lift {
    TEN(10),
    TWENTY(20),
    FIFTY(50),
    HUNDRED(100),
    INFINITE(Integer.MAX_VALUE);

    private final int count;

    Lift(int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }
}
