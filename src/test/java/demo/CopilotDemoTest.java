package demo;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CopilotDemoTest {
// How do I write a function to sort a list in Java?
    @Test
    public void sortsListInAscendingOrder() {
        List<Integer> input = Arrays.asList(5, 3, 8, 1);
        List<Integer> expected = Arrays.asList(1, 3, 5, 8);

        Collections.sort(input);

        Assert.assertEquals(input, expected);
    }

    @Test
    public void handlesEmptyList() {
        List<Integer> input = Collections.emptyList();
        List<Integer> expected = Collections.emptyList();

        Collections.sort(input);

        Assert.assertEquals(input, expected);
    }

    @Test
    public void handlesSingleElementList() {
        List<Integer> input = Collections.singletonList(42);
        List<Integer> expected = Collections.singletonList(42);

        Collections.sort(input);

        Assert.assertEquals(input, expected);
    }

    @Test
    public void handlesAlreadySortedList() {
        List<Integer> input = Arrays.asList(1, 2, 3, 4);
        List<Integer> expected = Arrays.asList(1, 2, 3, 4);

        Collections.sort(input);

        Assert.assertEquals(input, expected);
    }

    @Test
    public void handlesListWithDuplicates() {
        List<Integer> input = Arrays.asList(4, 2, 2, 4);
        List<Integer> expected = Arrays.asList(2, 2, 4, 4);

        Collections.sort(input);

        Assert.assertEquals(input, expected);
    }
}
