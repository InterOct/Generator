package by.interoct.generator.entity;

import java.util.LinkedList;
import java.util.List;

public class ClassModel {
    private String info;
    private String name;
    private String inheritance;
    private List<Variable> fields;
    private List<Method> methods;

    public ClassModel() {
        this.fields = new LinkedList<>();
        this.methods = new LinkedList<>();
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInheritance() {
        return inheritance;
    }

    public void setInheritance(String inheritance) {
        this.inheritance = inheritance;
    }

    public List<Variable> getFields() {
        return fields;
    }

    public void setFields(List<Variable> fields) {
        this.fields = fields;
    }

    public void addVariable(Variable variable) {
        this.fields.add(variable);
    }

    public List<Method> getMethods() {
        return methods;
    }

    public void setMethods(List<Method> methods) {
        this.methods = methods;
    }

    public void addMethod(Method method) {
        this.methods.add(method);
    }
}
