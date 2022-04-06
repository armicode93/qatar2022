package com.example.qatar2022.service;


import com.example.qatar2022.entities.Stade;
import com.example.qatar2022.repository.StadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
import java.util.List;

@Service
public class StadeService {

    @Autowired
    private StadeRepository stadeRepository;

    public List<Stade> getAllStades()
    {
        List<Stade> stades = new ArrayList<>();

        stadeRepository.findAll().forEach(stades::add);

        return stades;
    }

    public Stade getStade(String idStade)
    {
        int indice = Integer.parseInt(idStade);
        return stadeRepository.findById(indice);
    }

    public void addStade(Stade stade)
    {
        stadeRepository.save(stade);
    }
    public void updateStade(Long id, Stade stade)
    {
        stadeRepository.save(stade);
    }
    public void deleteStade (Long idStade)
    {
        stadeRepository.deleteById(idStade);
    }

}
