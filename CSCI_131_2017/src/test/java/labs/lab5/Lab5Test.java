package labs.lab5;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.Before;

public class Lab5Test {
    private Segment segment;

    @Before public void setup() {
        segment = new Segment(new Point(-1, -1), new Point(1, 1));
    }

    @Test public void testSegment_DirectlyOn() {
        assertTrue(segment.doesCollide(new Point(0, 0)));
    }

    @Test public void testSegment_WithinTollerance() {
        assertTrue(segment.doesCollide(new Point(0, 0.000001)));
    }

    @Test public void testSegment_NotWithinTollerance() {
        assertFalse(segment.doesCollide(new Point(0, 0.001)));
    }

    @Test public void testSegment_OnButOutsideDomain() {
        assertFalse(segment.doesCollide(new Point(2, 2)));
    }
}