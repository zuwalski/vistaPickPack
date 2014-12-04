package dk.lsz.pickpack;

import java.util.Collection;
import java.util.Iterator;
import java.util.PriorityQueue;

import static dk.lsz.pickpack.Vec3d.vec;
import static java.lang.Math.max;

/**
 * Start by combining 2 boxes such that each is joined on the other on the largest of its sides.
 * <p>
 * That is: Normalize each box - place the first at origin and the other at origin + first-boxes width.
 * By repeating this process the figures build will converge towards even-sided cubes (e.g as the figure grows in one
 * dimension that dimension will once its dominating attract boxes and thereby balancing the figure).
 * <p>
 * The resulting figure might leave areas of free space around the boxes.
 * <p>
 * Then try to add further boxes to the figure by filling spaces (smallest first) - creating new (smaller) spaces as
 * one is occupied by a box.
 * <p>
 * Created by lars.szuwalski on 17/10/14.
 */
public class ComboBuilder {
    public static final float MIN_SPACE = 0.1f;

    private static final int[][] turns = {{2, 1, 0}, {2, 0, 1}, {1, 2, 0}, {1, 0, 2}, {0, 2, 1}, {0, 1, 2}};

    private final PriorityQueue<Space> spaces = new PriorityQueue<Space>((o1, o2) -> Float.compare(o1.spc.x(), o2.spc.x()));
    private final ComboBox combo;

    private ComboBuilder(float x, float y, float z) {
        this.combo = new ComboBox(x, y, z);
    }

    /**
     * Factory-method to create a combo-builder that starts the combo-box by joining on dominating sides.
     *
     * Note will only actually add b1 (so b2 should be given to add(collection) after this
     *
     * @param b1
     * @param b2
     * @return
     */
    public static ComboBuilder stacked(Box b1, Box b2) {
        Vec3d n1 = b1.v.norm();
        Vec3d n2 = b2.v.norm();

        ComboBuilder cb = new ComboBuilder(max(n1.x(), n2.x()), max(n1.y(), n2.y()), n1.z() + n2.z());

        cb.addSpace(cb.combo.v.x(), cb.combo.v.y(), cb.combo.v.z(), 0, 0, 0);

        cb.add(b1, n1);

        return cb;
    }

    /**
     * Returns the current combo-box.
     *
     * @return
     */
    public ComboBox getCombo() {
        return combo;
    }

    /**
     * Adds as many boxes from bxs to this figure as possible (removing these boxes from the collection in the process).
     *
     * @param bxs
     */
    public void add(Collection<Box> bxs) {
        Iterator<Box> it = bxs.iterator();

        while (!spaces.isEmpty() && it.hasNext()) {
            Box b = it.next();
            if (add(b, b.v.norm())) {
                it.remove();
            }
        }
    }

    private boolean add(Box bx, Vec3d v) {
        final float[] d = v.toArray();

        for (Iterator<Space> it = spaces.iterator(); it.hasNext(); ) {
            final Space s = it.next();
            final int[] t = bestFit(s, d);

            if (t != null) {
                final Vec3d vt = v.transpose(t).transpose(s.spc.inverse());

                it.remove();

                split(s, vt.x(), vt.y(), vt.z());

                combo.addBox(bx, vt, s.lx, s.ly, s.lz);
                return true;
            }
        }

        return false;
    }

    /**
     * Tries to turn the box in a way that leaves most free space
     *
     * @param s
     * @param b
     * @return
     */
    private int[] bestFit(Space s, float[] b) {
        final float sx = s.spc.x(), sy = s.spc.y(), sz = s.spc.z();

        for (int[] t : turns) {
            if (sx >= b[t[0]] && sy >= b[t[1]] && sz >= b[t[2]]) {
                return t;
            }
        }

        return null;
    }

    /**
     * Place box in space and derive the smaller spaces created by this process.
     *
     * @param s
     * @param x
     * @param y
     * @param z
     */
    private void split(Space s, float x, float y, float z) {
        float sx = s.spc.ox(), sy = s.spc.oy(), sz = s.spc.oz();

        while (true) {
            final float a = sx - x > MIN_SPACE ? (sx - x) * sy * sz : 0;
            final float b = sy - y > MIN_SPACE ? sx * (sy - y) * sz : 0;
            final float c = sz - z > MIN_SPACE ? sx * sy * (sz - z) : 0;

            if (a > 0 && a >= b && a >= c) {
                addSpace(sx - x, sy, sz, s.lx + x, s.ly, s.lz);

                sx = x;
            } else if (b > 0 && b >= a && b >= c) {
                addSpace(sx, sy - y, sz, s.lx, s.ly + y, s.lz);

                sy = y;
            } else if (c > 0) {
                addSpace(sx, sy, sz - z, s.lx, s.ly, s.lz + z);

                sz = z;
            } else
                break;
        }
    }

    private void addSpace(float sx, float sy, float sz, float lx, float ly, float lz) {
        if (sx > MIN_SPACE && sy > MIN_SPACE && sz > MIN_SPACE)
            spaces.add(new Space(vec(sx, sy, sz).norm(), lx, ly, lz));
    }

    private static class Space {
        private final Vec3d spc;
        private final float lx;
        private final float ly;
        private final float lz;

        Space(Vec3d spc, float lx, float ly, float lz) {
            this.spc = spc;
            this.lx = lx;
            this.ly = ly;
            this.lz = lz;
        }
    }
}
