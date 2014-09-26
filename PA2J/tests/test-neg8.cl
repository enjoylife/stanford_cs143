(* Parser recovers from error in block *)
class A {
    a_test() : SELF_TYPE {
	    {
	        b <- STRING; (* error: invalid expression in block *)
	        c : String;
	        d : String;
	        a <-STRING; (* error: invalid expression in block *)
    	}
    };
};

(* Output:
    "tests/test-neg8.cl", line 5: parse error at or near TYPEID = STRING
    "tests/test-neg8.cl", line 8: parse error at or near TYPEID = STRING *)