package com.sprint.mission.discodeit.common;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public class MultipartFileConverter {
    public byte[] toByteArray(MultipartFile multipartFile) {
        try {
            return multipartFile.getBytes();
        } catch (IOException e) {
            throw new RuntimeException(ErrorMessage.FILE_CONVERSION_FAIL.format(e.getMessage()));
        }
    }
}
