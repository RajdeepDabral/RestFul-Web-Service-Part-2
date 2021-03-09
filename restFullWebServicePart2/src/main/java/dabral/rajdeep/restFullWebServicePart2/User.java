package dabral.rajdeep.restFullWebServicePart2;


import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
@JsonFilter("User")
public class User {
    private int id;
   //@JsonIgnore
    private String password;
    private String name;

    public User(){
        id=0;
        password=null;
        name=null;
    }
    public User(int id, String password, String name) {
        this.id = id;
        this.password = password;
        this.name = name;
    }


    public int getId() {
        return id;
    }
    public String getPassword() {
        return password;
    }
    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
