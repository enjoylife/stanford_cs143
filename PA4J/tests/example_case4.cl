class Main {
    var2 : A <- new C;
  main():Object {
  {
            case var2 of
            	 a : A => {a.method1(); a.method2();};
            	 c : C => {c.method1(); c.method2();};
            	 b : B => {b.method1(); b.method2();};
            esac;
  }
  };
};

class A inherits IO{

abc : Int <- 5;
method1(): Int {
    abc <- 50
};

method2(): SELF_TYPE {
{
out_string("I should be 50! ");
out_int(abc);
out_string("\n");
}
};

};

class B inherits A {

ab : Int <- 20;
method1(): Int {
    ab <- 70
};

method2(): SELF_TYPE { {
out_string("I should be 70! ");
out_int(ab);
out_string("\n");
}};
};

class C inherits B {

ac : Int <- 10;
method1(): Int {
    ac <- 60
};

method2(): SELF_TYPE {{
out_string("I should be 60! ");
out_int(ac);
out_string("\n");
}};

};