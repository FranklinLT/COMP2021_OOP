package hk.edu.polyu.comp.comp2021.simple.model.statements;

public class Variable<T> {
    private T value;

    public Variable(T element) { this.value=element; }

    //This function use for update the value.
    public void setvalue(T value) { this.value = value; }

    public T getvalue() { return value; }
}
