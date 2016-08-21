package test;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;

import java.io.FileInputStream;

public class CuPrinter {

    public static void main(String[] args) throws Exception {
        // creates an input stream for the file to be parsed

        CompilationUnit cu;
        try (FileInputStream in = new FileInputStream("D:\\workspaces\\cbt-nominated\\mca-staff-customer-ams\\mca-staff-customer-ams-composites\\src\\main\\java\\com\\sbsa\\mca\\csf\\customer\\ams\\enquires\\event\\history\\EventHistoryVM.java")) {
            // parse the file
            cu = JavaParser.parse(in);
            int x = 0;
        }

        // prints the resulting compilation unit to default system output
        System.out.println(cu.toString());
    }
}