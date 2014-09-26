(*  1. New on undefined class
 *  2. Dispatch on No_Type expression
 *  3. Undeclared identifier
 *  4. Illegal assignment statement
 *  5. self as attribute name
 *)

Class A {
    a : Int;
    self : String;
    getB(a : Int, b: Int) : Int { a + c };
};

Class B inherits A {
    b : Int;
    getB(b : Int, a : Int) : Int { a };
};

Class Main inherits A {

   invalid : String;
    	main() : Object {
    	   {
    	     (new C).getB(b);
    	     a;
    	     invalid <- 2;
    	   }
    	};

};

(* RESULT: tests/bad4.cl:24: 'new' used with undefined class C.
           tests/bad4.cl:24: Dispatch on type _no_type not allowed.
           tests/bad4.cl:24: Undeclared identifier b.
           tests/bad4.cl:26: Type Int of assigned expression does not conform to declared type String of identifier invalid.
           tests/bad4.cl:10: 'self' cannot be the name of an attribute.
           tests/bad4.cl:11: Undeclared identifier c.
           tests/bad4.cl:11: non-Int arguments: Int + _no_type. *)