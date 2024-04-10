public class Table {
    int tableNum;
    int capacity;
    String status;

    public Table(int tableNum, String status) {
        this.tableNum = tableNum;
        this.capacity = 2;
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
