package it.epicode.ProgettoCapstone.controllers;

import it.epicode.ProgettoCapstone.entities.User;
import it.epicode.ProgettoCapstone.exceptions.BadRequestException;
import it.epicode.ProgettoCapstone.payloads.LoginResponseDTO;
import it.epicode.ProgettoCapstone.payloads.NewUserDTO;
import it.epicode.ProgettoCapstone.payloads.UserLoginDTO;
import it.epicode.ProgettoCapstone.services.AuthSRV;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthCTRL {

    @Autowired
    public AuthSRV authSRV;

    @PostMapping("/login")
    public LoginResponseDTO login(@RequestBody @Validated UserLoginDTO payload, BindingResult validation) {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        }
        return new LoginResponseDTO(authSRV.authUserAndGenerateToken(payload));

    }

    @PostMapping("/register")
    public User register(@RequestBody @Validated NewUserDTO payload, BindingResult validation) {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        }
        return this.authSRV.saveUser(payload);
    }

}
