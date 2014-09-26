(* Dispatch to redefined method. *)

class A inherits IO{
    a: Int;
    b: Bool;
    c: String;
    d: Int;
    e: A;
    method5() : String {"Thank "};
    method1() : String {"You "};
    method2() : String {"For Working!"};
};

class Main inherits A {
  method2() : String {"For being nice!"};
  main():SELF_TYPE { {out_string(method5()); out_string(method1()); out_string(method2()); }};
};