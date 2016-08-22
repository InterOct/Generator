package by.interoct.generator.logic;

import by.interoct.generator.exception.GeneratorException;
import by.interoct.generator.util.LabelFormatter;
import by.interoct.parser.dom.entity.Attribute;
import by.interoct.parser.dom.entity.Element;
import by.interoct.parser.dom.entity.Node;
import by.interoct.parser.dom.exception.ParserException;
import by.interoct.parser.dom.helper.ParserFactory;
import by.interoct.parser.dom.logic.ReadSource;
import by.interoct.parser.dom.logic.impl.Parser;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author Branavets_AY on 7/26/2016.
 */
public class ZULGenerator {

    private static final String NAME = "name";
    private static final Pattern ZUL_COMMAND_PATTERN = Pattern.compile("@\\w+\\(.*\\)");
    private static final ZULGenerator instance = new ZULGenerator();

    private static final String UXP_LABEL = "UxpLabel";
    private static final String VALUE = "value";

    private static final String LISTHEADER = "listheader";
    private static final String LABEL = "label";
    private static final String TEMPLATE = "template";
    private static final String LISTITEM = "listitem";
    private static final String LISTCELL = "listcell";
    private static final String LOAD = "@load(";
    private static final String BR = ")";
    private static final String BIND_VM = "@bind(vm.";
    private static final String SORT = "sort";
    private static final String AUTO = "auto(";
    private static final String MODEL = "model";
    private static final String VAR = "var";
    private static final String ITEM = "item";

    private ZULGenerator() {
    }

    public static ZULGenerator getInstance() {
        return instance;
    }

    public Element generate(ReadSource in, boolean completeIfExist) throws GeneratorException {
        Parser parser = ParserFactory.getInstance().getParser(in);
        Element root;
        try {
            root = parser.getRootElement();
            return generate(root, completeIfExist);
        } catch (ParserException e) {
            throw new GeneratorException(e);
        }
    }

    public Element generate(Element root, boolean completeIfExist) {
        completeValuesForLabels(root, completeIfExist);
        completeValueForTables(root, completeIfExist);
        return root;
    }

    private void completeValuesForLabels(Element root, boolean completeIfExist) {
        List<Element> labels = filter(root.findElementsByName(UXP_LABEL));
        for (Element label : labels) {
            Node neighborNode = label.getNextNeighborNode();
            if (neighborNode != null && neighborNode instanceof Element) {
                Element neighbor = (Element) neighborNode;
                Attribute valueAttr = neighbor.getAttributeByName(VALUE);
                if (completeIfExist || valueAttr.getValue().isEmpty()) {
                    valueAttr.setValue(BIND_VM +
                            LabelFormatter.SIMPLE_TEXT.toCamelCaseVariable(
                                    label.getAttributeByName(VALUE).getValue())
                            + BR);
                }
            }
        }

    }

    private void completeValueForTables(Element root, boolean completeIfExist) {
        List<Element> listheaders = root.findElementsByName(LISTHEADER);
        for (Element listheader : listheaders) {
            Attribute labelAttr = listheader.getAttributeByName(LABEL);
            if (labelAttr == null) continue;
            String valueCC = LabelFormatter.SIMPLE_TEXT.toCamelCaseVariable(
                    labelAttr.getValue());
            completeSortAttribute(completeIfExist, listheader, valueCC);
            if (listheader.getParent().getNodes().size() != getListitem(listheader).getNodes().size()) {
                competeListitem(listheader, valueCC);
            }
        }
    }

    private void competeListitem(Element listheader, String valueCC) {
        Element listitem = getListitem(listheader);
        Element listcell = new Element();
        listcell.setName(LISTCELL);
        Attribute label = listheader.getAttributeByName(LABEL);
        if (label.getValue().isEmpty()) {
            listcell.addAttribute(new Attribute(LABEL, label.getValue()));
        } else {
            listcell.addAttribute(
                    new Attribute(LABEL, LOAD + listitem.getParent().getAttributeByName(VAR).getValue() + '.' + valueCC + BR));
        }
        listcell.setParent(listitem);
        listitem.addNode(listcell);
    }

    private Element getListitem(Element listheader) {
        Element listbox = listheader.getParent().getParent();
        Element template = listbox.findChildByName(TEMPLATE);
        if (template == null) {
            template = addTemplate(listbox);
        }
        Element listitem = template.findChildByName(LISTITEM);
        if (listitem == null) {
            listitem = addListitem(template);
        }
        return listitem;
    }

    private Element addListitem(Element template) {
        Element listitem;
        listitem = new Element(LISTITEM);
        listitem.setParent(template);
        listitem.setParent(template);
        template.addNode(listitem);
        return listitem;
    }

    private Element addTemplate(Element listbox) {
        Element template;
        template = new Element(TEMPLATE);
        template.addAttribute(new Attribute(NAME, MODEL));
        template.addAttribute(new Attribute(VAR, ITEM));
        template.setParent(listbox);
        template.setParent(listbox);
        listbox.addNode(template);
        return template;
    }

    private void completeSortAttribute(boolean completeIfExist, Element listheader, String valueCC) {
        if (listheader.getAttributeByName(LABEL).getValue().isEmpty()) {
            return;
        }
        Attribute sortAttr = listheader.getAttributeByName(SORT);
        if (sortAttr == null) {
            listheader.addAttribute(new Attribute(SORT, AUTO + valueCC + BR));
        } else {
            if (completeIfExist) {
                sortAttr.setValue(AUTO + valueCC + BR);
            }
        }
    }

    private List<Element> filter(List<Element> labels) {
        List<Element> result = new LinkedList<>();
        for (Element label : labels) {
            String value = label.getAttributeByName(VALUE).getValue();
            if (!isWithZULCommand(label) && !value.isEmpty() && !value.equals(VALUE)) {
                result.add(label);
            }
        }
        return result;
    }

    private boolean isWithZULCommand(Element label) {
        return ZUL_COMMAND_PATTERN.matcher(label.getAttributeByName(VALUE).getValue()).matches();
    }
}
