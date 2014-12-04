package dk.lsz.pickpack;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import static dk.lsz.pickpack.Vec3d.vec;

/**
 * Main class (driver) of packaging algorithm.
 * <p>
 * Implements the reading of box-definitions from standard-in and the reporting of the resulting box.
 * <p>
 * Created by larsszuwalski on 30/10/14.
 */
public class PackingDriver {
    public static final boolean BREAK_ON_BLANK = false;

    public static void main(String[] args) {
        try {
            List<Box> boxes = readStdin();

            packAndPrint(boxes);

        } catch (Exception e) {
            System.out.println("Error");
        }
    }

    /**
     * Note: Will break if reading an empty line - if BREAK_ON_BLANK == true
     *
     * @return
     * @throws IOException
     */
    private static List<Box> readStdin() throws IOException {
        List<Box> boxes = new ArrayList<>(100);

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        for (String line = br.readLine(); line != null; line = br.readLine()) {
            String[] parts = line.split("\\s");
            if (parts.length == 0 && BREAK_ON_BLANK)
                break;

            if (parts.length != 4)
                throw new IllegalArgumentException();

            boxes.add(readBox(parts));
        }

        return boxes;
    }

    private static void packAndPrint(List<Box> boxes) {
        Box best = Packing.packBest(boxes);

        Vec3d normDim = best.v.norm();

        System.out.printf(Locale.ENGLISH, "%.2f %.2f %.2f\n", normDim.x(), normDim.y(), normDim.z());

        List<Box.Location> locations = new ArrayList<>();
        best.locate(0, 0, 0, vec(0, 0, 0), locations);

        Collections.sort(locations, (b1, b2) -> Integer.compare(b1.id, b2.id));

        for (Box.Location l : locations) {
            System.out.print(l.id);
            System.out.print(' ');
            System.out.println(l.location);
        }
    }

    private static Box readBox(String[] parts) {
        final int id = Integer.parseInt(parts[0]);
        final float
                l = Float.parseFloat(parts[1]),
                w = Float.parseFloat(parts[2]),
                h = Float.parseFloat(parts[3]);

        return new Box(id, l, w, h);
    }
}
