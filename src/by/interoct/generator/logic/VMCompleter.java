package by.interoct.generator.logic;

import by.interoct.generator.entity.ClassModel;
import by.interoct.generator.entity.Method;
import by.interoct.generator.entity.Variable;
import com.github.javaparser.ASTHelper;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.*;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.stmt.BlockStmt;

import java.util.LinkedList;
import java.util.List;

public class VMCompleter {

    private static final String VM = "Vm";

    public static void complete(CompilationUnit cu, ClassModel classModel) {
        ClassOrInterfaceDeclaration vmDeclaration = getVmDeclaration(cu);
        if (vmDeclaration == null) {
            throw new IllegalArgumentException("Not a VM class!");
        }
        completeFields(vmDeclaration, classModel.getFields());
        completeMethods(vmDeclaration, classModel.getMethods());
    }

    private static void completeMethods(ClassOrInterfaceDeclaration vmDeclaration, List<Method> methods) {
        for (Method method : methods) {
            if (!methodExists(vmDeclaration, method)) {
                MethodDeclaration methodDec = new MethodDeclaration(ModifierSet.PUBLIC,
                        ASTHelper.createReferenceType(method.getReturnType(), 0), method.getName());
                for (Variable variable : method.getArguments()) {
                    ASTHelper.addParameter(methodDec, ASTHelper.createParameter(
                            ASTHelper.createReferenceType(variable.getType(), 0), variable.getName()));
                }
                BlockStmt body = new BlockStmt();
                ASTHelper.addStmt(body, new NameExpr(method.getContents()));
                methodDec.setBody(body);
                ASTHelper.addMember(vmDeclaration, methodDec);
            }
        }

    }

    private static boolean methodExists(ClassOrInterfaceDeclaration vmDeclaration, Method method) {
        for (BodyDeclaration bodyDeclaration : vmDeclaration.getMembers()) {
            if (bodyDeclaration instanceof MethodDeclaration) {
                MethodDeclaration methodDeclaration = (MethodDeclaration) bodyDeclaration;
                if (methodDeclaration.getName().equals(method.getName())) {
                    return true;
                }
            }
        }
        return false;
    }

    private static void completeFields(ClassOrInterfaceDeclaration vmDeclaration, List<Variable> variables) {
        for (Variable variable : variables) {
            if (!variableExists(vmDeclaration, variable)) {
                LinkedList<VariableDeclarator> variableDeclarators = new LinkedList<>();
                variableDeclarators.add(new VariableDeclarator(new VariableDeclaratorId(variable.getName())));
                FieldDeclaration fieldDeclaration = new FieldDeclaration(ModifierSet.PUBLIC, ASTHelper.createReferenceType(variable.getType(), 0), variableDeclarators);
                ASTHelper.addMember(vmDeclaration, fieldDeclaration);
            }
        }
    }

    private static boolean variableExists(ClassOrInterfaceDeclaration vmDeclaration, Variable variable) {
        for (BodyDeclaration bodyDeclaration : vmDeclaration.getMembers()) {
            if (bodyDeclaration instanceof FieldDeclaration) {
                FieldDeclaration fieldDeclaration = (FieldDeclaration) bodyDeclaration;
                if (fieldDeclaration.getVariables().size() < 1) continue;
                if (fieldDeclaration.getVariables().get(0).getId().getName().equals(variable.getName())) {
                    return true;
                }
            }
        }
        return false;
    }

    private static ClassOrInterfaceDeclaration getVmDeclaration(CompilationUnit cu) {
        for (TypeDeclaration typeDeclaration : cu.getTypes()) {
            if (typeDeclaration.getName().contains(VM)) {
                return (ClassOrInterfaceDeclaration) typeDeclaration;
            }
        }
        return null;
    }
}
