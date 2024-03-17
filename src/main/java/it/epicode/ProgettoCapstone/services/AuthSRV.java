package it.epicode.ProgettoCapstone.services;

import it.epicode.ProgettoCapstone.entities.User;
import it.epicode.ProgettoCapstone.exceptions.BadRequestException;
import it.epicode.ProgettoCapstone.payloads.NewUserDTO;
import it.epicode.ProgettoCapstone.payloads.UserLoginDTO;
import it.epicode.ProgettoCapstone.repos.UserDAO;
import it.epicode.ProgettoCapstone.security.JWTTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthSRV {

    @Autowired
    private JWTTools jwtTools;
    @Autowired
    private UserSRV userSRV;
    @Autowired
    private PasswordEncoder bcrypt;
    @Autowired
    private UserDAO userDAO;


    public String authUserAndGenerateToken(UserLoginDTO payload) {
        User user = userSRV.findUserByEmail(payload.email());
        if (bcrypt.matches(payload.password(), user.getPassword())) {
            return jwtTools.createToken(user);
        } else {
            throw new RuntimeException("Error logging the user in: incorrect credentials");
        }

    }

    public User saveUser(NewUserDTO payload) {
        userDAO.findByEmail(payload.email()).ifPresent(user -> {
            throw new BadRequestException("Email " + user.getEmail() + " already in use!");
        });

        User newUser = new User(
                payload.email(),
                payload.username(),
                bcrypt.encode(payload.password()),
                payload.name(),
                payload.surname()
        );

        return userDAO.save(newUser);
    }
}
