import java.io.PrintStream;
import java.lang.String;
import java.util.*;

/** This class may be used to contain the semantic information such as
 * the inheritance graph.  You may use it or not as you like: it is only
 * here to provide a container for the supplied methods.  */
class ClassTable {
    private int semantErrors;
    private PrintStream errorStream;

    // Class names mapped to the class definition
    private HashMap<String, class_c> classNamesTable = new HashMap<String, class_c>();
    // Class name mapped to its parent definition
    private HashMap<String, AbstractSymbol> inheritanceTable = new HashMap<String, AbstractSymbol>();
    // All basic classes
    private HashMap<String, class_c> basicClassTable = new HashMap<String, class_c>();
    // Classes that cannot be inherited
    private List<String> invalidInherit = new ArrayList<String>();
    // Copy of object_class from basic classes for comparison. Could not access the object_class directly
    private class_c object_cl_copy;

    /** Creates data structures representing basic Cool classes (Object,
     * IO, Int, Bool, String).  Please note: as is this method does not
     * do anything useful; you will need to edit it to make if do what
     * you want.
     * */
    private void installBasicClasses() {
	AbstractSymbol filename 
	    = AbstractTable.stringtable.addString("<basic class>");
	
	// The following demonstrates how to create dummy parse trees to
	// refer to basic Cool classes.  There's no need for method
	// bodies -- these are already built into the runtime system.

	// IMPORTANT: The results of the following expressions are
	// stored in local variables.  You will want to do something
	// with those variables at the end of this method to make this
	// code meaningful.

	// The Object class has no parent class. Its methods are
	//        cool_abort() : Object    aborts the program
	//        type_name() : Str        returns a string representation 
	//                                 of class name
	//        copy() : SELF_TYPE       returns a copy of the object

	class_c Object_class = 
	    new class_c(0, 
		       TreeConstants.Object_, 
		       TreeConstants.No_class,
		       new Features(0)
			   .appendElement(new method(0, 
					      TreeConstants.cool_abort, 
					      new Formals(0), 
					      TreeConstants.Object_, 
					      new no_expr(0)))
			   .appendElement(new method(0,
					      TreeConstants.type_name,
					      new Formals(0),
					      TreeConstants.Str,
					      new no_expr(0)))
			   .appendElement(new method(0,
					      TreeConstants.copy,
					      new Formals(0),
					      TreeConstants.SELF_TYPE,
					      new no_expr(0))),
		       filename);
	
	// The IO class inherits from Object. Its methods are
	//        out_string(Str) : SELF_TYPE  writes a string to the output
	//        out_int(Int) : SELF_TYPE      "    an int    "  "     "
	//        in_string() : Str            reads a string from the input
	//        in_int() : Int                "   an int     "  "     "

	class_c IO_class = 
	    new class_c(0,
		       TreeConstants.IO,
		       TreeConstants.Object_,
		       new Features(0)
			   .appendElement(new method(0,
					      TreeConstants.out_string,
					      new Formals(0)
						  .appendElement(new formalc(0,
								     TreeConstants.arg,
								     TreeConstants.Str)),
					      TreeConstants.SELF_TYPE,
					      new no_expr(0)))
			   .appendElement(new method(0,
					      TreeConstants.out_int,
					      new Formals(0)
						  .appendElement(new formalc(0,
								     TreeConstants.arg,
								     TreeConstants.Int)),
					      TreeConstants.SELF_TYPE,
					      new no_expr(0)))
			   .appendElement(new method(0,
					      TreeConstants.in_string,
					      new Formals(0),
					      TreeConstants.Str,
					      new no_expr(0)))
			   .appendElement(new method(0,
					      TreeConstants.in_int,
					      new Formals(0),
					      TreeConstants.Int,
					      new no_expr(0))),
		       filename);

	// The Int class has no methods and only a single attribute, the
	// "val" for the integer.

	class_c Int_class = 
	    new class_c(0,
		       TreeConstants.Int,
		       TreeConstants.Object_,
		       new Features(0)
			   .appendElement(new attr(0,
					    TreeConstants.val,
					    TreeConstants.prim_slot,
					    new no_expr(0))),
		       filename);

	// Bool also has only the "val" slot.
	class_c Bool_class = 
	    new class_c(0,
		       TreeConstants.Bool,
		       TreeConstants.Object_,
		       new Features(0)
			   .appendElement(new attr(0,
					    TreeConstants.val,
					    TreeConstants.prim_slot,
					    new no_expr(0))),
		       filename);

	// The class Str has a number of slots and operations:
	//       val                              the length of the string
	//       str_field                        the string itself
	//       length() : Int                   returns length of the string
	//       concat(arg: Str) : Str           performs string concatenation
	//       substr(arg: Int, arg2: Int): Str substring selection

	class_c Str_class =
	    new class_c(0,
		       TreeConstants.Str,
		       TreeConstants.Object_,
		       new Features(0)
			   .appendElement(new attr(0,
					    TreeConstants.val,
					    TreeConstants.Int,
					    new no_expr(0)))
			   .appendElement(new attr(0,
					    TreeConstants.str_field,
					    TreeConstants.prim_slot,
					    new no_expr(0)))
			   .appendElement(new method(0,
					      TreeConstants.length,
					      new Formals(0),
					      TreeConstants.Int,
					      new no_expr(0)))
			   .appendElement(new method(0,
					      TreeConstants.concat,
					      new Formals(0)
						  .appendElement(new formalc(0,
								     TreeConstants.arg, 
								     TreeConstants.Str)),
					      TreeConstants.Str,
					      new no_expr(0)))
			   .appendElement(new method(0,
					      TreeConstants.substr,
					      new Formals(0)
						  .appendElement(new formalc(0,
								     TreeConstants.arg,
								     TreeConstants.Int))
						  .appendElement(new formalc(0,
								     TreeConstants.arg2,
								     TreeConstants.Int)),
					      TreeConstants.Str,
					      new no_expr(0))),
		       filename);

        basicClassTable.put(Int_class.getName().toString(), Int_class);
        basicClassTable.put(Str_class.getName().toString(), Str_class);
        basicClassTable.put(Object_class.getName().toString(), Object_class);
        basicClassTable.put(Bool_class.getName().toString(), Bool_class);
        basicClassTable.put(IO_class.getName().toString(), IO_class);

        object_cl_copy = Object_class;

        invalidInherit.add(Int_class.getName().toString());
        invalidInherit.add(Str_class.getName().toString());
        invalidInherit.add(Bool_class.getName().toString());

        inheritanceTable.put(IO_class.getName().toString(), Object_class.getName());
        inheritanceTable.put(Int_class.getName().toString(), Object_class.getName());
        inheritanceTable.put(Bool_class.getName().toString(), Object_class.getName());
        inheritanceTable.put(Str_class.getName().toString(), Object_class.getName());
    }

    /**
     * Constructor for classTable. Creates inheritance table and class name table.
     * Also performs some semantic checks on classes such as multiple defined classes,
     * inheritance and redefinition of basic classes etc.
     * @param cls list of classes
     */
    public ClassTable(Classes cls) {
	    semantErrors = 0;
	    errorStream = System.err;

        installBasicClasses();
        invalidInherit.add(TreeConstants.SELF_TYPE.toString());

        // Populate the classNamesTable with all classes. (Class names are global
        // and do not need to be defined before usage)
        for (Enumeration e = cls.getElements(); e.hasMoreElements();) {
            class_c coolClass = (class_c)e.nextElement();
            if (classNamesTable.containsKey(coolClass.getName().toString())) {
                semantError(coolClass).println("Class " + coolClass.getName().toString() + " was previously defined.");
            } else if (basicClassTable.containsKey(coolClass.getName().toString()) ||
                    (TreeConstants.SELF_TYPE.toString()).equals(coolClass.getName().toString())) {
                semantError(coolClass).println("Redefinition of basic class " + coolClass.getName().toString() + ".");
            } else  {
                classNamesTable.put(coolClass.getName().toString(), coolClass);
            }
        }
        // Add all basic classes AFTER first pass to avoid basic classes to be caught in
        // "previously defined class" error
        classNamesTable.putAll(basicClassTable);

        // Check preliminary inheritance conditions - does not inherit from basic classes and parent definition exists
        for (Map.Entry<String, class_c> entry : classNamesTable.entrySet()) {
            class_c coolClass = (class_c)entry.getValue();
            String className = entry.getKey();
            AbstractSymbol classParent = coolClass.getParent();

            if (invalidInherit.contains(classParent.toString())) {
                semantError(coolClass).println("Class " + className + " cannot inherit class " +
                        classParent.toString() + ".");
            } else if (!(classNamesTable.containsKey(classParent.toString())) &&
                    !(className.equals(object_cl_copy.getName().toString()))) {
                semantError(coolClass).println("Class " + className + " inherits from an undefined class "
                        + classParent.toString() + ".");
            } else {
                inheritanceTable.put(className, classParent);
            }
        }

        // Check for cycle in the inheritence tree
        for (Map.Entry<String, AbstractSymbol> entry : inheritanceTable.entrySet()) {
            String className = entry.getKey();
            AbstractSymbol classParent = entry.getValue();
            AbstractSymbol currentParent = classParent;
            List<String> listOfChildren = new ArrayList<String>();
            listOfChildren.add(className);
            while (currentParent != object_cl_copy.getName()
                    && !(className.equals(object_cl_copy.getName().toString()))) {
                if (listOfChildren.contains(currentParent.toString())) {
                    semantError(classNamesTable.get(className)).println("Class " + className
                            + " or an ancestor of Class " + className + ", is involved in an inheritance cycle.");
                    break;
                } else {
                        listOfChildren.add(currentParent.toString());
                        currentParent = inheritanceTable.get(currentParent.toString());
                }
            }
        }
    }

    /** Prints line number and file name of the given class.
     *
     * Also increments semantic error count.
     *
     * @param c the class
     * @return a print stream to which the rest of the error message is
     * to be printed.
     *
     * */
    public PrintStream semantError(class_c c) {
	return semantError(c.getFilename(), c);
    }

    /** Prints the file name and the line number of the given tree node.
     *
     * Also increments semantic error count.
     *
     * @param filename the file name
     * @param t the tree node
     * @return a print stream to which the rest of the error message is
     * to be printed.
     *
     * */
    public PrintStream semantError(AbstractSymbol filename, TreeNode t) {
	errorStream.print(filename + ":" + t.getLineNumber() + ": ");
	return semantError();
    }

    /** Increments semantic error count and returns the print stream for
     * error messages.
     *
     * @return a print stream to which the error message is
     * to be printed.
     *
     * */
    public PrintStream semantError() {
        semantErrors++;
        return errorStream;
    }

    /** Returns true if there are any static semantic errors. */
    public boolean errors() {
	return semantErrors != 0;
    }

    public HashMap<String, class_c> getClassNamesTable () {
        return classNamesTable;
    }

    public boolean isSubtype (AbstractSymbol parent, AbstractSymbol child) {
        if (parent.equals(child)) {
            return true;
        }

        String currentParentName = inheritanceTable.get(child.toString()).toString();
        while ( currentParentName != object_cl_copy.getName().toString() && child != object_cl_copy.getName()) {
            if (currentParentName == parent.toString()) {
                    return true;
            }
            currentParentName = inheritanceTable.get(currentParentName).toString();
        }
        return false;
    }

    /**
     * Least upper bound of classes
     * @param class1
     * @param class2
     * @return least upper bound
     */
    public AbstractSymbol lub (AbstractSymbol class1, AbstractSymbol class2) {
        if (class1.equals(class2)) {
            return class1;
        }

        if (TreeConstants.Object_.equals(class1) || TreeConstants.Object_.equals(class2)) {
            return TreeConstants.Object_;
        }

        AbstractSymbol currentParent1Name = inheritanceTable.get(class1.toString());
        List<AbstractSymbol> class1Ancestors = new ArrayList<AbstractSymbol>();
        class1Ancestors.add(class1);
        while (currentParent1Name.toString() != object_cl_copy.getName().toString()) {
            class1Ancestors.add(currentParent1Name);
            currentParent1Name = inheritanceTable.get(currentParent1Name.toString());
        }

        AbstractSymbol currentParent2Name = inheritanceTable.get(class2.toString());
        List<AbstractSymbol> class2Ancestors = new ArrayList<AbstractSymbol>();
        class2Ancestors.add(class2);
        while (currentParent2Name.toString() != object_cl_copy.getName().toString()) {
            class2Ancestors.add(currentParent2Name);
            currentParent2Name = inheritanceTable.get(currentParent2Name.toString());
        }

        for (AbstractSymbol ancestor1 : class1Ancestors) {
            for (AbstractSymbol ancestor2 : class2Ancestors) {
                if (ancestor1.equals(ancestor2)) {
                    return ancestor1;
                }
            }
        }
        return TreeConstants.Object_;
    }

    /**
     * Finds least upper bound of list of classes
     * @param types list of types (classes)
     * @return least upper bound
     */
    public AbstractSymbol lubMultiple(List<String> types) {
        List<String> typesCopy = types;
        AbstractSymbol lubComputed = TreeConstants.Object_;

        if (typesCopy.size() > 1) {
            class_c type1 = classNamesTable.get(typesCopy.get(0));
            class_c type2 = classNamesTable.get(typesCopy.get(1));
            lubComputed = lub(type1.getName(), type2.getName());
            typesCopy.remove(0);
            typesCopy.remove(0);
        } else {
            return classNamesTable.get(types.get(0)).getName();
        }

        while (!typesCopy.isEmpty()) {
            lubComputed = lub(classNamesTable.get(typesCopy.get(0)).getName(), lubComputed);
            typesCopy.remove(0);
        }

        return lubComputed;
    }

    /**
     * Get parent class of the child passed in
     * @param child child class
     * @return
     */
    public class_c getParentClass(class_c child) {
        return classNamesTable.get(inheritanceTable.get(child.getName().toString()).toString());
    }

    /**
     * Checks if class is a basic class
     * @param cl class to be checked
     * @return true if class is basic, false otherwise
     */
    public boolean isBasicClass(class_c cl) {

        if (basicClassTable.containsKey(cl.getName().toString())) {
            return true;
        }
        return false;
    }

    /**
     * Get class by name of class
     * @param name of class
     * @return class definition
     */
    public class_c getClassByName(AbstractSymbol name) {

        if (classNamesTable.containsKey(name.toString())) {
            return classNamesTable.get(name.toString());
        }
        return null;
    }

    /**
     * Checks semantics of Class Main. Checks existence of class, existence of main method and makes
     * sure no arguments are passed into main method.
     */
    public void checkMainSemantics() {
        // Main class defined
        if (!classNamesTable.containsKey(TreeConstants.Main.toString())) {
            semantError().println("Class Main is not defined.");
        } else {
            // Check if main method defined in class main
            class_c mainClass = classNamesTable.get(TreeConstants.Main.toString());
            if (mainClass != null) {
                boolean isMain = mainClass.hasMethod(TreeConstants.main_meth);
                if (!isMain) {
                    semantError(mainClass).println("No 'main' method in class Main.");
                } else {
                    Formals mainFormals = mainClass.getMethodFromFeatures(TreeConstants.main_meth).getFormals();
                    Enumeration e1 = mainFormals.getElements();
                    if (e1.hasMoreElements()) {
                        semantError(mainClass).println("'main' method in class Main should have no arguments.");
                    }
                }
            }
        }
    }
}