package com.apiexamples.service;

import com.apiexamples.entity.Registration;
import com.apiexamples.exception.ResourceNotFound;
import com.apiexamples.payload.RegistrationDto;
import com.apiexamples.repository.RegistrationRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;



@Service
public class RegistrationServiceImpl implements RegistrationService{

    private RegistrationRepository registrationRepository;

    public RegistrationServiceImpl(RegistrationRepository registrationRepository) {
        this.registrationRepository = registrationRepository;
    }


    @Override
    public RegistrationDto createRegistration(RegistrationDto registrationDto) {
       Registration registration = mapToEntity(registrationDto);
        Registration savedEntity = registrationRepository.save(registration);
        RegistrationDto dto = mapToDto(savedEntity);
        dto.setMessage("Registration saved!");
        return dto;
    }

    @Override
    public void deleteRegistrationById(Long id) {
            registrationRepository.deleteById(id);
    }

    @Override
    public RegistrationDto updateRegistration(Long id, RegistrationDto registrationDto) {
        Optional<Registration> opReg = registrationRepository.findById(id);
        Registration registration = opReg.get();

        registration.setName(registrationDto.getName());
        registration.setEmail(registrationDto.getEmail());
        registration.setMobile(registrationDto.getMobile());
        Registration savedEntity = registrationRepository.save(registration);
        RegistrationDto dto = mapToDto(savedEntity);
        return dto;
    }

    //http://localhost:8080/api/v1/registration?pageNo=1&pageSize=4&sortBy=name&sortDir=asc
    //TernaryOperator
    @Override
    public List<RegistrationDto> getAllRegistration(int pageNo, int pageSize, String sortBy, String sortDir) {
        //List<Registration> registrations = registrationRepository.findAll();
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(Sort.Direction.ASC, sortBy) : Sort.by(Sort.Direction.DESC, sortBy);
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Registration> all = registrationRepository.findAll(pageable);
        List<Registration> registrations = all.getContent();
        List<RegistrationDto> registrationDtos = registrations.stream().map(r->mapToDto(r)).collect(Collectors.toList());

        System.out.println(all.getTotalPages());
        System.out.println(all.isFirst());
        System.out.println(all.isLast());
        System.out.println(pageable.getPageNumber());
        System.out.println(pageable.getPageSize());

        return registrationDtos;
    }

    @Override
    public RegistrationDto getRegistrationById(long id) {

        Registration registration = registrationRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFound("Registration not found for id " + id)
        );
        RegistrationDto registrationDto = mapToDto(registration);
        return registrationDto;
    }


    Registration mapToEntity(RegistrationDto dto){
        Registration entity = new Registration();
        entity.setName(dto.getName());
        entity.setEmail(dto.getEmail());
        entity.setMobile(dto.getMobile());
        return entity;
    }
    RegistrationDto mapToDto(Registration registration){
        RegistrationDto dto = new RegistrationDto();
        dto.setId(registration.getId());
        dto.setName(registration.getName());
        dto.setEmail(registration.getEmail());
        dto.setMobile(registration.getMobile());
        return dto;
    }

}
