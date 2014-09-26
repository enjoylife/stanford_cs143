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
import java.util.*;

/** This class aggregates all kinds of support routines and constants
    for the code generator; all routines are statics, so no instance of
    this class is even created. */
class CgenSupport {
    final static int MAXINT = 100000000;
    final static int WORD_SIZE = 4;
    final static int LOG_WORD_SIZE = 2;     // for logical shifts

    // Global names
    final static String CLASSNAMETAB = "class_nameTab";
    final static String CLASSOBJTAB  = "class_objTab";
    final static String INTTAG       = "_int_tag";
    final static String BOOLTAG      = "_bool_tag";
    final static String STRINGTAG    = "_string_tag";
    final static String HEAP_START   = "heap_start";
    final static String DISP_ABORT   = "_dispatch_abort";
    final static String CASE_ABORT_2 = "_case_abort2";
    final static String CASE_ABORT   = "_case_abort";

    // Naming conventions
    final static String DISPTAB_SUFFIX      = "_dispTab";
    final static String METHOD_SEP          = ".";
    final static String CLASSINIT_SUFFIX    = "_init";
    final static String PROTOBJ_SUFFIX      = "_protObj";
    final static String OBJECTPROTOBJ       = "Object" + PROTOBJ_SUFFIX;
    final static String INTCONST_PREFIX     = "int_const";
    final static String STRCONST_PREFIX     = "str_const";
    final static String BOOLCONST_PREFIX    = "bool_const";


    final static int    EMPTYSLOT           = 0;
    final static String LABEL               = ":\n";

    // information about object headers
    final static int DEFAULT_OBJFIELDS = 3;
    final static int TAG_OFFSET = 0;
    final static int SIZE_OFFSET = 1;
    final static int DISPTABLE_OFFSET = 2;

    final static int STRING_SLOTS      = 1;
    final static int INT_SLOTS         = 1;
    final static int BOOL_SLOTS        = 1;

    final static String GLOBAL       = "\t.globl\t"; 
    final static String ALIGN        = "\t.align\t2\n";
    final static String WORD         = "\t.int\t";

    // register names,
    final static String ACC = "%eax";		// Accumulator 
    final static String T1  = "%ecx";		// Temporary 1 
    final static String T2  = "%edx";       // Temporary 2
    final static String SP  = "%esp";		// Stack pointer 
    final static String FP  = "%ebp";		// Frame pointer
    final static String SELF = "8(%ebp)";
    
    // Opcodes
    final static String JMP   = "\tjmp\t";
    final static String CALL  = "\tcall\t";
    final static String RET   = "\tret\t";

    final static String MOV   = "\tmovl\t";
    final static String LEA   = "\tleal\t";

    final static String NEG   = "\tnegl\t";
    final static String ADD   = "\taddl\t";
    final static String DIV   = "\tidivl\t";
    final static String MUL   = "\timull\t";
    final static String SUB   = "\tsubl\t";
    final static String SLL   = "\tshll\t";

    final static String PUSH  = "\tpushl\t";
    final static String POP   = "\tpopl\t";

    final static String CMP   = "\tcmpl\t";
    final static String JE    = "\tje\t";
    final static String JNE   = "\tjne\t";
    final static String JL    = "\tjl\t";
    final static String JLE   = "\tjle\t";
    final static String JG    = "\tjg\t";
    final static String JGE   = "\tjge\t";

    public static int labelNum = 0;
    public static Map<AbstractSymbol, List<AbstractSymbol>> classToMethods = new HashMap<AbstractSymbol, List<AbstractSymbol>>();
    public static Map<AbstractSymbol, CgenNode> classNameToNode = new HashMap<AbstractSymbol, CgenNode>();

    /** Emits an LW instruction.
     * @param dest_reg the destination register
     * @param offset the word offset from source register
     * @param source_reg the source register
     * @param s the output stream
     * */
    static void emitLoad(String dest_reg, int offset, String source_reg, 
			 PrintStream s) {
	s.println(MOV + offset * WORD_SIZE + "(" + source_reg + "), " + dest_reg);
    }

    /** Emits an SW instruction.
     * @param dest_reg the destination register
     * @param offset the word offset from source register
     * @param source_reg the source register
     * @param s the output stream
     * */
    static void emitStore(String source_reg, int offset, String dest_reg, 
			  PrintStream s) {
	s.println(MOV + source_reg + ", " + offset * WORD_SIZE + "(" + dest_reg + ")");
    }

    /** Emits the LI instruction.
     * @param dest_reg the destination register
     * @param val the integer value
     * @param s the output stream
     * */
    static void emitLoadImm(String dest_reg, int val, PrintStream s) {
	s.println(MOV + "$" + val + ", " + dest_reg);
    }

    /** Emits an LA instruction.
     * @param dest_reg the destination register
     * @param address the address from which a word is loaded
     * @param s the output stream
     * */
    static void emitLoadAddress(String dest_reg, String address,PrintStream s){
	s.println(LEA + address + ", " + dest_reg);
    }

    /** Emits an instruction to load a boolean constant into a register.
     * @param dest_reg the destination register
     * @param b the boolean constant
     * @param s the output stream
     * */
    static void emitLoadBool(String dest_reg, BoolConst b, PrintStream s) {
	s.print(LEA);
	b.codeRef(s);
	s.println(", " + dest_reg);
    }

    /** Emits an instruction to load a string constant into a register.
     * @param dest_reg the destination register
     * @param str the string constant
     * @param s the output stream
     * */
    static void emitLoadString(String dest_reg, StringSymbol str, 
			       PrintStream s) {
	s.print(LEA);
	str.codeRef(s);
	s.println(", " + dest_reg);
    }

    /** Emits an instruction to load an integer constant into a register.
     * @param dest_reg the destination register
     * @param i the integer constant
     * @param s the output stream
     * */
    static void emitLoadInt(String dest_reg, IntSymbol i, PrintStream s) {
	s.print(LEA);
	i.codeRef(s);
	s.println(", " + dest_reg);
    }

    /** Emits a MOVE instruction.
     * @param dest_reg the destination register
     * @param source_reg the source register
     * @param s the output stream
     * */
    static void emitMove(String dest_reg, String source_reg, PrintStream s) {
	    s.println(MOV + source_reg + ", " + dest_reg);
        s.println(MOV + "8(" + FP + ")");
    }

    /** Emits a NEG instruction.
     * @param dest_reg the destination register
     * @param source_reg the source register
     * @param s the output stream
     * */
    static void emitNeg(String dest_reg, PrintStream s) {
	s.println(NEG + dest_reg);
    }
    
    /** Emits an ADD instruction.
     * @param dest_reg the destination register
     * @param src1 the source register 1
     * @param src2 the source register 2
     * @param s the output stream
     * */
    static void emitAdd(String dest_reg, String src,
			PrintStream s) {
	s.println(ADD + src + ", " + dest_reg);
    }

    /** Emits a DIV instruction.
     * @param dest_reg the destination register
     * @param src1 the source register 1
     * @param src2 the source register 2
     * @param s the output stream
     * */
    static void emitDiv(String divisor,
			PrintStream s) {
	s.println(DIV + divisor);
    }

    /** Emits a MUL instruction.
     * @param dest_reg the destination register
     * @param src1 the source register 1
     * @param src2 the source register 2
     * @param s the output stream
     * */
    static void emitMul(String multiplicand,
			PrintStream s) {
	s.println(MUL + multiplicand);
    }

    /** Emits a SUB instruction.
     * @param dest_reg the destination register
     * @param src1 the source register 1
     * @param src2 the source register 2
     * @param s the output stream
     * */
    static void emitSub(String dest_reg, String src,
			PrintStream s) {
	s.println(SUB + src + ", " + dest_reg);
    }

    /** Emits an SLL instruction.
     * @param dest_reg the destination register
     * @param src1 the source register 1
     * @param num the number of bits to shift
     * @param s the output stream
     * */
    static void emitSll(String dest_reg, int num, PrintStream s) {
	s.println(SLL + "$" + num + ", " + dest_reg);
    }

    static void emitPush(String source_reg, PrintStream s) {
	s.println(PUSH + source_reg);
    }

    static void emitPop(String dest_reg, PrintStream s) {
	s.println(POP + dest_reg);
    }

    /** Emits a JALR instruction.
     * @param dest_reg the register with target address
     * @param s the output stream
     * */
    static void emitCallInd(String dest_reg, PrintStream s) {
	s.println(CALL + "(" + dest_reg + ")");
    }

    /** Emits a JAL instruction.
     * @param dest the target address or label
     * @param s the output stream
     * */
    static void emitCall(String dest, PrintStream s) {
	s.println(CALL + dest);
    }

    /** Emits a RET instruction.
     * @param s the output stream
     * */
    static void emitReturn(PrintStream s) {
	s.println(RET);
    }

    /** Emits a reference to dispatch table.
     * @param sym the name of the class 
     * @param s the output stream
     * */
    static void emitDispTableRef(AbstractSymbol sym, PrintStream s) {
	s.print(sym + DISPTAB_SUFFIX);
    }

    /** Emits a reference to class' init() method.
     * @param sym the name of the class 
     * @param s the output stream
     * */
    static void emitInitRef(AbstractSymbol sym, PrintStream s) {
	s.print(sym + CLASSINIT_SUFFIX);
    }

    /** Emits a reference to class' prototype object.
     * @param sym the name of the class 
     * @param s the output stream
     * */
    static void emitProtObjRef(AbstractSymbol sym, PrintStream s) {
	s.print(sym + PROTOBJ_SUFFIX);
    }

    /** Emits a reference to a method in a class
     * @param classname the name of the class 
     * @param methodname the name of the method
     * @param s the output stream
     * */
    static void emitMethodRef(AbstractSymbol classname, 
			      AbstractSymbol methodname, 
			      PrintStream s) {
	s.print(classname + METHOD_SEP + methodname);
    }

    static void emitStartInitMethod(PrintStream s) {
        emitPush(FP, s);
        s.println(MOV + SP + ", " + FP);
        s.println(MOV + SELF + ", "  + ACC);
    }

    static void emitStartMethod(PrintStream s) {
        emitPush(FP, s);
        s.println(MOV + SP + ", " + FP);
    }

    static void emitParentInitMethod(AbstractSymbol parentName, PrintStream s) {
        emitPush(ACC, s);
        emitCall(parentName.toString() + CLASSINIT_SUFFIX, s);
        emitAdd(SP, "$4", s);
    }

    static void emitEndInitMethod(PrintStream s) {
        emitPop(FP, s);
        emitReturn(s);
    }

    static void emitEndMethod(PrintStream s) {
        emitPop(FP, s);
        emitReturn(s);
    }

    static void emitMakeNewInt(String src_reg, String dest_reg, PrintStream s) {
        emitPush(dest_reg, s);
        emitPush("$Int_protObj", s);
        emitCall("Object.copy", s);
        emitAdd(SP, "$4", s);
        emitPop("12(" + src_reg + ")", s);
    }

    /** Emits a reference to a label
     * @param label the label number
     * @param s the output stream
     * */
    static void emitLabelRef(int label, PrintStream s) {
	s.print("label" + label);
    }

    /** Emits a definition of a label
     * @param label the label number
     * @param s the output stream
     * */
    static void emitLabelDef(int label, PrintStream s) {
	emitLabelRef(label, s);
	s.println(":");
    }

    static void emitCmp(String srcop, String dstop, PrintStream s) {
	s.print(CMP + srcop + ", " + dstop);
    }

    /** Emits a BEQ instruction.
     * @param src1 the source register 1
     * @param src2 the source register 2
     * @param label the label number
     * @param s the output stream
     * */
    static void emitJe(int label, PrintStream s) {
	s.print(JE);
	emitLabelRef(label, s);
	s.println("");
    }

    /** Emits a BNE instruction.
     * @param src1 the source register 1
     * @param src2 the source register 2
     * @param label the label number
     * @param s the output stream
     * */
    static void emitJne(int label, PrintStream s) {
	s.print(JNE);
	emitLabelRef(label, s);
	s.println("");
    }
    
    /** Emits a BLEQ instruction.
     * @param src1 the source register 1
     * @param src2 the source register 2
     * @param label the label number
     * @param s the output stream
     * */
    static void emitJle(int label, PrintStream s) {
	s.print(JLE);
	emitLabelRef(label, s);
	s.println("");
    }
    
    /** Emits a BLT instruction.
     * @param src1 the source register 1
     * @param src2 the source register 2
     * @param label the label number
     * @param s the output stream
     * */
    static void emitJl(int label, PrintStream s) {
	s.print(JL);
	emitLabelRef(label, s);
	s.println("");
    }

    /** Emits a BLTI instruction.
     * @param src the source register
     * @param imm the immediate
     * @param label the label number
     * @param s the output stream
     * */
    static void emitJg(int label, PrintStream s) {
	s.print(JG);
	emitLabelRef(label, s);
	s.println("");
    }

    /** Emits a BGTI instruction.
     * @param src the source register
     * @param imm the immediate
     * @param label the label number
     * @param s the output stream
     * */
    static void emitJge(int label, PrintStream s) {
	s.print(JGE);
	emitLabelRef(label, s);
	s.println("");
    }

    /** Emits code to fetch the integer value of the Integer object.
     * @param source a pointer to the Integer object
     * @param dest the destination register for the value
     * @param s the output stream
     * */
    static void emitFetchInt(String dest, String source, PrintStream s) {
	emitLoad(dest, DEFAULT_OBJFIELDS, source, s);
    }

    /** Emits code to store the integer value of the Integer object.
     * @param source an integer value
     * @param dest the pointer to an Integer object
     * @param s the output stream
     * */
    static void emitStoreInt(String source, String dest, PrintStream s) {
	emitStore(source, DEFAULT_OBJFIELDS, dest, s);
    }

    public static int getStaticMethodOffset(AbstractSymbol className, AbstractSymbol methodName) {
        int index = 0;
        List<AbstractSymbol> methodsInClass = classToMethods.get(className);
        for (AbstractSymbol m: methodsInClass) {
            if (m.equals(methodName)) {
                return index * WORD_SIZE;
            }
            ++index;
        }
        return index * WORD_SIZE;
    }

    private static boolean ascii = false;

    /** Switch output mode to ASCII.
     * @param s the output stream
     * */
    static void asciiMode(PrintStream s) {
	if (!ascii) {
	    s.print("\t.ascii\t\"");
	    ascii = true;
	}
    }

    /** Switch output mode to BYTE
     * @param s the output stream
     * */
    static void byteMode(PrintStream s) {
	if (ascii) {
	    s.println("\"");
	    ascii = false;
	}
    }
    
    /** Emits a string constant.
     * @param str the string constant
     * @param s the output stream
     * */
    static void emitStringConstant(String str, PrintStream s) {
	ascii = false;
	
	for (int i = 0; i < str.length(); i++) {
	    char c = str.charAt(i);
	    
	    switch (c) {
	    case '\n':
		asciiMode(s);
		s.print("\\n");
		break;
	    case '\t':
		asciiMode(s);
		s.print("\\t");
		break;
	    case '\\':
		byteMode(s);
		s.println("\t.byte\t" + (byte) '\\');
		break;
	    case '"':
		asciiMode(s);
		s.print("\\\"");
		break;
	    default:
		if (c >= 0x20 && c <= 0x7f) {
		    asciiMode(s);
		    s.print(c);
		} else {
		    byteMode(s);
		    s.println("\t.byte\t" + (byte) c);
		}
	    }
	}
	byteMode(s);
	s.println("\t.byte\t0\t");
    }
}