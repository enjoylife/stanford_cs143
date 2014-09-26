(* Parser recovers from an error in let binding and moves on to the next let binding. *)

class A {
    title : String;
    author : String;
    i : Int;
    initBook (title_p: Int, author_p: String) : Book {
	( let bad, (* error: Invalid let binding. Expects a type declaration *)
	titlenew:String,
	bad2, (* error: Invalid let binding. Expects a type declaration *)
	inde <- Int
        in {
            author <- author_p
            author <<- author_s; (* error: Invalid assignment *)
          }
	)
} (* error: Missing ; *)
};

Class BB__ inherits A {
};

(* Output:
   "tests/test-neg4.cl", line 8: parse error at or near ','
   "tests/test-neg4.cl", line 10: parse error at or near ','
   "tests/test-neg4.cl", line 14: parse error at or near OBJECTID = author
   "tests/test-neg4.cl", line 18: parse error at or near '}' *)
