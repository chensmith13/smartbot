package domain;
import lombok.Data;
import java.util.List;

@Data
public class Pagebean {
    private int currentpage;
    private int totalpage;
    private int totalcount;
    private int row;
    private List list;
}
