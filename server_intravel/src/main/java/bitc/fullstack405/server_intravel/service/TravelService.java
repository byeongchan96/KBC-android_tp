package bitc.fullstack405.server_intravel.service;

import bitc.fullstack405.server_intravel.entity.MoneyEntity;
import bitc.fullstack405.server_intravel.entity.PayEntity;
import bitc.fullstack405.server_intravel.entity.TravelEntity;
import bitc.fullstack405.server_intravel.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
@RequiredArgsConstructor
public class TravelService {

    private final TravelRepository travelRepository;
    private final TodoRepository todoRepository;
    private final MemoRepository memoRepository;
    private final MoneyRepository moneyRepository;
    private final PayRepository payRepository;
    private final MapRepository mapRepository;

    @Transactional
    public List<TravelEntity> findAll() {

        List<TravelEntity> travels = travelRepository.findAll();

        //******************************************************************** 삭제금지
        
//        날짜 계산 로직 짬
//        현재 날짜를 구함
//        LocalDate nowDate = LocalDate.now();
//
//        travels 리스트에서 각 TravelEntity의 시작 날짜를 가져와 D-Day를 계산
//        for (TravelEntity travel : travels) {
//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//            LocalDate startDate = LocalDate.parse(travel.getStartDate(), formatter);
//
//        // 시작 날짜와 현재 날짜 사이의 일수를 계산하여 D-Day 값을 설정
//            Long days = ChronoUnit.DAYS.between(startDate, nowDate);
//            travel.setDDay(days);
//        }
//
//        계산된 D-Day 값을 저장
//        travelRepository.saveAll(travels);
//
//        D-Day 포맷팅 함수 (양수면 '+' 표시, 음수면 그냥 출력)
//        public String formatDDay(Long days) {
//        if (days > 0) {
//            return "+" + days.toString();
//        } else {
//            return days.toString();
//        }
//    }
//
//        ************************************************************************* 삭제금지
//        조회한 여행 리스트를 반환
        return travels;
    }

    public TravelEntity save(TravelEntity travelEntity) {
        return travelRepository.save(travelEntity);
    }

    @Transactional
    public TravelEntity updateTravel(Long travId, TravelEntity travelEntity) {
        TravelEntity travel = travelRepository.findById(travId).get();

        travel.setTravTitle(travelEntity.getTravTitle());
        travel.setStartDate(travelEntity.getStartDate());
        travel.setEndDate(travelEntity.getEndDate());
        travel.setCate(travelEntity.getCate());

        return travelRepository.save(travel);
    }

    public List<TravelEntity> findByTravelComplete(char comp) {
        return travelRepository.findByTravComplete(comp);
    }

    @Transactional
    public void delete(Long travId) {
        todoRepository.deleteByTravId(travId);
        memoRepository.deleteByTravId(travId);
        mapRepository.deleteByTravId(travId);

//        삭제할 pay 테이블의 moneyId 구하기
        List<MoneyEntity> moneyEntityList = moneyRepository.findByTravId(travId);

//        moneyId는 여러개 있으므로 for 반복문을 통해 List 전체에 담긴 moneyId를 통해 pay 테이블의 데이터를 삭제함
        for (MoneyEntity moneyEntity : moneyEntityList) {
            payRepository.deleteByMoneyId(moneyEntity.getMoneyId());
        }

        moneyRepository.deleteByTravId(travId);
        travelRepository.deleteById(travId);
    }
}
