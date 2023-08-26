package domain;

import lombok.Data;

@Data
public class Answer {
    int rid;
    String account;
    String rpath;
    String rcontent;
    String rtime;
    int mid;
    int rrank;
    User user;
    String status;
    int isevaluated=0;
}
