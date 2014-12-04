package dk.lsz.pickpack;

import java.util.Collection;
import java.util.Locale;

import static dk.lsz.pickpack.Vec3d.vec;

/**
 * A basic box with a name.
 * <p>
 * Created by larsszuwalski on 06/10/14.
 */
public class Box {
    private final static float[][] cornerOffset = new float[][]{
            {0, 0, 0}, {1, 0, 0}, {0, 1, 0}, {1, 1, 0},
            {0, 0, 1}, {1, 0, 1}, {0, 1, 1}, {1, 1, 1}};

    public final int id;
    public final Vec3d v;

    public Box(float x, float y, float z) {
        this.v = vec(x, y, z);
        this.id = 0;
    }

    public Box(int id, float x, float y, float z) {
        this.v = vec(x, y, z);
        this.id = id;
    }

    public double usedVolume() {
        return v.volume();
    }

    public void collectBestBoxes(Collection<Box> col, double tolerance) {
        col.add(this);
    }

    public static class Location {
        public final int id;
        public final String location;

        public Location(int id, String location) {
            this.id = id;
            this.location = location;
        }
    }

    public void locate(float lx, float ly, float lz, Vec3d bx, Collection<Location> locations) {
        StringBuilder b = new StringBuilder();

        for (int i = 0; i < cornerOffset.length; ++i) {
            float cx = lx + bx.x() * cornerOffset[i][0];
            float cy = ly + bx.y() * cornerOffset[i][1];
            float cz = lz + bx.z() * cornerOffset[i][2];

            b.append(String.format(Locale.ENGLISH, " (%.2f, %.2f, %.2f)", cx, cy, cz));
        }

        locations.add(new Location(id, b.toString()));
    }

    @Override
    public String toString() {
        return "Box{" +
                "id=" + id +
                ", x=" + v.x() +
                ", y=" + v.y() +
                ", z=" + v.z() +
                '}';
    }
}
