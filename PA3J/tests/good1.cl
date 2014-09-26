Class A {
    a : Int;
};

Class B inherits C {
    b : String;
    getB() : String { b };
};

Class C inherits D {
};

Class D inherits A {

};

Class Main inherits A {

   d : Int;
    	main() : Object {
    	   {
    	     d <- a;
    	   }
    	};

};