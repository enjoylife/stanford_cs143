(*  ADAPTED FROM EXAMPLE FILES
    Tests:
    1. Block Expressions
    2. Nested Let expressions
    3. Expressions in parentheses
    4. While loop
    5. method dispatching (line 26)
*)
class CC__ {

    feature6() : Int {
        4
    };

    feature8() : Int {
                    (* let ID : TYPE [ <- expr ] [[,ID : TYPE [ <- expr ]]]∗ in expr *)
                    (let int : Int <- 0 in
                       {
                           (let j : Int <- 1 in
                              (let i : Int <- 0 in
                              (* while expr loop expr pool *)
                                while i < j loop
                                    {
                                        (* ID( [ expr [[,expr]]∗] ) *)

                                        int <- int * 10 + feature6();
                                        i <- i + 1;
                                    }
                                pool
                              )
                           );
                          int;
                        }
                    )
    };
};

class Main {
    main() : Int {
        3
    };
};