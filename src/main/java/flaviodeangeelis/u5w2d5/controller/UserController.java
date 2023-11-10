package flaviodeangeelis.u5w2d5.controller;

import flaviodeangeelis.u5w2d5.entities.User;
import flaviodeangeelis.u5w2d5.exception.BadRequestException;
import flaviodeangeelis.u5w2d5.exception.NotFoundException;
import flaviodeangeelis.u5w2d5.payload.NewUserDTO;
import flaviodeangeelis.u5w2d5.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService usersService;

    @GetMapping("")
    public List<User> getUser() {
        return usersService.getUsers();
    }

    @GetMapping(value = "/{id}")
    public User findById(@PathVariable int id) {
        return usersService.findById(id);
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public User save(@RequestBody @Validated NewUserDTO body, BindingResult validation) {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        } else {
            try {
                return usersService.save(body);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }

    }

    @PutMapping("/{id}")
    public User findByIdAndUpdate(@PathVariable int id, @RequestBody User body) {
        return usersService.findByIdAndUpdate(id, body);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findByIdAndDelete(@PathVariable int id) throws NotFoundException {
        usersService.findByIdAndDelete(id);
    }

    @PutMapping("/updateImg/{id}")
    public String uploadImg(@RequestParam("avatar") MultipartFile file, @PathVariable int id) throws IOException {
        User found = usersService.findById(id);
        return usersService.uploadImg(file, id, found);
    }

}
