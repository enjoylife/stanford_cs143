class Main inherits IO {
    a: Int <- 3;
    main() : SELF_TYPE {{
    out_int(a);
    }};
};