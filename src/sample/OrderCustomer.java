package sample;

public class OrderCustomer {
    String oid;
    String desc;
    String gst;
    String cid;
    String name;
    String address;
    String country;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }
//
    public OrderCustomer(String oid,String cid,String desc,String gst,String name,String address,String country) {
        this.oid = oid;
        this.desc = desc;
        this.gst = gst;
        this.cid = cid;
        this.name = name;
        this.address = address;
        this.country = country;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getGst() {
        return gst;
    }

    public void setGst(String gst) {
        this.gst = gst;
    }
}
