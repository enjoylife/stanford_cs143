class C {
	a : Int;
	b : Bool;
	init(x : Int, y : Bool) : C {
           {
		a <- x;
		b <- y;
		self;
           }
	};
};

Class Main {
	main():C {
	 {
	  (new C).init(1,1);
	  (new C).init(1,true,3);
	  (new C).iinit(1,true);
	  (new C);
	 }
	};
};

(* RESULT: tests/bad.cl:16: In call of method init, type Int of parameter y does not conform to declared type Bool.
           tests/bad.cl:17: Method init called with wrong number of arguments.
           tests/bad.cl:18: Dispatch to undefined method iinit. *)
