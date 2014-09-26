(*  Inherited attributes and individual attributes. Method call with parameter. *)

class Main inherits IO {
  i : Int <- 2;
  bc : B <- new B;
  main():B { {out_int(30); bc.b();} };
};

class A inherits IO {
    s : String;
    c : B;
    a : Int <- 10;
};

class B inherits A {
    b : Int <- 6;
    b():SELF_TYPE {{out_int(a); out_int(b); }};
};