(*  ADAPTED FROM EXAMPLE FILES
    Tests:
    1. Class definitions with single feature and multiple features (including attributes(with and without initialization) and methods)
    2. Class definition with and without inheritance
    3. Features with formal list
    4. Some arithmetic operations
    5. Implicitly testing blocks
    6. Assignment expression
*)
class A {
    method5 (formal1 : Int): Int {
    formal1
    };
};

(* Examples for feature and formal rules *)
class BB__ inherits A {
    feature1 : String;
    (* ID : TYPE [ <- expr ] *)
    feature2 : Int <- 3;

    (* ID( [ formal [[, formal]]∗] ) : TYPE { expr } *)
    feature3(formal1 : Int) : Int{
        formal1 <- 1 + 2
    };

    feature4 () : Int {
        feature2 <- 1 + 2
    };
};

class Main {
    main() : Int {
        3
    };
};



