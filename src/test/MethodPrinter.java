package test;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.io.FileInputStream;

public class MethodPrinter {

    public static void main(String[] args) throws Exception {
        // creates an input stream for the file to be parsed

        CompilationUnit cu;
        try (FileInputStream in = new FileInputStream("D:\\workspaces\\cbt-nominated\\mca-staff-customer-ams\\mca-staff-customer-ams-composites\\src\\main\\java\\com\\sbsa\\mca\\csf\\customer\\ams\\enquires\\event\\history\\EventHistoryVM.java")) {
            // parse the file
            cu = JavaParser.parse(in);
        }

        // visit and print the methods names
        new MethodVisitor().visit(cu, null);
    }

    /**
     * Simple visitor implementation for visiting MethodDeclaration nodes.
     */
    private static class MethodVisitor extends VoidVisitorAdapter {

        @Override
        public void visit(MethodDeclaration n, Object arg) {
            // here you can access the attributes of the method.
            // this method will be called for all methods in this
            // CompilationUnit, including inner class methods
            System.out.println(n.getName());
            super.visit(n, arg);
        }
    }
}