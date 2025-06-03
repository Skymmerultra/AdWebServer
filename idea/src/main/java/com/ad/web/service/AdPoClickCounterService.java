package com.ad.web.service;

import com.ad.web.entity.AdPo;
import com.ad.web.mapper.AdPoMapper;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.LongAdder;

@Service
@Slf4j
public class AdPoClickCounterService {
    @Autowired
    private AdPoMapper adPoMapper;

    private final ConcurrentHashMap<Long,Long> initialValues = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<Long, LongAdder> clickCounter = new ConcurrentHashMap<>();

    @PostConstruct
    public void init(){
        List<AdPo> allAdPo = adPoMapper.getAllClickNum();
        allAdPo.forEach(adPo -> {
            initialValues.put(adPo.getId(),adPo.getClickNum());
            clickCounter.put(adPo.getId(),new LongAdder());
        });
    }

    public void clickIncrement(Long adPoId){
        clickCounter.computeIfAbsent(adPoId,k -> new LongAdder()).increment();
    }
    public Long sumClick(Long adPoId){
        LongAdder longAdder = clickCounter.get(adPoId);
        Long current = (longAdder!=null) ? longAdder.sum() : 0;
        Long l = initialValues.get(adPoId);
        return current + ((l != null) ? l : 0);
    }

    @Scheduled(fixedRate = 600000)//ms
    @Transactional
    public void updateClickCountToDataBase(){
        if (clickCounter.isEmpty()) {
            return;
        }
        List<AdPo> updates = new ArrayList<>();
        clickCounter.forEach((adPoId, longAdder) -> {
            long sum = longAdder.sum();
            Long l = initialValues.get(adPoId);
            if (l != null){
                AdPo adPo = new AdPo();
                adPo.setId(adPoId);
                adPo.setClickNum(l + sum);
                updates.add(adPo);
            }else {
                AdPo adPo = new AdPo();
                adPo.setId(adPoId);
                adPo.setClickNum(sum);
                initialValues.put(adPoId,sum);
            }
        });

        // 执行批量更新
        if (!updates.isEmpty()) {
            adPoMapper.batchUpdateClickCount(updates);
            log.info("批量更新了{}条广告点击量", updates.size());

            // 重置计数器
            clickCounter.forEach((adPoId, counter) -> {
                if (initialValues.containsKey(adPoId)) {
                    initialValues.put(adPoId, initialValues.get(adPoId) + counter.sum());
                    counter.reset();
                }
            });
        }
    }
}
