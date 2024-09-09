package bitc.fullstack405.server_intravel.service;

import bitc.fullstack405.server_intravel.entity.MoneyEntity;
import bitc.fullstack405.server_intravel.entity.PayEntity;
import bitc.fullstack405.server_intravel.repository.MoneyRepository;
import bitc.fullstack405.server_intravel.repository.PayRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PayService {

    private final PayRepository payRepository;
    private final MoneyRepository moneyRepository;

    public List<PayEntity> findAll(Long moneyId) {
        return payRepository.findByMoneyId(moneyId);
    }

    public PayEntity save(Long moneyId, PayEntity payEntity) {
        MoneyEntity moneyEntity = moneyRepository.findById(moneyId).get();

        payEntity.setMoney(moneyEntity);
        return payRepository.save(payEntity);
    }

    @Transactional
    public PayEntity update(Long payId, PayEntity payEntity) {
        PayEntity payUpdate = payRepository.findById(payId).get();

        payUpdate.setPayTitle(payEntity.getPayTitle());
        payUpdate.setPlusAmt(payEntity.getPlusAmt());
        payUpdate.setMinusAmt(payEntity.getMinusAmt());

        return payRepository.save(payUpdate);
    }

    public void delete(Long payId) {
        payRepository.deleteById(payId);
    }
}
