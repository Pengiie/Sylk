package dev.penguinz.Sylk.ui;

import dev.penguinz.Sylk.graphics.VAO;
import dev.penguinz.Sylk.graphics.shader.Shader;
import dev.penguinz.Sylk.graphics.shader.uniforms.UniformConstants;
import dev.penguinz.Sylk.util.Color;
import dev.penguinz.Sylk.util.MatrixUtils;
import dev.penguinz.Sylk.util.maths.Vector2;
import org.lwjgl.opengl.GL11;

public class UIBlock extends UIComponent implements UIRenderable {

    public Color color;

    public UIBlock(Color color) {
        this.color = color;
    }

    @Override
    public void render(Shader shader) {
        shader.loadUniform(UniformConstants.transformationMatrix,
                MatrixUtils.createTransformMatrix(
                        new Vector2(this.getConstraints().getXConstraint(), this.getConstraints().getYConstraint()),
                        new Vector2(this.getConstraints().getWidthConstraint(), this.getConstraints().getHeightConstraint())
                )
        );
        shader.loadUniform(UniformConstants.hasTexture, false);
        shader.loadUniform(UniformConstants.color, color.toVector());
        GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, VAO.quad.getVertexCount());
    }
}
