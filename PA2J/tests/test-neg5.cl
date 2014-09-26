(* Parser recovers from error in a properly terminated feature *)

class A {
    title : String;
    author : Int = 123; (* error: Invalid assignment. Expects <- *)
    i : Int;

    initBook (title_p : Int, author_p : String) : Book {
	(let titlenew:String,
	inde <- Int (* error: Invalid let binding. Expects type declaration *)
        in {
            author <- author_p
            author <<- author_s; (* error: Invalid assignment *)
          }
	)
};
};

Class BB__ inherits A {
};

(* Output:
    "tests/test-neg5.cl", line 5: parse error at or near '='
    "tests/test-neg5.cl", line 10: parse error at or near ASSIGN
    "tests/test-neg5.cl", line 13: parse error at or near OBJECTID = author *)
