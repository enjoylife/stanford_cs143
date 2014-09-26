(* Paser recovers from errors in features *)
class BB__ inherits A {

    feature1 : Int <- "blah";
    feature2 : Int;
    feature5 : Int;
    feature3 (formal1 : Int) { (* error: invalid feature. Expects return type declaration *)
        formal1 <- 1 + 2
    };

    feature4 () { (* error: invalid feature. Expects return type declaration *)
        feature <- 1 + 2
    };
};

(* Output: "tests/test-neg3.cl", line 7: parse error at or near '{'
           "tests/test-neg3.cl", line 11: parse error at or near '{' *)