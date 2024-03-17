package it.epicode.ProgettoCapstone.services;

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

@Service
public class WorkSRV {

    @Autowired
    private WorkDAO workDAO;

    public Page<Work> getWorks(int pageNum, int size, String orderBy) {
        if (size > 100) size = 100;
        Pageable pageable = PageRequest.of(pageNum, size, Sort.by(orderBy));
        return workDAO.findAll(pageable);

    }

    public Work getWorkById(Long id) {
        return workDAO.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public Work createWork(NewWork newWork) {
        Work work = new Work();
        work.setName(newWork.name());
        work.setDescription(newWork.description());
        work.setDateCreated(newWork.dateCreated());
        work.setWorksStatus(newWork.workStatus());
        return workDAO.save(work);
    }

    public Work updateWorkById(NewWork updatedWork, Long id) {
        Work found = getWorkById(id);
        found.setName(updatedWork.name());
        found.setDescription(updatedWork.description());
        found.setWorksStatus(updatedWork.workStatus());
        workDAO.save(found);
        return found;
    }

    public void deleteWork(Long id) {
        Work found = getWorkById(id);
        workDAO.delete(found);
    }
}
