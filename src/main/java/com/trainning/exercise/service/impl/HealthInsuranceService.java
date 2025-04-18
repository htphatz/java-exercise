package com.trainning.exercise.service.impl;

import com.trainning.exercise.entity.HealthInsurance;
import com.trainning.exercise.repository.HealthInsuranceRepository;
import com.trainning.exercise.service.IHealthInsuranceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HealthInsuranceService implements IHealthInsuranceService {
    @Autowired
    private HealthInsuranceRepository healthInsuranceRepository;

    @Override
    public HealthInsurance registerInsurance(String studentId) {
        HealthInsurance healthInsurance = HealthInsurance.builder()
                .studentId(studentId)
                .build();

        return healthInsuranceRepository.save(healthInsurance);
    }
}
