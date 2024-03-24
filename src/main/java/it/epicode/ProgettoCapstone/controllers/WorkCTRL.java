package it.epicode.ProgettoCapstone.controllers;

import it.epicode.ProgettoCapstone.entities.Work;
import it.epicode.ProgettoCapstone.exceptions.BadRequestException;
import it.epicode.ProgettoCapstone.payloads.NewWork;
import it.epicode.ProgettoCapstone.services.WorkSRV;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/works")
public class WorkCTRL {
    @Autowired
    private WorkSRV workSRV;

    @GetMapping
    public Page<Work> getWorks(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "12") int size, @RequestParam(defaultValue = "id") String orderBy) {
        return this.workSRV.getWorks(page, size, orderBy);
    }

    @GetMapping("/{id}")
    public Work getWorkById(@PathVariable Long id) {
        return this.workSRV.getWorkById(id);
    }

    @GetMapping("/comments/{id}")
    public Work getWorkByCommentId(@PathVariable Long id) {
        return this.workSRV.getWorkByCommentId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('ADMIN')")
    public Work saveWork(@RequestBody @Validated NewWork newWork, BindingResult validation) {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        }
        return this.workSRV.createWork(newWork);

    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Work updateWorkById(@PathVariable Long id, @RequestBody @Validated NewWork newWork, BindingResult validation) {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        }
        return this.workSRV.updateWorkById(newWork, id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT) //  Status Code 204
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteWorkById(@PathVariable Long id) {
        this.workSRV.deleteWork(id);
    }

    @PostMapping("/uploadAvatar")
    @ResponseStatus(HttpStatus.OK) // Status Code 200
    public String uploadAvatar(@RequestParam("image") MultipartFile image) throws IOException {
        return this.workSRV.uploadImageWork(image);
    }
}
