(*
    Copied from atoi_test.cl.
    Tests errors in nested let statements.
*)

class Main inherits IO {
   newline() : Object {
	out_string("\n")
   };

   prompt() : String {
	{
	   out_string("Enter a number>");
	   in_string();
	}
   };

   main() : Object {
   (* Since we didn't bother to inherit from the A2I class, we have
	to have an object of type A2I in order to access the
	methods of that class. *)
     (let z : A2I <- new A2I in
	while true loop
	   (let s : String <- prompt() in
		if s = "stop" then
		    abort() -- we don't bother to terminate gracefully
		else
		   (let i : Int <- z@A.a2i(s) in
			(let news : String <- z.i2a(i) in
			   {
			     out_int(i);
			     newline();
			     out_string(news);
			     newline();
			   }
	                )
                  )
		fi
	   )
        pool
     )
   };
};

(* RESULT: tests/bad10.cl:22: 'new' used with undefined class A2I.
           tests/bad10.cl:22: Class A2I of let-bound identifier z is undefined.
           tests/bad10.cl:28: Static dispatch on undefined class A
           tests/bad10.cl:28: Inferred type Object in initialization of i does not conform to identifier's declared type Int.
           tests/bad10.cl:29: Dispatch on type _no_type not allowed.
           tests/bad10.cl:29: Inferred type Object in initialization of news does not conform to identifier's declared type String. *)