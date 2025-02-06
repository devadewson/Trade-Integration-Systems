package com.maybank.integratorapp.model.restv2.XLBT.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class LoanAccountListResponseData {
    @JsonProperty("LoanAccount")

    private List<LoanAccount> loanAccounts;

    public List<LoanAccount> getLoanAccounts() {
        return loanAccounts;
    }

    public void setLoanAccounts(List<LoanAccount> loanAccounts) {
        this.loanAccounts = loanAccounts;
    }
}
