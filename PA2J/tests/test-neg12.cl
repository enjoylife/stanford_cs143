(* Paser recovers from typo in inherits *)
class BB__ inheits A { (* error: typo in inherits *)

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
    blah blah blah (* error: not a valid feature *)
};

(* Output: "tests/test-neg12.cl", line 2: parse error at or near OBJECTID = inheits
           "tests/test-neg12.cl", line 17: parse error at or near OBJECTID = blah *)