(* class Main is not defined and redefinition of an attribute, incorrectly redefined method *)
Class A {
    a : Int;
};

Class B inherits A {
    a : String;
};

Class C {
    a : Int;
    getB() : String { "Hello" };
};

Class D inherits C {
    b : Int;
    getB() : Int { a + b };
    getC() : String { c };
};

Class E {
    a : Int;
    getB(a : Int) : String { "Hello" };
    getC(a : String, b : Int) : String { "Hello Again!" };
};

Class F inherits E {
    b : Int;
    getB(b : Int, a : Int) : String { "Hello" };
    getC(a : Int, b : String) : String { "Hello Again!" };
};

Class G {
    a : Int;
    getB(a : String, b: Int) : String { "Hello" };
};

Class H inherits G {
    b : Int;
    getB(b : Int, a : Int) : Int { "Hello" };
};

(* RESULT :  Class Main is not defined.
             tests/bad3.cl:17: In redefined method getB, return type Int is different from original return type String
             tests/bad3.cl:29: Incompatible number of formal parameters in redefined method getB
             tests/bad3.cl:30: In redefined method getC, parameter type Int is inconsistent with the original type String
             tests/bad3.cl:6: Attribute: a is an attribute of an inherited class.
             tests/bad3.cl:40: In redefined method getB, return type Int is different from original return type String
             tests/bad3.cl:18: Undeclared identifier c.
             tests/bad3.cl:40: Inferred return type String of method getB does not conform to declared return type Int. *)
