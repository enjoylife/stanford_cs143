// -*- mode: java -*- 
//
// file: cool-tree.m4
//
// This file defines the AST
//
//////////////////////////////////////////////////////////

import java.util.*;
import java.io.PrintStream;

/** Defines simple phylum Program */
abstract class Program extends TreeNode {
    protected Program(int lineNumber) {
        super(lineNumber);
    }
    public abstract void dump_with_types(PrintStream out, int n);
    public abstract void semant();

}


/** Defines simple phylum Class_ */
abstract class Class_ extends TreeNode {
    protected Class_(int lineNumber) {
        super(lineNumber);
    }
    public abstract void dump_with_types(PrintStream out, int n);

}


/** Defines list phylum Classes
    <p>
    See <a href="ListNode.html">ListNode</a> for full documentation. */
class Classes extends ListNode {
    public final static Class elementClass = Class_.class;
    /** Returns class of this lists's elements */
    public Class getElementClass() {
        return elementClass;
    }
    protected Classes(int lineNumber, Vector elements) {
        super(lineNumber, elements);
    }
    /** Creates an empty "Classes" list */
    public Classes(int lineNumber) {
        super(lineNumber);
    }
    /** Appends "Class_" element to this list */
    public Classes appendElement(TreeNode elem) {
        addElement(elem);
        return this;
    }
    public TreeNode copy() {
        return new Classes(lineNumber, copyElements());
    }
}


/** Defines simple phylum Feature */
abstract class Feature extends TreeNode {
    protected Feature(int lineNumber) {
        super(lineNumber);
    }
    public abstract void dump_with_types(PrintStream out, int n);
    public abstract AbstractSymbol getName();
    public abstract void typeChecker (SymbolTable o, ClassTable m, class_c c);

}


/** Defines list phylum Features
    <p>
    See <a href="ListNode.html">ListNode</a> for full documentation. */
class Features extends ListNode {
    public final static Class elementClass = Feature.class;
    /** Returns class of this lists's elements */
    public Class getElementClass() {
        return elementClass;
    }
    protected Features(int lineNumber, Vector elements) {
        super(lineNumber, elements);
    }
    /** Creates an empty "Features" list */
    public Features(int lineNumber) {
        super(lineNumber);
    }
    /** Appends "Feature" element to this list */
    public Features appendElement(TreeNode elem) {
        addElement(elem);
        return this;
    }
    public TreeNode copy() {
        return new Features(lineNumber, copyElements());
    }
}


/** Defines simple phylum Formal */
abstract class Formal extends TreeNode {
    protected Formal(int lineNumber) {
        super(lineNumber);
    }
    public abstract void dump_with_types(PrintStream out, int n);

}


/** Defines list phylum Formals
    <p>
    See <a href="ListNode.html">ListNode</a> for full documentation. */
class Formals extends ListNode {
    public final static Class elementClass = Formal.class;
    /** Returns class of this lists's elements */
    public Class getElementClass() {
        return elementClass;
    }
    protected Formals(int lineNumber, Vector elements) {
        super(lineNumber, elements);
    }
    /** Creates an empty "Formals" list */
    public Formals(int lineNumber) {
        super(lineNumber);
    }
    /** Appends "Formal" element to this list */
    public Formals appendElement(TreeNode elem) {
        addElement(elem);
        return this;
    }
    public TreeNode copy() {
        return new Formals(lineNumber, copyElements());
    }
}


/** Defines simple phylum Expression */
abstract class Expression extends TreeNode {
    protected Expression(int lineNumber) {
        super(lineNumber);
    }
    private AbstractSymbol type = null;                                 
    public AbstractSymbol get_type() { return type; }           
    public Expression set_type(AbstractSymbol s) { type = s; return this; } 
    public abstract void dump_with_types(PrintStream out, int n);
    public void dump_type(PrintStream out, int n) {
        if (type != null)
            { out.println(Utilities.pad(n) + ": " + type.getString()); }
        else
            { out.println(Utilities.pad(n) + ": _no_type"); }
    }

    public abstract AbstractSymbol typeChecker(SymbolTable o, ClassTable m, class_c c);
}


/** Defines list phylum Expressions
    <p>
    See <a href="ListNode.html">ListNode</a> for full documentation. */
class Expressions extends ListNode {
    public final static Class elementClass = Expression.class;
    /** Returns class of this lists's elements */
    public Class getElementClass() {
        return elementClass;
    }
    protected Expressions(int lineNumber, Vector elements) {
        super(lineNumber, elements);
    }
    /** Creates an empty "Expressions" list */
    public Expressions(int lineNumber) {
        super(lineNumber);
    }
    /** Appends "Expression" element to this list */
    public Expressions appendElement(TreeNode elem) {
        addElement(elem);
        return this;
    }
    public TreeNode copy() {
        return new Expressions(lineNumber, copyElements());
    }
}


/** Defines simple phylum Case */
abstract class Case extends TreeNode {
    protected Case(int lineNumber) {
        super(lineNumber);
    }
    public abstract void dump_with_types(PrintStream out, int n);

}


/** Defines list phylum Cases
    <p>
    See <a href="ListNode.html">ListNode</a> for full documentation. */
class Cases extends ListNode {
    public final static Class elementClass = Case.class;
    /** Returns class of this lists's elements */
    public Class getElementClass() {
        return elementClass;
    }
    protected Cases(int lineNumber, Vector elements) {
        super(lineNumber, elements);
    }
    /** Creates an empty "Cases" list */
    public Cases(int lineNumber) {
        super(lineNumber);
    }
    /** Appends "Case" element to this list */
    public Cases appendElement(TreeNode elem) {
        addElement(elem);
        return this;
    }
    public TreeNode copy() {
        return new Cases(lineNumber, copyElements());
    }
}


/** Defines AST constructor 'programc'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class programc extends Program {
    protected Classes classes;
    /** Creates "programc" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for classes
      */
    public programc(int lineNumber, Classes a1) {
        super(lineNumber);
        classes = a1;
    }
    public TreeNode copy() {
        return new programc(lineNumber, (Classes)classes.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "programc\n");
        classes.dump(out, n + 2);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_program");
        for (Enumeration e = classes.getElements(); e.hasMoreElements(); ) {
            // sm: changed 'n + 1' to 'n + 2' to match changes elsewhere
	    ((Class_)e.nextElement()).dump_with_types(out, n + 2);
        }
    }
    /** This method is the entry point to the semantic checker.  You will
        need to complete it in programming assignment 4.
	<p>
        Your checker should do the following two things:
	<ol>
	<li>Check that the program is semantically correct
	<li>Decorate the abstract syntax tree with type information
        by setting the type field in each Expression node.
        (see tree.h)
	</ol>
	<p>
	You are free to first do (1) and make sure you catch all semantic
    	errors. Part (2) can be done in a second stage when you want
	to test the complete compiler.
    */
    public void semant() {
	/* ClassTable constructor may do some semantic analysis */
        ClassTable classTable = new ClassTable(classes);

        // Check for errors after semantic analysis of class hierarchy, do NOT recover from class hierarchy errors
        if (classTable.errors()) {
            System.err.println("Compilation halted due to static semantic errors.");
            System.exit(1);
        }

        // Check for errors in Main class ( existence, main method and no params in main meth)
        classTable.checkMainSemantics();

        // Adds all attributes and methods of the classes into their respective symbol tables
        for (Map.Entry<String, class_c> entry : classTable.getClassNamesTable().entrySet()) {
                entry.getValue().scopeIndividualClasses(classTable);
        }

        // Checks for parent classes and adds attributes and methods into their respective symbol tables
        // Error checking for inheritance semantics ( attributes cannot be redefined and
        // methods should be overwritten IF return type and parameters match)
        for (Map.Entry<String, class_c> entry : classTable.getClassNamesTable().entrySet()) {
                entry.getValue().scopeInheritedClasses(classTable);
        }

        // Scoping and type checking for rest of the program
        for (Map.Entry<String, class_c> entry : classTable.getClassNamesTable().entrySet()) {
            if (!classTable.isBasicClass(entry.getValue()))
                entry.getValue().typeChecker(classTable);
        }

        if (classTable.errors()) {
            System.err.println("Compilation halted due to static semantic errors.");
            System.exit(1);
        }
    }
}


/** Defines AST constructor 'class_c'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class class_c extends Class_ {
    protected AbstractSymbol name;
    protected AbstractSymbol parent;
    protected Features features;
    protected AbstractSymbol filename;
    // Two different tables since methods and attributes can have the same name
    protected SymbolTable attrSymTable;
    protected SymbolTable methodSymTable;
    /** Creates "class_c" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for name
      * @param a1 initial value for parent
      * @param a2 initial value for features
      * @param a3 initial value for filename
      */
    public class_c(int lineNumber, AbstractSymbol a1, AbstractSymbol a2, Features a3, AbstractSymbol a4) {
        super(lineNumber);
        name = a1;
        parent = a2;
        features = a3;
        filename = a4;
    }
    public TreeNode copy() {
        return new class_c(lineNumber, copy_AbstractSymbol(name), copy_AbstractSymbol(parent), (Features)features.copy(), copy_AbstractSymbol(filename));
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "class_c\n");
        dump_AbstractSymbol(out, n+2, name);
        dump_AbstractSymbol(out, n+2, parent);
        features.dump(out, n+2);
        dump_AbstractSymbol(out, n+2, filename);
    }

    public AbstractSymbol getFilename() { return filename; }
    public AbstractSymbol getName()     { return name; }
    public AbstractSymbol getParent()   { return parent; }

    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_class");
        dump_AbstractSymbol(out, n + 2, name);
        dump_AbstractSymbol(out, n + 2, parent);
        out.print(Utilities.pad(n + 2) + "\"");
        Utilities.printEscapedString(out, filename.getString());
        out.println("\"\n" + Utilities.pad(n + 2) + "(");
        for (Enumeration e = features.getElements(); e.hasMoreElements();) {
	    ((Feature)e.nextElement()).dump_with_types(out, n + 2);
        }
        out.println(Utilities.pad(n + 2) + ")");
    }

    public void scopeIndividualClasses(ClassTable classTable){
        attrSymTable = new SymbolTable();
        methodSymTable = new SymbolTable();
        for (Enumeration fe = features.getElements(); fe.hasMoreElements();) {
            Feature f = ((Feature) fe.nextElement());
            if (f instanceof attr) {
                attrSymTable.enterScope();
                if (attrSymTable.lookup(f.getName()) != null) {
                    classTable.semantError(this).println("Attribute " + f.getName().toString() + " has been redefined.");
                }
                attrSymTable.addId(f.getName(), ((attr) f).getType());
            } else {
                methodSymTable.enterScope();
                if (methodSymTable.lookup(f.getName()) != null) {
                    classTable.semantError(this).println("Method " + f.getName().toString() + " has been redefined in class" + name + ".");
                }
                methodSymTable.addId(f.getName(), (method) f);
            }
        }
    }

    public void scopeInheritedClasses(ClassTable classTable) {
        class_c currentParent = classTable.getParentClass(this);
        while (currentParent != null) {
            attrSymTable.enterScope();
            methodSymTable.enterScope();
            for (Enumeration fe = currentParent.features.getElements(); fe.hasMoreElements();) {
                Feature f = ((Feature) fe.nextElement());
                if (f instanceof attr) {
                    // If feature is an attribute
                    if (attrSymTable.lookup(f.getName()) != null) {
                        classTable.semantError(filename, f).println("Attribute: " + f.getName().toString()
                                + " is an attribute of an inherited class.");
                    } else {
                        attrSymTable.addId(f.getName(), ((attr) f).getType());
                    }
                }
                else {
                    method childMethod = (method)methodSymTable.lookup(f.getName());
                    if (childMethod != null) {
                        // check if all formals are of same type and same number, if not throw an error
                        ((method)f).isConsistent(childMethod, classTable, this);
                    } else {
                        methodSymTable.addId(f.getName(), (method)f);
                    }
                }
            }
        currentParent = classTable.getParentClass(currentParent);
        }
    }

    public void typeChecker (ClassTable m) {

        attrSymTable.enterScope();
        // SELF_TYPE is type of self so add this and change SELF_TYPE to its appropriate class name later
        attrSymTable.addId(TreeConstants.self, TreeConstants.SELF_TYPE);
        attrSymTable.addId(TreeConstants.SELF_TYPE, name);
        // Iterate through each feature for scope checking and type checking
        for (Enumeration e = features.getElements(); e.hasMoreElements();) {
            ((Feature)e.nextElement()).typeChecker(attrSymTable, m, this);
        }
        attrSymTable.exitScope();
    }

    public boolean hasMethod(AbstractSymbol checkMethod) {
        for (Enumeration fe = features.getElements(); fe.hasMoreElements();) {
            Feature f = ((Feature) fe.nextElement());
            if (f instanceof method) {
                if (checkMethod.toString().equals(f.getName().toString())) {
                    return true;
                }
            }
        }
        return false;
    }

    public method getMethodFromFeatures(AbstractSymbol methodName) {
        for (Enumeration fe = features.getElements(); fe.hasMoreElements();) {
            Feature f = ((Feature) fe.nextElement());
            if (f instanceof method) {
                if (methodName.toString().equals(f.getName().toString())) {
                    return (method)f;
                }
            }
        }
        return null;
    }

    public method getMethodFromMethodTable(AbstractSymbol methodName) {
        return (method)(methodSymTable.lookup(methodName));
    }
}


/** Defines AST constructor 'method'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class method extends Feature {
    protected AbstractSymbol name;
    protected Formals formals;
    protected AbstractSymbol return_type;
    protected Expression expr;
    protected SymbolTable formalTable;
    /** Creates "method" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for name
      * @param a1 initial value for formals
      * @param a2 initial value for return_type
      * @param a3 initial value for expr
      */
    public method(int lineNumber, AbstractSymbol a1, Formals a2, AbstractSymbol a3, Expression a4) {
        super(lineNumber);
        name = a1;
        formals = a2;
        return_type = a3;
        expr = a4;
    }
    public TreeNode copy() {
        return new method(lineNumber, copy_AbstractSymbol(name), (Formals)formals.copy(), copy_AbstractSymbol(return_type), (Expression)expr.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "method\n");
        dump_AbstractSymbol(out, n+2, name);
        formals.dump(out, n+2);
        dump_AbstractSymbol(out, n+2, return_type);
        expr.dump(out, n+2);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_method");
        dump_AbstractSymbol(out, n + 2, name);
        for (Enumeration e = formals.getElements(); e.hasMoreElements();) {
	    ((Formal)e.nextElement()).dump_with_types(out, n + 2);
        }
        dump_AbstractSymbol(out, n + 2, return_type);
	expr.dump_with_types(out, n + 2);
    }

    public AbstractSymbol getName() {
        return name;
    }

    public Formals getFormals() {
        return formals;
    }

    public AbstractSymbol getReturn_type() {
        return return_type;
    }

    public boolean isConsistent(method compareMethod, ClassTable classTable, class_c c) {

         if (!return_type.equals(compareMethod.getReturn_type())) {
            classTable.semantError(c.getFilename(), compareMethod).println("In redefined method " + compareMethod.getName().toString() +
                    ", return type " + compareMethod.getReturn_type().toString() +
                    " is different from original return type " + return_type.toString());
            return false;
        } else if (formals.getLength() != compareMethod.getFormals().getLength()) {
             classTable.semantError(c.getFilename(), compareMethod).println("Incompatible number of formal parameters in redefined method "
                     + compareMethod.getName());
             return false;
         } else {
            Enumeration e1 = formals.getElements();
            Enumeration e2 = compareMethod.getFormals().getElements();
            while (e1.hasMoreElements() && e2.hasMoreElements()) {

                formalc parentFormal = ((formalc)e1.nextElement());
                formalc childFormal = ((formalc)e2.nextElement());
                if (parentFormal.getType() != childFormal.getType()) {
                    classTable.semantError(c.getFilename(), compareMethod).println("In redefined method " + name.toString() + ", parameter type "
                            + childFormal.getType() + " is inconsistent with the original type "
                            + parentFormal.getType());
                    return false;
                }
            }
        }
        return true;
    }

    public void typeChecker(SymbolTable o, ClassTable m, class_c c) {

        if (return_type != TreeConstants.SELF_TYPE && m.getClassByName(return_type)==null) {
            m.semantError(c.filename, this).println("Undefined return type " + return_type + " in method " + name + ".");
        }

        o.enterScope();
        // M(C,f) = (T1,....Tn,Tr)
        List<formalc> formalList = new ArrayList<formalc>();
        for (Enumeration e = formals.getElements(); e.hasMoreElements();) {
            formalc formal = (formalc)e.nextElement();
            AbstractSymbol formalType = formal.typeChecker(m, c);
            if (formalList.contains(formal)) {
                m.semantError(c.getFilename(), this).println("Formal parameter " + formal.getName() + " is already defined for method" + name + ".");
            } else {
                o.addId(formal.getName(), formalType);
            }
        }
        AbstractSymbol type = expr.typeChecker(o, m, c);
        o.exitScope();

        // Equivalent of "Oc is extended with bindings for self"
        if (type.equals(TreeConstants.SELF_TYPE)) {
            expr.set_type(c.getName());
            type = c.getName();
        }

        // If return type is an Object, the type is always a subtype.
        if (!TreeConstants.Object_.equals(return_type)) {
            if (return_type.equals(TreeConstants.SELF_TYPE) && type != c.getName()) {
                m.semantError(c.getFilename(), this).println("Inferred return type " + type +
                        " of method " + name + " does not conform to declared return type " + return_type + ".");
            } else if (!TreeConstants.No_type.equals(type) && !return_type.equals(TreeConstants.SELF_TYPE) && !(m.isSubtype(return_type, type))) {
                m.semantError(c.getFilename(), this).println("Inferred return type " + type +
                        " of method " + name + " does not conform to declared return type " + return_type + ".");
            }
        }
    }
}


/** Defines AST constructor 'attr'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class attr extends Feature {
    protected AbstractSymbol name;
    protected AbstractSymbol type_decl;
    protected Expression init;
    /** Creates "attr" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for name
      * @param a1 initial value for type_decl
      * @param a2 initial value for init
      */
    public attr(int lineNumber, AbstractSymbol a1, AbstractSymbol a2, Expression a3) {
        super(lineNumber);
        name = a1;
        type_decl = a2;
        init = a3;
    }
    public TreeNode copy() {
        return new attr(lineNumber, copy_AbstractSymbol(name), copy_AbstractSymbol(type_decl), (Expression)init.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "attr\n");
        dump_AbstractSymbol(out, n+2, name);
        dump_AbstractSymbol(out, n+2, type_decl);
        init.dump(out, n+2);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_attr");
        dump_AbstractSymbol(out, n + 2, name);
        dump_AbstractSymbol(out, n + 2, type_decl);
	init.dump_with_types(out, n + 2);
    }

    public AbstractSymbol getName() {
        return name;
    }

    public AbstractSymbol getType() {
        return type_decl;
    }

    public void typeChecker(SymbolTable o, ClassTable m, class_c c) {
        // Attribute cannot have self as name
        if (name == TreeConstants.self) {
            m.semantError(c.getFilename(), this).println("'self' cannot be the name of an attribute.");
        }
        init.typeChecker(o, m, c);
    }
}


/** Defines AST constructor 'formalc'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class formalc extends Formal {
    protected AbstractSymbol name;
    protected AbstractSymbol type_decl;
    /** Creates "formalc" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for name
      * @param a1 initial value for type_decl
      */
    public formalc(int lineNumber, AbstractSymbol a1, AbstractSymbol a2) {
        super(lineNumber);
        name = a1;
        type_decl = a2;
    }
    public TreeNode copy() {
        return new formalc(lineNumber, copy_AbstractSymbol(name), copy_AbstractSymbol(type_decl));
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "formalc\n");
        dump_AbstractSymbol(out, n+2, name);
        dump_AbstractSymbol(out, n+2, type_decl);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_formal");
        dump_AbstractSymbol(out, n + 2, name);
        dump_AbstractSymbol(out, n + 2, type_decl);
    }

    public AbstractSymbol getType() {
        return type_decl;
    }

    public AbstractSymbol getName() {
        return name;
    }

    public AbstractSymbol typeChecker(ClassTable m, class_c c) {
        if (TreeConstants.SELF_TYPE.equals(type_decl)) {
            m.semantError(c.getFilename(), this).println("Formal parameter " + name + " cannot have type SELF_TYPE.");
        } else if(TreeConstants.self.equals(name)) {
            m.semantError(c.getFilename(), this).println("Formal parameter " + name + " cannot have type self.");
        }

        return type_decl;
    }
}


/** Defines AST constructor 'branch'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class branch extends Case {
    protected AbstractSymbol name;
    protected AbstractSymbol type_decl;
    protected Expression expr;
    /** Creates "branch" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for name
      * @param a1 initial value for type_decl
      * @param a2 initial value for expr
      */
    public branch(int lineNumber, AbstractSymbol a1, AbstractSymbol a2, Expression a3) {
        super(lineNumber);
        name = a1;
        type_decl = a2;
        expr = a3;
    }
    public TreeNode copy() {
        return new branch(lineNumber, copy_AbstractSymbol(name), copy_AbstractSymbol(type_decl), (Expression)expr.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "branch\n");
        dump_AbstractSymbol(out, n+2, name);
        dump_AbstractSymbol(out, n+2, type_decl);
        expr.dump(out, n+2);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_branch");
        dump_AbstractSymbol(out, n + 2, name);
        dump_AbstractSymbol(out, n + 2, type_decl);
	expr.dump_with_types(out, n + 2);
    }

    public AbstractSymbol getType_decl() {
        return type_decl;
    }

    public AbstractSymbol typeChecker(SymbolTable o, ClassTable m, class_c c) {
        // Add scope and check the type in the new defined environment
        o.enterScope();
        if (m.getClassByName(type_decl) == null) {
            m.semantError(c.getFilename(), this).println("Class " + type_decl.toString() + " of case branch is undefined.");
        }
        // Declare the identifier even if type is not defined. As per reference compiler.
        o.addId(name, type_decl);
        AbstractSymbol type = expr.typeChecker(o, m, c);
        o.exitScope();
        return type;
    }
}


/** Defines AST constructor 'assign'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class assign extends Expression {
    protected AbstractSymbol name;
    protected Expression expr;
    /** Creates "assign" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for name
      * @param a1 initial value for expr
      */
    public assign(int lineNumber, AbstractSymbol a1, Expression a2) {
        super(lineNumber);
        name = a1;
        expr = a2;
    }
    public TreeNode copy() {
        return new assign(lineNumber, copy_AbstractSymbol(name), (Expression)expr.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "assign\n");
        dump_AbstractSymbol(out, n+2, name);
        expr.dump(out, n+2);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_assign");
        dump_AbstractSymbol(out, n + 2, name);
	expr.dump_with_types(out, n + 2);
	dump_type(out, n);
    }

    public AbstractSymbol typeChecker(SymbolTable o, ClassTable m, class_c c) {

        // Cannot assign to self
        if (name == TreeConstants.self) {
            m.semantError(c.getFilename(), expr).println("Cannot assign to 'self'.");
            set_type(TreeConstants.No_type);
            return TreeConstants.No_type;
        }

        // Get the declared type and actual type for the expression being assigned
        AbstractSymbol declaredType = (AbstractSymbol)(o.lookup(name));
        AbstractSymbol actualType = expr.typeChecker(o, m, c);

        // If declared type not defined in the scope, throw error
        if (declaredType == null) {
            m.semantError(c.getFilename(), expr).println("Assignment to undeclared variable " + name + ".");
            set_type(TreeConstants.No_type);
            return TreeConstants.No_type;
        }

        // If actual type is SELF, change to appropriate type
        if (TreeConstants.SELF_TYPE.equals(actualType)) {
            actualType = c.getName();
        }

        // If declared type is not a parent of the actual type, throw error
        if (!TreeConstants.No_type.equals(actualType) && !m.isSubtype(declaredType, actualType)) {
            m.semantError(c.getFilename(), expr).println("Type " + actualType +
                    " of assigned expression does not conform to declared type " +
                    declaredType + " of identifier " + name + ".");
        }

        // Always set type and return declaredType to recover from error
        set_type(actualType);
        return actualType;
    }
}


/** Defines AST constructor 'static_dispatch'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class static_dispatch extends Expression {
    protected Expression expr;
    protected AbstractSymbol type_name;
    protected AbstractSymbol name;
    protected Expressions actual;
    /** Creates "static_dispatch" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for expr
      * @param a1 initial value for type_name
      * @param a2 initial value for name
      * @param a3 initial value for actual
      */
    public static_dispatch(int lineNumber, Expression a1, AbstractSymbol a2, AbstractSymbol a3, Expressions a4) {
        super(lineNumber);
        expr = a1;
        type_name = a2;
        name = a3;
        actual = a4;
    }
    public TreeNode copy() {
        return new static_dispatch(lineNumber, (Expression)expr.copy(), copy_AbstractSymbol(type_name), copy_AbstractSymbol(name), (Expressions)actual.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "static_dispatch\n");
        expr.dump(out, n+2);
        dump_AbstractSymbol(out, n+2, type_name);
        dump_AbstractSymbol(out, n+2, name);
        actual.dump(out, n+2);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_static_dispatch");
	expr.dump_with_types(out, n + 2);
        dump_AbstractSymbol(out, n + 2, type_name);
        dump_AbstractSymbol(out, n + 2, name);
        out.println(Utilities.pad(n + 2) + "(");
        for (Enumeration e = actual.getElements(); e.hasMoreElements();) {
	    ((Expression)e.nextElement()).dump_with_types(out, n + 2);
        }
        out.println(Utilities.pad(n + 2) + ")");
	dump_type(out, n);
    }

    public AbstractSymbol typeChecker(SymbolTable o, ClassTable m, class_c c) {

        // T0 is the return type of the expression that the method is dispatched on
        AbstractSymbol T0 = expr.typeChecker(o, m, c);

        // Make sure method is defined
        if (m.getClassByName(type_name) == null) {
            m.semantError(c.filename, expr).println("Static dispatch on undefined class " + type_name);
            set_type(TreeConstants.Object_);
            return TreeConstants.Object_;
        }

        // Change SELF_TYPE appropriately
        if (TreeConstants.SELF_TYPE.equals(T0)) {
            T0 = c.getName();
        }

        if (TreeConstants.No_type.equals(T0)) {
           m.semantError(c.getFilename(), expr).println("Static dispatch on type No_Type not allowed.");
           // Check the parameters inspite of getting an error. As per reference compiler's behavior.
           for (Enumeration e1 = actual.getElements(); e1.hasMoreElements();) {
               ((Expression)e1.nextElement()).typeChecker(o, m, c);
           }
           set_type(TreeConstants.Object_);
           return TreeConstants.Object_;
        }

        // Check that T0 is a subtype of type_name
        if (!m.isSubtype(type_name, T0)) {
            m.semantError(c.getFilename(), expr).println("Expression type " + T0 +
                    " does not conform to declared static dispatch type " + type_name + ".");
        }

        class_c TClass = m.getClassByName(type_name);
        method Tmethod = TClass.getMethodFromMethodTable(name);

        // Make sure method exists for the type_name class
        if (Tmethod == null) {
            m.semantError(c.getFilename(), expr).println("Static dispatch to undefined method " + name + ".");
        } else {

            Enumeration e1 = actual.getElements();
            Enumeration e2 = Tmethod.getFormals().getElements();

            // Check that each expression type is a subtype of the type of method formal
            while (e1.hasMoreElements() && e2.hasMoreElements()) {

                AbstractSymbol expType = ((Expression)e1.nextElement()).typeChecker(o, m, c);
                formalc methodFormal = ((formalc)e2.nextElement());

                if (TreeConstants.SELF_TYPE.equals(expType)) {
                    expType = c.getName();
                }

                if (!TreeConstants.No_type.equals(expType) && !m.isSubtype(methodFormal.getType(), expType)) {
                    m.semantError(c.getFilename(), expr).println("In call of method " + name + ", type " + expType +
                            " of parameter " + methodFormal.getName() + " does not conform to declared type " + methodFormal.getType() + ".");
                }
            }

            // If number of formals is not equal to the number of expression types, throw error
            if (e1.hasMoreElements() || e2.hasMoreElements()) {
                m.semantError(c.getFilename(), expr).println("Method " + name + " called with wrong number of arguments.");
            }
        }

        AbstractSymbol TrDash = Tmethod.getReturn_type();
        if (TreeConstants.SELF_TYPE.equals(TrDash)) {
            set_type(expr.get_type());
            return expr.get_type();
        } else {
            set_type(TrDash);
            return TrDash;
        }
    }
}


/** Defines AST constructor 'dispatch'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class dispatch extends Expression {
    protected Expression expr;
    protected AbstractSymbol name;
    protected Expressions actual;
    /** Creates "dispatch" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for expr
      * @param a1 initial value for name
      * @param a2 initial value for actual
      */
    public dispatch(int lineNumber, Expression a1, AbstractSymbol a2, Expressions a3) {
        super(lineNumber);
        expr = a1;
        name = a2;
        actual = a3;
    }
    public TreeNode copy() {
        return new dispatch(lineNumber, (Expression)expr.copy(), copy_AbstractSymbol(name), (Expressions)actual.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "dispatch\n");
        expr.dump(out, n + 2);
        dump_AbstractSymbol(out, n+2, name);
        actual.dump(out, n+2);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_dispatch");
	expr.dump_with_types(out, n + 2);
        dump_AbstractSymbol(out, n + 2, name);
        out.println(Utilities.pad(n + 2) + "(");
        for (Enumeration e = actual.getElements(); e.hasMoreElements();) {
	    ((Expression)e.nextElement()).dump_with_types(out, n + 2);
        }
        out.println(Utilities.pad(n + 2) + ")");
	dump_type(out, n);
    }

    public AbstractSymbol typeChecker(SymbolTable o, ClassTable m, class_c c) {

        // Same as dispatch except for conformance check

        // T0 is the return type of the expression that the method is dispatched on
        AbstractSymbol T0 = expr.typeChecker(o, m, c);
        // T0 dash is the class if T0 is self type OR the return type T0
        AbstractSymbol T0dash = TreeConstants.Object_;
        if (TreeConstants.SELF_TYPE.equals(T0)) {
            T0dash = (AbstractSymbol)o.lookup(TreeConstants.SELF_TYPE);
        } else {
            T0dash = T0;
        }

        if (TreeConstants.No_type.equals(T0dash)) {
            m.semantError(c.getFilename(), this).println("Dispatch on type " + T0dash.toString() + " not allowed.");
            // Check the parameters inspite of getting an error. As per reference compiler's behavior.
            for (Enumeration e1 = actual.getElements(); e1.hasMoreElements();) {
                ((Expression)e1.nextElement()).typeChecker(o, m, c);
            }
            set_type(TreeConstants.Object_);
            return TreeConstants.Object_;
        }
        class_c T0dashClass = m.getClassByName(T0dash);
        if (T0dashClass == null) {
            set_type(T0dash);
            return T0dash;
        }

        method T0method = T0dashClass.getMethodFromMethodTable(name);
        if (T0method == null) {
            m.semantError(c.getFilename(), expr).println("Dispatch to undefined method " + name + ".");
            set_type(T0dash);
            return T0dash;
        } else {
            Enumeration e1 = actual.getElements();
            Enumeration e2 = T0method.getFormals().getElements();
            while (e1.hasMoreElements() && e2.hasMoreElements()) {

                AbstractSymbol expType = ((Expression)e1.nextElement()).typeChecker(o, m, c);
                formalc methodFormal = ((formalc)e2.nextElement());

                if (TreeConstants.SELF_TYPE.equals(expType)) {
                    expType = c.getName();
                }

                if (!TreeConstants.No_type.equals(expType) && !m.isSubtype(methodFormal.getType(), expType)) {
                    m.semantError(c.getFilename(), expr).println("In call of method " + name + ", type " + expType +
                            " of parameter " + methodFormal.getName() + " does not conform to declared type " + methodFormal.getType() + ".");
                }
            }

            // Check if no. of formals and no. of expressions are equal.
            if (e1.hasMoreElements() || e2.hasMoreElements()) {
                m.semantError(c.getFilename(), expr).println("Method " + name + " called with wrong number of arguments.");
            }
        }

        AbstractSymbol TrDash = T0method.getReturn_type();
        if (TreeConstants.SELF_TYPE.equals(TrDash)) {
            set_type(T0dash);
            return T0dash;
        } else {
            set_type(TrDash);
            return TrDash;
        }
    }
}


/** Defines AST constructor 'cond'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class cond extends Expression {
    protected Expression pred;
    protected Expression then_exp;
    protected Expression else_exp;
    /** Creates "cond" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for pred
      * @param a1 initial value for then_exp
      * @param a2 initial value for else_exp
      */
    public cond(int lineNumber, Expression a1, Expression a2, Expression a3) {
        super(lineNumber);
        pred = a1;
        then_exp = a2;
        else_exp = a3;
    }
    public TreeNode copy() {
        return new cond(lineNumber, (Expression)pred.copy(), (Expression)then_exp.copy(), (Expression)else_exp.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "cond\n");
        pred.dump(out, n + 2);
        then_exp.dump(out, n + 2);
        else_exp.dump(out, n+2);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_cond");
	pred.dump_with_types(out, n + 2);
	then_exp.dump_with_types(out, n + 2);
	else_exp.dump_with_types(out, n + 2);
	dump_type(out, n);
    }

    public AbstractSymbol typeChecker(SymbolTable o, ClassTable m, class_c c) {

        AbstractSymbol typePred = pred.typeChecker(o, m, c);
        AbstractSymbol t1 = then_exp.typeChecker(o, m, c);
        AbstractSymbol t2 = else_exp.typeChecker(o, m, c);

        // Type pred has to be Bool
        if (!TreeConstants.Bool.equals(typePred)) {
            m.semantError(c.getFilename(), pred).println("Predicate of if statement does " +
                    "not contain a valid condition");
        }

        // Convert SELF_TYPE
        if (TreeConstants.SELF_TYPE.equals(t1)) {
            t1 = c.getName();
        }

        // Convert SELF_TYPE
        if (TreeConstants.SELF_TYPE.equals(t2)) {
            t2 = c.getName();
        }

        if (TreeConstants.No_type.equals(t1) || TreeConstants.No_type.equals(t2)) {
            set_type(TreeConstants.No_type);
            return TreeConstants.No_type;
        }

        // Least upper bound
        set_type(m.lub(t1, t2));
        return m.lub(t1,t2);
    }
}


/** Defines AST constructor 'loop'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class loop extends Expression {
    protected Expression pred;
    protected Expression body;
    /** Creates "loop" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for pred
      * @param a1 initial value for body
      */
    public loop(int lineNumber, Expression a1, Expression a2) {
        super(lineNumber);
        pred = a1;
        body = a2;
    }
    public TreeNode copy() {
        return new loop(lineNumber, (Expression)pred.copy(), (Expression)body.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "loop\n");
        pred.dump(out, n + 2);
        body.dump(out, n+2);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_loop");
	pred.dump_with_types(out, n + 2);
	body.dump_with_types(out, n + 2);
	dump_type(out, n);
    }

    public AbstractSymbol typeChecker (SymbolTable o, ClassTable m, class_c c) {
        AbstractSymbol typePred = pred.typeChecker(o, m, c);
        // Type of body doesn't matter
        body.typeChecker(o, m, c);

        if (!TreeConstants.Bool.equals(typePred)) {
            m.semantError(c.getFilename(), pred).println("Loop condition does not have type Bool.");
        }

        // Not body type as that would be too strict, if while loop is never entered
        set_type(TreeConstants.Object_);
        return TreeConstants.Object_;
    }
}


/** Defines AST constructor 'typcase'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class typcase extends Expression {
    protected Expression expr;
    protected Cases cases;
    /** Creates "typcase" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for expr
      * @param a1 initial value for cases
      */
    public typcase(int lineNumber, Expression a1, Cases a2) {
        super(lineNumber);
        expr = a1;
        cases = a2;
    }
    public TreeNode copy() {
        return new typcase(lineNumber, (Expression)expr.copy(), (Cases)cases.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "typcase\n");
        expr.dump(out, n + 2);
        cases.dump(out, n+2);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_typcase");
	expr.dump_with_types(out, n + 2);
        for (Enumeration e = cases.getElements(); e.hasMoreElements();) {
	    ((Case)e.nextElement()).dump_with_types(out, n + 2);
        }
	dump_type(out, n);
    }

    public AbstractSymbol typeChecker(SymbolTable o, ClassTable m, class_c c) {
        expr.typeChecker(o, m, c);

        List<String> typeDecls = new ArrayList<String>();
        List<String> expTypes = new ArrayList<String>();
        for (Enumeration e = cases.getElements(); e.hasMoreElements();) {
            branch br = (branch)e.nextElement();
            if (typeDecls.contains(br.getType_decl().toString())) {
                m.semantError(c.getFilename(), br).println("Duplicate branch " + br.getType_decl().toString() + " in case statement.");
                // Type check the branch inspite of being duplicate. According to reference compiler's behavior.
                br.typeChecker(o, m, c);
            } else {
                typeDecls.add(br.getType_decl().toString());
                AbstractSymbol type = br.typeChecker(o, m, c);
                if (!TreeConstants.No_type.equals(type)) {
                    expTypes.add(br.typeChecker(o, m, c).toString());
                }
            }
        }

        if (expTypes.isEmpty()) {
            set_type(TreeConstants.Object_);
            return TreeConstants.Object_;
        }

        AbstractSymbol caseType = m.lubMultiple(expTypes);
        set_type(caseType);
        return caseType;
    }
}


/** Defines AST constructor 'block'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class block extends Expression {
    protected Expressions body;
    /** Creates "block" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for body
      */
    public block(int lineNumber, Expressions a1) {
        super(lineNumber);
        body = a1;
    }
    public TreeNode copy() {
        return new block(lineNumber, (Expressions)body.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "block\n");
        body.dump(out, n+2);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_block");
        for (Enumeration e = body.getElements(); e.hasMoreElements();) {
	    ((Expression)e.nextElement()).dump_with_types(out, n + 2);
        }
	dump_type(out, n);
    }

    public AbstractSymbol typeChecker(SymbolTable o, ClassTable m, class_c c) {
        AbstractSymbol typeEx = TreeConstants.No_type;
        for (Enumeration e = body.getElements(); e.hasMoreElements();) {
            Expression ex = (Expression)e.nextElement();
            typeEx = ex.typeChecker(o, m, c);
        }

        // Block type is the type of the last expression in the block
        set_type(typeEx);
        return typeEx;
    }
}


/** Defines AST constructor 'let'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class let extends Expression {
    protected AbstractSymbol identifier;
    protected AbstractSymbol type_decl;
    protected Expression init;
    protected Expression body;
    /** Creates "let" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for identifier
      * @param a1 initial value for type_decl
      * @param a2 initial value for init
      * @param a3 initial value for body
      */
    public let(int lineNumber, AbstractSymbol a1, AbstractSymbol a2, Expression a3, Expression a4) {
        super(lineNumber);
        identifier = a1;
        type_decl = a2;
        init = a3;
        body = a4;
    }
    public TreeNode copy() {
        return new let(lineNumber, copy_AbstractSymbol(identifier), copy_AbstractSymbol(type_decl), (Expression)init.copy(), (Expression)body.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "let\n");
        dump_AbstractSymbol(out, n+2, identifier);
        dump_AbstractSymbol(out, n + 2, type_decl);
        init.dump(out, n+2);
        body.dump(out, n + 2);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_let");
	dump_AbstractSymbol(out, n + 2, identifier);
	dump_AbstractSymbol(out, n + 2, type_decl);
	init.dump_with_types(out, n + 2);
	body.dump_with_types(out, n + 2);
	dump_type(out, n);
    }

    public AbstractSymbol typeChecker(SymbolTable o, ClassTable m, class_c c) {

        if (TreeConstants.self.equals(identifier)) {
            m.semantError(c.getFilename(), this).println("'self' cannot be bound in a 'let' expression.");
            set_type(TreeConstants.No_type);
            return TreeConstants.No_type;
        }

        AbstractSymbol T0Dash;
        if (TreeConstants.SELF_TYPE.equals(type_decl)) {
            T0Dash = c.getName();
        } else {
            T0Dash = type_decl;
        }

        AbstractSymbol T1 = init.typeChecker(o, m, c);

        if (m.getClassByName(T0Dash) == null) {
            m.semantError(c.getFilename(), this).println("Class " + T0Dash.toString() +
                    " of let-bound identifier " + identifier + " is undefined.");
            T0Dash = TreeConstants.No_type;
        } else if (!TreeConstants.No_type.equals(T1)) {

            if (TreeConstants.SELF_TYPE.equals(T1)) {
                T1 = c.getName();
            }

            if (!m.isSubtype(T0Dash, T1)) {
                // Continue type checking the rest of the let statement even if this has an error. As per reference compiler.
                m.semantError(c.filename, this).println("Inferred type " + T1 + " in initialization of " +
                        identifier + " does not conform to identifier's declared type " + type_decl + ".");
            }
        }

        o.enterScope();
        o.addId(identifier, T0Dash);
        AbstractSymbol bodyType = body.typeChecker(o, m, c);
        o.exitScope();

        set_type(bodyType);
        return bodyType;
    }
}


/** Defines AST constructor 'plus'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class plus extends Expression {
    protected Expression e1;
    protected Expression e2;
    /** Creates "plus" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for e1
      * @param a1 initial value for e2
      */
    public plus(int lineNumber, Expression a1, Expression a2) {
        super(lineNumber);
        e1 = a1;
        e2 = a2;
    }
    public TreeNode copy() {
        return new plus(lineNumber, (Expression)e1.copy(), (Expression)e2.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "plus\n");
        e1.dump(out, n+2);
        e2.dump(out, n+2);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_plus");
	e1.dump_with_types(out, n + 2);
	e2.dump_with_types(out, n + 2);
	dump_type(out, n);
    }

    public AbstractSymbol typeChecker(SymbolTable o, ClassTable m, class_c c) {

        // Check subexpressions, no change in environment
        AbstractSymbol type1 = e1.typeChecker(o, m, c);
        AbstractSymbol type2 = e2.typeChecker(o, m, c);

        // If both expressions don't have type Int, throw error
        if (!TreeConstants.Int.equals(type1) || !TreeConstants.Int.equals(type2)) {
            m.semantError(c.getFilename(), e1).println("non-Int arguments: " + type1.toString() +
                    " + " + type2.toString() + ".");
        }

        // Always set type and return Int to recover from error
        set_type(TreeConstants.Int);
        return TreeConstants.Int;
    }

}


/** Defines AST constructor 'sub'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class sub extends Expression {
    protected Expression e1;
    protected Expression e2;
    /** Creates "sub" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for e1
      * @param a1 initial value for e2
      */
    public sub(int lineNumber, Expression a1, Expression a2) {
        super(lineNumber);
        e1 = a1;
        e2 = a2;
    }
    public TreeNode copy() {
        return new sub(lineNumber, (Expression)e1.copy(), (Expression)e2.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "sub\n");
        e1.dump(out, n + 2);
        e2.dump(out, n+2);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_sub");
	e1.dump_with_types(out, n + 2);
	e2.dump_with_types(out, n + 2);
	dump_type(out, n);
    }

    public AbstractSymbol typeChecker(SymbolTable o, ClassTable m, class_c c) {

        // Check subexpressions, no change in environment
        AbstractSymbol type1 = e1.typeChecker(o, m, c);
        AbstractSymbol type2 = e2.typeChecker(o, m, c);

        // If both expressions don't have type Int, throw error
        if (!TreeConstants.Int.equals(type1) || !TreeConstants.Int.equals(type2)) {
            m.semantError(c.getFilename(), e1).println("non-Int arguments: " + type1.toString() +
                    " + " + type2.toString() + ".");
        }

        // Always set type and return Int to recover from error
        set_type(TreeConstants.Int);
        return TreeConstants.Int;
    }
}


/** Defines AST constructor 'mul'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class mul extends Expression {
    protected Expression e1;
    protected Expression e2;
    /** Creates "mul" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for e1
      * @param a1 initial value for e2
      */
    public mul(int lineNumber, Expression a1, Expression a2) {
        super(lineNumber);
        e1 = a1;
        e2 = a2;
    }
    public TreeNode copy() {
        return new mul(lineNumber, (Expression)e1.copy(), (Expression)e2.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "mul\n");
        e1.dump(out, n + 2);
        e2.dump(out, n+2);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_mul");
	e1.dump_with_types(out, n + 2);
	e2.dump_with_types(out, n + 2);
	dump_type(out, n);
    }

    public AbstractSymbol typeChecker(SymbolTable o, ClassTable m, class_c c) {

        // Check subexpressions, no change in environment
        AbstractSymbol type1 = e1.typeChecker(o, m, c);
        AbstractSymbol type2 = e2.typeChecker(o, m, c);

        // If both expressions don't have type Int, throw error
        if (!TreeConstants.Int.equals(type1) || !TreeConstants.Int.equals(type2)) {
            m.semantError(c.getFilename(), e1).println("non-Int arguments: " + type1.toString() +
                    " + " + type2.toString() + ".");
        }

        // Always set type and return Int to recover from error
        set_type(TreeConstants.Int);
        return TreeConstants.Int;
    }
}


/** Defines AST constructor 'divide'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class divide extends Expression {
    protected Expression e1;
    protected Expression e2;
    /** Creates "divide" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for e1
      * @param a1 initial value for e2
      */
    public divide(int lineNumber, Expression a1, Expression a2) {
        super(lineNumber);
        e1 = a1;
        e2 = a2;
    }
    public TreeNode copy() {
        return new divide(lineNumber, (Expression)e1.copy(), (Expression)e2.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "divide\n");
        e1.dump(out, n + 2);
        e2.dump(out, n+2);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_divide");
	e1.dump_with_types(out, n + 2);
	e2.dump_with_types(out, n + 2);
	dump_type(out, n);
    }

    public AbstractSymbol typeChecker(SymbolTable o, ClassTable m, class_c c) {

        // Check subexpressions, no change in environment
        AbstractSymbol type1 = e1.typeChecker(o, m, c);
        AbstractSymbol type2 = e2.typeChecker(o, m, c);

        // If both expressions don't have type Int, throw error
        if (!TreeConstants.Int.equals(type1) || !TreeConstants.Int.equals(type2)) {
            m.semantError(c.getFilename(), e1).println("non-Int arguments: " + type1.toString() +
                    " + " + type2.toString() + ".");
        }

        // Always set type and return Int to recover from error
        set_type(TreeConstants.Int);
        return TreeConstants.Int;
    }
}


/** Defines AST constructor 'neg'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class neg extends Expression {
    protected Expression e1;
    /** Creates "neg" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for e1
      */
    public neg(int lineNumber, Expression a1) {
        super(lineNumber);
        e1 = a1;
    }
    public TreeNode copy() {
        return new neg(lineNumber, (Expression)e1.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "neg\n");
        e1.dump(out, n+2);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_neg");
	e1.dump_with_types(out, n + 2);
	dump_type(out, n);
    }

    public AbstractSymbol typeChecker(SymbolTable o, ClassTable m, class_c c) {

        // Check subexpressions, no change in environment
        AbstractSymbol type1 = e1.typeChecker(o, m, c);

        // If expression is not Int, throw an error
        if (!TreeConstants.Int.equals(type1)) {
            m.semantError(c.getFilename(), e1).println("non-Int arguments: " + type1.toString() + ".");
        }

        // Always set type and return Int to recover from error
        set_type(TreeConstants.Int);
        return TreeConstants.Int;
    }
}


/** Defines AST constructor 'lt'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class lt extends Expression {
    protected Expression e1;
    protected Expression e2;
    /** Creates "lt" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for e1
      * @param a1 initial value for e2
      */
    public lt(int lineNumber, Expression a1, Expression a2) {
        super(lineNumber);
        e1 = a1;
        e2 = a2;
    }
    public TreeNode copy() {
        return new lt(lineNumber, (Expression)e1.copy(), (Expression)e2.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "lt\n");
        e1.dump(out, n+2);
        e2.dump(out, n+2);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_lt");
	e1.dump_with_types(out, n + 2);
	e2.dump_with_types(out, n + 2);
	dump_type(out, n);
    }

    public AbstractSymbol typeChecker(SymbolTable o, ClassTable m, class_c c) {
        AbstractSymbol type1 = e1.typeChecker(o,m,c);
        AbstractSymbol type2 = e2.typeChecker(o,m,c);

        // If both expressions don't have type Int, throw error
        if (!TreeConstants.Int.equals(type1) || !TreeConstants.Int.equals(type2)) {
            m.semantError(c.getFilename(), e1).println("non-Int arguments: " + type1.toString() +
                    " < " + type2.toString() + ".");
        }

        set_type(TreeConstants.Bool);
        return TreeConstants.Bool;
    }

}


/** Defines AST constructor 'eq'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class eq extends Expression {
    protected Expression e1;
    protected Expression e2;
    /** Creates "eq" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for e1
      * @param a1 initial value for e2
      */
    public eq(int lineNumber, Expression a1, Expression a2) {
        super(lineNumber);
        e1 = a1;
        e2 = a2;
    }
    public TreeNode copy() {
        return new eq(lineNumber, (Expression)e1.copy(), (Expression)e2.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "eq\n");
        e1.dump(out, n + 2);
        e2.dump(out, n+2);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_eq");
	e1.dump_with_types(out, n + 2);
	e2.dump_with_types(out, n + 2);
	dump_type(out, n);
    }

    public AbstractSymbol typeChecker(SymbolTable o, ClassTable m, class_c c) {
        AbstractSymbol type1 = e1.typeChecker(o,m,c);
        AbstractSymbol type2 = e2.typeChecker(o,m,c);

            if (TreeConstants.Int.equals(type1) || TreeConstants.Bool.equals(type1) || TreeConstants.Str.equals(type1) ||
                    TreeConstants.Int.equals(type2) || TreeConstants.Bool.equals(type2) || TreeConstants.Str.equals(type2)) {
                if (!type1.equals(type2)) {
                    m.semantError(c.getFilename(), e1).println("Unmatched comparison types: " + type1.toString() +
                            " = " + type2.toString() + ".");
                }
            }

        set_type(TreeConstants.Bool);
        return TreeConstants.Bool;
    }
}


/** Defines AST constructor 'leq'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class leq extends Expression {
    protected Expression e1;
    protected Expression e2;
    /** Creates "leq" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for e1
      * @param a1 initial value for e2
      */
    public leq(int lineNumber, Expression a1, Expression a2) {
        super(lineNumber);
        e1 = a1;
        e2 = a2;
    }
    public TreeNode copy() {
        return new leq(lineNumber, (Expression)e1.copy(), (Expression)e2.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "leq\n");
        e1.dump(out, n + 2);
        e2.dump(out, n + 2);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_leq");
	e1.dump_with_types(out, n + 2);
	e2.dump_with_types(out, n + 2);
	dump_type(out, n);
    }

    public AbstractSymbol typeChecker(SymbolTable o, ClassTable m, class_c c) {
        AbstractSymbol type1 = e1.typeChecker(o,m,c);
        AbstractSymbol type2 = e2.typeChecker(o,m,c);

        // If both expressions don't have type Int, throw error
        if (!TreeConstants.Int.equals(type1) || !TreeConstants.Int.equals(type2)) {
            m.semantError(c.getFilename(), e1).println("non-Int arguments: " + type1.toString() +
                    " <= " + type2.toString() + ".");
        }

        set_type(TreeConstants.Bool);
        return TreeConstants.Bool;
    }
}


/** Defines AST constructor 'comp'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class comp extends Expression {
    protected Expression e1;
    /** Creates "comp" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for e1
      */
    public comp(int lineNumber, Expression a1) {
        super(lineNumber);
        e1 = a1;
    }
    public TreeNode copy() {
        return new comp(lineNumber, (Expression)e1.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "comp\n");
        e1.dump(out, n+2);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_comp");
	e1.dump_with_types(out, n + 2);
	dump_type(out, n);
    }

    public AbstractSymbol typeChecker(SymbolTable o, ClassTable m, class_c c) {

        // Check subexpressions(comparison operators), no change in environment
        AbstractSymbol type1 = e1.typeChecker(o, m, c);

        if (!TreeConstants.Bool.equals(type1)) {
            m.semantError(c.getFilename(), e1).println("Invalid condition type: " + type1 + ".");
        }

        // Always set type and return type1 to recover from error
        set_type(type1);
        return type1;
    }
}


/** Defines AST constructor 'int_const'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class int_const extends Expression {
    protected AbstractSymbol token;
    /** Creates "int_const" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for token
      */
    public int_const(int lineNumber, AbstractSymbol a1) {
        super(lineNumber);
        token = a1;
    }
    public TreeNode copy() {
        return new int_const(lineNumber, copy_AbstractSymbol(token));
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "int_const\n");
        dump_AbstractSymbol(out, n + 2, token);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_int");
	dump_AbstractSymbol(out, n + 2, token);
	dump_type(out, n);
    }

    public AbstractSymbol typeChecker (SymbolTable o, ClassTable m, class_c c) {
        set_type(TreeConstants.Int);
        return TreeConstants.Int;
    }
}


/** Defines AST constructor 'bool_const'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class bool_const extends Expression {
    protected Boolean val;
    /** Creates "bool_const" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for val
      */
    public bool_const(int lineNumber, Boolean a1) {
        super(lineNumber);
        val = a1;
    }
    public TreeNode copy() {
        return new bool_const(lineNumber, copy_Boolean(val));
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "bool_const\n");
        dump_Boolean(out, n + 2, val);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_bool");
	dump_Boolean(out, n + 2, val);
	dump_type(out, n);
    }

    public AbstractSymbol typeChecker (SymbolTable o, ClassTable m, class_c c) {
        set_type(TreeConstants.Bool);
        return TreeConstants.Bool;
    }
}


/** Defines AST constructor 'string_const'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class string_const extends Expression {
    protected AbstractSymbol token;
    /** Creates "string_const" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for token
      */
    public string_const(int lineNumber, AbstractSymbol a1) {
        super(lineNumber);
        token = a1;
    }
    public TreeNode copy() {
        return new string_const(lineNumber, copy_AbstractSymbol(token));
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "string_const\n");
        dump_AbstractSymbol(out, n + 2, token);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_string");
	out.print(Utilities.pad(n + 2) + "\"");
	Utilities.printEscapedString(out, token.getString());
	out.println("\"");
	dump_type(out, n);
    }

    public AbstractSymbol typeChecker(SymbolTable o, ClassTable m, class_c c) {
        set_type(TreeConstants.Str);
        return TreeConstants.Str;
    }
}


/** Defines AST constructor 'new_'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class new_ extends Expression {
    protected AbstractSymbol type_name;
    /** Creates "new_" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for type_name
      */
    public new_(int lineNumber, AbstractSymbol a1) {
        super(lineNumber);
        type_name = a1;
    }
    public TreeNode copy() {
        return new new_(lineNumber, copy_AbstractSymbol(type_name));
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "new_\n");
        dump_AbstractSymbol(out, n + 2, type_name);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_new");
	dump_AbstractSymbol(out, n + 2, type_name);
	dump_type(out, n);
    }

    public AbstractSymbol typeChecker(SymbolTable o, ClassTable m, class_c c) {
        if (TreeConstants.SELF_TYPE.equals(type_name)) {
            set_type(c.getName());
            return c.getName();
        } else if (m.getClassByName(type_name) == null) {
            m.semantError(c.filename, this).println("'new' used with undefined class " + type_name + ".");
            set_type(TreeConstants.No_type);
            return TreeConstants.No_type;
        } else {
                set_type(type_name);
                return type_name;
        }
    }
}


/** Defines AST constructor 'isvoid'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class isvoid extends Expression {
    protected Expression e1;
    /** Creates "isvoid" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for e1
      */
    public isvoid(int lineNumber, Expression a1) {
        super(lineNumber);
        e1 = a1;
    }
    public TreeNode copy() {
        return new isvoid(lineNumber, (Expression)e1.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "isvoid\n");
        e1.dump(out, n + 2);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_isvoid");
	e1.dump_with_types(out, n + 2);
	dump_type(out, n);
    }

    public AbstractSymbol typeChecker (SymbolTable o, ClassTable m, class_c c) {
        // Check subexpression, no change in environment
        e1.typeChecker(o, m, c);
        set_type(TreeConstants.Bool);
        return TreeConstants.Bool;
    }
}


/** Defines AST constructor 'no_expr'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class no_expr extends Expression {
    /** Creates "no_expr" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      */
    public no_expr(int lineNumber) {
        super(lineNumber);
    }
    public TreeNode copy() {
        return new no_expr(lineNumber);
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "no_expr\n");
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_no_expr");
	dump_type(out, n);
    }

    public AbstractSymbol typeChecker (SymbolTable o, ClassTable m, class_c c) {
        set_type(TreeConstants.No_type);
        return TreeConstants.No_type;
    }

}


/** Defines AST constructor 'object'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class object extends Expression {
    protected AbstractSymbol name;
    /** Creates "object" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for name
      */
    public object(int lineNumber, AbstractSymbol a1) {
        super(lineNumber);
        name = a1;
    }
    public TreeNode copy() {
        return new object(lineNumber, copy_AbstractSymbol(name));
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "object\n");
        dump_AbstractSymbol(out, n+2, name);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_object");
	dump_AbstractSymbol(out, n + 2, name);
	dump_type(out, n);
    }

    public AbstractSymbol typeChecker(SymbolTable o, ClassTable m, class_c c) {
        if (o.lookup(name) != null) {
            set_type((AbstractSymbol)o.lookup(name));
            return (AbstractSymbol)o.lookup(name);
        } else {
            m.semantError(c.getFilename(), this).println("Undeclared identifier " + name + ".");
            set_type(TreeConstants.No_type);
            return TreeConstants.No_type;
        }
    }
}