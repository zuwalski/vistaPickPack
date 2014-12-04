package dk.lsz.pickpack;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static dk.lsz.pickpack.Vec3d.vec;

/**
 * A box of boxes.
 * <p>
 * Created by larsszuwalski on 19/10/14.
 */
public class ComboBox extends Box {
    private final List<Enclosed> enclosed = new ArrayList<>();
    private double volume = 0d;

    public ComboBox(float x, float y, float z) {
        super(x, y, z);
    }

    /**
     * Add another box to this box at position lx, ly, lz and with the spacial orientation of trans.
     *
     * @param bx
     * @param trans
     * @param lx
     * @param ly
     * @param lz
     */
    public void addBox(Box bx, Vec3d trans, float lx, float ly, float lz) {
        enclosed.add(new Enclosed(bx, trans, lx, ly, lz));

        this.volume += bx.usedVolume();
    }

    @Override
    public double usedVolume() {
        return volume;
    }

    @Override
    public void locate(float lx, float ly, float lz, Vec3d bx, Collection<Location> locations) {
        for (Enclosed e : enclosed) {
            Vec3d d = vec(e.lx, e.ly, e.lz).transpose(bx.transposition());

            e.bx.locate(lx + d.x(), ly + d.y(), lz + d.z(), e.trs.transpose(bx.transposition()), locations);
        }
    }

    @Override
    public void collectBestBoxes(Collection<Box> col, double tolerance) {
        if (volume / v.volume() >= tolerance) {
            col.add(this);
        } else {
            for (Enclosed e : enclosed) {
                e.bx.collectBestBoxes(col, tolerance);
            }
        }
    }

    private static class Enclosed {
        final Box bx;
        final Vec3d trs;
        final float lx;
        final float ly;
        final float lz;

        Enclosed(Box bx, Vec3d trs, float lx, float ly, float lz) {
            this.bx = bx;
            this.trs = trs;
            this.lx = lx;
            this.ly = ly;
            this.lz = lz;
        }
    }
}
