package example.day07;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class StudentDto {
    private int sno;
    private String sname;
    private int skor;
    private int seng;
    private int smath;
    private String sdate;
}
