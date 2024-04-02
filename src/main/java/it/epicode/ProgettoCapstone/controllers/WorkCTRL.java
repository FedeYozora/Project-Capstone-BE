package it.epicode.ProgettoCapstone.controllers;

import it.epicode.ProgettoCapstone.config.EmailSender;
import it.epicode.ProgettoCapstone.entities.Work;
import it.epicode.ProgettoCapstone.enums.CommentStatus;
import it.epicode.ProgettoCapstone.exceptions.BadRequestException;
import it.epicode.ProgettoCapstone.payloads.NewWork;
import it.epicode.ProgettoCapstone.payloads.SendEmailModel;
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
import java.util.List;

@RestController
@RequestMapping("/works")
public class WorkCTRL {
    @Autowired
    private WorkSRV workSRV;
    @Autowired
    private EmailSender emailSender;

    @GetMapping
    public Page<Work> getWorks(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int size, @RequestParam(defaultValue = "id") String orderBy) {
        return this.workSRV.getWorks(page, size, orderBy);
    }

    @GetMapping("/visible-comments")
    public Page<Work> findWorksWithVisibleComments(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int size, @RequestParam(defaultValue = "id") String orderBy) {
        Page<Work> works = this.workSRV.getWorks(page, size, orderBy);
        works.get().forEach(work -> work.getComments().removeIf(comment -> comment.getCommentStatus() != CommentStatus.VISIBLE));
        return works;
    }

    @GetMapping("/featured")
    public List<Work> getFeaturedWorks(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String orderBy) {
        return this.workSRV.getFeaturedWorks();
    }

    @GetMapping("/{id}")
    public Work getWorkById(@PathVariable Long id) {
        return this.workSRV.getWorkById(id);
    }

    @GetMapping("/comments/{id}")
    public Work getWorkByCommentId(@PathVariable Long id) {
        return this.workSRV.getWorkByCommentId(id);
    }

    @GetMapping("/searchByName")
    public List<Work> searchWorksByName(@RequestParam String name) {
        return workSRV.searchWorksByName(name);
    }

    @GetMapping("/searchByNameVC")
    public List<Work> searchWorksByNameWithVisibleComments(@RequestParam String name) {
        List<Work> works = workSRV.searchWorksByName(name);
        works.forEach(work -> work.getComments().removeIf(comment -> comment.getCommentStatus() != CommentStatus.VISIBLE));
        return works;
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

    @PostMapping("/mail")
    public void sendEmail(@ModelAttribute SendEmailModel model) {
        this.emailSender.sendEmail(model);
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
    @PreAuthorize("hasAuthority('ADMIN')")
    public String uploadAvatar(@RequestParam("image") MultipartFile image) throws IOException {
        return this.workSRV.uploadImageWork(image);
    }
}
