package dev.penguinz.Sylk.graphics.shader.uniforms;

import dev.penguinz.Sylk.graphics.shader.Shader;

public abstract class ShaderUniform<T> {

    public final String location;
    public final String glslType;
    protected int shaderLocation;
    protected T value;

    public ShaderUniform(String location, String glslType) {
        this.location = location;
        this.glslType = glslType;
    }

    @SuppressWarnings("unchecked")
    public void setValue(Object value) {
        try {
            this.value = (T) value;
        } catch (ClassCastException e) {
            throw new IllegalArgumentException("Was not given correct uniform value for location "+location);
        }
    }

    public void setShaderLocation(int shaderLocation) {
        this.shaderLocation = shaderLocation;
    }

    public abstract void loadUniform(Shader shader);

}
