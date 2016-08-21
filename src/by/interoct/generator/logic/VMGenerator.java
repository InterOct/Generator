package by.interoct.generator.logic;

import by.interoct.generator.entity.ClassModel;
import by.interoct.generator.entity.Method;
import by.interoct.generator.entity.Variable;
import by.interoct.generator.exception.GeneratorException;
import by.interoct.generator.util.LabelFormatter;
import by.interoct.parser.dom.entity.Attribute;
import by.interoct.parser.dom.entity.Element;
import by.interoct.parser.dom.entity.Node;
import by.interoct.parser.dom.exception.ParserException;
import by.interoct.parser.dom.helper.ParserFactory;
import by.interoct.parser.dom.logic.ReadSource;
import by.interoct.parser.dom.logic.impl.Parser;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VMGenerator {


    private static final Pattern VM_REF_PARSE_PATTERN = Pattern.compile("(?<=vm\\.)(.*?)(?=(\\)|\\s))|(?<=auto\\()(.*?)(?=(\\)|\\s))");
    private static final Pattern LIST_REF_PARSE_PATTERN = Pattern.compile("(?<=\\w+\\(!?)([\\w.]*?)(?=(\\)|\\s))");

    private static final List<String> BOOLEAN_ATTRIBUTES = new ArrayList<>(Arrays.asList("visible", "disabled", "checked"));
    private static final List<String> LIST_ATTRIBUTES = new ArrayList<>(Arrays.asList("model", "selectedItems"));
    private static final List<String> INT_ATTRIBUTES = new ArrayList<>(Arrays.asList("pageSize", "rows"));
    private static final List<String> TYPE_ATTRIBUTES = new ArrayList<>(Arrays.asList("selectedItem", "provider"));

    private static final String BOOLEAN = "boolean";
    private static final String LIST = "List<Type>";
    private static final String STRING = "String";
    private static final String INT = "int";
    private static final String TYPE = "Type";
    private static final String CALENDAR = "Calendar";
    private static final String UXP_DATEBOX = "UxpDatebox";
    private static final String DOMAIN_COMBOBOX = "domainCombobox";
    private static final String AT = "@";
    private static final String UXP_LISTBOX = "UxpListbox";
    private static final String MODEL = "model";
    private static final String TEMPLATE = "template";
    private static final String VAR = "var";
    private static final String DOT_R = "\\.";
    private static final String BR = "(";
    private static final String PUBLIC = "public";
    private static final String BR_R = "\\(";
    private static final String DEFAULT_CONTENTS = " ";
    private static final String VOID = "void";
    private static final String SET = "set";
    private static final String GET = "get";
    private static final String BIND_UTILS_POST_NOTIFY_CHANGE_NULL_NULL_THIS = "BindUtils.postNotifyChange(null, null, this, \"";
    private static final String THIS = "this.";
    private static final String S1 = ";\n";
    private static final String S2 = "\")";
    private static final String EQ = " = ";
    private static final String RETURN = "return ";
    private static final String S3 = "";
    private static VMGenerator instance = new VMGenerator();

    private VMGenerator() {
    }

    public static VMGenerator getInstance() {
        return instance;
    }

    public ClassModel generate(ReadSource in) throws GeneratorException {
        Parser parser = ParserFactory.getInstance().getParser(in);
        Element root;
        try {
            root = parser.getRootElement();
            return generate(root);

        } catch (ParserException e) {
            throw new GeneratorException(e);
        }
    }

    public ClassModel generate(Element root) {
        Set<Variable> variables = new LinkedHashSet<>();
        List<Attribute> attributes = findAllAttributes(root);
        for (Attribute attribute : attributes) {
            if (hasZKCommand(attribute)) {
                parseAttribute(attribute, variables);
            }
        }
        completeListVariables(findCandidatesForComplete(variables), attributes, variables);

        ClassModel classModel = new ClassModel();
        classModel.setFields(new LinkedList<>(filterVariables(variables)));

        List<Method> methods = findMethodInvocations(variables);
        methods.addAll(generateGetSetMethods(variables));
        classModel.setMethods(methods);
        return classModel;
    }

    private Collection<? extends Method> generateGetSetMethods(Set<Variable> variables) {
        LinkedList<Method> result = new LinkedList<>();
        for (Variable variable : variables) {
            result.addAll(extractGSMethods(variable));
        }
        return result;
    }

    private Collection<? extends Method> extractGSMethods(Variable variable) {
        LinkedList<Method> methods = new LinkedList<>();
        methods.add(extractGetter(variable));
        methods.add(extractSetter(variable));
        return methods;
    }

    private Method extractSetter(Variable variable) {
        Method method = new Method();
        method.setInfo(PUBLIC);
        method.setReturnType(VOID);
        method.setName(SET + LabelFormatter.CAMEL_CASE_VARIABLE.toCamelCaseName(variable.getName()));
        method.addArgument(new Variable(variable.getType(), variable.getName()));
        method.setContents(THIS + variable.getName() + EQ + variable.getName() + S1 +
                BIND_UTILS_POST_NOTIFY_CHANGE_NULL_NULL_THIS + variable.getName() + S2);
        return method;
    }

    private Method extractGetter(Variable variable) {
        Method method = new Method();
        method.setInfo(PUBLIC);
        method.setReturnType(variable.getType());
        method.setName(GET + LabelFormatter.CAMEL_CASE_VARIABLE.toCamelCaseName(variable.getName()));
        method.setContents(RETURN + variable.getName() + S3);
        return method;
    }

    private Set<Variable> filterVariables(Set<Variable> variables) {
        Set<Variable> result = new LinkedHashSet<>();
        for (Variable variable : variables) {
            if (!variable.getName().contains(BR)) {
                result.add(variable);
            }
        }
        return result;
    }

    private List<Method> findMethodInvocations(Set<Variable> variables) {
        LinkedList<Method> methods = new LinkedList<>();
        for (Variable variable : variables) {
            if (variable.getName().contains(BR)) {
                String[] split = variable.getName().split(BR_R);
                Method method = extractMethod(variable, split);
                methods.add(method);
            }
        }
        return methods;
    }

    private Method extractMethod(Variable variable, String[] split) {
        Method method = new Method();
        method.setInfo(PUBLIC);
        method.setReturnType(variable.getType());
        method.setName(split[0]);
        method.addArgument(new Variable(TYPE, split[1]));
        method.setContents(DEFAULT_CONTENTS);
        return method;
    }

    private void completeListVariables(Set<Variable> candidatesForComplete, List<Attribute> attributes, Set<Variable> variables) {
        for (Variable variable : candidatesForComplete) {
            for (Attribute attribute : attributes) {
                if (attribute.getValue().contains(variable.getName())
                        && attribute.getName().equals(MODEL)
                        && attribute.getParent().getName().equals(UXP_LISTBOX)) {
                    Element template = attribute.getParent().findChildByName(TEMPLATE);
                    if (template == null) {
                        continue;
                    }
                    Attribute var = template.getAttributeByName(VAR);
                    if (var == null) {
                        continue;
                    }
                    String value = var.getValue();
                    for (Attribute attr : findAllAttributes(template)) {
                        if (attr.getValue().contains(value)) {
                            Matcher m = LIST_REF_PARSE_PATTERN.matcher(attr.getValue());
                            if (m.find()) {
                                addVariable(attr, variables, m.group().split(DOT_R));
                            }
                        }
                    }
                    Variable varItem = getIfExits(variables, value);
                    if (varItem != null) {
                        variable.setVariables(varItem.getVariables());
                    }
                    variables.remove(varItem);
                }
            }
        }
    }

    private Set<Variable> findCandidatesForComplete(Set<Variable> variables) {
        Set<Variable> result = new HashSet<>();
        for (Variable variable : variables) {
            if (variable.getType().equals(LIST)) {
                result.add(variable);
            }
        }
        return result;
    }

    private void parseAttribute(Attribute attribute, Set<Variable> variables) {
        Matcher m = VM_REF_PARSE_PATTERN.matcher(attribute.getValue());
        while (m.find()) {
            String[] split = m.group().split(DOT_R);
            addVariable(attribute, variables, split);
        }
    }

    private void addVariable(Attribute attribute, Set<Variable> variables, String[] split) {
        String name = split[0];
        String nameForChild = null;
        if (split.length > 1) {
            nameForChild = split[1];
        }
        if (nameForChild == null) {
            variables.add(new Variable(defineType(attribute), name));
        } else {
            Variable variable = getIfExits(variables, name);
            variables.remove(variable);
            if (variable == null) {
                variable = new Variable(defineType(attribute), name);
            }
            variable.addVariable(new Variable(defineType(attribute), nameForChild));
            variables.add(variable);
        }
    }

    private Variable getIfExits(Set<Variable> variables, String name) {
        for (Variable variable : variables) {
            if (name.equals(variable.getName())) {
                return variable;
            }
        }
        return null;
    }

    private String defineType(Attribute attribute) {
        if (BOOLEAN_ATTRIBUTES.contains(attribute.getName())) {
            return BOOLEAN;
        } else if (LIST_ATTRIBUTES.contains(attribute.getName())) {
            return LIST;
        } else if (INT_ATTRIBUTES.contains(attribute.getName())) {
            return INT;
        } else if (TYPE_ATTRIBUTES.contains(attribute.getName())) {
            return TYPE;
        } else if (isDate(attribute)) {
            return CALENDAR;
        } else if (isParentComboBox(attribute)) {
            return TYPE;
        } else {
            return STRING;
        }
    }

    private boolean isParentComboBox(Attribute attribute) {
        return DOMAIN_COMBOBOX.equals(attribute.getParent().getName());
    }

    private boolean isDate(Attribute attribute) {
        return UXP_DATEBOX.equals(attribute.getParent().getName());
    }

    private boolean hasZKCommand(Attribute attribute) {
        return attribute.getValue().contains(AT);
    }

    private List<Attribute> findAllAttributes(Element root) {
        return findAllAttributes(root, new LinkedList<Attribute>());
    }

    private List<Attribute> findAllAttributes(Element element, LinkedList<Attribute> attributes) {
        for (Node node : element.getNodes()) {
            if (node instanceof Element) {
                Element elem = (Element) node;
                attributes.addAll(elem.getAttributes());
                findAllAttributes(elem, attributes);
            }
        }
        return attributes;
    }

}
