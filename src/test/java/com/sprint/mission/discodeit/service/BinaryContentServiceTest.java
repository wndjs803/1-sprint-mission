package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.common.MultipartFileConverter;
import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.repository.BinaryContentRepository;
import com.sprint.mission.discodeit.repository.jcf.JCFBinaryContentRepository;
import com.sprint.mission.discodeit.service.basic.BasicBinaryContentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class BinaryContentServiceTest {

    private BinaryContentRepository binaryContentRepository;
    private MultipartFileConverter multipartFileConverter;
    private BinaryContentService binaryContentService;
    @BeforeEach
    void setUp() {
        binaryContentRepository = new JCFBinaryContentRepository();
        multipartFileConverter = new MultipartFileConverter();
        binaryContentService = new BasicBinaryContentService(binaryContentRepository, multipartFileConverter);
    }

    private MultipartFile createMulipartFile() {
        return  new MockMultipartFile(
                "file",                          // 필드명 (폼 필드 이름)
                "test.txt",                      // 파일명
                "text/plain",                    // MIME 타입
                "Hello, World!".getBytes()       // 파일 내용 (바이트 배열)
        );
    }

    @Nested
    @DisplayName("BinaryContent 생성 테스트")
    class CreateBinaryContentTest {

        @Test
        @DisplayName("BinaryContent 생성 성공 by MultipartFile")
        void successByMultipartFile() {
            // given
            MultipartFile multipartFile = createMulipartFile();

            // when
            BinaryContent binaryContent = binaryContentService.createBinaryContent(multipartFile);

            // then
            byte[] multipartFileBytes = null;
            try {
                multipartFileBytes = multipartFile.getBytes();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            byte[] content = binaryContent.getContent();

            assertEquals(multipartFileBytes, content);
        }

        @Test
        @DisplayName("BinaryContent 생성 성공 by BinaryContent")
        void successByBinaryContent() {
            // given
            MultipartFile multipartFile = createMulipartFile();
            byte[] bytes = multipartFileConverter.toByteArray(multipartFile);

            // when
            BinaryContent binaryContent = binaryContentService.createBinaryContent(BinaryContent.of(bytes));

            // then
            byte[] multipartFileBytes = null;
            try {
                multipartFileBytes = multipartFile.getBytes();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            byte[] content = binaryContent.getContent();

            assertEquals(multipartFileBytes, content);
        }
    }
}