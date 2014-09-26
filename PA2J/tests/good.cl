(* program ::= [[class; ]]+ *)
Class A {
};

Class A {
    a : String;
    getA() : String { a };
};

Class B {
};

Class B {
    b : String;
    getB() : String { b };
};

Class Main inherits IO {

    abc : A;
    	main() : Object {
    	   {

    	     out_string(abc.getB());
    	   }
    	};

};
