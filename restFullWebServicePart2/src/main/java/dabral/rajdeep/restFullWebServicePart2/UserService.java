package dabral.rajdeep.restFullWebServicePart2;

import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Component
public class UserService {
    private List<User> list;

    public UserService(){
        list =  new LinkedList<>();
        list.add(new User(101,"Rajdeep@123","Rajdeep"));
        list.add(new User(102,"Suraj#123","Suraj"));
        list.add(new User(103,"nitin3@123","nitin"));
        list.add(new User(104,"sumit123!","sumit"));
    }

    public List<User> getList() {
        return list;
    }

    public User getUserById(int id){
        for(User u: list){
            if(u.getId()==id){
                return u;
            }
        }
        return new User();
    }

    public boolean addUser(User user){
        for(User u :list){
            if(u.getId()==user.getId()){
                return false;
            }
        }
        list.add(user);
        return true;
    }
}
