package by.interoct.generator.entity;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Branavets_AY on 7/28/2016.
 */
public class Method {
    private String info;
    private String returnType;
    private String name;
    private List<Variable> arguments;
    private String contents;

    public Method() {
        this.arguments = new LinkedList<>();
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getReturnType() {
        return returnType;
    }

    public void setReturnType(String returnType) {
        this.returnType = returnType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Variable> getArguments() {
        return arguments;
    }

    public void setArguments(List<Variable> arguments) {
        this.arguments = arguments;
    }

    public void addArgument(Variable variable) {
        this.arguments.add(variable);
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    @Override
    public String toString() {
        return info + ' ' + returnType + ' ' + name + '(' + argsToStr(arguments) + ") {" + contents + '}';
    }

    private String argsToStr(List<Variable> arguments) {
        String result = "";
        for (Variable argument : arguments) {
            result += ", " + argument;
        }
        return result.replaceFirst(", ", "");
    }

}
