(* Parser recovers from typo in "let" *)

class A {
    title : String;
    author : String;
    i : Int;
    initBook (title_p : Int, author_p : String) : Book {
	(lt titlenew: String (* error: typo in let *)
        in {
            author <<- author_p; (* Ignores the entire let expression hence does not parse these errors *)
            author <<- author_s;
          }
	)
};
};

Class BB__ inherits A {
 blah blah (* invalid feature *)
};

(* Output:
    "tests/test-neg15.cl", line 8: parse error at or near OBJECTID = titlenew
    "tests/test-neg15.cl", line 18: parse error at or near OBJECTID = blah *)
