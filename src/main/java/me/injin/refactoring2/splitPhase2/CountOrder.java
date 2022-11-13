package me.injin.refactoring2.splitPhase2;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;

/**
 * 매번 main 에서 실행되어 테스트하기 힘들다.
 * 1.main 하위 로직 > try 실행로직을 메소드 추출한다.
 * 2.결과값을 sout 으로 보고있는데 이를 main 에서 볼 수 있도록 long 으로 리턴값을 변경했다.
 * 3.단계를 나뉘어 메소드 2번째 단계를 추출한다.
 * 4.중간단계 구조체를 만들어주자.
 * 5.decompose conditional 기법으로 countOrder 의 분기조건을 메소드, 또는 변수로 뽑아내자
 *      5.1 뽑아낸 변수는 파라미터로 올린다.
 *      5.2 올린 파라미터를 변수로 추출하여(onlyCountReady) 중간단계 구조체 한다 넘긴다.
 *      5.3 fileName 도 구조체로 넣어준다.
 * 6.정리가 다 되었다면 첫번째 단계를 메소드 추출한다.(parseCommandLine)
 */
public class CountOrder {
    private record Order(String status) {}

    public static void main(String[] args) {
        try {
            System.out.println(run(args));
        } catch (Exception e) {
            System.out.println(e);
            System.exit(1);
        }
    }

    record CommandLine(boolean onlyCountReady, String filename){}

    private static long run(String[] args) throws IOException {
        CommandLine commandLine = parseCommandLine(args);
        return countOrder(commandLine);
    }

    private static CommandLine parseCommandLine(String[] args) {
        if(args.length == 0)
            throw new RuntimeException("파일명을 입력하세요");
        String filename = args[args.length - 1];
        boolean onlyCountReady = Arrays.asList(args).contains("-r");
        return new CommandLine(onlyCountReady, filename);
    }

    private static long countOrder(CommandLine commandLine) throws IOException {
        File input = Paths.get(commandLine.filename()).toFile();
        ObjectMapper mapper = new ObjectMapper();
        Order[] orders = mapper.readValue(input, Order[].class);
        if (commandLine.onlyCountReady()) {
            return Arrays.stream(orders)
                    .filter(o -> "ready".equals(o.status()))
                    .count();
        } else {
            return orders.length;
        }
    }
}
