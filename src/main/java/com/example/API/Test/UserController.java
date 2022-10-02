package com.example.API.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/")
public class UserController {
@Autowired
    private UserRepository userRepository;

@GetMapping("/")
    public Iterable<User> findAllUsers(){
    return userRepository.findAll();

};

@GetMapping("/{id}")
    public ResponseEntity<User> findUserById(@PathVariable(value="id") Integer id){
   Optional<User> user = userRepository.findById(id);

   if(user.isPresent()){
       return ResponseEntity.ok().body(user.get());
   } else {
       return ResponseEntity.notFound().build();
   }
};
@PostMapping("/")
    public User saveUser(@Validated @RequestBody User user){
    return userRepository.save(user);};
@DeleteMapping("/{id}")
    public User deleteUser(@PathVariable("id") Integer id){
   Optional <User> userToDeleteOptional = this.userRepository.findById(id);
   if(!userToDeleteOptional.isPresent()){
       return null;
   }
   else{
       User userToDelete = userToDeleteOptional.get();
       userRepository.delete(userToDelete);
       return userToDelete;
   }
}


    @PutMapping("/{id}")
    public User updateUser(@PathVariable("id") Integer id, @RequestBody User user){
    Optional<User> userToUpdateOptional = this.userRepository.findById(id);
    if(!userToUpdateOptional.isPresent()){
        return null;
    }
    User userToUpdate = userToUpdateOptional.get();
        if(user.getId() != null){
            userToUpdate.setId(user.getId());
        }
        if(user.getName()!=null){
            userToUpdate.setName(user.getName());
        }

        User updatedUser = this.userRepository.save(userToUpdate);
        return updatedUser;}

}

