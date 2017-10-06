package djp.djp.djp.bubbler;

/**
 * Created by DJP on 8/27/2017.
 */

import org.junit.Test;

import static org.junit.Assert.*;

public class ListUnitTest {
    private PItemList list;

    public ListUnitTest() {
        list = new PItemList();
    }

    @Test
    public void isEmptyTest() {
        assertTrue(list.isEmpty());
        list.addItem(new PItem("A"));
        assertFalse(list.isEmpty());
    }

    @Test
    public void addItemTest() {
        list.addItem(new PItem("A"));
        assertEquals(list.getItemAt(0).getName(), "A");
        list.addItem(new PItem("B"));
        assertEquals(list.getItemAt(0).getName(), "B");
        assertEquals(list.getItemAt(1).getName(), "A");
    }

    @Test
    public void getSizeTest() {
        assertEquals(list.size(), 0);
        list.addItem(new PItem("A"));
        assertEquals(list.size(), 1);
        list.addItem(new PItem("B"));
        assertEquals(list.size(), 2);
    }

    @Test
    public void removeItemTest() {
        list.addItem(new PItem("A"));
        list.addItem(new PItem("B"));
        list.addItem(new PItem("C"));
        assertEquals(list.size(), 3);
        assertEquals(list.getItemAt(0).getName(), "C");
        assertEquals(list.getItemAt(1).getName(), "B");
        assertEquals(list.getItemAt(2).getName(), "A");
        list.removeItem(1);
        assertEquals(list.size(), 2);
        assertEquals(list.getItemAt(0).getName(), "C");
        assertEquals(list.getItemAt(1).getName(), "A");
        list.removeItem(0);
        assertEquals(list.getItemAt(0).getName(), "A");
        list.removeItem(0);
        assertTrue(list.isEmpty());
    }

    @Test
    public void promoteToTopTest() {
        list.addItem(new PItem("A"));
        list.addItem(new PItem("B"));
        list.addItem(new PItem("C"));
        assertEquals(list.getItemAt(0).getName(), "C");
        assertEquals(list.getItemAt(1).getName(), "B");
        assertEquals(list.getItemAt(2).getName(), "A");
        list.promoteToTop(1);
        assertEquals(list.getItemAt(0).getName(), "B");
        assertEquals(list.getItemAt(1).getName(), "C");
        assertEquals(list.getItemAt(2).getName(), "A");
        list.promoteToTop(2);
        assertEquals(list.getItemAt(0).getName(), "A");
        assertEquals(list.getItemAt(1).getName(), "B");
        assertEquals(list.getItemAt(2).getName(), "C");
        list.promoteToTop(0);
        assertEquals(list.getItemAt(0).getName(), "A");
        assertEquals(list.getItemAt(1).getName(), "B");
        assertEquals(list.getItemAt(2).getName(), "C");
    }

    @Test
    public void demoteToBottomTest() {
        list.addItem(new PItem("A"));
        list.addItem(new PItem("B"));
        list.addItem(new PItem("C"));
        assertEquals(list.getItemAt(0).getName(), "C");
        assertEquals(list.getItemAt(1).getName(), "B");
        assertEquals(list.getItemAt(2).getName(), "A");
        list.demoteToBottom(1);
        assertEquals(list.getItemAt(0).getName(), "C");
        assertEquals(list.getItemAt(1).getName(), "A");
        assertEquals(list.getItemAt(2).getName(), "B");
        list.demoteToBottom(0);
        assertEquals(list.getItemAt(0).getName(), "A");
        assertEquals(list.getItemAt(1).getName(), "B");
        assertEquals(list.getItemAt(2).getName(), "C");
        list.demoteToBottom(2);
        assertEquals(list.getItemAt(0).getName(), "A");
        assertEquals(list.getItemAt(1).getName(), "B");
        assertEquals(list.getItemAt(2).getName(), "C");
    }

    @Test
    public void setPrioritiesTest() {
        list.addItem(new PItem("A"));
        list.addItem(new PItem("B"));
        list.addItem(new PItem("C"));
        assertEquals(list.getItemAt(0).getPriority(), 1);
        assertEquals(list.getItemAt(1).getPriority(), 2);
        assertEquals(list.getItemAt(2).getPriority(), 3);
        list.demoteToBottom(1);
        assertEquals(list.getItemAt(0).getPriority(), 1);
        assertEquals(list.getItemAt(1).getPriority(), 2);
        assertEquals(list.getItemAt(2).getPriority(), 3);
        list.demoteToBottom(0);
        assertEquals(list.getItemAt(0).getPriority(), 1);
        assertEquals(list.getItemAt(1).getPriority(), 2);
        assertEquals(list.getItemAt(2).getPriority(), 3);
        list.demoteToBottom(2);
        assertEquals(list.getItemAt(0).getPriority(), 1);
        assertEquals(list.getItemAt(1).getPriority(), 2);
        assertEquals(list.getItemAt(2).getPriority(), 3);
    }

    @Test
    public void moveToTest() {
        list.addItem(new PItem("A"));
        list.addItem(new PItem("B"));
        list.addItem(new PItem("C"));
        assertEquals(list.getItemAt(0).getName(), "C");
        assertEquals(list.getItemAt(1).getName(), "B");
        assertEquals(list.getItemAt(2).getName(), "A");
        list.moveTo(1, 2);
        assertEquals(list.getItemAt(0).getName(), "C");
        assertEquals(list.getItemAt(1).getName(), "A");
        assertEquals(list.getItemAt(2).getName(), "B");
        list.moveTo(0, 2);
        assertEquals(list.getItemAt(0).getName(), "A");
        assertEquals(list.getItemAt(1).getName(), "B");
        assertEquals(list.getItemAt(2).getName(), "C");
        list.moveTo(0, 1);
        assertEquals(list.getItemAt(0).getName(), "B");
        assertEquals(list.getItemAt(1).getName(), "A");
        assertEquals(list.getItemAt(2).getName(), "C");
    }

    @Test
    public void noDuplicatesTest() {
        list.addItem(new PItem("A"));
        assertFalse(list.addItem(new PItem("A")));
        assertEquals(list.size(), 1);
    }

    @Test
    public void noEmptyItemTest() {
        list.addItem(new PItem(""));
        assertTrue(list.isEmpty());
    }
}
