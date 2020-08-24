package dev.penguinz.Sylk.animation.values;

public class AnimatableValue<T> {

    public T value;
    private final Class<T> classType;

    public AnimatableValue(T value, Class<T> classType) {
        this.value = value;
        this.classType = classType;
    }

    public Class<T> getClassType() {
        return classType;
    }
}
