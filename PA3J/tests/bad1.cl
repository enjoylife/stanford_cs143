(* Redefinition of illegal classes and inheriting illegal classes. Redefinition of previously defined classes*)
Class SELF_TYPE inherits C {
    b : String;
    getB() : String { b };
};

Class C inherits SELF_TYPE {
    b : String;
    getB() : String { b };
};

Class Int {
};

Class A {
};

Class A {
    b : String;
    getB() : String { b };
};

Class B inherits Int {
    b : String;
    getB() : String { b };
};

Class Bool {
    a : String;
    getA() : String { a };
};

Class Object {
};

Class E inherits F {
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
(* RESULT: tests/bad1.cl:2: Redefinition of basic class SELF_TYPE.
           tests/bad1.cl:12: Redefinition of basic class Int.
           tests/bad1.cl:18: Class A was previously defined.
           tests/bad1.cl:28: Redefinition of basic class Bool.
           tests/bad1.cl:33: Redefinition of basic class Object.
           tests/bad1.cl:36: Class E inherits from an undefined class F.
           tests/bad1.cl:23: Class B cannot inherit class Int.
           tests/bad1.cl:7: Class C cannot inherit class SELF_TYPE. *)