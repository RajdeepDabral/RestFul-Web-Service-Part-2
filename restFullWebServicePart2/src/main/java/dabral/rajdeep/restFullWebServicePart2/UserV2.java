package dabral.rajdeep.restFullWebServicePart2;

public class UserV2 extends User {
    private String Address;

    public UserV2() {
        super();
        Address = null;
    }
    public UserV2(int id, String password, String name, String address) {
        super(id, password, name);
        Address = address;
    }
    public String getAddress() {
        return Address;
    }
    public void setAddress(String address) {
        Address = address;
    }
    @Override
    public String toString() {
        return "Id : "+ this.getId() + "Name : "+this.getName() + "Address : "+this.getAddress();
    }
}
