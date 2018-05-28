package de.hawhh.informatik.sml.kino.fachwerte;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class PlatzTest
{
    @Test
    public void testPlatzNullNullIstGueltig()
    {
        Platz p = Platz.get(0, 0);
        assertEquals(0, p.getReihenNr());
        assertEquals(0, p.getSitzNr());
    }

    @Test
    public void testPlatzGibtReiheUndSitzZurueck()
    {
        Platz p = Platz.get(123, 456);
        assertEquals(123, p.getReihenNr());
        assertEquals(456, p.getSitzNr());
    }

    @Test
    public void testEqualsUndHashCode()
    {
        Platz p1 = Platz.get(1, 2);
        Platz p2 = Platz.get(1, 2);
        Platz p3 = Platz.get(1, 3); // Sitz unterschiedlich
        Platz p4 = Platz.get(2, 2); // Reihe unterschiedlich

        assertTrue(p1.equals(p2));
        assertTrue(p1.hashCode() == p2.hashCode());

        assertFalse(p1.equals(p3));
        assertFalse(p1.equals(p4));
    }
}
