package domain;

import lombok.Data;

import java.util.List;

@Data
public class Detailedquestion {
    private int mid;
    private List<Answer> list;
    private Answer myanswer;
    private String address;
}
