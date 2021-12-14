package sample;

public class desc {
    String slno;
    String item;
    String amount;

    public String getSlno() {
        return slno;
    }

    public void setSlno(String slno) {
        this.slno = slno;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public desc(String slno, String item, String amount) {
        this.slno = slno;
        this.item = item;
        this.amount = amount;
    }
}
