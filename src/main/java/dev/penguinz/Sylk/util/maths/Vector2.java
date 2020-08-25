package dev.penguinz.Sylk.util.maths;

/**
 * A two dimensional vector.
 */
public class Vector2 {

    public float x;

    public float y;

    /**
     * Creates a new {@link Vector2} and initializes its components to zero.
     */
    public Vector2() {
        this(0, 0);
    }

    /**
     * Creates a new {@link Vector2} and initializes its components with the given values.
     * @param x the x component.
     * @param y the y component.
     */
    public Vector2(float x, float y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Adds another {@link Vector2} to this vector.
     * @param right the vector being added.
     */
    public void add(Vector2 right) {
        this.x += right.x;
        this.y += right.y;
    }

    /**
     * Subtracts another {@link Vector2} from this vector.
     * @param right the vector subtracting.
     */
    public void sub(Vector2 right) {
        this.x -= right.x;
        this.y -= right.y;
    }

    /**
     * Adds two vectors together and returns the result.
     * @param left the left side vector.
     * @param right the right side vector.
     * @return the resulting {@link Vector2}.
     */
    public static Vector2 add(Vector2 left, Vector2 right) {
        return new Vector2(left.x + right.x, left.y + right.y);
    }

    /**
     * Subtracts two vectors together and returns the result.
     * @param left the left side vector.
     * @param right the right side vector.
     * @return the resulting {@link Vector2}.
     */
    public static Vector2 sub(Vector2 left, Vector2 right) {
        return new Vector2(left.x - right.x, left.y - right.y);
    }

    /**
     * Normalizes the vector.
     */
    public void normalize() {
        float total = (float) Math.sqrt(this.x * this.x + this.y * this.y);
        this.x = this.x / total;
        this.y = this.y / total;
    }

    @Override
    public String toString() {
        return "[ "+this.x+","+this.y+" ]";
    }
}
