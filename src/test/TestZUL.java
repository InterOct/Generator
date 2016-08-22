package test;

import by.interoct.generator.exception.GeneratorException;
import by.interoct.generator.logic.ZULGenerator;
import by.interoct.parser.dom.entity.Element;
import by.interoct.parser.dom.exception.DOMWriterException;
import by.interoct.parser.dom.exception.ReadSourceException;
import by.interoct.parser.dom.helper.ReadSourceFactory;
import by.interoct.parser.dom.logic.ReadSource;
import by.interoct.parser.dom.logic.impl.FileDOMWriter;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class TestZUL {
    public static void main(String[] args) throws ReadSourceException, GeneratorException, IOException, DOMWriterException {
        Element root;
        try (ReadSource readSource = ReadSourceFactory.getInstance()
                .getReadSource("D:\\workspaces\\cbt-nominated\\mca-staff-customer-ams\\mca-staff-customer-ams-composites\\src\\main\\resources\\web\\view\\customer\\balance\\certificateOfBalance.vm")) {
            root = ZULGenerator.getInstance().generate(readSource, true);
        }
        try (FileDOMWriter domWriter = new FileDOMWriter(new BufferedWriter(new FileWriter("new.vm")))) {
            domWriter.write(root);
        }
    }
}
