package io.dimoffon.sn.service;

import io.dimoffon.sn.entity.Interest;
import io.dimoffon.sn.repository.InterestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InterestServiceImpl implements InterestService {

    final InterestRepository interestRepository;

    @Autowired
    public InterestServiceImpl(final InterestRepository interestRepository) {
        this.interestRepository = interestRepository;
    }

    @Override
    public List<Interest> getInterests() {
        return interestRepository.getInterests();
    }

}
