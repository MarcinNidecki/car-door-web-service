package com.mnidecki.cardoor.services.DBService;

import com.mnidecki.cardoor.domain.booking.DiscountCode;
import com.mnidecki.cardoor.repository.DiscountCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiscountCodeService {

    @Autowired
    private DiscountCodeRepository discountCodeRepository;

    public List<DiscountCode> findAll() {
        return discountCodeRepository.findAll();
    }

    public DiscountCode findById(final Long id) {
        return discountCodeRepository.findById(id).orElseGet(null);
    }

    public DiscountCode save(final DiscountCode discountCode) {
        return discountCodeRepository.save(discountCode);
    }

    public void deleteById(final Long id) {
        discountCodeRepository.deleteById(id);
    }
}
