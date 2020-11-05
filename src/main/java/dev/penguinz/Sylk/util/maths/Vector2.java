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
     * Creates a new {@link Vector2} and initializes its components to the given value.
     * @param value the value to initialize both components to;
     */
    public Vector2(float value) {
        this(value, value);
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
     * Creates a new {@link Vector2} based off of the given vector
     * @param original the vector to copy from
     */
    public Vector2(Vector2 original) {
        this.x = original.x;
        this.y = original.y;;
    }

    /**
     * Adds another {@link Vector2} to this vector.
     * @param right the vector being added.
     * @return the current vector.
     */
    public Vector2 add(Vector2 right) {
        this.x += right.x;
        this.y += right.y;
        return this;
    }

    /**
     * Subtracts another {@link Vector2} from this vector.
     * @param right the vector subtracting.
     * @return the current vector.
     */
    public Vector2 sub(Vector2 right) {
        this.x -= right.x;
        this.y -= right.y;
        return this;
    }

    /**
     * Multiples the current vector with the scalar value.
     * @param scalar the value to multiply by.
     * @return the current vector.
     */
    public Vector2 mul(float scalar) {
        this.x *= scalar;
        this.y *= scalar;
        return this;
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
     * Multiplies a vector by a scalar value and returns the result.
     * @param left the vector to be multiplied.
     * @param scalar the value to multiply by.
     * @return the resulting {@link Vector2}.
     */
    public static Vector2 mul(Vector2 left, float scalar) {
        return new Vector2(left.x * scalar, left.y * scalar);
    }

    /**
     * Normalizes the vector.
     */
    public void normalize() {
        float total = (float) Math.sqrt(this.x * this.x + this.y * this.y);
        this.x = this.x / total;
        this.y = this.y / total;
    }

    /**
     * Normalizes the given vector and returns the result.
     * @param source the vector to normalize.
     * @return the normalized vector.
     */
    public static Vector2 normalize(Vector2 source) {
        Vector2 newVector = new Vector2(source);
        float total = (float) Math.sqrt(newVector.x * newVector.x + newVector.y * newVector.y);
        newVector.x = newVector.x / total;
        newVector.y = newVector.y / total;
        return newVector;
    }

    /**
     * Gives the dot product between the two vectors.
     * @param left the left hand vector.
     * @param right the right hand vector.
     * @return the dot product.
     */
    public static float dot(Vector2 left, Vector2 right) {
        return left.x * right.x + left.y * right.y;
    }

    public boolean equals(Vector2 other) {
        return this.x == other.x && this.y == other.y;
    }

    @Override
    public String toString() {
        return "[ "+this.x+","+this.y+" ]";
    }
}
