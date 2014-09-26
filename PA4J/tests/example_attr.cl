(* 1. Local variable takes precedence over attribute
   2. Formal can be assigned.
   3. Attribute can be assigned.
   4. Values can be edited.
   5. Access to inherited attributes. *)

class A inherits IO {
    a: Int <- 3;
    b: Bool <- true;
    c: B <- new B;
    method1(a: Int, b: String) : SELF_TYPE {{out_int(a); out_string(b);}};
};

class Main inherits A {
  abc : A <- new A;
  main():A { abc.method1(5 + a, " Hello!!")};
};

class B {
    i : Int;
    j: Int;
};

(* RESULT : 8 Hello!! *)