package com.galvanize.controllers;

import com.galvanize.entities.Person;

import com.galvanize.repository.HelloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api")
public class HelloRestController {

    @Autowired

    HelloRepository helloRepository;

    @GetMapping("/hello")
    public Person createGetPerson(@RequestParam String name,
                                  @RequestParam String email,
                                  @RequestParam String birthDate){

        LocalDate bd = LocalDate.parse(birthDate);
        return new Person(name, email, bd);

    }




    /*** CREATE ***/
    @PostMapping("/person")
    public Person createPerson(@RequestBody Person person){
        return helloRepository.save(person);
    }

    /*** READ ***/
    @GetMapping("/person")
    public List<Person> getPeople(){
        return helloRepository.findAll();
    }
    @GetMapping("/person/{id}")
    public Person getPerson(@PathVariable Long id){
        return helloRepository.findById(id);
    }

    /*** UPDATE ***/
    @PatchMapping("/person/{id}")
    public Person updatePerson(@RequestParam String email, @PathVariable Long id){
        return helloRepository.updateEmail(id, email);
    }

    /*** DELETE ***/
    @DeleteMapping("/person/{id}")
    public ResponseEntity deletePerson(@PathVariable Long id){
        boolean deleted = helloRepository.delete(id);
        if(!deleted){
            return ResponseEntity.notFound()
                    .header("errorMsg", "Person id "+id+" doesn't exist")
                    .build();
        }else{
            return ResponseEntity.ok().build();
        }
    }

}