package com.apiexamples.service;

import com.apiexamples.payload.RegistrationDto;

import java.util.List;

public interface RegistrationService {
    public RegistrationDto createRegistration(RegistrationDto registrationDto);

    void deleteRegistrationById(Long id);

    RegistrationDto updateRegistration(Long id, RegistrationDto registrationDto);

    List<RegistrationDto> getAllRegistration(int pageNo, int pageSize, String sortBy, String sortDir);


    RegistrationDto getRegistrationById(long id);
}
