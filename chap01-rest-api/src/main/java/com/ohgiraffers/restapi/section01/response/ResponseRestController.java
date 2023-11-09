package com.ohgiraffers.restapi.section01.response;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController //1. bean Scan을 한다. 2. @RequestMapping과 관련된 어노테이션을 쓸 수 있다. 3. @ResponseBody를 붙인 것과 같다(반환 값이 곧 응답 값이다.)
@RequestMapping("/response")
public class ResponseRestController {

    /* 1. 문자열 응답 */
    @GetMapping("/hello")
    public String helloWorld() { //일반 Controller이었으면

        return "Hello World";

    }

    /* 2. 기본 자료형 응답 */
    @GetMapping("/random")
    public int getRandomNumebr() {

        return (int)(Math.random() * 10) + 1;

    }

    //1,2 => @RestController이기 때문에 @ResponseBody를 굳이 작성하지 않아도 반환하는 값이 응답 값이되고있다.

    /* 3. Object 응답 */
    @GetMapping("/message")
    public Message getMessage() {

        return new Message(200, "메시지를 응답합니다.");

    }

    /* 4. List 응답 */
    @GetMapping("/list")
    public List<String> getList() {

        return List.of(new String[]{ "사과", "바나나", "복숭아" });

    }

    /* 5. Map 응답 */
    @GetMapping("/map")
    public Map<Integer, String> getMap() {

        List<Message> messageList = new ArrayList<>();
        messageList.add(new Message(200, "정상 응답"));
        messageList.add(new Message(404, "페이지를 찾을 수 없습니다."));
        messageList.add(new Message(500, "개발자의 잘못입니다."));

        return messageList.stream().collect(Collectors.toMap(Message::getHttpStatusCode, Message::getMessage));
        //collect(Collectors)을 작성하면 앞의 messageList.stream()이 Map 형태로 변경이 된다.     //여기는 key, value

    }

    /* 6. ImageFile 응답 */
    //이미지는 json으로 파싱되면 안된다.
    @GetMapping(value = "/image", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] getImage() throws IOException { //byte 값이 응답으로 나타난다.(이미지를 가짐)

        return getClass().getResourceAsStream("/images/toystory.jpg").readAllBytes();
        //getClass는 /images/toystory.png 경로로부터 getResourceAsStream를 꺼내오고 Bytes 데이터로 읽어오겠다.

    }

    /* 7. ResponseEntity 응답 */
    @GetMapping("/entity")
    public ResponseEntity<Message> getEntity() {

        return ResponseEntity.ok(new Message(123, "hello world!"));
        //ok StatusCode가 200이고 그것을 123으로 교체하겠다.

    }

}
