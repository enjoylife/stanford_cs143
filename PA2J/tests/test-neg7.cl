(* Two consecutive errors pair to be reported once when the first error is not followed by atleast
   3 valid tokens *)

class A {
    title <- String; (* error: invalid feature. Expects type declaration *)
    author <<- String; (* error: ignored error due to "3 valid token" problem *)
    i : Int;
    initBook (title_p : Int, author_p : String) : Book {
            author <<- author_p; (* error: invalid assignment *)
            author <<- author_s; (* error: ignored error due to "3 valid token" problem *)
          }
};
};

Class BB__ inherits A {
};

(* Expected Output:
    "tests/test-neg7.cl", line 5: parse error at or near ASSIGN
    "tests/test-neg7.cl", line 9: parse error at or near ASSIGN *)
