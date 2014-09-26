class Main inherits IO {
    var : D <- new D;
  main():SELF_TYPE {
  {
        case var of
        	 a : A => out_string("Class type is now A\n");
        	 c : C => out_string("Class type is now C\n");
        	 b : B => out_string("Class type is now B\n");
        	 o : Object => out_string("Oooops\n");
        esac;
  }
  };
};

class A {

};

class B inherits A {

};

class C inherits B {

};

class D {

};

class E inherits C {

};