package dev.penguinz.Sylk.graphics.shader;

import dev.penguinz.Sylk.util.Disposable;
import dev.penguinz.Sylk.graphics.shader.uniforms.ShaderUniform;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import java.util.HashMap;
import java.util.List;

public class Shader implements Disposable {

    private final int programId;

    private final HashMap<String, ShaderUniform<?>> uniforms = new HashMap<>();

    public Shader(String vertexSource, String fragmentSource, List<ShaderUniform<?>> uniforms) {
        uniforms.forEach(uniform -> this.uniforms.put(uniform.location, uniform));

        int vertexId = compileShader(vertexSource, GL20.GL_VERTEX_SHADER);
        int fragmentId = compileShader(fragmentSource, GL20.GL_FRAGMENT_SHADER);

        this.programId = GL20.glCreateProgram();

        GL20.glAttachShader(this.programId, vertexId);
        GL20.glAttachShader(this.programId, fragmentId);

        GL20.glLinkProgram(this.programId);

        GL20.glDeleteShader(vertexId);
        GL20.glDeleteShader(fragmentId);

        this.uniforms.values().forEach(uniform -> uniform.setShaderLocation(GL20.glGetUniformLocation(this.programId,uniform.location)));
    }

    public void use() {
        GL20.glUseProgram(this.programId);
    }

    public void loadUniform(String location, Object value) {
        ShaderUniform<?> uniform = this.uniforms.get(location);
        uniform.setValue(value);
        uniform.loadUniform(this);
    }

    @Override
    public void dispose() {
        GL20.glDeleteProgram(this.programId);
    }

    private int compileShader(String source, int type) {
        int shaderId = GL20.glCreateShader(type);

        GL20.glShaderSource(shaderId, source);
        GL20.glCompileShader(shaderId);

        if(GL20.glGetShaderi(shaderId, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
            System.err.println(GL20.glGetShaderInfoLog(shaderId));
        }

        return shaderId;
    }

}
