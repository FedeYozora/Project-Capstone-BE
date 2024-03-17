package it.epicode.ProgettoCapstone.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import it.epicode.ProgettoCapstone.entities.User;
import it.epicode.ProgettoCapstone.exceptions.UUIDNotFoundException;
import it.epicode.ProgettoCapstone.payloads.NewUserDTO;
import it.epicode.ProgettoCapstone.repos.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class UserSRV {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private Cloudinary cloudinaryUploader;


    public Page<User> getUsers(int pageNum, int size, String orderBy) {
        if (size > 100) size = 100;
        Pageable pageable = PageRequest.of(pageNum, size, Sort.by(orderBy));
        return userDAO.findAll(pageable);

    }

    public User getUserById(UUID id) {
        return userDAO.findById(id).orElseThrow(() -> new UUIDNotFoundException(id));
    }

    public User updateUserById(NewUserDTO updatedUser, UUID id) {
        User found = getUserById(id);
        found.setName(updatedUser.name());
        found.setSurname(updatedUser.surname());
        found.setEmail(updatedUser.email());
        found.setUsername(updatedUser.username());
        found.setPassword(updatedUser.password());
        userDAO.save(found);
        return found;
    }

    public void deleteUser(UUID id) {
        User found = getUserById(id);
        userDAO.delete(found);
    }

    public User findUserByEmail(String email) {
        return userDAO.findByEmail(email).orElseThrow(() -> new RuntimeException("User with this email not found"));

    }


    public String uploadAvatar(MultipartFile image, UUID id) throws IOException {
        String url = (String) cloudinaryUploader.uploader().upload(image.getBytes(),
                ObjectUtils.emptyMap()).get("url");

        User found = this.getUserById(id);
        found.setAvatar(String.valueOf(url));
        userDAO.save(found);
        return url;
    }


}
