package 종합.예제12.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import 종합.예제12.service.BoardService;

@RestController
public class BoardController {
    @Autowired private BoardService boardService;
}
