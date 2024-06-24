package com.project.api.exception;

public class UnsupportedMediaTypeException extends RuntimeException {

    public UnsupportedMediaTypeException() {
        super("지원하지 않은 미디어 타입입니다.");
    }

    public UnsupportedMediaTypeException(String... types) {
        super(String.format("지원하지 않은 미디어 타입입니다. (%s 확장자만 가능합니다.)", UnsupportedMediaTypeException.messageTypes(types)));
    }

    public UnsupportedMediaTypeException(String message) {
        super(message);
    }

    private static String messageTypes(String... types) {

        if (types == null) {
            return "";
        }

        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < types.length; i++) {
            sb.append(types[i]);
            if (i != types.length - 1)
                sb.append(", ");
        }
        return sb.toString();
    }
}
