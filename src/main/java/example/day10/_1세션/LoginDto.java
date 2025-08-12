package example.day10._1세션;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class LoginDto {
    private int mno;
    private String mid;
    private String mpw;
}
