package com.server.RandomDungeonFighter.rest.controller;

import com.server.RandomDungeonFighter.service.CharacterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;

@RestController
@RequestMapping(value = "api/character")
public class CharacterRestController {

    private CharacterService characterService;

    @Inject
    public CharacterRestController (CharacterService characterService){
        this.characterService = characterService;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public @ResponseBody
    ResponseEntity createNewAccount (@RequestParam(name = "className") String className,
                                     @RequestParam(name = "nickname") String nickname,
                                     @RequestParam(name = "level") int level,
                                     @RequestParam(name = "accountLogin") String accountName)
    {
        characterService.createNewAccount(className, level, nickname, accountName);

        return ResponseEntity.ok().build();
    }
}
