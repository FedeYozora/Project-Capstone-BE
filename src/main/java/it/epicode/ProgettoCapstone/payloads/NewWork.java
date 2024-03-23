package it.epicode.ProgettoCapstone.payloads;

import it.epicode.ProgettoCapstone.enums.WorksStatus;

import java.time.LocalDate;

public record NewWork(
        String name,
        String description,
        LocalDate dateCreated,
        WorksStatus worksStatus,
        String image,
        Boolean featured
) {
}
