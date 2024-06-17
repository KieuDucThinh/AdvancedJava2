package com.ltc.btl_javafx.model;

public class AccountLogin {
    private String userID;
    private String password;
    private String accountname;
    private int rank;

    public AccountLogin() {
    }

    public AccountLogin(String userID, String password, String accountname, int rank) {
        this.userID = userID;
        this.password = password;
        this.accountname = accountname;
        this.rank = rank;
    }

    public String getUserID() {
        return this.userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAccountname() {
        return this.accountname;
    }

    public void setAccountname(String accountname) {
        this.accountname = accountname;
    }

    public int getRank() {
        return this.rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String checkRank(int check_rank) {
        if (check_rank == 0) {
            return "Gói miễn phí";
        } else if (check_rank == 1) {
            return "Gói cơ bản 1";
        } else if (check_rank == 2) {
            return "Gói cơ bản 2";
        } else if (check_rank == 3) {
            return "Gói cao cấp 1";
        } else {
            return check_rank == 4 ? "Gói cao cấp 2" : "Gói kim cương";
        }
    }
}
