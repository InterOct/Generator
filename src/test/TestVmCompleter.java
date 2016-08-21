package test;

import by.interoct.generator.entity.ClassModel;
import by.interoct.generator.logic.ModelGenerator;
import by.interoct.generator.logic.VMCompleter;
import by.interoct.generator.logic.VMGenerator;
import by.interoct.parser.dom.helper.ReadSourceFactory;
import by.interoct.parser.dom.logic.ReadSource;
import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;

/**
 * @author Branavets_AY
 */
public class TestVmCompleter {
    public static void main(String[] args) throws Exception {
        ClassModel classModel;
        try (ReadSource readSource = ReadSourceFactory.getInstance()
                .getReadSource("D:\\workspaces\\cbt-nominated\\mca-staff-customer-ams\\mca-staff-customer-ams-composites\\src\\main\\resources\\web\\view\\customer\\product\\profile\\productProfile.zul")) {
            classModel = VMGenerator.getInstance().generate(readSource);
        }
        try (FileInputStream in = new FileInputStream("D:\\workspaces\\cbt-nominated\\mca-staff-customer-ams\\mca-staff-customer-ams-composites\\src\\main\\java\\com\\sbsa\\mca\\csf\\customer\\ams\\enquires\\product\\profile\\ProductProfileVm.java");
             BufferedWriter writer1 = new BufferedWriter(new FileWriter("1.java"));
             BufferedWriter writer2 = new BufferedWriter(new FileWriter("2.java"))) {
            CompilationUnit cu = JavaParser.parse(in);
            writer1.write(cu.toString());
            writer1.flush();

            VMCompleter.complete(cu, classModel);
            writer2.write(cu.toString());
            writer2.flush();
        }

        for (CompilationUnit cu : ModelGenerator.generate(classModel)) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(cu.getTypes().get(0).getName() + ".java"))) {
                writer.write(cu.toString());
            }
        }

    }
}
