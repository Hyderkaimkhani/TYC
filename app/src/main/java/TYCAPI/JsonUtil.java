package TYCAPI;

import com.fasterxml.jackson.databind.*;

class JsonUtil {
  private static final ObjectMapper mapper;

  static {
    mapper = new ObjectMapper();
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
  }

  public static ObjectMapper getJsonMapper() {
    return mapper;
  }
}

