package com.example.final_dn.Service;

import com.example.final_dn.Model.Grammar;

import java.util.Optional;

public interface GrammarService {
    public Iterable<Grammar> getAllGrammar();

    public Optional<Grammar> getGrammar(Long id);


    public void deleteGrammar(Long id);

    public Grammar saveGrammar(Grammar grammar);

}
