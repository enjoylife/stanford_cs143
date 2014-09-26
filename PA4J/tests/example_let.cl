(* 1. Let statement single
   2. Let statement multiple *)

class Main inherits IO {
    a : Int <- 12;
  main():SELF_TYPE {
  {(let a : Int <- 15 in
	 {
	        out_int(a);
	 }
  );

  (let x : Int <- 1 in
  	 {
  	    (let y : Int <- 1 in
  	       out_int((x + y)*3)
  	    );

  	 }
  );
  }
  };
};

(* 156 *)