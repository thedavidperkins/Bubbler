package djp.djp.djp.bubbler;

/**
 * Created by DJP on 8/27/2017.
 */

public class PItemList {
    private class ListNode {
        public PItem myItem;
        public ListNode prevNode;
        public ListNode nextNode;
        public ListNode(PItem p) {
            myItem = p;
            prevNode = null;
            nextNode = null;
        }
    }

    private ListNode first;
    private ListNode last;
    private int listSize;
    private boolean exists(Object o) {
        return o != null;
    }

    public PItemList() {
        first = null;
        last = null;
        listSize = 0;
    }

    private ListNode itemAtPos(int pos) throws RuntimeException {
        if(pos >= listSize || pos < 0) {
            throw new RuntimeException("Attempted to access out of bounds element");
        }
        ListNode l = first;
        for(int iter = 0; iter < pos; ++iter) {
            l = l.nextNode;
        }
        return l;
    }

    private void setPriorities() {
        ListNode l = first;
        for(int iter = 1; iter <= listSize; ++iter) {
            l.myItem.setPriority(iter);
            l = l.nextNode;
        }
    }

    public int size() { return listSize; }
    public boolean isEmpty() { return first == null; }

    public boolean contains(PItem item) {
        for(int iter = 0; iter < listSize; ++iter) {
            if(getItemAt(iter).isDuplicate(item)) {
                return true;
            }

        }
        return false;
    }

    public boolean addItem(PItem p) {
        if(contains(p) || p.getName().equals("")) {
            return false;
        }
        p.setList(this);
        ListNode l = new ListNode(p);
        if(isEmpty()) {
            first = l;
            last = first;
            l.prevNode = null;
            l.nextNode = null;
        }
        else {
            first.prevNode = l;
            l.nextNode = first;
            first = l;
        }
        ++listSize;
        setPriorities();
        return true;
    }

    public boolean removeItem(int pos) {
        ListNode l;
        try {
            l = itemAtPos(pos);
        }
        catch (Exception e) {
            return false;
        }
        if (listSize == 1) {
            first = null;
            last = null;
        }
        else if (l.equals(first)) {
            first = first.nextNode;
            first.prevNode = null;
        }
        else if (l.equals(last)) {
            last = last.prevNode;
            last.nextNode = null;
        }
        else {
            l.prevNode.nextNode = l.nextNode;
            l.nextNode.prevNode = l.prevNode;
        }
        listSize--;
        setPriorities();
        return true;
    }

    public boolean moveTo(int oldPos, int newPos) {
        if(oldPos == newPos) {
            return true;
        }

        ListNode oldL;
        ListNode newAbove;
        ListNode newBelow;
        ListNode oldAbove;
        ListNode oldBelow;

        try {
            oldL = itemAtPos(oldPos);
            newAbove = oldPos > newPos ? itemAtPos(newPos).prevNode : itemAtPos(newPos);
            newBelow = oldPos > newPos ? itemAtPos(newPos) : itemAtPos(newPos).nextNode;
            oldAbove = oldL.prevNode;
            oldBelow = oldL.nextNode;
        }
        catch (Exception e) {
            return false;
        }

        if (exists(oldAbove)) {
            oldAbove.nextNode = oldBelow;
        }
        else {
            first = oldBelow;
        }

        if (exists(oldBelow)) {
            oldBelow.prevNode = oldAbove;
        }
        else {
            last = oldAbove;
        }

        oldL.prevNode = newAbove;
        oldL.nextNode = newBelow;

        if(exists(newBelow)) {
            newBelow.prevNode = oldL;
        }
        else {
            last = oldL;
        }

        if(exists(newAbove)) {
            newAbove.nextNode = oldL;
        }
        else {
            first = oldL;
        }
        setPriorities();
        return true;
    }

    public void printList() {
        System.out.println("======================================");
        ListNode l = first;
        for (int iter = 0; iter < listSize; ++iter) {
            l.myItem.print();
            l = l.nextNode;
        }
    }

    public PItem getItemAt(int pos) {
        PItem ret = null;
        try {
            ret = itemAtPos(pos).myItem;
        }
        finally {
            return ret;
        }
    }

    public boolean promoteToTop(int pos) {
        return moveTo(pos, 0);
    }

    public boolean demoteToBottom(int pos) {
        return moveTo(pos, listSize - 1);
    }
}
