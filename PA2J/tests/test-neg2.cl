(* Parser recovers from an error in a let statement that
 * expects multiple bindings but only gets 1. And moves
 *  on to parsing block of expressions
 *)

class A {
    title : String;
    author : String;
    i : Int;
    initBook (title_p : Int, author_p : String) : Book {
	(let titlenew: String, (* error: extra , here. expects another let binding *)
        in {
            author <<- author_p; (* error: invalid assignment *)
            author <<- author_s; (* error: invalid assignment *)
          }
	)
} (* error: missing ; *)
};

Class BB__ inherits A {
};

(* Output:
    "tests/test-neg2.cl", line 12: parse error at or near IN
    "tests/test-neg2.cl", line 13: parse error at or near ASSIGN
    "tests/test-neg2.cl", line 14: parse error at or near ASSIGN
    "tests/test-neg2.cl", line 18: parse error at or near '}' *)
