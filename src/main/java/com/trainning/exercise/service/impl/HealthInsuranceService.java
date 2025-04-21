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

    /**
     * Registers a new health insurance for a student.
     *
     * @param studentId The unique identifier of the student for whom the health insurance is being registered.
     * @return The saved HealthInsurance entity.
     */
    @Override
    public HealthInsurance registerInsurance(String studentId) {
        HealthInsurance healthInsurance = HealthInsurance.builder()
                .studentId(studentId)
                .build();

        return healthInsuranceRepository.save(healthInsurance);
    }
}
