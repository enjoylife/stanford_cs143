class Main inherits IO{
 num : Int <- 12;
 main(): SELF_TYPE {
       (let x : Int <- 1 in
 	 {
 	    (let y : Int <- 1 in
 	       while y <= num loop
 	          {
                      x <- x * y;
 	             y <- y + 1;
 	          }
 	       pool
 	    );
 	    out_int(x);
 	 }
       )
    };
 };