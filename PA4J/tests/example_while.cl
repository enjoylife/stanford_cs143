(* 1. Let statement single
   2. Let statement multiple *)

class Main inherits IO {
    num : Int <- 5;
  main():SELF_TYPE {
  {
        (let x : Int <- 1 in
  	 {
  	    (let y : Int <- 1 in
  	    {
  	       while y <= num loop
  	          {
                 x <- x * y;
  	             y <- y + 1;
  	             out_string("y");
  	             out_int(y);
                 out_string("\n");
  	          }
  	       pool;
        }
  	    );
  	    out_string("x");
  	    out_int(x);
  	    out_string("\n");
  	 }
  	 );
  }
  };
};

(* y2
   y3
   y4
   y5
   y6
   x120 *)