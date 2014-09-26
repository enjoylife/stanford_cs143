(* Illegal expressions IF and WHILE *)

Class A {
    a : Int;
    getB(a : Int, b: Int) : Int { a };
};

Class B inherits D {
    b : Int;
    getB(b : Int, a : Int) : Int { a };
};

Class C inherits D {
    b : Int;
    getB(b : Int, a : Int) : Int { a };
};

Class D {
    a : Int;
    getB(a : Int, b: Int) : Int { a };
};

Class Main inherits A {

   invalid : String;
    	main() : Object {
    	   {
    	     (if 4 < 9 then (new B) else (new F) fi).getB(1, 2);
    	     if (new G) then new H else new B fi;
    	     while new I loop 3 + 4 pool;
    	     while 3 + 4 loop new I pool;
    	     3;
    	   }
    	};

};

(* RESULT: tests/bad6.cl:28: 'new' used with undefined class F.
           tests/bad6.cl:28: Dispatch on type _no_type not allowed.
           tests/bad6.cl:29: 'new' used with undefined class G.
           tests/bad6.cl:29: 'new' used with undefined class H.
           tests/bad6.cl:29: Predicate of if statement does not contain a valid condition
           tests/bad6.cl:30: 'new' used with undefined class I.
           tests/bad6.cl:30: Loop condition does not have type Bool.
           tests/bad6.cl:31: 'new' used with undefined class I.
           tests/bad6.cl:31: Loop condition does not have type Bool. *)