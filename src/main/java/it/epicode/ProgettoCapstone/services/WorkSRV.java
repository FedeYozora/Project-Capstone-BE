package it.epicode.ProgettoCapstone.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import it.epicode.ProgettoCapstone.entities.Work;
import it.epicode.ProgettoCapstone.exceptions.NotFoundException;
import it.epicode.ProgettoCapstone.payloads.NewWork;
import it.epicode.ProgettoCapstone.repos.WorkDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Service
public class WorkSRV {

    @Autowired
    private WorkDAO workDAO;
    @Autowired
    private Cloudinary cloudinaryUploader;

    public Page<Work> getWorks(int pageNum, int size, String orderBy) {
        if (size > 100) size = 100;
        Pageable pageable = PageRequest.of(pageNum, size, Sort.by(orderBy));
        return workDAO.findAll(pageable);

    }

    public List<Work> getFeaturedWorks() {
        return workDAO.findByFeaturedTrue();

    }

    public Work getWorkById(Long id) {
        return workDAO.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public Work getWorkByCommentId(Long id) {
        return workDAO.findWorkByCommentId(id);
    }

    public Work createWork(NewWork newWork) {
        Work work = new Work();
        work.setName(newWork.name());
        work.setImage(newWork.image());
        work.setFeatured(newWork.featured());
        work.setDescription(newWork.description());
        work.setDateCreated(newWork.dateCreated());
        work.setDateUploaded(LocalDate.now());
        work.setWorksStatus(newWork.worksStatus());
        return workDAO.save(work);
    }

    public Work updateWorkById(NewWork updatedWork, Long id) {
        Work found = getWorkById(id);
        found.setName(updatedWork.name());
        found.setFeatured(updatedWork.featured());
        found.setDateCreated(updatedWork.dateCreated());
        found.setDescription(updatedWork.description());
        found.setWorksStatus(updatedWork.worksStatus());
        workDAO.save(found);
        return found;
    }

    public void deleteWork(Long id) {
        Work found = getWorkById(id);
        workDAO.delete(found);
    }

    public String uploadImageWork(MultipartFile image) throws IOException {
        String url = (String) cloudinaryUploader.uploader().upload(image.getBytes(),
                ObjectUtils.emptyMap()).get("url");
        return url;
    }
}
