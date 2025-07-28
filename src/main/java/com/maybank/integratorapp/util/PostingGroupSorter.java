package com.maybank.integratorapp.util;

import com.maybank.integratorapp.component.coresystem.ProcessCompositeTBR;

import java.util.List;
import java.util.Comparator;
import java.util.Collections;

public class PostingGroupSorter {

    public static void sortPostingGroups(List<ProcessCompositeTBR.PostingGroup> postingGroups) {
        // Create a comparator that checks if the PostingGroup has the desired Posting
        Comparator<ProcessCompositeTBR.PostingGroup> comparator = (pg1, pg2) -> {
            boolean pg1HasCondition = hasPostingWithCondition(pg1);
            boolean pg2HasCondition = hasPostingWithCondition(pg2);

            if (pg1HasCondition && !pg2HasCondition) {
                return -1; // pg1 comes first
            } else if (!pg1HasCondition && pg2HasCondition) {
                return 1;  // pg2 comes first
            } else {
                return 0;  // maintain original order
            }
        };

        // Sort the list using the comparator
        Collections.sort(postingGroups, comparator);
    }

    private static boolean hasPostingWithCondition(ProcessCompositeTBR.PostingGroup pg) {
        if (pg == null || pg.getPostings() == null) {
            return false;
        }

        return pg.getPostings().stream()
                .anyMatch(posting ->
                        "CCA".equals(posting.getAccountType())
                                && "C".equals(posting.getDebitCreditFlag())
                );
    }
}
