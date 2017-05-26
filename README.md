# gratistester
Simple test tool for Java

Usage:
If you have an Object with a sloppy written equals method
```java
    @Override
    public boolean equals(Object obj) {
        return this.name.equalsIgnoreCase(((User) obj).name);
    }
```
The following test

```java
    @Test
    public void gratisTest() {
        User u1 = new User("foo"); // Two objects that are equal (u1 & u2)
        User u2 = new User("foo");
        User u3 = new User("bar"); // One that is not equal
        GratisTest.all().on(u1, u2, u3);
    }
```
will give you an AssertionError

```
java.lang.AssertionError: For any non-null reference value x, x.equals(null) should return false.
```

GratisTest will also check for other things, e.g. that the equals method is reflexive and symetric.
