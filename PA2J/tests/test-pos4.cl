(*  ADAPTED FROM EXAMPLE FILES - arith.cl
    Tests:
    1. Case expression
    2. Multiple formals in method.
*)

class A {
    method5 (formal1 : Int, formal2 : String): Int {
    formal1
    };
};

(* Examples for feature and formal rules *)
class BB__ inherits A {
    feature1 : String;
};

class CC__ inherits A {

    feature1 : Int;
    feature2 : A;

    feature9() : Int {
          case feature2 of
             a : BB__ => feature1 <- 3;
             b : CC__ => feature1 <- 4;
             c : A => feature1 <- 5;
             o : Object => feature1 <- 6;
          esac
    };
};

class Main {
    main() : Int {
        3
    };
};