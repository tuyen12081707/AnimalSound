package com.example.animalsound.viewmodel;

import androidx.lifecycle.MutableLiveData;

import com.example.animalsound.model.Animal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MiniGameVM extends BaseVM {
    private final List<Animal> listAnimal = new ArrayList<>();
    //    private int score = 0;
    private final MutableLiveData<Integer> score = new MutableLiveData<>(0);
    private int index = 0;
    private Animal animal;

    public void initAnimalList(List<Animal> animalList) {
        listAnimal.clear();
        listAnimal.addAll(animalList);
        Collections.shuffle(this.listAnimal);
    }

    public Animal getAnimal() {
        return animal;
    }

    public void initScore(int data) {
        score.setValue(data);
    }

    public MutableLiveData<Integer> getScore() {
        return score;
    }

    public String[] initCard() {
        animal = listAnimal.get(index);
        // gan vao list tamp
        List<Animal> tempList = new ArrayList<>(listAnimal);
        tempList.remove(animal);
        Collections.shuffle(tempList);
        String textA;
        String textB;
        if (new Random().nextBoolean()) {
            textA = String.format("A: %s", animal.getName());
            textB = String.format("B: %s", tempList.get(0).getName());
        } else {
            textA = String.format("B: %s", animal.getName());
            textB = String.format("A: %s", tempList.get(0).getName());
        }
        return new String[]{textA, textB};
    }

    public boolean checkAnswer(String ans) {
        String ansReplace = ans.replace("A: ", "").replace("B: ", "");
        if (ansReplace.equals(animal.getName())) {
            score.setValue(score.getValue() + 1);
            index++;
            if (index > listAnimal.size() - 1) {
                index = 0;
            }
            return true;
        } else {
            score.setValue(0);
            return false;
        }
    }
}
