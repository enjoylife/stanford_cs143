(* 1. Static dispatch
   2. Self variable *)

class A inherits IO {
    method1(a: Int, b: String) : SELF_TYPE {{out_int(a); out_string(b);}};
};

class Main inherits A {
  abc : A <- new A;
  main():A { self@A.method1(15, "I worked!") };
};

(* 15I worked! *)