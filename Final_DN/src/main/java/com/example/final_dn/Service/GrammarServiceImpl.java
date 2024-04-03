package com.example.final_dn.Service;

import com.example.final_dn.Model.Grammar;
import com.example.final_dn.Repository.GrammarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GrammarServiceImpl implements GrammarService {


    @Autowired
    private GrammarRepository grammarRepository;


    @Override
    public Iterable<Grammar> getAllGrammar() {
        return grammarRepository.findAll();
    }

    @Override
    public Optional<Grammar> getGrammar(Long id) {
        return grammarRepository.findById(id);
    }

    @Override
    public void deleteGrammar(Long id) {
        grammarRepository.deleteById(id);
    }

    @Override
    public Grammar saveGrammar(Grammar grammar) {
        return grammarRepository.save(grammar);
    }
}
