package com.nnk.springboot.services;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CurvePointService {

    @Autowired
    private CurvePointRepository curvePointRepository;

    public List<CurvePoint> findAll() {
        return curvePointRepository.findAll();
    }

    public CurvePoint save(CurvePoint curvePoint) {
        return curvePointRepository.save(curvePoint);
    }

    public CurvePoint findById(Integer id) {
        return curvePointRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid CurvePoint Id: " + id));
    }

    public CurvePoint update(Integer id, CurvePoint updatedCurvePoint) {
        CurvePoint curvePoint = findById(id);

        curvePoint.setTerm(updatedCurvePoint.getTerm());
        curvePoint.setValue(updatedCurvePoint.getValue());

        return curvePointRepository.save(curvePoint);
    }

    public void delete(CurvePoint curvePoint) {
        curvePointRepository.delete(curvePoint);
    }
}
