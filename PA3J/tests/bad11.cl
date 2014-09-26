(* Redefining IO method incorrectly *)

class Main inherits IO {
   newline() : Object {
	out_string("\n")
   };

   out_string() : Int { 3 };
};

(* RESULT: tests/bad11.cl:3: No 'main' method in class Main.
           tests/bad11.cl:8: In redefined method out_string, return type Int is different from original return type SELF_TYPE
           tests/bad11.cl:5: Method out_string called with wrong number of arguments. *)