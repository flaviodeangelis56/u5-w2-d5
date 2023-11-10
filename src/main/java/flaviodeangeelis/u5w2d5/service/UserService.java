package flaviodeangeelis.u5w2d5.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import flaviodeangeelis.u5w2d5.entities.User;
import flaviodeangeelis.u5w2d5.exception.BadRequestException;
import flaviodeangeelis.u5w2d5.exception.NotFoundException;
import flaviodeangeelis.u5w2d5.payload.NewUserDTO;
import flaviodeangeelis.u5w2d5.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private Cloudinary cloudinary;

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User findById(int id) {
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public User save(NewUserDTO body) throws IOException {

        userRepository.findByEmail(body.email()).ifPresent(user -> {
            throw new BadRequestException("L'email " + user.getEmail() + " è già utilizzata!");
        });

        User newUser = new User();
        newUser.setUsername(body.username());
        newUser.setName(body.name());
        newUser.setSurname(body.surname());
        newUser.setEmail(body.email());
        newUser.setProfileImg("...");
        User savedUser = userRepository.save(newUser);
        return savedUser;
    }

    public User findByIdAndUpdate(int id, User body) {
        User found = userRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
        found.setUsername(body.getUsername());
        found.setName(body.getName());
        found.setEmail(body.getEmail());
        found.setProfileImg(body.getProfileImg());
        return userRepository.save(found);
    }


    public void findByIdAndDelete(int id) throws NotFoundException {
        User found = this.findById(id);
        userRepository.delete(found);
    }

    public String uploadImg(MultipartFile file, int id, User body) throws IOException {
        User found = this.findById(id);
        String imgURL = (String) cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap()).get("url");
        found.setId(id);
        found.setName(body.getName());
        found.setSurname(body.getSurname());
        found.setEmail(body.getEmail());
        found.setUsername(body.getUsername());
        found.setProfileImg(imgURL);
        userRepository.save(found);
        return imgURL;
    }
}
