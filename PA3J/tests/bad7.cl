(* No main method defined and illegal LET *)
Class A {
    i : Int;
    getB(c : Int, b: Int) : Int {
     (let j : C <- 0 in
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
    getB(c : Int, b: Int) : Int {
     (let j : C <- 0 in
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

Class Main inherits A {

abc : Int;
};

(* RESULT: tests/bad7.cl:33: No 'main' method in class Main.
           tests/bad7.cl:5: Class C of let-bound identifier j is undefined.
           tests/bad7.cl:6: Type Int of assigned expression does not conform to declared type _no_type of identifier j.
           tests/bad7.cl:9: 'new' used with undefined class Cons.
           tests/bad7.cl:9: Dispatch on type _no_type not allowed.
           tests/bad7.cl:9: Undeclared identifier l.
           tests/bad7.cl:9: Assignment to undeclared variable l.
           tests/bad7.cl:10: non-Int arguments: _no_type + Int.
           tests/bad7.cl:10: Type Int of assigned expression does not conform to declared type _no_type of identifier j.
           tests/bad7.cl:6: Loop condition does not have type Bool.
           tests/bad7.cl:4: Inferred return type Object of method getB does not conform to declared return type Int.
           tests/bad7.cl:21: Class C of let-bound identifier j is undefined.
           tests/bad7.cl:22: Type Int of assigned expression does not conform to declared type _no_type of identifier j.
           tests/bad7.cl:25: 'new' used with undefined class Cons.
           tests/bad7.cl:25: Dispatch on type _no_type not allowed.
           tests/bad7.cl:25: Undeclared identifier l.
           tests/bad7.cl:25: Assignment to undeclared variable l.
           tests/bad7.cl:26: non-Int arguments: _no_type + Int.
           tests/bad7.cl:26: Type Int of assigned expression does not conform to declared type _no_type of identifier j.
           tests/bad7.cl:22: Loop condition does not have type Bool.
           tests/bad7.cl:20: Inferred return type Object of method getB does not conform to declared return type Int. *)