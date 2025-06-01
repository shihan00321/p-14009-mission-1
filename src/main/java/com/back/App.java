package com.back;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {
    private final Scanner scanner = new Scanner(System.in);
    private final List<WiseSaying> wiseSayingList = new ArrayList<>();
    private int lastId = 1;

    public void run() {
        System.out.println("== 명언 앱 ==");
        while (true) {
            System.out.print("명령) ");
            String cmd = scanner.nextLine().trim();
            switch (CommandManager.getCommand(cmd)) {
                case "종료" -> {
                    scanner.close();
                    return;
                }
                case "등록" -> registerWiseSaying();
                case "목록" -> showList();
                case "삭제" -> deleteWiseSaying(cmd);
                case "수정" -> updateWiseSaying(cmd);
                default -> System.out.println("등록되지 않은 명령어입니다.");
            }
        }
    }

    private void showList() {
        System.out.println("번호 / 작가 / 명언");
        System.out.println("----------------------");
        for (int i = wiseSayingList.size() - 1; i >= 0; i--) {
            System.out.println(wiseSayingList.get(i));
        }
    }

    private void registerWiseSaying() {
        System.out.print("명언 : ");
        String content = scanner.nextLine().trim();
        System.out.print("작가 : ");
        String author = scanner.nextLine().trim();
        wiseSayingList.add(new WiseSaying(lastId, content, author));
        System.out.println(lastId++ + "번 명언이 등록되었습니다.");
    }

    private void deleteWiseSaying(String cmd) {
        try {
            int removeId = Integer.parseInt(cmd.substring(6));
            WiseSaying targetWiseSaying = findWiseSayingById(removeId);
            if (targetWiseSaying != null) {
                wiseSayingList.remove(targetWiseSaying);
                System.out.printf("%d번 명언이 삭제되었습니다.%n", removeId);
            } else {
                System.out.printf("%d번 명언은 존재하지 않습니다.%n", removeId);
            }
        } catch (RuntimeException e) {
            System.out.println("올바른 형식이 아닙니다. (예: 삭제?id=1)");
        }
    }

    private void updateWiseSaying(String cmd) {
        try {
            int updateId = Integer.parseInt(cmd.substring(6));
            WiseSaying targetWiseSaying = findWiseSayingById(updateId);

            if (targetWiseSaying != null) {
                System.out.printf("명언(기존) : %s\n", targetWiseSaying.getContent());
                System.out.print("명언 : ");
                String newContent = scanner.nextLine().trim();

                System.out.printf("작가(기존) : %s\n", targetWiseSaying.getAuthor());
                System.out.print("작가 : ");
                String newAuthor = scanner.nextLine().trim();

                targetWiseSaying.update(newContent, newAuthor);
            } else {
                System.out.printf("%d번 명언은 존재하지 않습니다.\n", updateId);
            }
        } catch (RuntimeException e) {
            System.out.println("올바른 형식이 아닙니다. (예: 수정?id=1)");
        }
    }

    private WiseSaying findWiseSayingById(int id) {
        for (WiseSaying wiseSaying : wiseSayingList) {
            if (wiseSaying.getId() == id) {
                return wiseSaying;
            }
        }
        return null;
    }
}
