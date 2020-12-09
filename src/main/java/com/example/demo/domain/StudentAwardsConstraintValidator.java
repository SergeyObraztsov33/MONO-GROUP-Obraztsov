package com.example.demo.domain;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class StudentAwardsConstraintValidator implements ConstraintValidator<StudentAwardsConstraint, String> {
    @Override
    public  boolean isValid(String isHavingDept, ConstraintValidatorContext constraintValidatorContext){
        return "1".equals(isHavingDept) || "0".equals(isHavingDept);
    }
}
