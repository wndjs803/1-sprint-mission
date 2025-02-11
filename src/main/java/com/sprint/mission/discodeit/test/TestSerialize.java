package com.sprint.mission.discodeit.test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class TestSerialize {
    public static void init(Path directory) {
        // 저장할 경로의 파일 초기화
        if (!Files.exists(directory)) {
            try {
                Files.createDirectories(directory);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static <T> void save(Path filePath, T data) {
        try (
                FileOutputStream fos = new FileOutputStream(filePath.toFile());
                ObjectOutputStream oos = new ObjectOutputStream(fos);
        ) {
            oos.writeObject(data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> List<T> load(Path directory) {
        if (Files.exists(directory)) {
            try {
                List<T> list = Files.list(directory)
                        .map(path -> {
                            try (
                                    FileInputStream fis = new FileInputStream(path.toFile());
                                    ObjectInputStream ois = new ObjectInputStream(fis)
                            ) {
                                Object data = ois.readObject();
                                return (T) data;
                            } catch (IOException | ClassNotFoundException e) {
                                throw new RuntimeException(e);
                            }
                        })
                        .toList();
                return list;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            return new ArrayList<>();
        }
    }

    public static void main(String[] args) {
        // 객체 파일을 저장할 디렉토리
        Path directory = Paths.get(System.getProperty("user.dir"), "data");
        // 초기화
        init(directory);
        // 데이터 추가
        List.of(
                new Account("woody", "woody1234"),
                new Account("buzz", "buzz5678"),
                new Account("jessie", "jessie9012")
        ).forEach(account -> {
            Path filePath = directory.resolve(account.getId().concat(".ser"));
            save(filePath, account);
        });

        // 데이터 조회
        load(directory)
                .forEach(data -> System.out.println(data));
    }
}