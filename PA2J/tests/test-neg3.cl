(* Parser recovers from error in let statement and parses the next let statement. *)

class A {
    title : String;
    author : String;
    i : Int;
    initBook (title_p : Int, author_p : String) : Book {
	(let titlenew <- String, (* error: Invalid let binding. Expects a type declaration *)
        in (let titleIn <- String in { (* error:  Invalid let binding. Expects a type declaration *)
            author <- author_p;
            author <<- author_s; (* error: Invalid assignment *)
          }
	))
};
};

Class BB__ inherits A {
};

(* Output:
    "tests/test-neg3.cl", line 8: parse error at or near ASSIGN
    "tests/test-neg3.cl", line 9: parse error at or near ASSIGN
    "tests/test-neg3.cl", line 11: parse error at or near ASSIGN *)
