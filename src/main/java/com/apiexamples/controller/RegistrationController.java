package com.apiexamples.controller;

import com.apiexamples.payload.RegistrationDto;
import com.apiexamples.service.RegistrationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/registration")
public class RegistrationController {

    private RegistrationService registrationService;

    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    // http://localhost:8080/api/v1/registration
    @PostMapping
    public ResponseEntity<?> addRegistration(@Valid @RequestBody RegistrationDto registrationDto, BindingResult result){
        if(result.hasErrors()){
            return new ResponseEntity<>(result.getFieldError().getDefaultMessage(),HttpStatus.OK);
        }
        RegistrationDto regDto = registrationService.createRegistration(registrationDto);
        return new ResponseEntity<>(regDto, HttpStatus.CREATED);
    }
    //http://localhost:8080/api/v1/registration?id=ps
    @DeleteMapping
    public ResponseEntity<String> deleteRegistrationById(@RequestParam Long id){
        registrationService.deleteRegistrationById(id);
        return new ResponseEntity<>("Registration deleted", HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<RegistrationDto> updateRegistration(@RequestParam Long id,
              @RequestBody RegistrationDto registrationDto) {
        RegistrationDto dto = registrationService.updateRegistration(id, registrationDto);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<List<RegistrationDto>> getAllRegistration(
            @RequestParam(name="pageNo",defaultValue = "0",required = false) int pageNo,
            @RequestParam(name="pageSize",defaultValue = "5",required = false) int pageSize,
            @RequestParam(name="sortBy",defaultValue = "id",required = false) String sortBy,
            @RequestParam(name="sortDir",defaultValue = "id",required = false) String sortDir
    ){
        List<RegistrationDto> dtos = registrationService.getAllRegistration(pageNo, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }
    @GetMapping("/byId")
    public ResponseEntity<RegistrationDto> getRegistrationById(@RequestParam long id){
        RegistrationDto dto = registrationService.getRegistrationById(id);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

}
