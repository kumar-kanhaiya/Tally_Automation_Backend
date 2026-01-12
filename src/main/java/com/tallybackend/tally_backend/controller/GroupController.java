package com.tallybackend.tally_backend.controller;

import com.tallybackend.tally_backend.service.parser.GroupParserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/group")
public class GroupController {

    private final GroupParserService groupParserService;

    public GroupController(GroupParserService groupParserService) {
        this.groupParserService = groupParserService;
    }

    /**
     * Receive Tally XML in request body
     * Parse and return Group Names
     */
    @PostMapping(
            consumes = "application/xml",
            produces = "application/json"
    )
    public List<String> getGroups(@RequestBody String xml) {
        return groupParserService.extractGroupNames(xml);
    }
}
