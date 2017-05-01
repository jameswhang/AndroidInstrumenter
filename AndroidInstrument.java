import java.util.Iterator;
import java.util.Map;

import soot.*;
import soot.jimple.*;
import soot.options.Options;


public class AndroidInstrument {

    public static void main(String[] args) {

        //prefer Android APK files// -src-prec apk
        Options.v().set_src_prec(Options.src_prec_apk);

        //output as APK, too//-f J
//        Options.v().set_output_format(Options.output_format_dex);

        Options.v().set_soot_classpath(System.getProperty("java.class.path"));
        Options.v().set_prepend_classpath(true);

        // resolve the PrintStream and System soot-classes
        Scene.v().addSootBasicClasses();
        Scene.v().addBasicClass("java.io.PrintStream",SootClass.SIGNATURES);
        Scene.v().addBasicClass("java.lang.System",SootClass.SIGNATURES);

        /*
        SootClass sClass = new SootClass("out", Modifier.PUBLIC);
        sClass.setSuperclass(Scene.v().getSootClass("java.io.PrintStream"));
        Scene.v().addClass(sClass);
        */



        PackManager.v().getPack("jtp").add(new Transform("jtp.myInstrumenter", new BodyTransformer() {

            @Override
            protected void internalTransform(final Body b, String phaseName, @SuppressWarnings("rawtypes") Map options) {
                final PatchingChain<Unit> units = b.getUnits();

                //important to use snapshotIterator here
                for(Iterator<Unit> iter = units.snapshotIterator(); iter.hasNext();) {
                    final Unit u = iter.next();
                    u.apply(new AbstractStmtSwitch() {

                        public void caseInvokeStmt(InvokeStmt stmt) {
                            InvokeExpr invokeExpr = stmt.getInvokeExpr();
                            if(invokeExpr.getMethod().getName().contains("setContentView")) {

                                Local tmpRef = addTmpRef(b);
                                Local tmpRef2 = addTmpRef(b);
                                Local tmpString = addTmpString(b);

                                String argStr = "";


                                for (Value v : invokeExpr.getArgs()) {
                                    argStr += v.toString() + "(" + v.getType() + "), " ;
                                }

                                Scene.v().addBasicClass("java.lang.System",SootClass.SIGNATURES);

                                // insert "tmpRef = java.lang.System.out;"
                                units.insertBefore(Jimple.v().newAssignStmt(
                                        tmpRef, Jimple.v().newStaticFieldRef(
                                                Scene.v().getField("<java.lang.System: java.io.PrintStream out>").makeRef())), u);

                                /*
                                units.insertBefore(Jimple.v().newCastExpr(invokeExpr.getArg(0),
                                */


                                // insert "tmpLong = 'HELLO';"
                                /*
                                units.insertBefore(Jimple.v().newAssignStmt(tmpString,
                                        StringConstant.v("[NUDEBUG] setContentView: " + argStr)), u);
                                        */

                                // insert "tmpRef.println(tmpString);"
                                //SootMethod toCall = Scene.v().getSootClass("java.io.PrintStream").getMethod("void println(java.lang.Integer)");
                                SootMethod toCall = Scene.v().getSootClass("java.io.PrintStream").getMethod("void println(int)");
                                units.insertBefore(Jimple.v().newInvokeStmt(
                                        Jimple.v().newVirtualInvokeExpr(tmpRef, toCall.makeRef(), invokeExpr.getArg(0))), u);

                                //check that we did not mess up the Jimple
                                b.validate();
                            }
                        }

                    });
                }
            }


        }));

        soot.Main.main(args);
    }

    private static Local addTmpRef(Body body)
    {
        Local tmpRef = Jimple.v().newLocal("tmpRef", RefType.v("java.io.PrintStream"));
        body.getLocals().add(tmpRef);
        return tmpRef;
    }

    private static Local addTmpString(Body body)
    {
        Local tmpString = Jimple.v().newLocal("tmpString", RefType.v("java.lang.String"));
        body.getLocals().add(tmpString);
        return tmpString;
    }
}
