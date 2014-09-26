(* Parser reports non associative error *)
class A {
    a_test() : SELF_TYPE {
	    {
	        title <- 10;
            if title > 0 = 1 then out_string("\n") else google <- 3 fi; (* error:  > and = are non associative *)
            title <- String; (* error: invalid assignment *)
    	}
    };
};

(* OUTPUT: "tests/test-neg10.cl", line 6: parse error at or near ERROR = ">"
           "tests/test-neg10.cl", line 7: parse error at or near TYPEID = String *)