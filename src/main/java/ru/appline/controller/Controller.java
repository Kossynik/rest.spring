package ru.appline.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.appline.logic.Pet;
import ru.appline.logic.PetModel;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
public class Controller {
    private static final PetModel petModel = PetModel.getInstance();
    private static final AtomicInteger newId = new AtomicInteger(1);

    // добавление переменной id для определения первого и последующих созданных питомцев
    private int id;

    @PostMapping(value = "/createPet", consumes = "application/json", produces = "application/json")
    @ResponseBody
    public Object createPet(@RequestBody Pet pet) {

        id = newId.getAndIncrement();
        petModel.add(pet, id);

    // добавление первого питомца
        if (id == 1) {

            return new ResponseEntity<String>("{\"text\":Вы добавили своего первого питомца!}", HttpStatus.OK);

    // добавление последующих питомцев
        } else if (id != 1) {

            return new ResponseEntity<String>("{\"text\":Вы добавили еще одного питомца!}", HttpStatus.OK);

        }

        return new ResponseEntity<String>("{\"text\":Ошибка!}", HttpStatus.OK);

    }

    @GetMapping(value = "/getAll", produces = "application/json")
    public Map<Integer, Pet> getAll() {
        return petModel.getAll();
    }

    @GetMapping(value = "/getPet", consumes = "application/json", produces = "application/json")
    public Pet getPet(@RequestBody Map<String, Integer> id) {
        return petModel.getFromList(id.get("id"));
    }

    @PutMapping(value = "/putPet", consumes = "application/json")
    public void putPet(@RequestBody Pet pet, int Id) {
        petModel.put(pet, Id);
    }

    @DeleteMapping(value = "/deletePet", consumes = "application/json")
    public void deletePet(@RequestParam(value="del") Integer del) {
        petModel.delete(del);
    }

}