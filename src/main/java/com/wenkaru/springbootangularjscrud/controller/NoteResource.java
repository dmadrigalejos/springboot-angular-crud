package com.wenkaru.springbootangularjscrud.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wenkaru.springbootangularjscrud.service.NoteService;

@RestController
@RequestMapping("/note")
public class NoteResource {
	protected final static Logger LOGGER = LoggerFactory.getLogger(NoteResource.class);
	
	@Autowired
    private NoteService noteService;
	
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> getNotes(        
            @RequestParam(required = true) String searchQuery, 
            @RequestParam(required = true) Integer pageNumber, 
            @RequestParam(required = true) String sortBy, 
            @RequestParam(required = true) String order, 
            @RequestParam(required = true) Integer limit ) throws Exception {
        return noteService.get(searchQuery, pageNumber, sortBy, order, limit);
    }
}