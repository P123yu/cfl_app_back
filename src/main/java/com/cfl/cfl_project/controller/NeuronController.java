package com.cfl.cfl_project.controller;

import com.cfl.cfl_project.dto.NeuronRequest;
import com.cfl.cfl_project.dto.NeuronResponse;
import com.cfl.cfl_project.service.NeuronService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/neuron")
@CrossOrigin
public class NeuronController {

    @Autowired
    private NeuronService neuronService;

    @PostMapping("/ask")
    public ResponseEntity<NeuronResponse> askNeuron(@RequestBody NeuronRequest request) {
        NeuronResponse response = neuronService.askQuestion(request.getQuestion());
        return ResponseEntity.ok(response);
    }
}
