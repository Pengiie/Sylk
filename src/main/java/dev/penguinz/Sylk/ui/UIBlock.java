package dev.penguinz.Sylk.ui;

import dev.penguinz.Sylk.animation.values.AnimatableColor;
import dev.penguinz.Sylk.animation.values.AnimatableValue;
import dev.penguinz.Sylk.graphics.VAO;
import dev.penguinz.Sylk.graphics.shader.Shader;
import dev.penguinz.Sylk.graphics.shader.uniforms.UniformConstants;
import dev.penguinz.Sylk.util.Color;
import dev.penguinz.Sylk.util.MatrixUtils;
import dev.penguinz.Sylk.util.maths.Vector2;
import org.lwjgl.opengl.GL11;

public class UIBlock extends UIComponent implements UIRenderable {

    public AnimatableValue<Color> color;

    public UIBlock(Color color) {
        this.color = new AnimatableColor(color);
    }

    @Override
    public void render(Shader shader) {
        shader.loadUniform(UniformConstants.transformationMatrix,
                MatrixUtils.createTransformMatrix(
                        new Vector2(this.getConstraints().getXConstraintValue(), this.getConstraints().getYConstraintValue()),
                        new Vector2(this.getConstraints().getWidthConstraintValue(), this.getConstraints().getHeightConstraintValue())
                )
        );
        shader.loadUniform(UniformConstants.hasTexture, false);
        shader.loadUniform(UniformConstants.color, color.value.toVector());
        GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, VAO.quad.getVertexCount());
    }
}
