(* Parser recovers from an error in formal list by moving on to the next feature *)

class A {
    title : String;
    author : String;
    i : Int;
    initBook (title_p <- Int, author_p : String) : Book { (* error: Invalid formal. Expects type declaration *)
	(let titlenew: String,
        in {
            author <<- author_p; (* First error in the block - WILL NOT BE CAUGHT *)
            author <<- author_s; (* Second error in the block - WILL NOT BE CAUGHT *)
          }
	)
    };
    title4 : Int;
    title5 : String;
    title3 <- String; (* error: Invalid feature. Expects type declaration *)
};

(* Output: "tests/test-neg6.cl", line 7: parse error at or near ASSIGN
           "tests/test-neg6.cl", line 17: parse error at or near ASSIGN *)
