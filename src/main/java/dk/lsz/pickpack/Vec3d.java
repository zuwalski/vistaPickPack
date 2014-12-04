package dk.lsz.pickpack;

import java.util.Arrays;

/**
 * A basic 3-D vector
 * <p>
 * Created by larsszuwalski on 21/10/14.
 */
public class Vec3d {
    private final static int[] NO_TRANS = new int[]{0, 1, 2};

    private final float[] dim;
    private final int[] trans;

    private Vec3d(float x, float y, float z, int[] t) {
        dim = new float[]{x, y, z};
        trans = t;
    }

    private Vec3d(float[] d, int[] t) {
        this.dim = d;
        this.trans = t;
    }

    public float x() {
        return dim[trans[0]];
    }

    public float y() {
        return dim[trans[1]];
    }

    public float z() {
        return dim[trans[2]];
    }

    /**
     * un-transposed x
     *
     * @return
     */
    public float ox() {
        return dim[0];
    }

    /**
     * un-transposed y
     *
     * @return
     */
    public float oy() {
        return dim[1];
    }

    /**
     * un-transposed z
     *
     * @return
     */
    public float oz() {
        return dim[2];
    }

    public double volume() {
        return dim[0] * dim[1] * dim[2];
    }

    public double surface() {
        return (dim[0] * dim[1] + dim[1] * dim[2] + dim[0] * dim[2]) * 2;
    }

    public float[] toArray() {
        return new float[]{x(), y(), z()};
    }

    public float maxDim() {
        return Math.max(dim[0], Math.max(dim[1], dim[2]));
    }

    /**
     * Transposition (orientation) of the vector.
     *
     * @return
     */
    public int[] transposition() {
        return new int[]{trans[0], trans[1], trans[2]};
    }

    /**
     * The inverse (reverse-turn) transposition.
     *
     * @return
     */
    public int[] inverse() {
        int[] i = new int[3];
        i[trans[0]] = 0;
        i[trans[1]] = 1;
        i[trans[2]] = 2;
        return i;
    }

    /**
     * Apply this transposition to the vector - turning it.
     *
     * @param t
     * @return
     */
    public Vec3d transpose(int... t) {
        return new Vec3d(dim, applyT(t));
    }

    /**
     * Sort (turn) vector on dimensions.
     *
     * @return
     */
    public Vec3d norm() {
        final float x = x(), y = y(), z = z();

        if (x >= y) {
            if (y >= z) {
                return new Vec3d(dim, applyT(0, 1, 2));
            } else if (z >= x) {
                return new Vec3d(dim, applyT(2, 0, 1));
            } else {
                return new Vec3d(dim, applyT(0, 2, 1));
            }
        } else if (x >= z) {
            return new Vec3d(dim, applyT(1, 0, 2));
        } else if (z >= y) {
            return new Vec3d(dim, applyT(2, 1, 0));
        } else {
            return new Vec3d(dim, applyT(1, 2, 0));
        }
    }

    private int[] applyT(int... ts) {
        return new int[]{trans[ts[0]], trans[ts[1]], trans[ts[2]]};
    }

    /**
     * Factory-method to create a Vec3d-object.
     *
     * @param x
     * @param y
     * @param z
     * @return
     */
    public static Vec3d vec(float x, float y, float z) {
        return new Vec3d(x, y, z, NO_TRANS);
    }

    /**
     * Factory-method to create a Vec3d-object.
     *
     * @param d
     * @return
     */
    public static Vec3d vec(float[] d) {
        if (d == null) throw new NullPointerException();
        if (d.length != 3) throw new IllegalArgumentException("argument must have 3 elements");

        return new Vec3d(d[0], d[1], d[2], NO_TRANS);
    }

    @Override
    public String toString() {
        return "Vec3d{" +
                "dim=" + Arrays.toString(dim) +
                ", trans=" + Arrays.toString(trans) +
                '}';
    }
}
