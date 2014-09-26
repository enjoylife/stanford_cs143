(* Parser reports two consecutive error lines as long as 3 valid tokens follow the 1st erroneous expression *)
class A {
    a_test() : SELF_TYPE {
	    {
	        title <- String; (* error: invalid assignment *)
            if title = 0 then out_string("\n") else google <- Int fi; (* error: consecutive error caught - invalid assignment *)
    	}
    };
};

(* OUTPUT:
    "tests/test-neg9.cl", line 5: parse error at or near TYPEID = String
    "tests/test-neg9.cl", line 6: parse error at or near TYPEID = Int *)