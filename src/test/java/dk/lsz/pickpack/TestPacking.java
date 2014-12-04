package dk.lsz.pickpack;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by larsszuwalski on 06/10/14.
 */
public class TestPacking {

    @Test
    public void testLoadItems() {
        Collection<Box> boxes = new ArrayList<Box>();
        boxes.add(new Box(1, 2, 3));
        boxes.add(new Box(10, 20, 30));
        boxes.add(new Box(5, 4, 3));

        Box b = Packing.pack(boxes);
    }

    @Test
    public void testPackSimple() {
        Collection<Box> boxes = new ArrayList<Box>();
        boxes.add(new Box(1, 1, 2, 3));
        boxes.add(new Box(2, 10, 20, 30));
        boxes.add(new Box(3, 5, 4, 3));
        boxes.add(new Box(4, 5, 4, 3));
        boxes.add(new Box(5, 5, 4, 3));
        boxes.add(new Box(6, 5, 4, 3));

        Box b = Packing.pack(boxes);

        List<Box.Location> locations = new ArrayList<>();
        b.locate(0, 0, 0, b.v, locations);
    }

    @Test
    public void testPackingDices() {
        Collection<Box> boxes = new ArrayList<Box>();
        for (int i = 0; i < 9; ++i) {
            boxes.add(new Box(i, 2, 1, 1));
        }

        Box b = Packing.pack(boxes);

        System.out.println(b);

        List<Box.Location> locations = new ArrayList<>();
        b.locate(0, 0, 0, b.v, locations);

        System.out.println("shuffle");

        b = Packing.rePack(b, 0.98d);

        System.out.println(b);

        b.locate(0, 0, 0, b.v, locations);
    }

    @Test
    public void testPackingDices2() {
        Collection<Box> boxes = new ArrayList<Box>();
        for (int i = 0; i < 12; ++i) {
            boxes.add(new Box(2, 2, 2));
        }

        Box b = Packing.pack(boxes);

        System.out.println(b);
    }
}
