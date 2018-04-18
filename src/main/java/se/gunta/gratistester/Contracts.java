package se.gunta.gratistester;

import java.io.IOException;
import java.io.Serializable;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/** 
 * 
 * @author Magnus Gunnarsson
 * 
 */
public class Contracts {
    
    protected Contracts() {}
    
    public void on(Object... objects) {
        for (Object object1 : objects) {
            for (Object object2 : objects) {
                if (object1 != object2) {
                    assertEquality(object1, object2);
                    assertHashCode(object1, object2);
                    assertComperableNice(object1, object2);
                }
            }
            assertSerializable(object1);
            assertCompareToNullYieldsNullPointerException(object1);
        }
    }

    public static void assertEquality(Object x, Object y) {
        assertReflexive(x);
        assertSymmetric(x, y);
        assertConsistent(x, y);
        assertNullEquality(x);
    }
    
    public static void assertHashCode(Object x, Object y) {
        if (x.equals(y)) {
            assertTrue("If two objects are equal according to the equals(Object) method, then calling the hashCode method on each of the two objects must produce the same integer result.", x.hashCode() == y.hashCode());
        }
    }

    public static void assertReflexive(Object x) {
        assertTrue("For any non-null reference value x, x.equals(x) should return true.", x.equals(x));
    }

    public static void assertSymmetric(Object x, Object y) {
        assertTrue("For any non-null reference values x and y, x.equals(y) should return true if and only if y.equals(x) returns true.", x.equals(y) == y.equals(x));
    }

    public static void assertConsistent(Object x, Object y) {
        boolean eq = x.equals(y);
        assertEquals("For any non-null reference values x and y, multiple invocations of x.equals(y) consistently return true or consistently return false, provided no information used in equals comparisons on the objects is modified.", eq, x.equals(y));
    }

    @SuppressWarnings("ObjectEqualsNull")
    public static void assertNullEquality(Object x) {
        assertFalse("For any non-null reference value x, x.equals(null) should return false.", x.equals(null));
    }
    
    /**
     * (x.compareTo(y)==0) == (x.equals(y))
     */
    public static void assertComperableNice(Object x, Object y) {
        if (x instanceof Comparable) {
            Comparable xc = (Comparable) x;
            assertTrue("It is strongly recommended (though not required) that natural orderings be consistent with equals.", (xc.compareTo(y)==0) == (xc.equals(y)));
        }
    }
    
    public static void assertSerializable(Object x) {
        try {
            if (x instanceof Serializable) {
                Serializable xs = (Serializable) x;
                byte[] asByteArray = Serializer.pickle(xs);
                Serializable unpickle = Serializer.unpickle(asByteArray, (Class<Serializable>) x.getClass());
            }
        } catch (IOException | ClassNotFoundException ex) {
            throw new AssertionError(ex);
        }
    }
    
    public static void assertCompareToNullYieldsNullPointerException(Object x) {
        if (x instanceof Comparable) {
            Comparable xc = (Comparable) x;
            boolean thrown = false;
            try {
                xc.compareTo(null);
            } catch (NullPointerException e) {
                thrown = true;
            }
            assertTrue("Note that null is not an instance of any class, and e.compareTo(null) should throw a NullPointerException even though e.equals(null) returns false.", thrown);
        }
    }
}
