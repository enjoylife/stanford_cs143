
(*
 *  execute "coolc bad.cl" to see the error messages that the coolc parser
 *  generates
 *
 *  execute "parsetest < bad.cl" to see the error messages that your parser
 *  generates
 *)

(* no error *)
class A {
};

(* error:  b is not a type identifier *)
Class b inherits A {
};

(* error:  a is not a type identifier *)
Class C inherits a {
};

(* error:  keyword inherits is misspelled *)
Class D inherts A {
};

(* error:  closing brace is missing *)
Class E inherits A {
;

(* Output:

"tests/bad.cl", line 15: parse error at or near OBJECTID = b
"tests/bad.cl", line 19: parse error at or near OBJECTID = a
"tests/bad.cl", line 23: parse error at or near OBJECTID = inherts
"tests/bad.cl", line 28: parse error at or near ';' *)

