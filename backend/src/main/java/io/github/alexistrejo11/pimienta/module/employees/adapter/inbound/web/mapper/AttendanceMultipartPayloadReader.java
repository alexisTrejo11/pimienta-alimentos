package io.github.alexistrejo11.pimienta.module.employees.adapter.inbound.web.mapper;

import io.github.alexistrejo11.pimienta.module.employees.adapter.inbound.web.dto.request.EndWorkdayRequest;
import io.github.alexistrejo11.pimienta.module.employees.adapter.inbound.web.dto.request.StartWorkdayRequest;
import java.io.IOException;
import java.lang.reflect.Method;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import tools.jackson.databind.ObjectMapper;

/**
 * Parses multipart {@code attendance} parts as UTF-8 JSON (regardless of declared part content type)
 * and validates them like JSON request bodies.
 */
@Component
public final class AttendanceMultipartPayloadReader {

  private static final Method READ_START;
  private static final Method READ_END;
  private static final MethodParameter START_ATTENDANCE_PART;
  private static final MethodParameter END_ATTENDANCE_PART;

  static {
    try {
      READ_START =
          AttendanceMultipartPayloadReader.class.getDeclaredMethod("readStartWorkday", MultipartFile.class);
      READ_END =
          AttendanceMultipartPayloadReader.class.getDeclaredMethod("readEndWorkday", MultipartFile.class);
      START_ATTENDANCE_PART = new MethodParameter(READ_START, 0);
      END_ATTENDANCE_PART = new MethodParameter(READ_END, 0);
    } catch (NoSuchMethodException e) {
      throw new IllegalStateException(e);
    }
  }

  private final ObjectMapper objectMapper;
  private final Validator mvcValidator;

  public AttendanceMultipartPayloadReader(ObjectMapper objectMapper, Validator mvcValidator) {
    this.objectMapper = objectMapper;
    this.mvcValidator = mvcValidator;
  }

  public StartWorkdayRequest readStartWorkday(MultipartFile attendancePayload)
      throws MethodArgumentNotValidException {
    if (attendancePayload.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "attendance multipart part is empty");
    }
    StartWorkdayRequest parsed;
    try {
      parsed = objectMapper.readValue(attendancePayload.getInputStream(), StartWorkdayRequest.class);
    } catch (IOException e) {
      throw new HttpMessageNotReadableException("Malformed attendance JSON part.", e, null);
    }
    if (parsed == null) {
      throw new HttpMessageNotReadableException(
          "Attendance JSON part was empty or literal null.",
          new IllegalArgumentException("null attendance JSON"),
          null);
    }
    BeanPropertyBindingResult validation =
        new BeanPropertyBindingResult(parsed, "attendance", true, 100);
    mvcValidator.validate(parsed, validation);
    if (validation.hasErrors()) {
      throw new MethodArgumentNotValidException(START_ATTENDANCE_PART, validation);
    }
    return parsed;
  }

  public EndWorkdayRequest readEndWorkday(MultipartFile attendancePayload)
      throws MethodArgumentNotValidException {
    if (attendancePayload.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "attendance multipart part is empty");
    }
    EndWorkdayRequest parsed;
    try {
      parsed = objectMapper.readValue(attendancePayload.getInputStream(), EndWorkdayRequest.class);
    } catch (IOException e) {
      throw new HttpMessageNotReadableException("Malformed attendance JSON part.", e, null);
    }
    if (parsed == null) {
      throw new HttpMessageNotReadableException(
          "Attendance JSON part was empty or literal null.",
          new IllegalArgumentException("null attendance JSON"),
          null);
    }
    BeanPropertyBindingResult validation =
        new BeanPropertyBindingResult(parsed, "attendance", true, 100);
    mvcValidator.validate(parsed, validation);
    if (validation.hasErrors()) {
      throw new MethodArgumentNotValidException(END_ATTENDANCE_PART, validation);
    }
    return parsed;
  }
}
