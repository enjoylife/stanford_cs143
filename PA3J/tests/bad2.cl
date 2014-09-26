(* Inheritance cycle B -> C -> D -> B *)
Class B inherits C {
    b : String;
    getB() : String { b };
};

Class C inherits D {
};

Class D inherits B {

};

Class Main inherits IO {

    abc : A;
    	main() : Object {
    	   {

    	     out_string(abc.getB());
    	   }
    	};

};
(* RESULT: tests/bad6.cl:9: Class D or an ancestor of Class D, is involved in an inheritance cycle.
           tests/bad6.cl:2: Class B or an ancestor of Class B, is involved in an inheritance cycle.
           tests/bad6.cl:7: Class C or an ancestor of Class C, is involved in an inheritance cycle. *)