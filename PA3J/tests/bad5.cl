(*  Legal and illegal static and non-static dispatch *)

Class A {
    a : Int;
    c : Int;
    getB(a : Int, b: Int) : Int { a + c };
    getClass(d: A): Int { 4 };
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
             self.getB(3, "Hello");
             self@B.getB(4, 5);
             self@A.getB("Hello");
             (new B).getClass(new B);
             (1 + 2).getClass(new B);
    	   }
    	};

};

(* RESULT: tests/bad5.cl:20: 'new' used with undefined class C.
           tests/bad5.cl:20: Dispatch on type _no_type not allowed.
           tests/bad5.cl:20: Undeclared identifier b.
           tests/bad5.cl:21: In call of method getB, type String of parameter b does not conform to declared type Int.
           tests/bad5.cl:22: Expression type Main does not conform to declared static dispatch type B.
           tests/bad5.cl:23: In call of method getB, type String of parameter a does not conform to declared type Int.
           tests/bad5.cl:23: Method getB called with wrong number of arguments.
           tests/bad5.cl:25: Dispatch to undefined method getClass. *)