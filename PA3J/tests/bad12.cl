(* Comparison operators *)
Class Main inherits IO {

   string1 : String;
   integer1 : Int;
   bool1 : Bool;
    	main() : Object {
    	   {
                string1 = integer1;
                bool1 = string1;
                integer1 = string1;
                string1 < integer1;
    	   }
    	};

};

(* RESULT: tests/examples/bad12.cl:8: Unmatched comparison types: String = Int.
           tests/examples/bad12.cl:9: Unmatched comparison types: Bool = String.
           tests/examples/bad12.cl:10: Unmatched comparison types: Int = String.
           tests/examples/bad12.cl:11: non-Int arguments: String < Int. *)