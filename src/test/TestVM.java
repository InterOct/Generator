package test;

import by.interoct.generator.entity.ClassModel;
import by.interoct.generator.entity.Method;
import by.interoct.generator.entity.Variable;
import by.interoct.generator.exception.GeneratorException;
import by.interoct.generator.logic.VMGenerator;
import by.interoct.parser.dom.entity.Element;
import by.interoct.parser.dom.exception.ParserException;
import by.interoct.parser.dom.exception.ReadSourceException;
import by.interoct.parser.dom.helper.ReadSourceFactory;
import by.interoct.parser.dom.logic.ReadSource;

import java.io.IOException;


public class TestVM {
    public static void main(String[] args) throws ReadSourceException, ParserException, IOException, GeneratorException {
        Element root;
        try (ReadSource readSource = ReadSourceFactory.getInstance()
                .getReadSource("D:\\workspaces\\cbt-nominated\\mca-staff-customer-ams\\mca-staff-customer-ams-composites\\src\\main\\resources\\web\\view\\customer\\product\\profile\\productProfile.zul")) {
            ClassModel classModel = VMGenerator.getInstance().generate(readSource);
            for (Variable variable : classModel.getFields()) {
                System.out.println(variable);
            }
            for (Method method : classModel.getMethods()) {
                System.out.println(method);
            }
        }
    }
}
