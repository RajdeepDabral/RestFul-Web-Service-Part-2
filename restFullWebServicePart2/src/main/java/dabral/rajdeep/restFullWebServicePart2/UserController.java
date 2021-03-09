package dabral.rajdeep.restFullWebServicePart2;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;


@RestController
public class UserController {
    @Autowired
    private  UserService userService;

    @Autowired
    private MessageSource messageSource;

    @GetMapping("/users")
    public List<User> showAllUsers(){
        return userService.getList();
    }
    @GetMapping("/user/{id}")
    public User userById(@PathVariable int id){
        return userService.getUserById(id);
    }
    @PostMapping("/user")
    public User addUser(@RequestBody User user){
        Boolean bool= userService.addUser(user);
        if(bool == false){
            System.out.println("Id All Ready Exits!!!!!");
            return new User();
        }
        return user;
    }

    /*
    1. Add support for Internationalization in your application allowing messages
        to be shown in English, German and Swedish, keeping English as default.
    */
    @GetMapping("/userMessage")
    public String userMessage(){
       return  messageSource.getMessage("good_Morning" , null , LocaleContextHolder.getLocale());
    }

    /*
    2. Create a GET request which takes "username" as param and shows a localized message "Hello Username".
        (Use parameters in message properties)
    */
    @GetMapping("/helloUser/{username}")
    public String helloUser(@PathVariable String username){
        return  messageSource.getMessage("hello" , null , LocaleContextHolder.getLocale()) + username;
    }

    /*
    3. Create POST Method to create user details which can accept XML for user creation.
     */
    @PostMapping("/addUserInXmlFormat")
    public User addUserInXmlFormat(@RequestBody User user){
        Boolean bool= userService.addUser(user);
        if(bool == false){
            System.out.println("Id All Ready Exits!!!!!");
            return new User();
        }
        return user;
    }

    /*
    4. Create GET Method to fetch the list of users in XML format.
     */
    @GetMapping("/usersInXmlFormat")
    public List<User> showAllUsersInXmlFormat(){
        return userService.getList();
    }


    /*8. Create API which saves details of User (along with the password)
        but on successfully saving returns only non-critical data. (Use static filtering)
    */
    @PostMapping("/addUserWithPasswordFiltering")
    public User addUserWithPasswordFiltering(@RequestBody User user){
        Boolean bool= userService.addUser(user);
        if(bool == false){
            System.out.println("Id All Ready Exits!!!!!");
            return new User();
        }
        return user;
    }
    /*
    9. Create another API that does the same by using Dynamic Filtering.
    */
    @PostMapping("/addUserWithPasswordFilteringDynamic")
    public MappingJacksonValue addUserWithPasswordFilteringDynamic(@RequestBody User user){
        Boolean bool= userService.addUser(user);
        SimpleBeanPropertyFilter filter =SimpleBeanPropertyFilter.
                filterOutAllExcept("id", "name");
        FilterProvider filters = new SimpleFilterProvider().
                addFilter("User",filter);
        MappingJacksonValue mapping = new MappingJacksonValue(user);
        mapping.setFilters(filters);
        if(bool == false){
            System.out.println("Id All Ready Exits!!!!!");
            return mapping;
        }
        return mapping;
    }


    /*
    11. Configure hateoas with your springboot application.
        Create an api which returns User Details along with url to show all topics.
     */
    @GetMapping("/userHateoas/{id}")
    public EntityModel<User> userByIdHateoas(@PathVariable int id){
        User user= userService.getUserById(id);

        EntityModel<User> resource = EntityModel.of(user);

        WebMvcLinkBuilder linkTo =
                linkTo(methodOn(this.getClass()).showAllUsers());

        resource.add(linkTo.withRel("show-all-users"));

        return resource;
    }
}
