package com.example.qatar2022.entities;

import com.example.qatar2022.entities.personne.Joueur;

import java.util.List;

    public class JoueurPostes {
        private Joueur joueur;
        private List<Poste> postes;

        public JoueurPostes(Joueur joueur, List<Poste> postes) {
            this.joueur = joueur;
            this.postes = postes;
        }

        public Joueur getJoueur() {
            return joueur;
        }

        public List<Poste> getPostes() {
            return postes;
        }
    }

