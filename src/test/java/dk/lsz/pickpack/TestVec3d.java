package dk.lsz.pickpack;

import org.junit.Test;

import static dk.lsz.pickpack.Vec3d.vec;
import static org.junit.Assert.*;

/**
 * Created by larsszuwalski on 23/10/14.
 */
public class TestVec3d {

    @Test
    public void testCreate() {
        Vec3d v = vec(1, 2, 3);

        assertEquals(v.x(), 1f, 0);
        assertEquals(v.y(), 2f, 0);
        assertEquals(v.z(), 3f, 0);

        final float[] io = fs(1, 2, 3);
        assertArrayEquals(io, vec(io).toArray(), 0);
    }

    @Test
    public void testNorm() {
        final float[] res = fs(3, 2, 1);
        assertArrayEquals(res, vec(1, 2, 3).norm().toArray(), 0);
        assertArrayEquals(res, vec(1, 3, 2).norm().toArray(), 0);
        assertArrayEquals(res, vec(2, 1, 3).norm().toArray(), 0);
        assertArrayEquals(res, vec(2, 3, 1).norm().toArray(), 0);
        assertArrayEquals(res, vec(3, 1, 2).norm().toArray(), 0);
        assertArrayEquals(res, vec(3, 2, 1).norm().toArray(), 0);

        assertArrayEquals(is(2, 1, 0), vec(1, 2, 3).norm().transposition());
        assertArrayEquals(is(1, 2, 0), vec(1, 3, 2).norm().transposition());
        assertArrayEquals(is(2, 0, 1), vec(2, 1, 3).norm().transposition());
        assertArrayEquals(is(1, 0, 2), vec(2, 3, 1).norm().transposition());
        assertArrayEquals(is(0, 2, 1), vec(3, 1, 2).norm().transposition());
        assertArrayEquals(is(0, 1, 2), vec(3, 2, 1).norm().transposition());
    }

    private static float[] fs(float... fs) {
        return fs;
    }

    private static int[] is(int... is) {
        return is;
    }
}
