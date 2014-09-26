(* Parser recovers from an error single let statement and moves on to the block. *)

class A {
    title : String;
    author : String;
    i : Int;
    initBook (title_p : Int, author_p : String) : Book {
	(let titlenew : String,
	inde <- Int (* error:  invalid assignment for let binding *)
        in {
            author <- author_p
            author <<- author_s; (* error: invalid assignment operator *)
          }
	)
} (* error:  missing ; *)
};

Class BB__ inherits A {
};

(* Output:
   "tests/test-neg1.cl", line 9: parse error at or near ASSIGN
   "tests/test-neg1.cl", line 12: parse error at or near OBJECTID = author
   "tests/test-neg1.cl", line 16: parse error at or near '}' *)
