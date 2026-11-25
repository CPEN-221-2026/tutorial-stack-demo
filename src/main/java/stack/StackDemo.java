package stack;

import java.util.Arrays;

/**
 * Nested method calls, for watching the stack and the heap in a debugger.
 * Put a breakpoint on the line marked BREAKPOINT in third(), then Debug.
 */
public class StackDemo {

    /** A heap object with one field that never changes and one that does. */
    static class Tracker {
        final String name;   // fixed when the object is created
        int calls;           // mutated by every method that receives the Tracker

        Tracker(String name) {
            this.name = name;
            this.calls = 0;
        }
    }

    public static void main(String[] args) {
        int x = 1;                                     // primitive: stored in main's frame
        Tracker tracker = new Tracker("demo");         // reference in main's frame, object on the heap
        int[] result = {0, 0, 0};                      // result points at an array on the heap
        result = first(x, tracker);                    // at the breakpoint, third's digits is a different array;
                                                       // after the call, result is repointed to it
        System.out.println(tracker.name + " " + tracker.calls + " " + Arrays.toString(result));
    }

    static int[] first(int x, Tracker tracker) {       // this x is a copy of main's x; tracker is a copy of the reference
        int doubled = x * 2;
        tracker.calls++;                               // mutates the one shared Tracker
        return second(doubled, tracker);
    }

    static int[] second(int x, Tracker tracker) {      // a different x: this frame has its own copy
        int tripled = x * 3;
        tracker.calls++;
        return third(tripled, tracker);
    }

    static int[] third(int x, Tracker tracker) {       // and another x, in the third frame
        int[] digits = {x, x + 1, x + 2};              // a new object on the heap, created in the deepest frame
        tracker.calls++;                               // BREAKPOINT: four frames are on the stack here
        return digits;                                 // third's frame is popped; the array lives on because
    }                                                  // main ends up holding a reference to it
}
