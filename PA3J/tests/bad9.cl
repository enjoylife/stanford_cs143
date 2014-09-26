(* Illegal expressions : LET ; Duplicate branches, undeclared identifier, invalid expressions and class *)
Class Main inherits IO {

   invalid : String;
    	main() : Object {
    	   {
    	    case c of
                dummy : A => isvoid dummy;
                dummy2 : A => 4 + "hello";
                dummy3 : A => getB();
                dummy4 : String => 3.length();
            esac;
    	   }
    	};

};

(* RESULT: tests/bad9.cl:7: Undeclared identifier c.
           tests/bad9.cl:8: Class A of case branch is undefined.
           tests/bad9.cl:8: Class A of case branch is undefined.
           tests/bad9.cl:9: Duplicate branch A in case statement.
           tests/bad9.cl:9: Class A of case branch is undefined.
           tests/bad9.cl:9: non-Int arguments: Int + String.
           tests/bad9.cl:10: Duplicate branch A in case statement.
           tests/bad9.cl:10: Class A of case branch is undefined.
           tests/bad9.cl:10: Dispatch to undefined method getB.
           tests/bad9.cl:11: Dispatch to undefined method length.
           tests/bad9.cl:11: Dispatch to undefined method length. *)