(*  ADAPTED FROM EXAMPLE FILES
    Tests:
    1. Block expressions
    2. Assignment expression
    3. if else then expression
    4. accessing methods of parent classes that have been hidden by redefinitions in child classes (@TYPE)
*)
class A {
    method5 (formal1 : Int): Int {
    formal1
    };
};

(* Examples for feature and formal rules *)
class BB__ inherits A {
    feature1 : String;
};

class CC__ {

    feature1 : Int;
    feature2 : Int;
    var : A;

    feature6 () : Int {
    (* { [[expr; ]]+} *)
    {
        (*  ID <- expr *)
        feature1 <- 3;

        (* expr[@TYPE].ID( [ expr [[,expr]]∗] ) *)
        (* if expr then expr else expr ﬁ *)
        if feature1 = 3 then feature2 <- (new BB__)@A.method5(feature1) else feature2 <- 5 fi;
    }
    };
};

class Main {
    main() : Int {
        3
    };
};