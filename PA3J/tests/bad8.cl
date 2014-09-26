(* Illegal expressions LET and main method with arguments *)

Class A {
    a : Int;
    getB(a : Int, b: Int) : Int { a };
};

Class C {
    i : Int;
    getB(c : Int, b: Int) : Int {
     (let j : Int <- new G in
     		   while j <- i
     		   loop
     		     {
     		       l <- (new Cons).init(j,l);
     		       j <- j + 1;
     		     }
     		   pool
     		)
     };
};

Class B {
    i : Int;
    getB(c : Int, b: Int) : Int { new SELF_TYPE };
};

Class Main inherits A {

   invalid : String;
    	main() : Object {
    	   {
    	    case c of
                dummy : A => getB();
                dummy2 : A => getB();
                dummy3 : A => getB();
            esac;
    	   }
    	};

};

(* RESULT:  tests/bad8.cl:33: Undeclared identifier c.
            tests/bad8.cl:34: Method getB called with wrong number of arguments.
            tests/bad8.cl:34: Method getB called with wrong number of arguments.
            tests/bad8.cl:35: Duplicate branch A in case statement.
            tests/bad8.cl:35: Method getB called with wrong number of arguments.
            tests/bad8.cl:36: Duplicate branch A in case statement.
            tests/bad8.cl:36: Method getB called with wrong number of arguments.
            tests/bad8.cl:25: Inferred return type B of method getB does not conform to declared return type Int.
            tests/bad8.cl:11: 'new' used with undefined class G.
            tests/bad8.cl:15: 'new' used with undefined class Cons.
            tests/bad8.cl:15: Dispatch on type _no_type not allowed.
            tests/bad8.cl:15: Undeclared identifier l.
            tests/bad8.cl:15: Assignment to undeclared variable l.
            tests/bad8.cl:12: Loop condition does not have type Bool.
            tests/bad8.cl:10: Inferred return type Object of method getB does not conform to declared return type Int. *)