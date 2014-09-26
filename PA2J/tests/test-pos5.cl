(*  ADAPTED FROM EXAMPLE FILES
    Tests:
    1. Multiple let bindings
    2. new TYPE
    3. If else then expression
    4. NOT operator
*)
class CC__ {

    feature1 : Int;
    feature6() : Int {
        4
    };

    feature8() : Bool {
        false
    };

    feature7(x : Int, e : String) : Bool {
        if not(x = 5) then
          true
        else
          let new_x : Int <- feature6(),
              new_y : Int <- new Int in
            feature8()
        fi
      };
    };

class Main {
    main() : Int {
        3
    };
};


