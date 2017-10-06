package djp.djp.djp.bubbler;

/**
 * Created by DJP on 8/27/2017.
 */

public class PItem {
    private String name;
    private int priority;
    private PItemList list;

    PItem(String name) {
        this.name = name;
        priority = 0;
    }

    PItem(PItem rhs) {
        name = rhs.getName();
        priority = 0;
    }

    public void setName(String name) { this.name = name; }
    public String getName() { return name; }
    public void setPriority(int p) { priority = p; }
    public int getPriority() { return priority; }
    public void setList(PItemList list) { this.list = list; }
    public void print() {
        System.out.print("Task: ");
        System.out.print(name);
        System.out.print(", Priority: ");
        System.out.print(priority);
        System.out.print("\n");
    }

    public boolean isDuplicate(PItem rhs) {
        return rhs.getName().equals(name);
    }
}
