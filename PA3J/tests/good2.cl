class Silly {
    copy() : SELF_TYPE { self };
    add(a: Int, b: Int): Int { a + b };
};

class Sally inherits Silly { };

class Main {
    x : Sally <- (new Sally).copy();
    y : Silly;
    a : Int;
    b : Int;

    main() : Sally { {
        y.add(a, b);
        y@Silly.add(a, b);
        x;
    } };
};