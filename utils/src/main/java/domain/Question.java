package domain;

import lombok.Data;

import java.util.List;

@Data
public class Question {
    private String mcontent="暂无";
    private String answer;
    int hot;
    String mtime;
    private int mid;
    private String account;
    private int isreply;
    private String status;
    int ansnum;
    private List<Answer> answers;
}
