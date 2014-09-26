(* Copied from Piazza. Example by Sean Treichler *)
class C {

  foo() : SELF_TYPE { self };

};

class D inherits C {

  bar() : Object { self };

  d : D <- foo();

  x : Object <- d.bar();

};

class Main {
    main() : Object {
        (new D)
    };
};