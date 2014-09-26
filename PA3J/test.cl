Class A {
i: Int;
b: Bool;
x: SELF_TYPE;
foo(): SELF_TYPE { x };
};
class B inherits A {
y: SELF_TYPE;
g(b: Bool): Object { case b of
i: Int => b;
b: Bool => b;
b: Object => true;
esac };
};
class Main {
 main(): Object{3};
};
