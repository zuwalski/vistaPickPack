package dk.lsz.pickpack;

import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static dk.lsz.pickpack.Vec3d.vec;

/**
 * Created by larsszuwalski on 08/10/14.
 */
public class TestComboBuilder {

    @Test
    public void test() {
        Box b1 = new Box(1, 2, 1, 2);
        Box b2 = new Box(2, 2, 1, 1);

        ComboBuilder cb = ComboBuilder.stacked(b1, b2);

        Box c = cb.getCombo();

        System.out.println(c);

        List<Box.Location> locations = new ArrayList<>();
        c.locate(0, 0, 0, vec(0, 0, 0), locations);
    }

    @Test
    public void test2() {
        LinkedList<Box> boxes = new LinkedList<Box>();

        boxes.add(new Box(1, 2, 1, 1));
        boxes.add(new Box(1, 2, 1, 1));

        ComboBuilder cb = ComboBuilder.stacked(boxes.poll(), boxes.poll());

        cb.add(boxes);

        Box c = cb.getCombo();

        System.out.println(c);

        List<Box.Location> locations = new ArrayList<>();
        c.locate(0, 0, 0, vec(0, 0, 0), locations);
    }
}
