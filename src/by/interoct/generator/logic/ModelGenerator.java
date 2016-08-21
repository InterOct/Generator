package by.interoct.generator.logic;

import by.interoct.generator.entity.ClassModel;
import by.interoct.generator.entity.Variable;
import by.interoct.generator.util.LabelFormatter;
import com.github.javaparser.ASTHelper;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.body.*;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.ReturnStmt;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;


public class ModelGenerator {

    private static final String MODEL = "Model";
    private static final String GENERATED = "generated";
    private static final String THIS = "this.";
    private static final String EQ = " = ";
    private static final String SET = "set";
    private static final String GET = "get";

    public static List<CompilationUnit> generate(ClassModel classModel) {
        LinkedList<CompilationUnit> result = new LinkedList<>();
        for (Variable variable : getComplexFields(classModel.getFields())) {
            result.add(createCU(variable));
        }
        return result;
    }

    private static CompilationUnit createCU(Variable variable) {
        CompilationUnit cu = new CompilationUnit();
        cu.setPackage(new PackageDeclaration(ASTHelper.createNameExpr(GENERATED)));

        ClassOrInterfaceDeclaration clazz = new ClassOrInterfaceDeclaration(
                ModifierSet.PUBLIC, false, LabelFormatter.CAMEL_CASE_VARIABLE.toCamelCaseName(variable.getName()) + MODEL);
        addFields(variable.getVariables(), clazz);
        addGSMethods(variable.getVariables(), clazz);
        ASTHelper.addTypeDeclaration(cu, clazz);
        return cu;
    }

    private static void addGSMethods(Set<Variable> variables, ClassOrInterfaceDeclaration clazz) {
        for (Variable var : variables) {
            ASTHelper.addMember(clazz, generateGetter(var));
            ASTHelper.addMember(clazz, generateSetter(var));
        }
    }

    private static BodyDeclaration generateSetter(Variable variable) {
        MethodDeclaration methodDec = new MethodDeclaration(ModifierSet.PUBLIC,
                ASTHelper.VOID_TYPE, SET + LabelFormatter.CAMEL_CASE_VARIABLE.toCamelCaseName(variable.getName()));

        Parameter param = ASTHelper.createParameter(ASTHelper.createReferenceType(variable.getType(), 0), variable.getName());
        ASTHelper.addParameter(methodDec, param);

        BlockStmt body = new BlockStmt();
        ASTHelper.addStmt(body, new NameExpr(THIS + variable.getName() + EQ + variable.getName()));
        methodDec.setBody(body);
        return methodDec;
    }

    private static MethodDeclaration generateGetter(Variable variable) {
        MethodDeclaration methodDec = new MethodDeclaration(ModifierSet.PUBLIC,
                ASTHelper.createReferenceType(variable.getType(), 0), GET + LabelFormatter.CAMEL_CASE_VARIABLE.toCamelCaseName(variable.getName()));
        BlockStmt body = new BlockStmt();
        ReturnStmt stmt = new ReturnStmt();
        stmt.setExpr(new NameExpr(variable.getName()));
        ASTHelper.addStmt(body, stmt);
        methodDec.setBody(body);
        return methodDec;
    }


    private static void addFields(Set<Variable> variables, ClassOrInterfaceDeclaration clazz) {
        for (Variable var : variables) {
            LinkedList<VariableDeclarator> variableDeclarators = new LinkedList<>();
            variableDeclarators.add(new VariableDeclarator(new VariableDeclaratorId(var.getName())));
            FieldDeclaration fieldDeclaration = new FieldDeclaration(ModifierSet.PUBLIC, ASTHelper.createReferenceType(var.getType(), 0), variableDeclarators);
            ASTHelper.addMember(clazz, fieldDeclaration);
        }
    }

    private static List<Variable> getComplexFields(List<Variable> fields) {
        LinkedList<Variable> result = new LinkedList<>();
        for (Variable field : fields) {
            if (!field.getVariables().isEmpty()) {
                result.add(field);
            }
        }
        return result;
    }
}
