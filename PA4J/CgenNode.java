/*
Copyright (c) 2000 The Regents of the University of California.
All rights reserved.

Permission to use, copy, modify, and distribute this software for any
purpose, without fee, and without written agreement is hereby granted,
provided that the above copyright notice and the following two
paragraphs appear in all copies of this software.

IN NO EVENT SHALL THE UNIVERSITY OF CALIFORNIA BE LIABLE TO ANY PARTY FOR
DIRECT, INDIRECT, SPECIAL, INCIDENTAL, OR CONSEQUENTIAL DAMAGES ARISING OUT
OF THE USE OF THIS SOFTWARE AND ITS DOCUMENTATION, EVEN IF THE UNIVERSITY OF
CALIFORNIA HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

THE UNIVERSITY OF CALIFORNIA SPECIFICALLY DISCLAIMS ANY WARRANTIES,
INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY
AND FITNESS FOR A PARTICULAR PURPOSE.  THE SOFTWARE PROVIDED HEREUNDER IS
ON AN "AS IS" BASIS, AND THE UNIVERSITY OF CALIFORNIA HAS NO OBLIGATION TO
PROVIDE MAINTENANCE, SUPPORT, UPDATES, ENHANCEMENTS, OR MODIFICATIONS.
*/

// This is a project skeleton file

import java.io.PrintStream;
import java.util.Vector;
import java.util.Enumeration;
import java.util.*;

class CgenNode extends class_ {
    /** The parent of this node in the inheritance tree */
    private CgenNode parent;

    /** The children of this node in the inheritance tree */
    private Vector children;

    /** Indicates a basic class */
    final static int Basic = 0;

    /** Indicates a class that came from a Cool program */
    final static int NotBasic = 1;
    
    /** Does this node correspond to a basic class? */
    private int basic_status;

    private List<attr> classAttrs = new ArrayList<attr>();
    private List<attr> allAttrs = new ArrayList<attr>();
    private List<method> classMethods = new ArrayList<method>();
    private Map<AbstractSymbol, AbstractSymbol> allMethodsClassMap = new LinkedHashMap<AbstractSymbol, AbstractSymbol>();
    private Map<AbstractSymbol, method> methodNameToMethod = new HashMap<AbstractSymbol, method>();
    private int size = 0;
    public SymbolTable attrSymTable = new SymbolTable();
    public SymbolTable formalAndLocalSymTable = new SymbolTable();

    /** Constructs a new CgenNode to represent class "c".
     * @param c the class
     * @param basic_status is this class basic or not
     * @param table the class table
     * */
    CgenNode(Class_ c, int basic_status, CgenClassTable table) {
	super(0, c.getName(), c.getParent(), c.getFeatures(), c.getFilename());
	this.parent = null;
	this.children = new Vector();
	this.basic_status = basic_status;
	AbstractTable.stringtable.addString(name.getString());
    }

    void addChild(CgenNode child) {
	children.addElement(child);
    }

    /** Gets the children of this class
     * @return the children
     * */
    Enumeration getChildren() {
	return children.elements(); 
    }

    /** Sets the parent of this class.
     * @param parent the parent
     * */
    void setParentNd(CgenNode parent) {
	if (this.parent != null) {
	    Utilities.fatalError("parent already set in CgenNode.setParent()");
	}
	if (parent == null) {
	    Utilities.fatalError("null parent in CgenNode.setParent()");
	}
	this.parent = parent;
    }    
	

    /** Gets the parent of this class
     * @return the parent
     * */
    CgenNode getParentNd() {
	return parent; 
    }

    /** Returns true is this is a basic class.
     * @return true or false
     * */
    boolean basic() { 
	return basic_status == Basic; 
    }

    /**
     * Add all attr to list in desired order
     */
    public void setAllAttrs() {
        Stack<CgenNode> ancestors = new Stack<CgenNode>();
        ancestors.push(this);
        CgenNode currentParent = parent;
        // Add parents to stack to get them in order when popping
        while (currentParent != null) {
            ancestors.push(currentParent);
            currentParent = currentParent.getParentNd();
        }

        while (!ancestors.empty()) {
            allAttrs.addAll(ancestors.pop().getAttrs());
        }

        setSize(allAttrs.size());
    }

    /**
     * Add all attributes from the class's definition (except inherited attributes)
     */
    public void setAttrs() {
        for (Enumeration e = features.getElements(); e.hasMoreElements();) {
            Feature feature = (Feature)e.nextElement();
            if (feature instanceof attr) {
                classAttrs.add((attr) feature);
            }
        }
    }

    /**
     * Add all methods to list in desired order
     */
    public void setAllMethods() {
        Stack<CgenNode> ancestors = new Stack<CgenNode>();
        ancestors.push(this);
        CgenNode currentParent = parent;
        // Add parents on to stack to get methods in the right order.
        while (currentParent != null) {
            ancestors.push(currentParent);
            currentParent = currentParent.getParentNd();
        }

        while (!ancestors.empty()) {
            CgenNode currentClass = ancestors.pop();
            List<method> methods = currentClass.getClassMethods();

            for (method m: methods) {
                    allMethodsClassMap.put(m.name, currentClass.getName());
                    methodNameToMethod.put(m.name, m);

            }
        }

        List<AbstractSymbol> methods= new ArrayList<AbstractSymbol>();
        for (Map.Entry<AbstractSymbol, AbstractSymbol> entry : allMethodsClassMap.entrySet()) {
            methods.add(entry.getKey());
        }

        CgenSupport.classToMethods.put(this.getName(), methods);
    }

    public void setMethods() {
        for (Enumeration e = features.getElements(); e.hasMoreElements();) {
            Feature feature = (Feature)e.nextElement();
            if (feature instanceof method) {
                classMethods.add((method) feature);
            }
        }
    }

    /**
     * Emit attributes in order
     * @param str output stream
     */
    public void emitAttributes(PrintStream str) {
        for (attr a: allAttrs) {
            AbstractSymbol type = a.type_decl;
            str.print(CgenSupport.WORD);
            if (TreeConstants.Int.equals(type)) {
                IntSymbol sym = (IntSymbol)AbstractTable.inttable.lookup("0");
                sym.codeRef(str);
            } else if (TreeConstants.Bool.equals(type)) {
                BoolConst.falsebool.codeRef(str);
            } else if (TreeConstants.Str.equals(type)) {
                StringSymbol sym = (StringSymbol)AbstractTable.stringtable.lookup("");
                sym.codeRef(str);
            } else {
                str.print(CgenSupport.EMPTYSLOT);
            }
            str.println("");
        }
    }

    /**
     * Emit methods in order
     * @param str output stream
     */
    public void emitMethods(PrintStream str) {
        for (Map.Entry<AbstractSymbol, AbstractSymbol> entry : allMethodsClassMap.entrySet()) {
            str.println(CgenSupport.WORD + entry.getValue().toString() + CgenSupport.METHOD_SEP +
                    (entry.getKey()));
        }
    }

    /**
     * Emit class initialization and method definition code
     * @param str
     */
    public void code(PrintStream str) {
        attrSymTable = new SymbolTable();
        attrSymTable.enterScope();
        int index = 0;
        for (attr a: allAttrs) {
            attrSymTable.addId(a.name, index + CgenSupport.DEFAULT_OBJFIELDS);
            index ++;
        }
        // Code init class
        codeInit(str);

        if(!basic()) {
            // Code method definition
            codeMethodDef(str);
        }
        attrSymTable.exitScope();
    }

    private void codeMethodDef(PrintStream str) {
        for (Map.Entry<AbstractSymbol, AbstractSymbol> entry : allMethodsClassMap.entrySet()) {
            if(!TreeConstants.cool_abort.equals(entry.getKey()) &&
                    !TreeConstants.type_name.equals(entry.getKey()) &&
                    !TreeConstants.copy.equals(entry.getKey()) &&
                    entry.getValue().equals(name)) {
                formalAndLocalSymTable = new SymbolTable();
                formalAndLocalSymTable.enterScope();
                str.print(name + CgenSupport.METHOD_SEP + (entry.getKey()) + CgenSupport.LABEL);
                CgenSupport.emitStartMethod(str);
                (methodNameToMethod.get(entry.getKey())).codeGen(str, this);
                formalAndLocalSymTable.exitScope();
            }
        }
    }

    public void codeInit(PrintStream str) {
        str.print(name + CgenSupport.CLASSINIT_SUFFIX + CgenSupport.LABEL);
        CgenSupport.emitStartInitMethod(str);

        if (this.name.equals(TreeConstants.Object_)) {
            CgenSupport.emitEndInitMethod(str);
        } else {

            if (parent != null && parent.name != TreeConstants.No_class) {
                CgenSupport.emitParentInitMethod(parent.getName(), str);
            }

            for (attr a : classAttrs) {
                int attrOffset = (int) attrSymTable.lookup(a.name);
                a.codeInit(attrOffset, str, this);
            }

            str.println(CgenSupport.MOV + CgenSupport.SELF + "," + CgenSupport.ACC);
            CgenSupport.emitEndInitMethod(str);
        }
    }

    public int getMethodOffset (AbstractSymbol methodName) {
        int index = 0;
        for (Map.Entry<AbstractSymbol, AbstractSymbol> entry : allMethodsClassMap.entrySet()) {
            if (methodName.equals(entry.getKey())) {
                return index;
            }
            ++index;
        }
        return index;
    }

    /**
     * Get the maximum tag of the child "deepest child"
     * @param classNode class node to find the max child of
     * @return max tag
     */
    public int getMaxChildTag(CgenNode classNode) {
        int maxTag = CgenClassTable.getClassTag(classNode.getName().toString());
        for (Enumeration e = classNode.getChildren(); e.hasMoreElements(); ) {
            CgenNode child = (CgenNode)e.nextElement();

            int currentTag = getMaxChildTag(child);
            if (currentTag > maxTag) {
                maxTag = currentTag;
            }
        }
        return maxTag;
    }

    public List<attr> getAllAttrs() { return allAttrs; }

    public List<attr> getAttrs() {
        return classAttrs;
    }

    public List<method> getClassMethods() {
        return classMethods;
    }

    public void setSize(int s) {
        size = CgenSupport.DEFAULT_OBJFIELDS + s;
    }

    public int getSize() {
        return size;
    }
}