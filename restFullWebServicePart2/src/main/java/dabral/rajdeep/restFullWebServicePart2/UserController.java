package dabral.rajdeep.restFullWebServicePart2;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import io.swagger.annotations.ApiOperation;
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
    @ApiOperation(value="Get User List" , notes ="Get User List")
    public List<User> showAllUsers(){
        return userService.getList();
    }

    @GetMapping("/user/{id}")
    @ApiOperation(value="Get User By Id" , notes ="Get User By Id")
    public User userById(@PathVariable int id){
        return userService.getUserById(id);
    }

    @PostMapping("/user")

    @ApiOperation(value="Add User" , notes ="Add User")
    public User addUser(@RequestBody User user){
        Boolean bool= userService.addUser(user);
        if(bool == false){
            System.out.println("Id All Ready Exits!!!!!");
            return new User();
        }
        return user;
    }

    @DeleteMapping("/user/{id}")
    @ApiOperation(value="Delete User" , notes ="Delete specific user")
    public Boolean deleteUser(@PathVariable  int id){
        Boolean bool= userService.deleteUser(id);
        if(bool == false){
            System.out.println("Id All Ready Exits!!!!!");
            return false;
        }
        return true;
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


    /*
    *Swagger
     5. Configure swagger plugin and create document of following methods:
             Get details of User using GET request.
             Save details of the user using POST request.
             Delete a user using DELETE request.

             // screenshot added


     7. In swagger documentation, add the description of each class and URI so that in swagger
           UI the purpose of class and URI is clear.


          // screenshot added
     */


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

    10. Create 2 API for showing user details. The first api should return only basic
    details of the user and the other API should return more/enhanced details of the user,
    Now apply versioning using the following methods:

    - MimeType Versioning
    - Request Parameter versioning
    - URI versioning
    - Custom Header Versioning
     */

    //URI versioning
    @GetMapping("v1/user")
    public User getUserByIdV1(){
        return new User(110,"newUser@123","NewUser");
    }
    @GetMapping("v2/user")
    public UserV2 getUserByIdUriV2(){
        return new UserV2(110,"newUser@123","NewUser","A-148 sector 20");
    }

    //Request Parameter versioning
    @GetMapping(value="/user/param", params = "version=1")
    public User getUserByIdParam(){
        return new User(111,"newUser@123","NewUser");
    }
    @GetMapping(value="/user/param" , params = "version=2")
    public UserV2 getUserByIdParamV2(){
        return new UserV2(111,"newUser@123","NewUser","Delhi");
    }

    //Custom Header Versioning
    @GetMapping(value="/user/header", headers = "version=1")
    public User getUserByIdHeader(){
        return new User(111,"newUser@123","NewUser");
    }
    @GetMapping(value="/user/header" , headers = "version=2")
    public UserV2 getUserByIdHeaderV2(){
        return new UserV2(111,"newUser@123","NewUser","Delhi");
    }

    //MimeType Versioning
    @GetMapping(value="/user/produce", produces  = "application/version1+json")
    public User getUserByIdProduce(){
        return new User(111,"newUser@123","NewUser");
    }
    @GetMapping(value="/user/produce" , produces = "application/version2+json")
    public UserV2 getUserByIdProduceV2(){
        return new UserV2(111,"newUser@123","NewUser","Delhi");
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
