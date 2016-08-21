package by.interoct.generator.entity;

import javafx.beans.property.SimpleStringProperty;

import java.util.HashSet;
import java.util.Set;

public class Variable {
    private SimpleStringProperty type = new SimpleStringProperty();
    private SimpleStringProperty name = new SimpleStringProperty();
    private Set<Variable> variables;

    public Variable() {
    }

    public Variable(String type, String name) {
        this.type.set(type);
        this.name.set(name);
        variables = new HashSet<>();
    }

    public String getType() {
        return type.get();
    }

    public void setType(String type) {
        this.type.set(type);
    }

    public SimpleStringProperty typeProperty() {
        return type;
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public Set<Variable> getVariables() {
        return variables;
    }

    public void setVariables(Set<Variable> variables) {
        this.variables = variables;
    }

    public void addVariable(Variable variable) {
        this.variables.add(variable);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Variable variable = (Variable) o;

        if (getType() != null ? !getType().equals(variable.getType()) : variable.getType() != null) return false;
        if (getName() != null ? !getName().equals(variable.getName()) : variable.getName() != null) return false;
        return getVariables() != null ? getVariables().equals(variable.getVariables()) : variable.getVariables() == null;

    }

    @Override
    public int hashCode() {
        int result = getType() != null ? getType().hashCode() : 0;
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getVariables() != null ? getVariables().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return type + " " + name + " " + variables;
    }
}
