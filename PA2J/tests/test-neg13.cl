(* Paser recovers from typo in class (with inheritance) *)
clss BB__ inherits A { (* error: typo in class *)

    feature1 : Int <- 4;
    feature2 : Int;
    feature5 : Int;
    feature3 (formal1 : Int) {
        formal1 <- 1 + 2
    };

    feature4 () {
        feature <- 1 + 2
    };
};

class AA {
blah blah blah (* error: invalid feature *)
};

(* Output: "tests/test-neg13.cl", line 2: syntax error at or near OBJECTID = clss
           "tests/test-neg13.cl", line 17: syntax error at or near OBJECTID = blah *)