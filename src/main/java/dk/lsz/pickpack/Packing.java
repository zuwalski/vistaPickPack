package dk.lsz.pickpack;

import java.util.*;

/**
 * Packing machine
 *
 * Created by larsszuwalski on 06/10/14.
 */
public class Packing {
    private final static Random rnd = new Random();

    /**
     * Keep repacking a fixed number of times.
     * Track the package with the smallest surface and return that.
     * Boxes with poor density are "opened" and repacked
     *
     * @param boxes
     * @return
     */
    public static Box packBest(Collection<Box> boxes) {
        Box best, b;
        b = best = pack(boxes);

        for (int i = 0; i < 100; ++i) {
            b = rePack(b, 0.95d);

            if (b.v.surface() < best.v.surface()) {
                best = b;
            }
        }

        return best;
    }

    /**
     * Packing-stage that starts from the largest box and combines until only one box remains.
     *
     * @param pack
     * @return
     */
    public static Box pack(Collection<Box> pack) {
        LinkedList<Box> batch = new LinkedList<>(pack);

        Collections.sort(batch, (b1, b2) -> Double.compare(b2.v.maxDim(), b1.v.maxDim()));

        while (batch.size() > 1) {
            Box b1 = batch.poll();
            Box b2 = batch.peek();

            batch.addFirst(stackedCombo(batch, b1, b2));
        }

        return batch.peek();
    }

    /**
     * Re-packing stage where a box is opened and contained boxes are repacked into
     * smaller boxes using random selection.
     *
     * @param root
     * @param tolerance
     * @return
     */
    public static Box rePack(Box root, double tolerance) {
        LinkedList<Box> batch = new LinkedList<>();

        root.collectBestBoxes(batch, tolerance);

        Collections.sort(batch, (b1, b2) -> Double.compare(b2.v.volume(), b1.v.volume()));

        LinkedList<Box> next = new LinkedList<>();

        // skip the largest element
        next.add(batch.poll());

        // combine next-largest element with random (smaller) element
        while (batch.size() > 1) {
            Box b1 = batch.poll();

            int i = rnd.nextInt(batch.size());
            Box b2 = batch.get(i);

            next.add(stackedCombo(batch, b1, b2));
        }

        // add rest of batch
        next.addAll(batch);

        // take a new round of packing
        return pack(next);
    }

    /**
     * Driver for the Combo-builder factory
     *
     * @param bxs
     * @param b1
     * @param b2
     * @return
     */
    public static ComboBox stackedCombo(Collection<Box> bxs, Box b1, Box b2) {
        ComboBuilder cb = ComboBuilder.stacked(b1, b2);

        cb.add(bxs);

        return cb.getCombo();
    }
}
