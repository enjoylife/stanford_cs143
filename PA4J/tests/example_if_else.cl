(* 1. If then else (both true and false branches)
   2. Comparison and arithmetic operations *)

class Main inherits IO {
    a : A;
  main():SELF_TYPE {
  {
    if 3<5 then out_int(1) else out_int(3) fi;
    if 3<=3 then out_int(1) else out_int(5) fi;
    if 3<=2 then out_int(4) else out_int(1) fi;
    if not(5<3) then out_int(1) else out_int(5) fi;
    if 3+4=7 then out_int(1) else out_int(7) fi;
    if not(15/3 < 7) then out_int(7) else out_int(1) fi;
    if not(5-5=0) then out_int(5) else out_int(1) fi;
    if 3*5<1 then out_int(3) else out_int(1) fi;
    if isvoid a then out_int(1) else out_int(0) fi;
    a <- new A;
    if isvoid a then out_int(0) else out_int(1) fi;
  }
  };
};

class A {

};

(* 1111111111 *)