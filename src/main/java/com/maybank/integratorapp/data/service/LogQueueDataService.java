package com.maybank.integratorapp.data.service;

import com.maybank.integratorapp.controller.DashboardController;
import com.maybank.integratorapp.data.entity.FtiTransaction;
import com.maybank.integratorapp.data.entity.LogQueueData;
import com.maybank.integratorapp.data.repository.LogQueueDataRepository;
import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class LogQueueDataService {
    @Autowired
    private LogQueueDataRepository repo;
    public Page<LogQueueData> getAllWithSearch(Pageable pageable,
                                               Optional<String> correlationId,
                                               Optional<String> transref,
                                               Optional<String> queueOrigin){
        Specification<LogQueueData> spec = (root, query, cb) ->
        {
            List<Predicate> predicates = new ArrayList<>();

            if (correlationId.isPresent() && !correlationId.get().isEmpty()) {
                predicates.add(cb.like(
                        cb.lower(root.get("correlationID")),
                        "%" + correlationId.get().toLowerCase() + "%"
                ));
            }

//            if (transref.isPresent() && !transref.get().isEmpty()) {
//                predicates.add(cb.like(
//                        cb.lower(root.get("relatedTransRef")),
//                        "%" + transref.get().toLowerCase() + "%"
//                ));
//            }

//            if (transref.isPresent() && !transref.get().isEmpty()) {
//                Predicate reqMessagePredicate = cb.like(
//                        root.get("reqMessage"),
//                        "%" + transref.get().toLowerCase() + "%"
//                );
//                Predicate resMessagePredicate = cb.like(
//                        root.get("resMessage"),
//                        "%" + transref.get().toLowerCase() + "%"
//                );
//                predicates.add(cb.or(reqMessagePredicate, resMessagePredicate));
//            }

            if  (queueOrigin.isPresent() && !queueOrigin.get().isEmpty()) {
                predicates.add(cb.like(
                        cb.lower(root.get("origin")),
                        "%" + queueOrigin.get().toLowerCase() + "%"
                ));
            }

            return predicates.isEmpty()
                    ? cb.conjunction()  // no filter if both null/empty
                    : cb.and(predicates.toArray(new Predicate[0]));
        };

        if ((correlationId.isPresent() && !correlationId.get().isEmpty())
                || (queueOrigin.isPresent() && !queueOrigin.get().isEmpty())) {
            // Use your Specification filter
            return repo.findAll(spec, pageable);
        } else if (transref.isPresent()) {
            // Use native query (optimized for TEXT search)
            return repo.findByTransactionIdContainingIgnoreCase(transref.get(), pageable);
        } else {
            // No filters at all — get all paginated
            return repo.findAll(pageable);
        }

//        Page<LogQueueData> result = repo.findAll(spec, pageable);

//        return result;
    }
    public Page<LogQueueData> getAll(Pageable pageable) {
        return repo.findAll(pageable);
    }
    public Page<LogQueueData> searchByCorrelationId(String correlationId, Pageable pageable) {
        return repo.findByCorrelationIdContainingIgnoreCase(correlationId, pageable);
    }
    public Page<LogQueueData> searchByTransactionId(String transref, Pageable pageable) {
        return repo.findByTransactionIdContainingIgnoreCase(transref, pageable);
    }
    public List<LogQueueData> searchByTransactionId(String transref) {
        return repo.findByTransactionIdContainingIgnoreCase(transref);
    }
    public LogQueueData findByCorrelationId(String correlationId){
        return repo.findByCorrelationId(correlationId);
    }

    public Optional<LogQueueData> findById(Long id){
        return repo.findById(id);
    }

    public Map<String, Object> getHourlyMessageCountsByOrigin(String startDate, String endDate) {
        List<Object[]> rawData = repo.countMessagesByOriginAndHour(startDate, endDate);

        // Get all unique origins and sort them for consistent ordering
        Set<String> origins = rawData.stream()
                .map(row -> (String) row[0])
                .collect(Collectors.toCollection(TreeSet::new));

        // Initialize a map for each origin's hourly counts
        Map<String, int[]> hourlyCountsByOrigin = new LinkedHashMap<>();
        origins.forEach(origin -> hourlyCountsByOrigin.put(origin, new int[24]));

        // Populate the counts
        for (Object[] row : rawData) {
            String origin = (String) row[0];
            int hour = (int) row[1];
            long count = ((Number) row[2]).longValue();
            hourlyCountsByOrigin.get(origin)[hour] = (int) count;
        }

        // Prepare the result for the view
        Map<String, Object> result = new HashMap<>();
        result.put("hourlyCountsByOrigin", hourlyCountsByOrigin);
        result.put("labels", IntStream.range(0, 24).mapToObj(i -> i + ":00").collect(Collectors.toList()));
        result.put("origins", new ArrayList<>(origins)); // Add origins list

        return result;
    }

    public Page<LogQueueData> searchByQueueOrigin(String queueOrigin, Pageable pageable) {
        return repo.findByQueueOriginContainingIgnoreCase(queueOrigin, pageable);
    }

    public List<DashboardController.QueueStats> getTodayQueueStats() {
        List<Object[]> results = repo.getTodayQueueStats();
        List<DashboardController.QueueStats> queueStatsList = new ArrayList<>();

        for (Object[] result : results) {
            DashboardController.QueueStats stats = new DashboardController.QueueStats();
            stats.setQueueName((String) result[0]);
            stats.setTotalMessages(((Number) result[1]).longValue());
            stats.setAvgProcessingSeconds(result[2] != null ? ((Number) result[2]).longValue() : 0L);
            stats.setMaxProcessingSeconds(result[3] != null ? ((Number) result[3]).longValue() : 0L);
            stats.setMinProcessingSeconds(result[4] != null ? ((Number) result[4]).longValue() : 0L);
            queueStatsList.add(stats);
        }

        return queueStatsList;
//        return repo.getTodayQueueStats();
    }
}
